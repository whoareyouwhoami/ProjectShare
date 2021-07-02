package com.project.share.controller;

import com.project.share.exception.ProjectException;
import com.project.share.model.MessageProject;
import com.project.share.model.Project;
import com.project.share.model.User;
import com.project.share.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.Map;
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
    private RedisService redisService;

    @Autowired
    private MessageProjectService messageProjectService;

    @Autowired
    private MessageChatService messageChatService;

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
        User user = userService.getUserByEmail(principal.getName());
        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        project.setOwner(user);
        project.setUser(userSet);

        // Save project
        Project savedProject = projectService.saveProject(project);
        rm.addFlashAttribute("proj_detail", savedProject);
        mv.setViewName("redirect:/project/view/" + project.getId());

        savedProject.setOwnerName(savedProject.getOwner().getFirstName() + " " + savedProject.getOwner().getLastName());

        /* CREATES MESSAGE GROUP FOR PROJECT */
        MessageProject messageProject = messageProjectService.createMessageProject(savedProject);
        messageProjectService.addMessageProjectUser(user, messageProject);

//        kafkaService.send("topicRediSearch", project);

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
    public ModelAndView projectViewDetail(@PathVariable("pid") int pid, Model model, Principal principal) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("project/projectView");

        Project project = (Project) model.getAttribute("proj_detail");
        if(project == null) {
            log.info("Retrieving project detail");
            project = projectService.getProject(pid);
        }
        mv.addObject("p_detail", project);
        return mv;
    }

    @GetMapping("/update/{pid}")
    public ModelAndView projectUpdate(@PathVariable("pid") int pid, Principal principal) {
        Project project = projectService.getProject(pid);
        if(project == null) // Check if project exist
            return new ModelAndView("project/projectView");

        if(project.getOwner().getEmail().equals(principal.getName())) // Check if the project belongs to user
            return new ModelAndView("project/projectUpload", "ProjectForm", project);

        log.info("FAIL UPDATE - Invalid user");
        return new ModelAndView("project/projectView");
    }

    @PostMapping("/update/{pid}")
    public ModelAndView projectUpdateProcess(@PathVariable("pid") int pid, @ModelAttribute("ProjectForm") @Valid Project project, BindingResult bindingResult, Principal principal, RedirectAttributes rm) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("project/projectUpload");

        Project projectPrev = projectService.getProject(pid);
        if(!projectPrev.getOwner().getEmail().equals(principal.getName())) { // Validate owner
            log.info("FAIL UPDATE - Invalid user");
            return new ModelAndView("project/projectView");
        }

        if (bindingResult.hasErrors()) { // Check error
            log.error("Project upload binding form error");
            mv.addObject("ProjectForm", project);
            return mv;
        }

        Project savedProject = projectService.updateProject(projectPrev, project); // Update project
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
    public ModelAndView projectSearch(@RequestParam(name="q", required = false) String query) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("project/projectSearch");

        if(query != null) {
            query = query.strip();
            List<Map<Object, Object>> searchResult = redisService.searchProject(query);
            if(searchResult.size() == 0) {
                mv.addObject("emptyResult", 0);
                return mv;
            }
            mv.addObject("searchResult", searchResult);
        }
        return mv;
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
