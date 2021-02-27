package sena.activitytracker.acktrack.model;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity  implements Serializable {

    /* todo: consider switching to ULID instead of  */
    /* docs : https://thorben-janssen.com/generate-uuids-primary-keys-hibernate/ */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /* Using versioning may also be overkill in the base case: https://www.baeldung.com/jpa-optimistic-locking*/
    /* Optimistic Locking versioning property for securing concurrent access*/
    @Version
    private Long version; /* DO NOT UPDATE. This is handled by the persistence provider*/

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdTimestamp;
    /* Tests show nano or even micro clock precision is not reliably achieved. What we get most of the time is ms period
    snapshots with ns information.
    On PCs "real" precision is low, seems ms or us is what it can "guarantee".
    This is NOT a reliable way of sorting data since parsing a Collection may create identical Timestamps*/

    @UpdateTimestamp
    private Timestamp updatedTimestamp;

    public BaseEntity(Long id, Long version, Timestamp createdTimestamp, Timestamp updatedTimestamp) {

        this.id = id;
        this.version = version;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }

    /*Returns True if object has an ID allocated*/
    public boolean isNew(){

        return this.id ==null;
    }

    @Transient
    static Function<Set,Set> checkedSet = set -> set==null? new HashSet<>():set;

    public Long getId() {
        return this.id;
    }

    public Long getVersion() {
        return this.version;
    }

    public Timestamp getCreatedTimestamp() {
        return this.createdTimestamp;
    }

    public Timestamp getUpdatedTimestamp() {
        return this.updatedTimestamp;
    }

//    @PrePersist
//    protected void onCreate(){
//        /* On PCs "real" precision is low, seems ms or us is what it can "guarantee". So we need to create some unique timestamps
//        * by hand*/
//
//        createdTimestamp = Timestamp.valueOf(Instant.now());
//    }

}
