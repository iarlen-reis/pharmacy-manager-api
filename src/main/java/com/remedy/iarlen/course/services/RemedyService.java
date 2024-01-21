package com.remedy.iarlen.course.services;

import com.remedy.iarlen.course.Remedy.RemedyDTO;
import com.remedy.iarlen.course.Remedy.RemedyWithIdDTO;
import com.remedy.iarlen.course.models.RemedyModel;
import com.remedy.iarlen.course.repositories.RemedyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemedyService {
    @Autowired
    private RemedyRepository remedyRepository;


    public RemedyWithIdDTO createRemedy(RemedyDTO data){
        RemedyModel remedy = remedyRepository.save(new RemedyModel(data));

        return new RemedyWithIdDTO(remedy);
    }

    public List<RemedyWithIdDTO> listAllRemedy() {
        return remedyRepository.findAllByActiveTrue().stream().map(RemedyWithIdDTO::new).toList();
    }

    @Transactional
    public RemedyWithIdDTO getRemedyById(Long id){
        return new RemedyWithIdDTO(this.remedyRepository.getReferenceById(id));
    }

    @Transactional
    public RemedyWithIdDTO updateRemedyById(Long id, RemedyDTO data){
        RemedyModel remedy = this.remedyRepository.getReferenceById(id);
        remedy.updateData(data);

        return new RemedyWithIdDTO(remedy);
    }

    @Transactional
    public RemedyWithIdDTO deleteRemedyById(Long id){
        RemedyModel remedy = this.remedyRepository.getReferenceById(id);
        this.remedyRepository.delete(remedy);

        return new RemedyWithIdDTO(remedy);
    }

    @Transactional
    public RemedyWithIdDTO disableRemedyById(Long id){
        RemedyModel remedy = this.remedyRepository.getReferenceById(id);
        remedy.deactivate();

        return new RemedyWithIdDTO(remedy);
    }

    @Transactional
    public RemedyWithIdDTO enableRemedyById(Long id){
        RemedyModel remedy = this.remedyRepository.getReferenceById(id);
        remedy.reactivate();

        return new RemedyWithIdDTO(remedy);
    }
}
