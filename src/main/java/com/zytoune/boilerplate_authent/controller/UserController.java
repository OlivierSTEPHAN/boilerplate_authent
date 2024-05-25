package com.zytoune.boilerplate_authent.controller;

import com.zytoune.boilerplate_authent.dto.AuthentificationDTO;
import com.zytoune.boilerplate_authent.dto.ResetPasswordDTO;
import com.zytoune.boilerplate_authent.entity.User;
import com.zytoune.boilerplate_authent.security.JwtService;
import com.zytoune.boilerplate_authent.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@RequestMapping(consumes = APPLICATION_JSON_VALUE)
@RestController
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping(path = "inscription")
    public void inscription(@RequestBody User user){
        log.info("Inscription de l'utilisateur :{}", user.getUsername());
        this.userService.inscription(user);
    }

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation){
        log.info("Activation de l'utilisateur :{}", activation.get("code"));
        this.userService.activation(activation);
    }

    @PostMapping(path = "connexion")
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO){
        log.info("Connexion de l'utilisateur : {}", authentificationDTO.username());
        Authentication authentification = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password()));

        if(authentification.isAuthenticated()){
            return this.jwtService.generate(authentificationDTO.username());
        }
        return null;
    }

    @PostMapping(path = "refresh-token")
    public @ResponseBody Map<String, String>  refreshToken(@RequestBody Map<String, String> refreshTokenRequest){
        log.info("Refresh du token");
       return this.jwtService.refreshToken(refreshTokenRequest);
    }

    @PostMapping(path = "update-password")
    public void  updatePassword(@RequestBody Map<String, String> param){
        log.info("Update du password");
        this.userService.updatePassword(param);
    }

    @PostMapping(path = "new-password")
    public void  newPassword(@RequestBody ResetPasswordDTO resetPasswordDTO){
        log.info("Nouveau password");
        this.userService.newPassword(resetPasswordDTO);
    }

    @PostMapping(path = "deconnexion")
    public void deconnexion(){
        log.info("Déconnexion de l'utilisateur");
        this.jwtService.deconnexion();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "users")
    public List<User> getUsers(){
        return this.userService.getUsers();
    }
}
