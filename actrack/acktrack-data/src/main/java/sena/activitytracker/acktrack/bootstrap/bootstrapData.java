package sena.activitytracker.acktrack.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sena.activitytracker.acktrack.model.*;
import sena.activitytracker.acktrack.repositories.*;

@Slf4j
@Component
public class bootstrapData implements CommandLineRunner {

    /*Injected repository handles*/
    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;
    private final WorkpackageRepository workpackageRepository;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final RoleRepository rolesRepository;
    private final ProjectUserRolesRepository projectUserRolesRepository;

    /*Users available*/
    Project alpha, beta, gamma;
    Issue quality, review, bugfix;
    Workpackage qualfix, revido, bugfixdo;
    Activity qualact, revact, fixact;
    User sergiu, ade, mihai;
    Role dev_alpha, lead_alpha, dev_beta, lead_beta;
    ProjectUserRoles pdev, plead;


    @Autowired
    public bootstrapData(ProjectRepository projectRepository, IssueRepository issueRepository, WorkpackageRepository workpackageRepository, ActivityRepository activityRepository, UserRepository userRepository, RoleRepository rolesRepository, ProjectUserRolesRepository projectUserRolesRepository) {
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.workpackageRepository = workpackageRepository;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.projectUserRolesRepository = projectUserRolesRepository;
    }



    @Override
    public void run(String... args) throws Exception {
        initData();
    }

    private void initData(){
        /* Bootstrap some basic data */
        log.info("BOOTSTRAPPER - init data section");

        initUsers();
        initProjects();
        initIssues();
        initWorkpackages();
        initActivities();
        initRoles();
        initProjectUserRoles();

        /*PROJECT 1 SETUP : configure the first project save at the end*/

        /*configure roles reverse map - though this may be overkill to see what projects use what roles*/
//        dev_alpha.getProject().add(alpha);
//        lead_alpha.getProject().add(alpha);
        rolesRepository.save(dev_alpha);  //todo: check if we can to this with a list cast to be cleaner and not repeat code
        rolesRepository.save(lead_alpha);

        // has a quality issues
        alpha.getIssues().add(quality);
        // has 2 roles
        alpha.getRoles().add(dev_alpha);
//        alpha.getRoles().add(lead_alpha);
//        // has 2 assigned workers
//        alpha.getUsers().add(sergiu);
//        alpha.getUsers().add(mihai);
        // todo : configure user roles

//        /* configure issues for p1*/
//        // add wp
//        quality.getWorkpackages().add(qualfix);
//        // add activity
//        quality.getActivities().add(qualact);
//        // reverse map to pj -- todo : implement this in the POJO
//        quality.setProject(alpha);
//
//        /* configure wps for alpha*/
//        qualfix.getActivities().add(qualact);
//        // reverse map to Issue
//        qualfix.getIssues().add(quality);
//
//        /* configure activities for alpha */
//        qualact.setUser(sergiu);
//        // reverse map to wp
//        qualact.getWorkpackages().add(qualfix);
//        qualact.getIssues().add(quality);
//
//        /* Configure users for alpha*/
//        // reverse mapping to activity and proj
//        sergiu.getActivities().add(qualact);
//        sergiu.getProject().add(alpha);
//        mihai.getProject().add(alpha);
//


        /* persist PJ1 */

        projectRepository.save(alpha);
//        issueRepository.save(quality);
//        workpackageRepository.save(qualfix);
//        activityRepository.save(qualact);
//        userRepository.save(sergiu);  //todo: check if we can to this with a list cast to be cleaner and not repeat code
//        userRepository.save(mihai);
//        rolesRepository.save(dev_alpha);  //todo: check if we can to this with a list cast to be cleaner and not repeat code
//        rolesRepository.save(lead_alpha);

    }

    private void initProjectUserRoles(){

//        pdev = ProjectUserRoles.builder()
//                .
    }

