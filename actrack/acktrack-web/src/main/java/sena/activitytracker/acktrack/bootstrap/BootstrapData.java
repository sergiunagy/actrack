package sena.activitytracker.acktrack.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import sena.activitytracker.acktrack.model.Activity;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.model.security.Authority;
import sena.activitytracker.acktrack.model.security.Role;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.repositories.ActivityRepository;
import sena.activitytracker.acktrack.repositories.IssueRepository;
import sena.activitytracker.acktrack.repositories.ProjectRepository;
import sena.activitytracker.acktrack.repositories.WorkpackageRepository;
import sena.activitytracker.acktrack.repositories.security.AuthorityRepository;
import sena.activitytracker.acktrack.repositories.security.RoleRepository;
import sena.activitytracker.acktrack.repositories.security.UserRepository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
public class BootstrapData implements CommandLineRunner {

    final int NO_OF_PROJECTS = 5;
    final int NO_OF_ISSUES = 30;
    final int NO_OF_WORKPACKAGES = 60;
    final int NO_OF_ACTIVITIES = 120;
    final int NO_OF_USERS = 20;

    ProjectRepository projectRepository;
    UserRepository userRepository;
    IssueRepository issueRepository;
    WorkpackageRepository workpackageRepository;
    ActivityRepository activityRepository;
    RoleRepository roleRepository;
    AuthorityRepository authorityRepository;

    List<Project> projects = new ArrayList<>();
    List<User> users = new ArrayList<>();
    List<Issue> issues = new ArrayList<>();
    List<Workpackage> workpackages = new ArrayList<>();
    List<Activity> activities = new ArrayList<>();

    Role admin, developer, projectLead, teamLead, manager, administrative;
    Authority userCreate, userRead, userUpdate, userDelete,
            roleCreate, roleRead, roleUpdate, roleDelete,
            projectCreate, projectRead, projectUpdate, projectDelete,
            issueCreate, issueRead, issueUpdate, issueDelete,
            workpackageCreate, workpackageRead, workpackageUpdate, workpackageDelete,
            activityCreate, activityRead, activityUpdate, activityDelete;

//    /*Security loading*/
//    @Autowired
//    @Qualifier("authenticationManager")
//    protected AuthenticationManager authenticationManager;


    @Autowired
    public BootstrapData(ProjectRepository projectRepository, UserRepository userRepository, IssueRepository issueRepository, WorkpackageRepository workpackageRepository, ActivityRepository activityRepository, RoleRepository roleRepository, AuthorityRepository authorityRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
        this.workpackageRepository = workpackageRepository;
        this.activityRepository = activityRepository;
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        initProjectsData(NO_OF_PROJECTS);
        initUsersData(NO_OF_USERS);
        initIssuesData(NO_OF_ISSUES);
        initWorkpackageData(NO_OF_WORKPACKAGES);
        initActivitiesData(NO_OF_ACTIVITIES);
        initRolesAndAuthorities();

        mapIssuesToProjects();
        mapWorkpackagesToIssues();
        mapActivitiesToWorkpackages();
        mapActivitiesFromWpsToIssues();
        mapActivitiesToUsers();
        mapUsersToRoles();

        /*performAutoAuthentication();*/
    }
    /*************** Auto-authentication *******************/
    /* */
    private void performAutoAuthentication(){
        /* TODO:
                We are injecting directly in the  SecurityContextHolder here.
                Without setting up the authentication filter, THIS WILL NOT TRIGGER a UserDetails load
                We must create the filter then use one of the users there to authenticate, maybe find a way to
                connect to the filter .
                - First create an authentication filter - REST
                - check if the UserDertails service is now triggered
                https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-authentication-abstractprocessingfilter
        */
        String user = "user";
        String password = "user";
        String role = "role_test_user";

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new TestingAuthenticationToken(user, password, role);

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        System.out.println("TEEEEEEEEEEEEEEST");
        System.out.println(role.equalsIgnoreCase("role_test_user"));
    }


    /*************** MAPPING functions *******************/

    /**********
     * Map a subset of objects to one object
     * @param mappingAction - adder method belonging to the entity we map to
     * @param subsetOffset
     * @param subsetSize
     * @return updated offset - last written position
     */
    private int mapSubsetToObject(Consumer mappingAction, List mappedSet, int subsetOffset, int subsetSize) {

        IntStream.range(subsetOffset, subsetOffset + subsetSize).forEach(id -> {
            mappingAction.accept(mappedSet.get(id));
        });

        return subsetOffset + subsetSize;
    }

    /******************
     * Map issues to projects
     ************************ */
    private void mapIssuesToProjects() {
        /* Map issues - issues count stored as list */
        List<Integer> split = Arrays.asList(3, 4, 10, 8, 5);
        int offset = 0;
        for (int id = 0; id < split.size(); id++) {
            /* Build a Consumer wrapper for the addIssue so we can pass it to the mapper */
            Consumer<Issue> addIssueToProject = projects.get(id)::addIssue;
            /* Update offset as we move through the mapped set */
            offset = mapSubsetToObject(addIssueToProject, issues, offset, split.get(id));
        }

        projectRepository.saveAll(projects);
    }

