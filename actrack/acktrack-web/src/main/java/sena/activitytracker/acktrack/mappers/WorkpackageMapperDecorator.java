package sena.activitytracker.acktrack.mappers;

import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class WorkpackageMapperDecorator implements WorkpackageMapper {

    private WorkpackageMapper workpackageMapper;

    @Autowired
    @Qualifier("delegate")
    public void setWorkpackageMapper(WorkpackageMapper workpackageMapper) {
        this.workpackageMapper = workpackageMapper;
    }


}
