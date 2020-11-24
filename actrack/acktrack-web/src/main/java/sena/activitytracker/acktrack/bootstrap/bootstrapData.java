package sena.activitytracker.acktrack.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sena.activitytracker.acktrack.model.*;
import sena.activitytracker.acktrack.services.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Component
public class bootstrapData implements CommandLineRunner {

    /*Injected repository handles*/
    private final ProjectService projectService;
    private final IssueService issueService;
    private final WorkpackageService workpackageService;
    private final ActivityService activityService;
    private final UserService userService;
    private final RoleService roleService;
    private final ProjectUserRolesService projectUserRolesService;

    public static String TEXT500 = "Nam quis nulla. Integer malesuada. In in enim a arcu imperdiet malesuada. Sed vel lectus. Donec odio " +
            "urna, tempus molestie, porttitor ut, iaculis quis, sem. Phasellus rhoncus. Aenean id metus id velit ullamcorper " +
            "pulvinar. Vestibulum fermentum tortor id mi. Pellentesque ipsum. Nulla non arcu lacinia neque faucibus fringilla." +
            " Nulla non lectus sed nisl molestie malesuada. Proin in tellus sit amet nibh dignissim sagittis. " +
            "Vivamus luctus egestas leo. Maecenas sollicitudin. Nullam rhoncus aliquam met";

    /*Users available*/
    Project alpha, beta, gamma;
    Issue quality, review, bugfix;
    Workpackage qualfix, revido, bugfixdo;
    Activity qualact, revact, bugfixact;
    User sergiu, ade, mihai;
    Role developer_on_alpha, plead_on_alpha, dev_beta, lead_beta;
    ProjectUserRole pdev_alpha, plead_alpha, pdev_beta, plead_beta;


    @Autowired
    public bootstrapData(ProjectService projectService, IssueService issueService, WorkpackageService workpackageService, ActivityService activityRepository, UserService userService, RoleService roleService, ProjectUserRolesService projectUserRolesService, ActivityService activityService) {
        this.projectService = projectService;
        this.issueService = issueService;
        this.workpackageService = workpackageService;
        this.userService = userService;
        this.roleService = roleService;
        this.projectUserRolesService = projectUserRolesService;
        this.activityService = activityService;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        log.info("WebApp: Data loader..");
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

        /*PROJECT 1 SETUP : configure the first project save at the end*/

        // has 2 roles
        alpha.addRole(developer_on_alpha);
        alpha.addRole(plead_on_alpha);

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

        /*add project roles to user mapping */
        alpha.addUserToRole(sergiu, developer_on_alpha);
        alpha.addUserToRole(mihai, plead_on_alpha);

        /* persist PJ1 */
        projectService.save(alpha);


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

        /*add project roles to user mapping */
        beta.addUserToRole(sergiu, dev_beta);
        beta.addUserToRole(mihai, dev_beta);
        beta.addUserToRole(ade, lead_beta);

        /* persist PJ2 */
        projectService.save(beta);


    }

    private void initRoles() {

        developer_on_alpha = Role.builder()
                .name("Developer")
                .description("Implements, tests, reviews")
                .build();

        plead_on_alpha = Role.builder()
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
                .description("Check quality issues ----" + TEXT500)
                .duration(Duration.of(4, ChronoUnit.HOURS))
                .date(LocalDate.of(2020, 10, 18))
                .isExported(false)
                .build();

        revact = Activity.builder()
                .description("Execute review on beta ----" + TEXT500)
                .duration(Duration.of(8, ChronoUnit.HOURS))
                .date(LocalDate.of(2020, 10, 19))
                .isExported(false)
                .build();

        bugfixact = Activity.builder()
                .description("Bugfix problem on beta  ----" + TEXT500)
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
                            .id(UUID.randomUUID())
                            .description(TEXT500)
                            .date(LocalDate.now().minusDays((MAXRANGE - idx)))
                            .description("activity" + idx)
                            .build());
                }
        );

        activityService.saveAll(activities);
    }
}