    /***************************
     * Map workpackages to issues
     ************************ */

    /***
     *  Map 2 wps for each issue */
    private void mapWorkpackagesToIssues() {

        int WPS_PER_ISSUE = 2;
        int offset = 0;

        for (int id = 0; id < NO_OF_ISSUES; id++) {
            Consumer<Workpackage> addWpToIssue = issues.get(id)::addWorkpackage;
            offset = mapSubsetToObject(addWpToIssue, workpackages, offset, WPS_PER_ISSUE);
        }

        issueRepository.saveAll(issues);
    }

    /***************************
     * ActivitysWorkpackage mappers
     ************************ */

    private void mapActivitiesToWorkpackages() {
        /* Use overlapping when mapping activities to wps since, in reality,
         * we may get activities solving multiple issues/wps
         * split as:
         * - 1st wp gets 2 activities
         * - the rest of the wps get 3 activities each with eaach 3 activities set
         * overlapping the last activity in the previous set. Ex:
         * 1 2            - wp 1
         *   2 3 4        - wp 2
         *       4 5 6    - wp 3
         * ...            - wp n   */

        int ACT_PER_WP = 3;
        int offset = 0;

        /*map the first 2 activities to the first issue*/
        workpackages.get(0).addActivity(activities.get(0));
        workpackages.get(0).addActivity(activities.get(1));
        /* map the rest as overlapping triplets*/
        offset = 1;
        for (int id = 1; id < NO_OF_WORKPACKAGES; id++) {
            Consumer<Activity> addActToWp = workpackages.get(id)::addActivity;
            offset = mapSubsetToObject(addActToWp, activities, offset, ACT_PER_WP);
            offset -= 1; /* overlap over the last element*/
        }

        workpackageRepository.saveAll(workpackages);

    }

    /*******
     * Since we went with the more flexible approach where an Activity can be mapped first
     * to either a Wp or an Issue. Let's map all activities mapped to workpackages on the corresponding issues.
     * We could shuffle some extra activities to have some mapped on issues only, but this is a negative
     * case and should only appear in tests.
     */
    private void mapActivitiesFromWpsToIssues() {

        workpackages.forEach(
                workpackage -> {  /* for each wp in the list*/
                    workpackage.getActivities().stream() /* for each activity associated with the wp */
                            .forEach(workpackage.getIssues().stream().findFirst().get()::addActivity); /* map it to the first
                    issue associated with this wp*/
                }
        );

        issueRepository.saveAll(issues); /*PERSIST is cascaded down from issues to activities*/
    }

    /***************************
     * Map activities to users
     ************************ */

    /***
     *  Map 6 activities for each user */
    private void mapActivitiesToUsers() {

        int ACT_PER_USER = 6;
        int offset = 0;

        for (int id = 0; id < NO_OF_USERS; id++) {
            Consumer<Activity> addActToUser = users.get(id)::addActivity;
            offset = mapSubsetToObject(addActToUser, activities, offset, ACT_PER_USER);
        }

        userRepository.saveAll(users); /* Save cascades to activities on PERSIST from User*/
    }

    /***************************
     * Randomly Map roles and users
     ************************ */
    private void mapUsersToRoles() {

        List<Role> rolesList=new ArrayList<>(List.of(admin, developer, projectLead, teamLead, manager, administrative));
        /* for each user grab a random selection of roles */
        Random rand = new Random();
        System.out.println("Randomly assigning Roles to Users..");
        users.forEach(user -> {
            rand.ints(3, 0, 5)
                    .boxed()
                    .collect(Collectors.toSet())
                    .forEach(roleid-> user.addRole(rolesList.get(roleid.intValue())));
        });

        userRepository.saveAll(users);
    }

    /******************************DATA INITIALIZERS******************/

    private void initProjectsData(int n_days) {

        String PROJECT_SUFFIX = "Project_";
        int DATE_RANGE_MAX = n_days;

        IntStream.range(0, 5).forEach(id -> {
            projects.add(Project.builder()
                    .name(PROJECT_SUFFIX + id)
                    .actualStartDate(LocalDate.now().minusDays(DATE_RANGE_MAX - id))
                    .active(true)
                    .customerName("Client_" + id)
                    .notes("Notes for project_" + id)
                    .description("Project implementation for Project_" + id)
                    .mainLocation("Location project_" + id)
                    .build());
        });
        projectRepository.saveAll(projects);
    }

    private void initUsersData(int n_users) {

        IntStream.range(0, n_users).forEach(id -> {
            users.add(User.builder()
                    .familyName("Fam_name_" + id)
                    .givenName("Given_name_" + id)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .accountNonExpired(true)
                    .enabled(true)
                    .username("usr_" + id)
                    .build());
        });
        userRepository.saveAll(users);
    }

