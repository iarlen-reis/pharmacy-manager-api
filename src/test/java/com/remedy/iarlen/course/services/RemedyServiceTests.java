package com.remedy.iarlen.course.services;

import com.remedy.iarlen.course.Remedy.Laboratory;
import com.remedy.iarlen.course.Remedy.RemedyDTO;
import com.remedy.iarlen.course.Remedy.RemedyWithIdDTO;
import com.remedy.iarlen.course.Remedy.Via;
import com.remedy.iarlen.course.models.RemedyModel;
import com.remedy.iarlen.course.repositories.RemedyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RemedyServiceTests {

    @InjectMocks
    RemedyService remedyService;

    @Mock
    private RemedyRepository remedyRepository;

    @Mock
    private GrantedAuthority grantedAuthority;

    private RemedyModel remedy;

    private  RemedyModel remedyDisabled;


    private RemedyDTO remedyDTO;

    @BeforeEach
    public void setUp() {
        this.remedy = new RemedyModel(1L, "Dipirona", Via.ORAL, "1", 20, LocalDate.now(), Laboratory.ACHE, true);

        this.remedyDisabled = new RemedyModel(2L, "Dorflex", Via.ORAL, "2", 1, LocalDate.now(), Laboratory.MEDLEY, false);

        this.remedyDTO = new RemedyDTO("Dipirona", Via.ORAL, "1", 20, LocalDate.now(), Laboratory.ACHE);
    }

    @Test
    @DisplayName("should create a remedy.")
    public void createRemedy() {
        when(this.remedyRepository.save(ArgumentMatchers.any())).thenReturn(this.remedy);

        RemedyWithIdDTO result = remedyService.createRemedy(this.remedyDTO);

        Assertions.assertEquals(result.id(), this.remedy.getId());
        Assertions.assertEquals(result.name(), this.remedy.getName());
        Assertions.assertEquals(result.via(), this.remedy.getVia());
        Assertions.assertEquals(result.batch(), this.remedy.getBatch());
        Assertions.assertEquals(result.amount(), this.remedy.getAmount());
        Assertions.assertEquals(result.validity(), this.remedy.getValidity());
        Assertions.assertEquals(result.laboratory(), this.remedy.getLaboratory());
    }

    @Test
    @DisplayName("Should list all remedy")
    public void listAllRemedy() {
        when(this.remedyRepository.count()).thenReturn(1L);

        when(this.remedyRepository.findAllByActiveTrue()).thenReturn(List.of(this.remedy));

        List<RemedyWithIdDTO> result = this.remedyService.listAllRemedy();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), this.remedyRepository.count());
    }

    @Test
    @DisplayName("Should get a remedy by id")
    public void getRemedyById() {
        when(this.remedyRepository.getReferenceById(eq(1L))).thenReturn(this.remedy);

        RemedyWithIdDTO result = this.remedyService.getRemedyById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.id(), this.remedy.getId());
        Assertions.assertEquals(result.name(), this.remedy.getName());
        Assertions.assertEquals(result.via(), this.remedy.getVia());
        Assertions.assertEquals(result.batch(), this.remedy.getBatch());
        Assertions.assertEquals(result.amount(), this.remedy.getAmount());
        Assertions.assertEquals(result.validity(), this.remedy.getValidity());
        Assertions.assertEquals(result.laboratory(), this.remedy.getLaboratory());
    }

    @Test
    @DisplayName("Should update a remedy")
    public void updateRemedyById() {
        RemedyDTO data = new RemedyDTO("Dipirona", Via.RETAL, "1", 20, LocalDate.now(), Laboratory.MEDLEY);
        when(this.remedyRepository.getReferenceById(eq(1L))).thenReturn(this.remedy);

        RemedyWithIdDTO result = this.remedyService.updateRemedyById(1L, data);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.via(), data.via());
        Assertions.assertEquals(result.laboratory(), data.laboratory());
    }

    @Test
    @DisplayName("Should delete a remedy by id")
    public void deleteRemedyById() {
        when(this.remedyRepository.getReferenceById(1L)).thenReturn(this.remedy);

        RemedyWithIdDTO result = this.remedyService.deleteRemedyById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.id(), this.remedy.getId());

        Assertions.assertEquals(this.remedyRepository.findAllByActiveTrue().size(), 0);

        verify(this.remedyRepository).delete(this.remedy);

    }

    @Test
    @DisplayName("Should disable a remedy by id")
    public void disableRemedyById() {
        when(this.remedyRepository.getReferenceById(1L)).thenReturn(this.remedy);

        RemedyWithIdDTO result = this.remedyService.disableRemedyById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.active());
        Assertions.assertEquals(this.remedyRepository.findAllByActiveTrue().size(), 0);
    }

    @Test
    @DisplayName("Should enabled a remedy by id")
    public void enableRemedyById() {
        when(this.remedyRepository.getReferenceById(2L)).thenReturn(this.remedyDisabled);

        RemedyWithIdDTO result = this.remedyService.enableRemedyById(2L);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.active());
    }
}
