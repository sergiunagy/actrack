package sena.actrack.vaadin.vaadinui.bootstrap;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sena.activitytracker.acktrack.model.*;
import sena.activitytracker.acktrack.model.security.Authority;
import sena.activitytracker.acktrack.model.security.Role;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.services.*;
import sena.activitytracker.acktrack.services.security.AuthorityService;
import sena.activitytracker.acktrack.services.security.RoleService;
import sena.activitytracker.acktrack.services.security.UserService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

@Slf4j
@SpringComponent
@Component
public class bootstrapData implements CommandLineRunner {

    /*Injected repository handles*/
    private final ProjectService projectService;
    private final IssueService issueService;
    private final WorkpackageService workpackageService;
    private final ActivityService activityService;
    private final UserService userService;
    private final RoleService roleService;
    private final AuthorityService authorityService;

    /*Users available*/
    Project alpha, beta, gamma;
    Issue quality, review, bugfix;
    Workpackage qualfix, revido, bugfixdo;
    Activity qualact, revact, bugfixact;

    User        sergiu, ade, mihai, cicero, maximus;
    Role        admin, developer, projectLead, teamLead, manager, administrative;
    Authority   userCreate, userRead, userUpdate, userDelete,
                roleCreate, roleRead, roleUpdate, roleDelete,
                projectCreate, projectRead, projectUpdate, projectDelete,
                issueCreate, issueRead, issueUpdate, issueDelete,
                workpackageCreate, workpackageRead, workpackageUpdate, workpackageDelete,
                activityCreate, activityRead, activityUpdate, activityDelete;


    @Autowired
    public bootstrapData(ProjectService projectService, IssueService issueService, WorkpackageService workpackageService, UserService userService, RoleService roleService, ActivityService activityService, AuthorityService authorityService) {

        log.info("VAADIN: bootstrap creator..");
        this.projectService = projectService;
        this.issueService = issueService;
        this.workpackageService = workpackageService;
        this.authorityService = authorityService;
        this.userService = userService;
        this.roleService = roleService;
        this.activityService = activityService;
    }


    @Transactional
    @Override
    public void run(String... args) throws Exception {
        log.info("VAADIN: Data loader..");
        bootstrapNActivities(10);
        initData();
    }

    private void initData() {
        /* Bootstrap some basic data */
        log.info("BOOTSTRAPPER - init data section");
        initRolesAndAuthorities();
        initUsers();
        initProjects();
        initIssues();
        initWorkpackages();
        initActivities();

        /******************************
        * Atribute roles to users*/
        sergiu.addRoles(new HashSet<>(Set.of(admin, developer)));
        maximus.addRole(manager);
        cicero.addRole(administrative);
        ade.addRoles(new HashSet<>(Set.of(teamLead, developer)));
        mihai.addRole(developer);

        userService.saveAll(new HashSet<>(Set.of(sergiu, maximus, cicero, ade, mihai)));
        /***************************************************************
         ** PROJECT 1 SETUP : configure the first project save at the end*/

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
        projectService.save(alpha);

        /***************************************************************
         ** PROJECT 2 SETUP : configure the first project save at the end*/

        // has 3 assigned workers
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

        /* persist PJ2 */
        projectService.save(beta);


    }

    /* 5 users*/
    private void initUsers() {

        /* sergiu, ade, mihai, cicero, maximus */
        sergiu = userService.save(User.builder()
                .familyName("Nagy")
                .givenName("Sergiu")
                .build());

        ade = userService.save(User.builder()
                .familyName("Nagy")
                .givenName("Ade")
                .build());

        mihai = userService.save(User.builder()
                .familyName("Popa")
                .givenName("Mihai")
                .build());

        maximus = userService.save(User.builder()
                .familyName("Nagy")
                .givenName("Maximus")
                .build());

        cicero = userService.save(User.builder()
                .familyName("Nagy")
                .givenName("Cicero")
                .build());
    }


