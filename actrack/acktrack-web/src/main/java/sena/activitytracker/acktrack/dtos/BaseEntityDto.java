package sena.activitytracker.acktrack.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntityDto {

    private UUID id;
    private Long version;
    private Timestamp createdTimestamp;
    private Timestamp updatedTimestamp;

}