    private void initRoles(){

        dev_alpha = Role.builder()
                .id(1L)
                .name("Developer")
                .description("Implements, tests, reviews")
                .build();

        lead_alpha = Role.builder()
                .id(2L)
                .name("Project lead")
                .description("Assigns, manages, client interface")
                .build();

        dev_beta = Role.builder()
                .id(3L)
                .name("Developer")
                .description("Implements, tests, reviews")
                .build();

        lead_beta = Role.builder()
                .id(4L)
                .name("Project lead")
                .description("Assigns, manages, client interface")
                .build();
    }

    private void initActivities(){

        qualact = Activity.builder()
                .id(1L)
                .description("Check quality issues")
                .startDateTime("30.10.2020")
                .isExported(false)
                .build();

        revact = Activity.builder()
                .id(2L)
                .description("Execute review on beta")
                .startDateTime("30.10.2020")
                .isExported(false)
                .build();

        fixact = Activity.builder()
                .id(3L)
                .description("Bugfix problem on beta")
                .startDateTime("31.10.2020")
                .isExported(true)
                .build();
    }

    private void initWorkpackages(){

        qualfix = Workpackage.builder()
                .id(1L)
                .name("fix quality")
                .description("fix quality on alpha")
                .build();

        revido = Workpackage.builder()
                .id(2L)
                .name("do reviews")
                .description("do reviews on beta")
                .build();

        bugfixdo = Workpackage.builder()
                .id(3L)
                .name("do bugfixing")
                .description("do bugfixing on beta")
                .build();
    }


    private void initIssues(){
        quality = Issue.builder()
                .id(1L)
                .issue_id("14a8")
                .description("quality issue alpha")
                .link("url quality")
                .build();

        review = Issue.builder()
                .id(2L)
                .issue_id("aaaa")
                .description("review issue beta")
                .link("url review")
                .build();

        bugfix = Issue.builder()
                .id(3L)
                .issue_id("111a")
                .description("bug issue beta")
                .link("url bug")
                .build();

    }

    private void initProjects(){

        alpha = Project.builder()
                .id(1L)
                .name("alpha")
                .description("dummy alpha")
                .mainLocation("Alpha location")
                .plannedEndDate("21.10.2020")
                .actualEndDate("28.10.2020")
                .plannedSopDate("28.10.2021")
                .plannedEndDate("28.10.2022")
                .customerName("Alpha Daimler")
                .customerId("12s42")
                .productLine("alpha moto")
                .active(true)
                .build();


        beta = Project.builder()
                .id(2L)
                .name("beta")
                .description("dummy beta")
                .mainLocation("Beta location")
                .plannedEndDate("21.10.2020")
                .actualEndDate("28.10.2020")
                .plannedSopDate("28.10.2021")
                .plannedEndDate("28.10.2022")
                .customerName("Alpha Daimler")
                .customerId("13s42")
                .productLine("Beta moto")
                .active(true)
                .build();

        gamma = Project.builder()
                .id(3L)
                .name("gamma")
                .description("dummy gamma")
                .mainLocation("Beta location")
                .plannedEndDate("21.10.2020")
                .actualEndDate("28.10.2020")
                .plannedSopDate("28.10.2021")
                .plannedEndDate("28.10.2022")
                .customerName("Alpha Daimler")
                .customerId("zzzzzz1")
                .productLine("gama moto")
                .active(false)
                .build();
    }






    /* 3 users*/
    private void initUsers(){

        sergiu = User.builder()
                .id(1L)
                .familyName("Nagy")
                .givenName("Sergiu")
                .uid("u1")
                .build();

        mihai = User.builder()
                .id(2L)
                .familyName("Popa")
                .givenName("Mihai")
                .uid("u2")
                .build();

        ade = User.builder()
                .id(3L)
                .familyName("Nagy")
                .givenName("Adelina")
                .uid("u3")
                .build();
    }
}
