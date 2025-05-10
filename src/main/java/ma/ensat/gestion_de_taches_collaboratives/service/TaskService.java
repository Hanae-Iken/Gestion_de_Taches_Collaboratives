package ma.ensat.gestion_de_taches_collaboratives.service;

import ma.ensat.gestion_de_taches_collaboratives.dto.CreateTaskRequest;
import ma.ensat.gestion_de_taches_collaboratives.dto.UpdateTaskStatusRequest;
import ma.ensat.gestion_de_taches_collaboratives.entity.Task;
import ma.ensat.gestion_de_taches_collaboratives.entity.User;
import ma.ensat.gestion_de_taches_collaboratives.exception.ResourceNotFoundException;
import ma.ensat.gestion_de_taches_collaboratives.exception.UnauthorizedActionException;
import ma.ensat.gestion_de_taches_collaboratives.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task createTask(CreateTaskRequest request, User currentUser) {
        User assignedUser = userService.getUserById(request.getUtilisateurAssigneId());

        Task task = new Task();
        task.setTitre(request.getTitre());
        task.setDescription(request.getDescription());
        task.setStatut(Task.Statut.A_FAIRE);
        task.setUtilisateurAssigne(assignedUser);

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUtilisateurAssigne(user);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tâche non trouvée avec l'id: " + id));
    }

    public Task updateTaskStatus(Long taskId, UpdateTaskStatusRequest request, User currentUser) {
        Task task = getTaskById(taskId);

        if (!task.getUtilisateurAssigne().getId().equals(currentUser.getId())) {
            throw new UnauthorizedActionException("Vous n'êtes pas autorisé à mettre à jour cette tâche");
        }

        task.setStatut(request.getStatut());
        return taskRepository.save(task);
    }
}