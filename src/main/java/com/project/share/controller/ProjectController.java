 package com.project.share.controller;

 import com.project.share.model.MessageProject;
 import com.project.share.model.Project;
 import com.project.share.model.ProjectES;
 import com.project.share.model.User;
 import com.project.share.service.*;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.validation.BindingResult;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.servlet.ModelAndView;
 import org.springframework.web.servlet.mvc.support.RedirectAttributes;

 import javax.validation.Valid;
 import java.security.Principal;
 import java.time.LocalDateTime;
 import java.util.List;
 import java.util.Set;

 @Controller
 @RequestMapping("/project")
 public class ProjectController {
     static final Logger log = LoggerFactory.getLogger(ProjectController.class);

     @Autowired
     private ProjectService projectService;

     @Autowired
     private UserService userService;

     @Autowired
     private MessageProjectService messageProjectService;

     @Autowired
     private ESService esService;

     @GetMapping(path = {"", "/"})
     public ModelAndView projectHome() {
         ModelAndView mv = new ModelAndView();
         mv.setViewName("project/projectHome");

         /* ADD PAGING LATER */

         List<Project> projectList = projectService.getAllProject();
         mv.addObject("projectList", projectList);
         return mv;
     }

     @GetMapping("/upload")
     public ModelAndView projectUpload() {
         return new ModelAndView("project/projectUpload", "ProjectForm", new Project());
     }

     @PostMapping("/upload")
     public ModelAndView projectUploadProcess(@ModelAttribute("ProjectForm") @Valid Project project, BindingResult bindingResult, Principal principal, RedirectAttributes rm) {
         ModelAndView mv = new ModelAndView();
         mv.setViewName("project/projectUpload");

         if (bindingResult.hasErrors()) {
             log.error("Project upload binding form error");
             mv.addObject("ProjectForm", project);
             return mv;
         }

         /* SET AUTHOR OF THE PROJECT */
         User currentUser = userService.getUserByEmail(principal.getName());
         project.setAuthor(currentUser);

         /* SAVE PROJECT */
         Project savedProject = projectService.saveProject(project);
         rm.addFlashAttribute("proj_detail", savedProject);
         mv.setViewName("redirect:/project/view/" + project.getId());

         /* SAVING PROJECT DETAILS IN ELASTICSEARCH */
         ProjectES projectES = new ProjectES(
                 savedProject.getId(),
                 savedProject.getTitle(),
                 savedProject.getDescription(),
                 savedProject.getMember(),
                 savedProject.getDateStart(),
                 savedProject.getDateEnd(),
                 LocalDateTime.now(),
                 savedProject.getAuthor().getFirstName());
         esService.saveProjectToES(projectES);

         /* CREATES MESSAGE GROUP FOR PROJECT */
         MessageProject messageProject = messageProjectService.createMessageProject(savedProject);
         messageProjectService.addMessageProjectUser(currentUser, messageProject);

         log.info("SUCCESS UPLOAD");
         return mv;
     }

     @GetMapping("/view")
     public ModelAndView projectView(Principal principal) {
         /* GET ALL PROJECTS UPLOADED BY THE USER */
         User currentUser = userService.getUserByEmail(principal.getName());
         Set<Project> projectSet = currentUser.getProjectSet();

         ModelAndView mv = new ModelAndView();
         mv.setViewName("project/projectHome");
         mv.addObject("projectList", projectSet);
         return mv;
     }

     @GetMapping("/view/{pid}")
     public ModelAndView projectViewDetail(@PathVariable("pid") int pid, Model model, Principal principal) {
         ModelAndView mv = new ModelAndView();
         mv.setViewName("project/projectView");

         /* SHOW PROJECT INFORMATION WHETHER IT HAS BEEN REDIRECTED OR ACCESSED BY CLICKING */
         Project project = (Project) model.getAttribute("proj_detail");
         if(project == null) {
             log.info("Retrieving project detail");
             project = projectService.getProject(pid);
         }

         mv.addObject("p_detail", project); // MAY GIVE EMPTY RESULT BACK
         return mv;
     }

     @GetMapping("/update/{pid}")
     public ModelAndView projectUpdate(@PathVariable("pid") int pid, Principal principal) {
         Project project = projectService.getProject(pid);

         /* CHECK IF PROJECT EXIST */
         if(project == null)
             return new ModelAndView("project/projectView");

         /* CHECK IF THE PROJECT BELONGS TO USER */
         if(project.getAuthor().getEmail().equals(principal.getName()))
             return new ModelAndView("project/projectUpload", "ProjectForm", project);

         log.info("FAIL UPDATE - Invalid user");
         return new ModelAndView("project/projectView");
     }

     @PostMapping("/update/{pid}")
     public ModelAndView projectUpdateProcess(@PathVariable("pid") int pid, @ModelAttribute("ProjectForm") @Valid Project project, BindingResult bindingResult, Principal principal, RedirectAttributes rm) {
         ModelAndView mv = new ModelAndView();
         mv.setViewName("project/projectUpload");

         Project projectPrev = projectService.getProject(pid);

         /* VALIDATE AUTHOR OF THE PROJECT AND CURRENT USER */
         if(!projectPrev.getAuthor().getEmail().equals(principal.getName())) {
             log.info("FAIL UPDATE - Invalid user");
             return new ModelAndView("project/projectView");
         }

         /* CHECK ERRORS */
         if (bindingResult.hasErrors()) {
             log.error("Project upload binding form error");
             mv.addObject("ProjectForm", project);
             return mv;
         }

         /* UPDATE PROJECT */
         Project savedProject = projectService.updateProject(projectPrev, project);
         if(savedProject == null) {
             log.error("New starting date cannot be before original starting date");
             mv.addObject("ProjectForm", project);
             return mv;
         }

         /* UPDATING PROJECT DETAILS IN ELASTICSEARCH */
         ProjectES projectES = new ProjectES(
                 savedProject.getId(),
                 savedProject.getTitle(),
                 savedProject.getDescription(),
                 savedProject.getMember(),
                 savedProject.getDateStart(),
                 savedProject.getDateEnd(),
                 LocalDateTime.now(),
                 savedProject.getAuthor().getFirstName());
         esService.saveProjectToES(projectES);

         /* REDIRECTING TO VIEW PAGE */
         mv.setViewName("redirect:/project/view/" + savedProject.getId());
         rm.addFlashAttribute("proj_detail", savedProject);

         log.info("SUCCESS UPDATE");
         return mv;
     }

     @GetMapping("/search")
     public ModelAndView projectSearch(@RequestParam(name="q", required = false) String query) {
         ModelAndView mv = new ModelAndView();
         mv.setViewName("project/projectSearch");

         if(query != null) {
             query = query.strip();
             List<ProjectES> result = esService.getSearchQuery(query);
             if(result.size() == 0) {
                 mv.addObject("emptyResult", 0);
                 return mv;
             }
             mv.addObject("searchResult", result);
         }
         return mv;
     }

     @GetMapping("/delete/{pid}")
     public String projectDelete(@PathVariable("pid") int pid, Principal principal) {
         /* VALIDATE PROJECT AUTHOR */
         Project project = projectService.getProject(pid);
         if(!project.getAuthor().getEmail().equals(principal.getName()))
             return "redirect:/project/view/" + pid;

        /* DELETE PROJECT FROM ELASTICSEARCH */
         esService.removeProject(pid);

         /* DELETE PROJECT FROM DB */
         projectService.removeProject(pid);

         return "redirect:/project";
     }
 }
