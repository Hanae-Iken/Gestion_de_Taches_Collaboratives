package ma.ensat.gestion_de_taches_collaboratives.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.ensat.gestion_de_taches_collaboratives.entity.User;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String nom;
    private String email;
    private User.Role role;

    public JwtResponse(String token, Long id, String nom, String email, User.Role role) {
        this.token = token;
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
    }
}