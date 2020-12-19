package sena.activitytracker.acktrack.model.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseSecurityEntity {
    /* Using a UUID is an overkill in the base use case. But it also has no observable drawbacks */
    /* docs : https://thorben-janssen.com/generate-uuids-primary-keys-hibernate/ */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name="UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    /* Using versioning may also be overkill in the base case: https://www.baeldung.com/jpa-optimistic-locking*/
    /* Optimistic Locking versioning property for securing concurrent access*/
    @Version
    private Long version; /* DO NOT UPDATE. This is handled by the persistence provider*/

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdTimestamp;

    @UpdateTimestamp
    private Timestamp updatedTimestamp;


    /*Returns True if object has an ID allocated*/
    public boolean isNew(){

        return this.id ==null;
    }

    public BaseSecurityEntity(UUID id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp) {

        this.id = id;
        this.version = version;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }

    @Transient
    public static Function<Set,Set> checkedSet = set -> set==null? new HashSet<>():set;

}
