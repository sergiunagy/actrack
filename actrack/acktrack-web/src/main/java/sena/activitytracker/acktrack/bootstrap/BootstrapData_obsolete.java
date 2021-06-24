package sena.activitytracker.acktrack.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sena.activitytracker.acktrack.model.*;
import sena.activitytracker.acktrack.model.security.Authority;
import sena.activitytracker.acktrack.model.security.Role;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.repositories.ProjectRepository;
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
public class BootstrapData_obsolete implements CommandLineRunner {

    public static String TEXT500 = "Nam quis nulla. Integer malesuada. In in enim a arcu imperdiet malesuada. Sed vel lectus. Donec odio " +
            "urna, tempus molestie, porttitor ut, iaculis quis, sem. Phasellus rhoncus. Aenean id metus id velit ullamcorper " +
            "pulvinar. Vestibulum fermentum tortor id mi. Pellentesque ipsum. Nulla non arcu lacinia neque faucibus fringilla." +
            " Nulla non lectus sed nisl molestie malesuada. Proin in tellus sit amet nibh dignissim sagittis. " +
            "Vivamus luctus egestas leo. Maecenas sollicitudin. Nullam rhoncus aliquam met";

    /*Injected repository handles*/
    private final ProjectRepository projectService;
    private final IssueService issueService;
    private final WorkpackageService workpackageService;
    private final ActivityService activityService;
    private final UserService userService;
    private final RoleService roleService;
    private final AuthorityService authorityService;

    /*Users available*/
    Project alpha, beta, gamma;
    Issue iss1, iss2, iss3, iss4;
    Workpackage wp1, wp2, wp3, wp4;
    Activity qualact, revact, bugfixact;

    User        sergiu, ade, mihai, cicero, maximus;
    Role        admin, developer, projectLead, teamLead, manager, administrative;
    Authority userCreate, userRead, userUpdate, userDelete,
            roleCreate, roleRead, roleUpdate, roleDelete,
            projectCreate, projectRead, projectUpdate, projectDelete,
            issueCreate, issueRead, issueUpdate, issueDelete,
            workpackageCreate, workpackageRead, workpackageUpdate, workpackageDelete,
            activityCreate, activityRead, activityUpdate, activityDelete;


    @Autowired
    public BootstrapData_obsolete(ProjectRepository projectService, IssueService issueService, WorkpackageService workpackageService, UserService userService, RoleService roleService, ActivityService activityService, AuthorityService authorityService) {

//        log.info("VAADIN: bootstrap creator..");
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
       // initData();
        //bootstrapNActivities(10);
    }

}
