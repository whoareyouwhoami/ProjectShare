package com.project.share.controller;

import com.project.share.exception.ProjectException;
import com.project.share.model.Project;
import com.project.share.model.User;
import com.project.share.service.KafkaService;
import com.project.share.service.ProjectService;
import com.project.share.service.UserService;
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
import java.util.HashSet;
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
    private KafkaService kafkaService;

    @GetMapping(path = {"", "/"})
    public ModelAndView projectHome() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("project/projectHome");

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

        // Success -> Set user
        Set<User> userSet = new HashSet<>();
        User u = userService.getUserByEmail(principal.getName());
        userSet.add(u);
        project.setOwner(u);
        project.setUser(userSet);

        // Save project
        Project savedProject = projectService.saveProject(project);
        rm.addFlashAttribute("proj_detail", savedProject);
        mv.setViewName("redirect:/project/view/" + project.getId());

        savedProject.setOwnerName(savedProject.getOwner().getFirstName() + " " + savedProject.getOwner().getLastName());

        // kafkaService.send("topicES", savedProject);
        kafkaService.send("topicRediSearch", project);

        log.info("SUCCESS UPLOAD");
        return mv;
    }

    @GetMapping("/view")
    public ModelAndView projectView(Principal principal) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("project/projectHome");

        User user = userService.getUserByEmail(principal.getName());
        List<Project> projectList = projectService.getAllUserProject(user);

        mv.addObject("projectList", projectList);
        return mv;
    }

    @GetMapping("/view/{pid}")
    public ModelAndView projectViewDetail(@PathVariable("pid") int pid, Model model) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("project/projectView");

        Project project = null;
        try {
            project = (Project) model.getAttribute("proj_detail");
            System.out.println(project.getTitle());
        } catch(NullPointerException e) {
            log.info("Retrieving project detail");
            try {
                project = projectService.getProject(pid);
                System.out.println(project.getTitle());
            } catch (ProjectException pe) {
                log.warn("Invalid project ID");
                // return mv;
            }
        }

        if(project != null) {
            mv.addObject("p_detail", project);
        }
        return mv;
    }

    @GetMapping("/update/{pid}")
    public ModelAndView projectUpdate(@PathVariable("pid") int pid, @ModelAttribute("ProjectForm") Project project, Principal principal) {
        try {
            Project p = projectService.getProject(pid);
            if(p.getOwner().getEmail().equals(principal.getName()))
                return new ModelAndView("project/projectUpload", "ProjectForm", p);
        } catch(Exception e) {
            e.printStackTrace();
        }

        log.info("FAIL UPDATE - Invalid user");
        return new ModelAndView("project/projectView");
    }

    @PostMapping("/update/{pid}")
    public ModelAndView projectUpdateProcess(@PathVariable("pid") int pid, @ModelAttribute("ProjectForm") @Valid Project project, BindingResult bindingResult, Principal principal, RedirectAttributes rm) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("project/projectUpload");

        // Validate owner
        Project projectPrev = projectService.getProject(pid);
        if(!projectPrev.getOwner().getEmail().equals(principal.getName())) {
            log.info("FAIL UPDATE - Invalid user");
            return new ModelAndView("project/projectView");
        }

        // Check error
        if (bindingResult.hasErrors()) {
            log.error("Project upload binding form error");
            mv.addObject("ProjectForm", project);
            return mv;
        }

        // Update project
        Project savedProject = projectService.updateProject(projectPrev, project);
        if(savedProject == null) {
            log.error("New starting date cannot be before original starting date");
            mv.addObject("ProjectForm", project);
            return mv;
        }

        // Redirect page
        mv.setViewName("redirect:/project/view/" + savedProject.getId());
        rm.addFlashAttribute("proj_detail", savedProject);

        log.info("SUCCESS UPDATE");
        return mv;
    }

    @GetMapping("/search")
    public String projectSearch() {

        return "project/projectSearch";
    }

    /*
     * Add - ALERT BEFORE DELETION
     */
    @GetMapping("/delete/{pid}")
    public String projectDelete(@PathVariable("pid") int pid, Principal principal) {
        // Validate owner
        Project project = projectService.getProject(pid);
        if(!project.getOwner().getEmail().equals(principal.getName())) {
            log.info("FAIL DELETE - Invalid user");
            return "project/projectView";
        }
        log.info("SUCCESS DELETE");
        projectService.removeProject(pid);
        return "main/home";
    }

}
