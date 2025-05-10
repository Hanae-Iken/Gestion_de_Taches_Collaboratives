package ma.ensat.gestion_de_taches_collaboratives.security;

import ma.ensat.gestion_de_taches_collaboratives.entity.Task;
import ma.ensat.gestion_de_taches_collaboratives.service.TaskService;
import ma.ensat.gestion_de_taches_collaboratives.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("taskSecurity")
public class TaskSecurity {

    @Autowired
    private TaskService taskService;

    public boolean canAccessTask(Long taskId, Authentication authentication) {
        Task task = taskService.getTaskById(taskId);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return task.getUtilisateurAssigne().getId().equals(userDetails.getId());
    }

    public boolean canUpdateTask(Long taskId, Authentication authentication) {
        Task task = taskService.getTaskById(taskId);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return task.getUtilisateurAssigne().getId().equals(userDetails.getId());
    }
}