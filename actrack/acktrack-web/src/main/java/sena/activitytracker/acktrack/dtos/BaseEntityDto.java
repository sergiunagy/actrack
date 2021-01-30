package sena.activitytracker.acktrack.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public abstract class BaseEntityDto {

    /*READ ONLY VALUES*/
    private UUID id;
    private Long version;
    private Timestamp createdTimestamp;
    private Timestamp updatedTimestamp;

}
