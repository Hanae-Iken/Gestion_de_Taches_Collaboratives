package ma.ensat.gestion_de_taches_collaboratives.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ma.ensat.gestion_de_taches_collaboratives.entity.Task;

@Data
public class UpdateTaskStatusRequest {

    @NotNull(message = "Le statut est obligatoire")
    private Task.Statut statut;
}