    private void initRolesAndAuthorities() {

        /* userCreate, userRead, userUpdate, userDelete,
           roleCreate, roleRead, roleUpdate, roleDelete,
           projectCreate, projectRead, projectUpdate, projectDelete,
           issueCreate, issueRead, issueUpdate, issueDelete,
           workpackageCreate, workpackageRead, workpackageUpdate, workpackageDelete,
           activityCreate, activityRead, activityUpdate, activityDelete */

        userCreate = authorityService.save(Authority.builder().permission("user.create").build());
        userRead = authorityService.save(Authority.builder().permission("user.read").build());
        userUpdate = authorityService.save(Authority.builder().permission("user.update").build());
        userDelete = authorityService.save(Authority.builder().permission("user.delete").build());

        roleCreate = authorityService.save(Authority.builder().permission("role.create").build());
        roleRead = authorityService.save(Authority.builder().permission("role.read").build());
        roleUpdate = authorityService.save(Authority.builder().permission("role.update").build());
        roleDelete = authorityService.save(Authority.builder().permission("role.delete").build());

        projectCreate = authorityService.save(Authority.builder().permission("project.create").build());
        projectRead = authorityService.save(Authority.builder().permission("project.read").build());
        projectUpdate = authorityService.save(Authority.builder().permission("project.update").build());
        projectDelete = authorityService.save(Authority.builder().permission("project.delete").build());

        issueCreate = authorityService.save(Authority.builder().permission("issue.create").build());
        issueRead = authorityService.save(Authority.builder().permission("issue.read").build());
        issueUpdate = authorityService.save(Authority.builder().permission("issue.update").build());
        issueDelete = authorityService.save(Authority.builder().permission("issue.delete").build());

        workpackageCreate = authorityService.save(Authority.builder().permission("workpackage.create").build());
        workpackageRead = authorityService.save(Authority.builder().permission("workpackage.read").build());
        workpackageUpdate = authorityService.save(Authority.builder().permission("workpackage.update").build());
        workpackageDelete = authorityService.save(Authority.builder().permission("workpackage.delete").build());

        activityCreate = authorityService.save(Authority.builder().permission("activity.create").build());
        activityRead = authorityService.save(Authority.builder().permission("activity.read").build());
        activityUpdate = authorityService.save(Authority.builder().permission("activity.update").build());
        activityDelete = authorityService.save(Authority.builder().permission("activity.delete").build());

        /*admin, developer, projectLead, teamLead, manager, administrative*/
        admin = Role.builder()
                .name("administrator")
                .build();

        developer = Role.builder()
                .name("administrator")
                .build();

        projectLead = Role.builder()
                .name("administrator")
                .build();

        teamLead = Role.builder()
                .name("administrator")
                .build();

        manager = Role.builder()
                .name("administrator")
                .build();

        administrative = Role.builder()
                .name("administrator")
                .build();

        /*Add authorities */
        admin.setAuthorities(new HashSet<>(Set.of(
                userCreate, userRead, userUpdate, userDelete,
                roleCreate, roleRead, roleUpdate, roleDelete,
                projectCreate, projectRead, projectUpdate, projectDelete,
                issueCreate, issueRead, issueUpdate, issueDelete,
                workpackageCreate, workpackageRead, workpackageUpdate, workpackageDelete,
                activityCreate, activityRead, activityUpdate, activityDelete
                )));

        developer.setAuthorities(new HashSet<>(Set.of(
                projectRead,
                issueCreate, issueRead, issueUpdate, issueDelete,
                workpackageRead, workpackageUpdate,
                activityCreate, activityRead, activityUpdate, activityDelete
                )));

        projectLead.setAuthorities(new HashSet<>(Set.of(
                projectCreate, projectRead, projectUpdate, projectDelete,
                issueCreate, issueRead, issueUpdate, issueDelete,
                workpackageCreate, workpackageRead, workpackageUpdate, workpackageDelete,
                activityCreate, activityRead, activityUpdate, activityDelete
                )));

        teamLead.setAuthorities(new HashSet<>(Set.of(
                userCreate, userRead, userUpdate, userDelete
                )));

        manager.setAuthorities(new HashSet<>(Set.of(
                userCreate, userRead, userUpdate, userDelete,
                roleCreate, roleRead, roleUpdate, roleDelete,
                projectRead,
                issueCreate, issueRead, issueUpdate, issueDelete,
                workpackageRead,
                activityRead
                )));

        administrative.setAuthorities(new HashSet<>(Set.of(
                userCreate, userRead, userUpdate, userDelete,
                roleCreate, roleRead, roleUpdate, roleDelete,
                projectCreate, projectRead, projectUpdate, projectDelete,
                issueRead,
                workpackageRead,
                activityRead
                )));

        /*save roles*/
        roleService.saveAll(new HashSet<>(Set.of(admin, developer, projectLead, teamLead,manager,administrative)));
    }

    private void initActivities() {

        qualact = activityService.save(Activity.builder()
                .description("Check quality issues")
                .duration(Duration.of(4, ChronoUnit.HOURS))
                .date(LocalDate.of(2020, 10, 18))
                .isExported(false)
                .build());

        revact = activityService.save(Activity.builder()
                .description("Execute review on beta")
                .duration(Duration.of(8, ChronoUnit.HOURS))
                .date(LocalDate.of(2020, 10, 19))
                .isExported(false)
                .build());

        bugfixact = activityService.save(Activity.builder()
                .description("Bugfix problem on beta")
                .duration(Duration.of(8, ChronoUnit.HOURS))
                .date(LocalDate.of(2020, 10, 20))
                .isExported(true)
                .build());
    }

    private void initWorkpackages() {

        qualfix = workpackageService.save(Workpackage.builder()
                .name("fix quality")
                .description("fix quality on alpha")
                .build());

        revido = workpackageService.save(Workpackage.builder()
                .name("do reviews")
                .description("do reviews on beta")
                .build());

        bugfixdo = workpackageService.save(Workpackage.builder()
                .name("do bugfixing")
                .description("do bugfixing on beta")
                .build());
    }


    private void initIssues() {
        quality = issueService.save(Issue.builder()
                .issue_id("14a8")
                .description("quality issue alpha")
                .link("url quality")
                .build());

        review = issueService.save(Issue.builder()
                .issue_id("aaaa")
                .description("review issue beta")
                .link("url review")
                .build());

        bugfix = issueService.save(Issue.builder()
                .issue_id("111a")
                .description("bug issue beta")
                .link("url bug")
                .build());

    }

    private void initProjects() {

        alpha = projectService.save(Project.builder()
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
                .build());


        beta = projectService.save(Project.builder()
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
                .build());

        gamma = projectService.save(Project.builder()
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
                .build());
    }


    void bootstrapNActivities(int n) {

        int MAXRANGE = n; //days

        Set<Activity> activities = new HashSet<>();

        IntStream.range(0, MAXRANGE).parallel().forEach(
                idx -> {
                    activities.add(Activity.builder()
                            .date(LocalDate.now().minusDays((MAXRANGE - idx)))
                            .description("activity" + idx)
                            .build());
                }
        );

        activityService.saveAll(activities);
    }
}
