//package com.project.share.service;
//
//import com.project.share.dao.ProjectDao;
//import com.project.share.model.Project;
//import com.project.share.model.User;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDate;
//import java.util.*;
//
//
//import static org.assertj.core.api.BDDAssertions.then;
//
//import static org.hamcrest.MatcherAssert.*;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ProjectServiceTest {
//    @Mock
//    private ProjectDao projectDao;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private ProjectService projectService = new ProjectServiceImpl();
//
//    private static LocalDate date1 = null;
//    private static LocalDate date2 = null;
//    private static User user = null;
//    private static Project project = null;
//
//    @BeforeAll
//    static void setUp() {
//        date1 = LocalDate.of(2021, 7, 12);
//        date2 = LocalDate.of(2021, 10, 18);
//
//        user = new User("firstName", "lastName", "test@test.com", "password", "m", date1);
//        project = new Project("TITLE", "DESCRIPTION", 7, date1, date2);
//        project.setId(1);
//    }
//
//    @Test
//    public void testGetProjectById() {
//        // given
//        when(projectDao.findById(project.getId())).thenReturn(Optional.ofNullable(project));
//
//        // when
//        Project p = projectService.getProject(project.getId());
//
//        // then
//        then("TITLE").isEqualTo(p.getTitle());
//        then("DESCRIPTION").isEqualTo(p.getDescription());
//        then(7).isEqualTo(p.getMember());
//        then(date1).isEqualTo(p.getDateStart());
//        then(date2).isEqualTo(p.getDateEnd());
//
//        verify(projectDao, times(1)).findById(project.getId());
//    }
//
//    @Test
//    public void testGetAllProjects() {
//        // given
//        Project project2 = new Project("TITLE2", "DESCRIPTION2", 2, date1, date2);
//        Project project3 = new Project("TITLE3", "DESCRIPTION3", 3, date1, date2);
//        when(projectDao.findAll()).thenReturn(List.of(project, project2, project3));
//
//        // when
//        List<Project> pAll = projectService.getAllProject();
//
//        // then
//        assertThat(pAll.size(), is(3));
//
//        verify(projectDao, times(1)).findAll();
//    }
//
//    @Test
//    public void testGetAllUserProjects() {
//        // given
//        Project project2 = new Project("TITLE2", "DESCRIPTION2", 2, date1, date2);
//        Project project3 = new Project("TITLE3", "DESCRIPTION3", 3, date1, date2);
//
//        when(projectDao.findAllByUser(user)).thenReturn(List.of(project2, project3));
//
//        // when
////        List<Project> pAll = projectService.getAllUserProject(user);
//
//        // then
////        assertThat(pAll.size(), is(2));
//
//        verify(projectDao, times(1)).findAllByUser(user);
//    }
//
//    @Test
//    public void testSavingProject() {
//        when(projectDao.save(project)).thenReturn(project);
//
//        Project saved = projectService.saveProject(project);
//
//        assertThat(saved.getTitle(), is("TITLE"));
//        assertThat(saved.getDescription(), is("DESCRIPTION"));
//        assertThat(saved.getMember(), is(7));
//        assertThat(saved.getDateStart(), is(date1));
//        assertThat(saved.getDateEnd(), is(date2));
//
//        verify(projectDao, times(1)).save(project);
//    }
//
//    @Test
//    public void testUpdatingProject() {
//        LocalDate dateNew2 = LocalDate.of(2021, 12, 10);
//        Project projectNew = new Project("TITLE2", "DESCRIPTION2", 2, date1, dateNew2);
//
//        when(projectDao.save(project)).thenReturn(projectNew);
//
//        Project updatedProject = projectService.updateProject(project, projectNew);
//        assertThat(updatedProject.getTitle(), is("TITLE2"));
//    }
//}
