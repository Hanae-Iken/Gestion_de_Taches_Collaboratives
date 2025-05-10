package ma.ensat.gestion_de_taches_collaboratives.controller;

import jakarta.validation.Valid;
import ma.ensat.gestion_de_taches_collaboratives.dto.JwtResponse;
import ma.ensat.gestion_de_taches_collaboratives.dto.LoginRequest;
import ma.ensat.gestion_de_taches_collaboratives.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Récupérer le JwtResponse complet
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);

        // Créer une Map contenant uniquement le token
        Map<String, String> response = new HashMap<>();
        response.put("token", jwtResponse.getToken());

        // Retourner cette Map
        return ResponseEntity.ok(response);
    }
}