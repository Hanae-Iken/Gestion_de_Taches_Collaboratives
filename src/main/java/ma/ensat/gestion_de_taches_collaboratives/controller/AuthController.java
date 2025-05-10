package ma.ensat.gestion_de_taches_collaboratives.controller;

import jakarta.validation.Valid;
import ma.ensat.gestion_de_taches_collaboratives.dto.JwtResponse;
import ma.ensat.gestion_de_taches_collaboratives.dto.LoginRequest;
import ma.ensat.gestion_de_taches_collaboratives.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}