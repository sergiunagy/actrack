package sena.activitytracker.acktrack.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sena.activitytracker.acktrack.model.*;
import sena.activitytracker.acktrack.repositories.*;
import sena.activitytracker.acktrack.services.ActivityService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

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

    private final ActivityService activityService;

    /*Users available*/
    Project alpha, beta, gamma;
    Issue quality, review, bugfix;
    Workpackage qualfix, revido, bugfixdo;
    Activity qualact, revact, bugfixact;
    User sergiu, ade, mihai;
    Role dev_alpha, lead_alpha, dev_beta, lead_beta;
    ProjectUserRoles pdev, plead;


    @Autowired
    public bootstrapData(ProjectRepository projectRepository, IssueRepository issueRepository, WorkpackageRepository workpackageRepository, ActivityRepository activityRepository, UserRepository userRepository, RoleRepository rolesRepository, ProjectUserRolesRepository projectUserRolesRepository, ActivityService activityService) {
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.workpackageRepository = workpackageRepository;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.projectUserRolesRepository = projectUserRolesRepository;
        this.activityService = activityService;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        bootstrapNActivities(10);
        initData();
    }

    private void initData() {
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

        // has 2 roles
        alpha.addRole(dev_alpha);
        alpha.addRole(lead_alpha);

        // has 2 assigned workers
        alpha.addUser(sergiu);
        alpha.addUser(mihai);

        // has a quality issue
        alpha.addIssue(quality);

        // add wp
        quality.getWorkpackages().add(qualfix);
        // add activity
        quality.getActivities().add(qualact);

        /* configure wps for alpha*/
        qualfix.addActivity(qualact);

        /* configure activities for alpha */
        sergiu.addActivity(qualact);

        /* persist PJ1 */
        projectRepository.save(alpha);


        /*PROJECT 2 SETUP : configure the 2nd project save at the end*/

        // has 2 roles
        beta.addRole(dev_beta);
        beta.addRole(lead_beta);

        // has 2 assigned workers
        beta.addUser(sergiu);
        beta.addUser(mihai);
        beta.addUser(ade);

        // has a quality and a bug issues
        beta.addIssue(review);
        beta.addIssue(bugfix);

        // add wp
        review.getWorkpackages().add(revido);
        bugfix.getWorkpackages().add(bugfixdo);

        // add activity
        bugfix.getActivities().add(bugfixact);
        bugfix.getActivities().add(revact);  // assume the review affects a bugfix so we get more complexity
        review.getActivities().add(revact);

        /* configure wps for alpha*/
        revido.getActivities().add(revact);
        bugfixdo.getActivities().add(bugfixact);


        /* configure activities for alpha */
        ade.addActivity(revact);
        mihai.addActivity(bugfixact);

        /* persist PJ2 */
        projectRepository.save(beta);


    }

    private void initProjectUserRoles() {

//        pdev = ProjectUserRoles.builder()
//                .
    }

    private void initRoles() {

        dev_alpha = Role.builder()
                .name("Developer")
                .description("Implements, tests, reviews")
                .build();

        lead_alpha = Role.builder()
                .name("Project lead")
                .description("Assigns, manages, client interface")
                .build();

        dev_beta = Role.builder()
                .name("Developer")
                .description("Implements, tests, reviews")
                .build();

        lead_beta = Role.builder()
                .name("Project lead")
                .description("Assigns, manages, client interface")
                .build();
    }

    private void initActivities() {

        qualact = Activity.builder()
                .description("Check quality issues")
                .duration(Duration.of(4, ChronoUnit.HOURS))
                .date(LocalDate.of(2020, 10, 18))
                .isExported(false)
                .build();

        revact = Activity.builder()
                .description("Execute review on beta")
                .duration(Duration.of(8, ChronoUnit.HOURS))
                .date(LocalDate.of(2020, 10, 19))
                .isExported(false)
                .build();

        bugfixact = Activity.builder()
                .description("Bugfix problem on beta")
                .duration(Duration.of(8, ChronoUnit.HOURS))
                .date(LocalDate.of(2020, 10, 20))
                .isExported(true)
                .build();
    }

    private void initWorkpackages() {

        qualfix = Workpackage.builder()
                .name("fix quality")
                .description("fix quality on alpha")
                .build();

        revido = Workpackage.builder()
                .name("do reviews")
                .description("do reviews on beta")
                .build();

        bugfixdo = Workpackage.builder()
                .name("do bugfixing")
                .description("do bugfixing on beta")
                .build();
    }


    private void initIssues() {
        quality = Issue.builder()
                .issue_id("14a8")
                .description("quality issue alpha")
                .link("url quality")
                .build();

        review = Issue.builder()
                .issue_id("aaaa")
                .description("review issue beta")
                .link("url review")
                .build();

        bugfix = Issue.builder()
                .issue_id("111a")
                .description("bug issue beta")
                .link("url bug")
                .build();

    }

    private void initProjects() {

        alpha = Project.builder()
                .name("alpha")
                .description("dummy alpha")
                .mainLocation("Alpha location")
                .plannedEndDate(LocalDate.of(2018, 10, 20))
                .actualEndDate(LocalDate.of(2020, 10, 20))
                .plannedSopDate(LocalDate.of(2019, 6, 20))
                .plannedEndDate(LocalDate.of(2020, 10, 1))
                .customerName("Alpha Daimler")
                .customerId("12s42")
                .productLine("alpha moto")
                .active(true)
                .build();


        beta = Project.builder()
                .name("beta")
                .description("dummy beta")
                .mainLocation("Beta location")
                .plannedEndDate(LocalDate.of(2018, 10, 20))
                .actualEndDate(LocalDate.of(2020, 10, 20))
                .plannedSopDate(LocalDate.of(2019, 6, 20))
                .plannedEndDate(LocalDate.of(2020, 10, 1))
                .customerName("Alpha Daimler")
                .customerId("13s42")
                .productLine("Beta moto")
                .active(true)
                .build();

        gamma = Project.builder()
                .name("gamma")
                .description("dummy gamma")
                .mainLocation("Beta location")
                .plannedEndDate(LocalDate.of(2018, 10, 20))
                .actualEndDate(LocalDate.of(2020, 10, 20))
                .plannedSopDate(LocalDate.of(2019, 6, 20))
                .plannedEndDate(LocalDate.of(2020, 10, 1))
                .customerName("Alpha Daimler")
                .customerId("zzzzzz1")
                .productLine("gama moto")
                .active(false)
                .build();
    }


    /* 3 users*/
    private void initUsers() {

        sergiu = User.builder()
                .familyName("Nagy")
                .givenName("Sergiu")
                .uid("u1")
                .build();

        mihai = User.builder()
                .familyName("Popa")
                .givenName("Mihai")
                .uid("u2")
                .build();

        ade = User.builder()
                .familyName("Nagy")
                .givenName("Adelina")
                .uid("u3")
                .build();
    }

    void bootstrapNActivities(int n) {

        int MAXRANGE = n; //days

        Set<Activity> activities = new HashSet<>();

        IntStream.range(0, MAXRANGE).parallel().forEach(
                idx -> {
                    activities.add(Activity.builder()
                            .id(Long.valueOf(idx))
                            .date(LocalDate.now().minusDays((MAXRANGE - idx)))
                            .description("activity" + idx)
                            .build());
                }
        );

        activityService.saveAll(activities);
    }
}
