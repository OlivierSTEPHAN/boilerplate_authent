package com.zytoune.boilerplate_authent.controller;

import com.zytoune.boilerplate_authent.entity.Avis;
import com.zytoune.boilerplate_authent.entity.User;
import com.zytoune.boilerplate_authent.service.AvisService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("avis")
@RestController
public class AvisController {
    private final AvisService avisService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void creer(@RequestBody Avis avis){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        avis.setUser(user);
        this.avisService.creerAvis(avis);
    }

    @GetMapping
    public List<Avis> liste(){
        return this.avisService.getAvis();
    }
}
