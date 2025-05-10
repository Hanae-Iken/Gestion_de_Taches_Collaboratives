package ma.ensat.gestion_de_taches_collaboratives.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTaskRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "L'ID de l'utilisateur assigné est obligatoire")
    private Long utilisateurAssigneId;
}