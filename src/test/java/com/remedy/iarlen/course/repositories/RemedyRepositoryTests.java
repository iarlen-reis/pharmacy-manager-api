package com.remedy.iarlen.course.repositories;

import com.remedy.iarlen.course.Remedy.Laboratory;
import com.remedy.iarlen.course.Remedy.Via;
import com.remedy.iarlen.course.models.RemedyModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class RemedyRepositoryTests {

    @Autowired
    private RemedyRepository remedyRepository;

    private RemedyModel remedy;

    @BeforeEach
    public void createRemedy() {
        this.remedy = new RemedyModel("Dipirona", 12, Via.ORAL, Laboratory.ACHE, LocalDate.now(), "1");
    }

    @Test
    public void shouldCreateRemedy() {
        RemedyModel remedySaved = remedyRepository.save(this.remedy);

        Assertions.assertNotNull(remedySaved.getId());
        Assertions.assertNotNull(remedySaved.getName());
        Assertions.assertNotEquals(remedySaved.getAmount(), 0);
        Assertions.assertNotNull(remedySaved.getVia());
        Assertions.assertNotNull(remedySaved.getLaboratory());
        Assertions.assertNotNull(remedySaved.getValidity());
        Assertions.assertNotNull(remedySaved.getBatch());
    }

    @Test
    public void shouldGetARemedyById() {
        this.remedyRepository.save(this.remedy);

        Optional<RemedyModel> remedySaved = this.remedyRepository.findById(this.remedy.getId());

        Assertions.assertNotNull(remedySaved.get().getId());
        Assertions.assertEquals(remedySaved.get().getId(), this.remedy.getId());
    }


    @Test
    public void shouldShowAllRemedy() {
        List<RemedyModel> list = remedyRepository.findAll();
        
        Assertions.assertEquals(list.size(), this.remedyRepository.count());
    }

    @Test
    public void shouldUpdateRemedy() {
        this.remedyRepository.save(this.remedy);

        Optional<RemedyModel> remedySaved = this.remedyRepository.findById(this.remedy.getId());

        remedySaved.get().setName("Calcitran");
        remedySaved.get().setAmount(2);
        remedySaved.get().setVia(Via.RETAL);
        remedySaved.get().setLaboratory(Laboratory.MEDLEY);

        this.remedyRepository.save(remedySaved.get());

        Optional<RemedyModel> remedyUpdated = this.remedyRepository.findById(this.remedy.getId());

        Assertions.assertNotEquals(remedyUpdated.get().getName(), "Dipirona");
        Assertions.assertNotEquals(remedyUpdated.get().getAmount(), 12);
        Assertions.assertNotEquals(remedyUpdated.get().getVia(), Via.ORAL);
        Assertions.assertNotEquals(remedyUpdated.get().getLaboratory(), Laboratory.ACHE);
    }

    @Test
    public void shouldDisableRemedy() {
        this.remedyRepository.save(this.remedy);

        Optional<RemedyModel> remedySaved = this.remedyRepository.findById(this.remedy.getId());

        remedySaved.get().deactivate();
        this.remedyRepository.save(remedy);

        Optional<RemedyModel> remedyUpdated = this.remedyRepository.findById(this.remedy.getId());

        Assertions.assertNotEquals(remedyUpdated.get().isActive(), true);
    }

    @Test
    public void shouldEnableRemedy() {
        this.remedyRepository.save(this.remedy);

        Optional<RemedyModel> remedySaved = this.remedyRepository.findById(this.remedy.getId());

        remedySaved.get().deactivate();
        this.remedyRepository.save(remedy);

        Optional<RemedyModel> remedyUpdated = this.remedyRepository.findById(this.remedy.getId());

        Assertions.assertNotEquals(remedyUpdated.get().isActive(), true);

        remedyUpdated.get().reactivate();

        Optional<RemedyModel> remedyUpdatedAgain = this.remedyRepository.findById(this.remedy.getId());

        Assertions.assertNotEquals(remedyUpdatedAgain.get().isActive(), false);

    }

    @Test
    public void shouldDeleteRemedy() {
        this.remedyRepository.save(this.remedy);
        RemedyModel remedySaved = this.remedyRepository.getReferenceById(this.remedy.getId());

        this.remedyRepository.delete(remedySaved);

        Assertions.assertEquals(this.remedyRepository.findAll().size(), 0);
    }

    @Test
    public void shouldReturnNoneRemedyActive() {
        RemedyModel firstRemedy = this.remedyRepository.save(this.remedy);
        firstRemedy.deactivate();

        Assertions.assertEquals(this.remedyRepository.findAllByActiveTrue().size(), 0);
    }
}
