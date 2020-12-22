package sena.activitytracker.acktrack.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sena.activitytracker.acktrack.model.Workpackage;
import sena.activitytracker.acktrack.repositories.WorkpackageRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class WorkpackageServiceImplTest extends BaseServiceTest{

    @Mock
    WorkpackageRepository workpackageRepository;

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

        when(workpackageRepository.findAll()).thenReturn(workpackageSet);

        Set<Workpackage> foundWorkpackages = workpackageService.findAll();

        assertNotNull(foundWorkpackages);
        assertEquals(2, foundWorkpackages.size());
        verify(workpackageRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        when(workpackageRepository.findById(any())).thenReturn(Optional.of(review));

        Optional<Workpackage> foundWorkpackageOptional = workpackageService.findById(review.getId());

        assertTrue(foundWorkpackageOptional.isPresent());
        assertTrue(review.getId().equals(foundWorkpackageOptional.get().getId()));
        verify(workpackageRepository, times(1)).findById(any());
    }

    @Test
    void save() {

        when(workpackageRepository.save(any(Workpackage.class))).thenReturn(review);

        Workpackage foundWorkpackage = workpackageService.save(review);

        assertNotNull(foundWorkpackage);
        assertTrue(review.getId().equals(foundWorkpackage.getId()));
        verify(workpackageRepository, times(1)).save(any());
    }

    @Test
    void saveAll() {
        when(workpackageRepository.saveAll(any(Set.class))).thenReturn(workpackageSet);

        Set<Workpackage> foundWorkpackages = workpackageService.saveAll(workpackageSet);

        assertNotNull(foundWorkpackages);
        assertEquals(2, foundWorkpackages.size());
        verify(workpackageRepository, times(1)).saveAll(any());
    }

    @Test
    void delete() {

        workpackageService.delete(review);

        verify(workpackageRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {

        workpackageService.deleteById(IDONE);

        verify(workpackageRepository, times(1)).deleteById(any());
    }
}