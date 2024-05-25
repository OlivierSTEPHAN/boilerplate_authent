package com.zytoune.boilerplate_authent.service;

import com.zytoune.boilerplate_authent.entity.Avis;
import com.zytoune.boilerplate_authent.repository.AvisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AvisService {

    private final AvisRepository avisRepository;

    public void creerAvis(Avis avis){
        this.avisRepository.save(avis);
    }

    public List<Avis> getAvis() {
        return avisRepository.findAll();
    }
}
