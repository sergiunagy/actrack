package sena.activitytracker.acktrack.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDTO extends BaseEntityDto implements Comparable<ProjectDTO>{

    private String name;
    private String description;
    private String notes;
    private String mainLocation;
    private LocalDate plannedStartDate;
    private LocalDate actualStartDate;
    private LocalDate plannedEndDate;
    private LocalDate actualEndDate;
    private LocalDate plannedSopDate;
    private LocalDate actualSopDate;
    private String customerName;
    private String customerId;
    private String productLine;
    private Boolean active;

    private int noOpenIssues;
    private int noTeamMembers;
    private int hoursWorked;

    @Builder
    public ProjectDTO(String name, String description, String notes, String mainLocation, LocalDate plannedStartDate, LocalDate actualStartDate, LocalDate plannedEndDate, LocalDate actualEndDate, LocalDate plannedSopDate, LocalDate actualSopDate, String customerName, String customerId, String productLine, Boolean active, int noOpenIssues, int noTeamMembers, int hoursWorked) {
        this.name = name;
        this.description = description;
        this.notes = notes;
        this.mainLocation = mainLocation;
        this.plannedStartDate = plannedStartDate;
        this.actualStartDate = actualStartDate;
        this.plannedEndDate = plannedEndDate;
        this.actualEndDate = actualEndDate;
        this.plannedSopDate = plannedSopDate;
        this.actualSopDate = actualSopDate;
        this.customerName = customerName;
        this.customerId = customerId;
        this.productLine = productLine;
        this.active = active;
        this.noOpenIssues = noOpenIssues;
        this.noTeamMembers = noTeamMembers;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public int compareTo(ProjectDTO comparedProject) {
        return actualStartDate.compareTo(comparedProject.actualStartDate);
    }
}
