package sena.activitytracker.acktrack.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sena.activitytracker.acktrack.model.Issue;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.model.security.User;
import sena.activitytracker.acktrack.repositories.ActivityRepository;
import sena.activitytracker.acktrack.repositories.IssueRepository;
import sena.activitytracker.acktrack.repositories.ProjectRepository;
import sena.activitytracker.acktrack.repositories.WorkpackageRepository;
import sena.activitytracker.acktrack.repositories.security.UserRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

@Slf4j
@Component
public class bootstrapData_v2 implements CommandLineRunner {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    IssueRepository issueRepository;
    WorkpackageRepository workpackageRepository;
    ActivityRepository activityRepository;


    @Override
    public void run(String... args) throws Exception {
        initProjectsData(10);
    }

    private void initProjectsData(int n_days) {

        String PROJECT_SUFFIX = "Project_";
        int DATE_RANGE_MAX = n_days;
        Set<Project> projects = new HashSet<>();

        IntStream.range(0,5).forEach(id->{
            projects.add(Project.builder()
                    .name(PROJECT_SUFFIX + id)
                    .actualStartDate(LocalDate.now().minusDays(DATE_RANGE_MAX-id))
                    .active(true)
                    .customerName("Client_"+id)
                    .notes("Notes for project_" + id)
                    .description("Project implementation for Project_"+id)
                    .mainLocation("Location project_"+id)
                    .build());
        });
        projectRepository.saveAll(projects);
    }

    private void initUsersData(int n_users) {

        Set<User> users = new HashSet<>();

        IntStream.range(0,n_users).forEach(id->{
            users.add(User.builder()
                    .familyName("Fam_name_"+id)
                    .givenName("Given_name_"+id)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .accountNonExpired(true)
                    .enabled(true)
                    .username("usr_"+id)
                    .build());
        });
        userRepository.saveAll(users);
    }

    private void initIssuesData(int n_issues) {

        String ISSUE_SUFFIX = "Issue_";

        int ISSUE_RANGE_MAX = n_issues;

        Set<Issue> issues = new HashSet<>();

        IntStream.range(0,n_issues).forEach(id->{
            issues.add(Issue.builder()
                    .description("Description " + ISSUE_SUFFIX + id)
                    .createdDate(LocalDate.now().minusDays(ISSUE_RANGE_MAX-id))
                    .build());
        });
        issueRepository.saveAll(issues);
    }

    private void initWorkpackageData(int n_workpackages) {

        String WP_SUFFIX = "Workpackage_";
        int WP_RANGE_MAX = n_workpackages;


        Set<Workpackage> workpackages = new HashSet<>();

        IntStream.range(0,n_workpackages).forEach(id->{
            workpackages.add(Workpackage.builder()
                    .name(WP_SUFFIX+id)
                    .description("Description " + WP_SUFFIX +id)
                    .build());
        });

        workpackageRepository.saveAll(workpackages);
    }

}
