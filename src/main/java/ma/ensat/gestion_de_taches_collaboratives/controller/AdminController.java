package ma.ensat.gestion_de_taches_collaboratives.controller;

import jakarta.validation.Valid;
import ma.ensat.gestion_de_taches_collaboratives.dto.CreateUserRequest;
import ma.ensat.gestion_de_taches_collaboratives.entity.User;
import ma.ensat.gestion_de_taches_collaboratives.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createAdministrator(@Valid @RequestBody CreateUserRequest request) {
        User admin = userService.createAdministrator(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(admin);
    }
}
