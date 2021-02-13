package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.dtos.ProjectDTO;
import sena.activitytracker.acktrack.dtos.WorkpackageDTO;
import sena.activitytracker.acktrack.mappers.WorkpackageMapper;
import sena.activitytracker.acktrack.model.Project;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.repositories.WorkpackageRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class WorkpackageServiceImplTest extends BaseServiceTest{

    @Mock
    WorkpackageRepository workpackageRepository;
    @Mock
    WorkpackageMapper workpackageMapper;

    @InjectMocks
    WorkpackageServiceImpl workpackageService;

    Workpackage review, quality;
    Set<Workpackage> workpackageSet =new HashSet<>();

    @BeforeEach
    void setUp() {
        /*Dummy project init*/
        quality = Workpackage.builder()
                .id(IDONE)
                .name("fix quality")
                .description("fix quality on alpha")
                .build();

        review = Workpackage.builder()
                .id(IDTWO)
                .name("do reviews")
                .description("do reviews on beta")
                .build();


        workpackageSet.add(review);
        workpackageSet.add(quality);
    }

    @Test
    void findAll() {

        /* given*/
        WorkpackageDTO dummyDTO = WorkpackageDTO.builder().build();
        /* when */
        when(workpackageRepository.findAll()).thenReturn(workpackageSet);
        when(workpackageMapper.toWorkpackageDTO(any(Workpackage.class))).thenReturn(dummyDTO); /* findAll returns a Set.
                                                                                This will be overwritten since it is same object.
                                                                                So there is no point in counting objects, count the calls instead*/

        Set<WorkpackageDTO> foundWorkpackages = workpackageService.findAll();

        assertNotNull(foundWorkpackages);
        verify(workpackageRepository, times(1)).findAll();
        verify(workpackageMapper, times(2)).toWorkpackageDTO(any());


    }

    @Test
    void findById() {
        /* given*/
        WorkpackageDTO dummyDTO = WorkpackageDTO.builder().build();

        /* when */
        when(workpackageRepository.findById(any())).thenReturn(Optional.of(review));
        when(workpackageMapper.toWorkpackageDTO(any(Workpackage.class))).thenReturn(dummyDTO);

        Optional<WorkpackageDTO> foundWorkpackageOptional = workpackageService.findById(review.getId());

        assertTrue(foundWorkpackageOptional.isPresent());
        verify(workpackageRepository, times(1)).findById(any());
        verify(workpackageMapper, times(1)).toWorkpackageDTO(any());
    }

    @Test
    void save() {
        /* given*/
        WorkpackageDTO dummyDTO = WorkpackageDTO.builder().build();

        /* when */
        when(workpackageRepository.save(any())).thenReturn(review);
        when(workpackageMapper.toWorkpackage(any(WorkpackageDTO.class))).thenReturn(review);
        when(workpackageMapper.toWorkpackageDTO(any(Workpackage.class))).thenReturn(dummyDTO);


        WorkpackageDTO foundWorkpackage = workpackageService.save(dummyDTO);

        assertNotNull(foundWorkpackage);
        verify(workpackageRepository, times(1)).save(any());
        verify(workpackageMapper, times(1)).toWorkpackageDTO(any());
        verify(workpackageMapper, times(1)).toWorkpackage(any());
    }

    @Test
    void saveAll() {
        Set<WorkpackageDTO> dtoSet = new HashSet<>();
        dtoSet.add(WorkpackageDTO.builder().build());
        dtoSet.add(WorkpackageDTO.builder().build());

        when(workpackageRepository.saveAll(any(Set.class))).thenReturn(workpackageSet);
        when(workpackageMapper.toWorkpackage(any(WorkpackageDTO.class))).thenReturn(review);
        when(workpackageMapper.toWorkpackageDTO(any(Workpackage.class))).thenReturn(dtoSet.stream().findAny().get());

        Set<WorkpackageDTO> foundWorkpackages = workpackageService.saveAll(dtoSet);

        assertNotNull(foundWorkpackages);
        verify(workpackageRepository, times(1)).saveAll(any());
        verify(workpackageMapper, times(2)).toWorkpackage(any());
        verify(workpackageMapper, times(2)).toWorkpackageDTO(any());
    }

    @Test
    void delete() {
        /* given*/
        WorkpackageDTO dummyDTO = WorkpackageDTO.builder().build();

        workpackageService.delete(dummyDTO);

        verify(workpackageRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        workpackageService.deleteById(IDONE);

        verify(workpackageRepository, times(1)).deleteById(any());
    }
}