    private void initIssuesData(int n_issues) {

        String ISSUE_SUFFIX = "Issue_";

        int ISSUE_RANGE_MAX = NO_OF_ISSUES;

        IntStream.range(0, n_issues).forEach(id -> {
            issues.add(Issue.builder()
                    .issue_id(ISSUE_SUFFIX + "ID_" + id)
                    .description("Description " + ISSUE_SUFFIX + id)
                    .createdDate(LocalDate.now().minusDays(ISSUE_RANGE_MAX - id))
                    .build());
        });
        issueRepository.saveAll(issues);
    }

    private void initWorkpackageData(int n_workpackages) {

        String WP_SUFFIX = "Workpackage_";
        int WP_RANGE_MAX = n_workpackages;

        IntStream.range(0, n_workpackages).forEach(id -> {
            workpackages.add(Workpackage.builder()
                    .name(WP_SUFFIX + id)
                    .startDate(LocalDate.now().minusDays(id))
                    .description("Description " + WP_SUFFIX + id)
                    .build());
        });

        workpackageRepository.saveAll(workpackages);
    }

    private void initActivitiesData(int n_activities) {

        String ACT_SUFFIX = "Activity_";
        int ACT_RANGE_MAX = n_activities;

        IntStream.range(0, n_activities).forEach(id -> {
            Activity a = Activity.builder()
                    .duration(Duration.ofHours(id))
                    .date(LocalDate.now().minusDays(ACT_RANGE_MAX - id))
                    .isExported(false)
                    .description("Activity actions description for activity_" + id)
                    .build();
            activities.add(activityRepository.save(a));
        });
        /* todo: check problem with persisting by save-all . Seems Timestamp does not have enough resolution if we
        *   store using saveall */
    }

    private void initRolesAndAuthorities() {

        /* userCreate, userRead, userUpdate, userDelete,
           roleCreate, roleRead, roleUpdate, roleDelete,
           projectCreate, projectRead, projectUpdate, projectDelete,
           issueCreate, issueRead, issueUpdate, issueDelete,
           workpackageCreate, workpackageRead, workpackageUpdate, workpackageDelete,
           activityCreate, activityRead, activityUpdate, activityDelete */

        userCreate = authorityRepository.save(Authority.builder().permission("user.create").build());
        userRead = authorityRepository.save(Authority.builder().permission("user.read").build());
        userUpdate = authorityRepository.save(Authority.builder().permission("user.update").build());
        userDelete = authorityRepository.save(Authority.builder().permission("user.delete").build());

        roleCreate = authorityRepository.save(Authority.builder().permission("role.create").build());
        roleRead = authorityRepository.save(Authority.builder().permission("role.read").build());
        roleUpdate = authorityRepository.save(Authority.builder().permission("role.update").build());
        roleDelete = authorityRepository.save(Authority.builder().permission("role.delete").build());

        projectCreate = authorityRepository.save(Authority.builder().permission("project.create").build());
        projectRead = authorityRepository.save(Authority.builder().permission("project.read").build());
        projectUpdate = authorityRepository.save(Authority.builder().permission("project.update").build());
        projectDelete = authorityRepository.save(Authority.builder().permission("project.delete").build());

        issueCreate = authorityRepository.save(Authority.builder().permission("issue.create").build());
        issueRead = authorityRepository.save(Authority.builder().permission("issue.read").build());
        issueUpdate = authorityRepository.save(Authority.builder().permission("issue.update").build());
        issueDelete = authorityRepository.save(Authority.builder().permission("issue.delete").build());

        workpackageCreate = authorityRepository.save(Authority.builder().permission("workpackage.create").build());
        workpackageRead = authorityRepository.save(Authority.builder().permission("workpackage.read").build());
        workpackageUpdate = authorityRepository.save(Authority.builder().permission("workpackage.update").build());
        workpackageDelete = authorityRepository.save(Authority.builder().permission("workpackage.delete").build());

        activityCreate = authorityRepository.save(Authority.builder().permission("activity.create").build());
        activityRead = authorityRepository.save(Authority.builder().permission("activity.read").build());
        activityUpdate = authorityRepository.save(Authority.builder().permission("activity.update").build());
        activityDelete = authorityRepository.save(Authority.builder().permission("activity.delete").build());

        /*admin, developer, projectLead, teamLead, manager, administrative*/
        admin = Role.builder()
                .name("administrator")
                .build();

        developer = Role.builder()
                .name("developer")
                .build();

        projectLead = Role.builder()
                .name("project lead")
                .build();

        teamLead = Role.builder()
                .name("team lead")
                .build();

        manager = Role.builder()
                .name("manager")
                .build();

        administrative = Role.builder()
                .name("administration")
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
        roleRepository.saveAll(new HashSet<>(Set.of(admin, developer, projectLead, teamLead, manager, administrative)));

    }

}