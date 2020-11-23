package sena.activitytracker.acktrack.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: why UUID ?

    // why Version ?

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdTimestamp;

    @UpdateTimestamp
    private Timestamp updatedTimestamp;


    /*Returns True if object has an ID allocated*/
    public boolean isNew(){

        return this.id ==null;
    }

    public BaseEntity(Long id, Timestamp createdTimestamp, Timestamp updatedTimestamp) {
        this.id = id;
        this.createdTimestamp = createdTimestamp;
        this.updatedTimestamp = updatedTimestamp;
    }
}
