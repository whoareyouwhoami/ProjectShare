package com.project.share.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.share.model.Project;
import com.project.share.model.User;
import com.project.share.service.ProjectService;
import com.project.share.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc
public class ProjectControllerTest {

    /*
     * @Autowired -> controller is injected before test
     */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private UserService userService;

    private static LocalDate date1 = null;
    private static LocalDate date2 = null;
    private static User user = null;
    private static Project project = null;

    @BeforeAll
    static void setUp() {
        date1 = LocalDate.of(2021, 7, 12);
        date2 = LocalDate.of(2021, 10, 18);

        user = new User("firstName", "lastName", "test@test.com", "password", "m", date1);
        project = new Project("TITLE", "DESCRIPTION", 7, date1, date2);

    }

    @Test
    @WithMockUser
    public void testGetProjectPage() throws Exception {
        mockMvc
                .perform(get("/project"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/projectHome"))
                .andExpect(model().attributeExists("projectList"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void testGetProjectUploadPage() throws Exception {
        mockMvc
                .perform(get("/project/upload"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/projectUpload"))
                .andExpect(model().attributeExists("ProjectForm"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void testPostProject() throws Exception {
        Project savedProject = project;
        savedProject.setId(1);

        String redirect = "redirect:/project/view/" + savedProject.getId();

        when(projectService.saveProject(project)).thenReturn(savedProject);
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);

        RequestBuilder request = post("/project/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(project))
                .with(csrf());

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name(redirect))
                .andExpect(model().attribute("proj_detail", savedProject))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void testViewProjectPage() throws Exception {
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
//        when(projectService.getAllUserProject(user)).thenReturn(Arrays.asList(project));

        mockMvc
                .perform(get("/project/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/projectHome"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("projectList"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void testViewProjectDetail() throws Exception {
            project.setId(1);

            when(projectService.getProject(1)).thenReturn(project);

            mockMvc
                    .perform(get("/project/view/1"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("project/projectView"))
                    .andExpect(MockMvcResultMatchers.model().attributeExists("p_detail"))
                    .andExpect(model().attribute("p_detail", hasProperty("title", is("TITLE"))))
                    .andExpect(model().attribute("p_detail", hasProperty("description", is("DESCRIPTION"))))
                    .andExpect(model().attribute("p_detail", hasProperty("member", is(7))))
                    .andExpect(model().attribute("p_detail", hasProperty("dateStart", is(date1))))
                    .andExpect(model().attribute("p_detail", hasProperty("dateEnd", is(date2))))
                    .andDo(print());

            verify(projectService, times(1)).getProject(1);
    }
}
