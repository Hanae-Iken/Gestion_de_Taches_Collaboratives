package ma.ensat.gestion_de_taches_collaboratives.service;

import ma.ensat.gestion_de_taches_collaboratives.dto.UpdateTaskStatusRequest;
import ma.ensat.gestion_de_taches_collaboratives.entity.Task;
import ma.ensat.gestion_de_taches_collaboratives.entity.User;
import ma.ensat.gestion_de_taches_collaboratives.exception.ResourceNotFoundException;
import ma.ensat.gestion_de_taches_collaboratives.exception.UnauthorizedActionException;
import ma.ensat.gestion_de_taches_collaboratives.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskService taskService;

    private User utilisateur1;
    private User utilisateur2;
    private Task task;
    private UpdateTaskStatusRequest updateRequest;

    @BeforeEach
    public void setup() {
        // Configuration de l'utilisateur 1
        utilisateur1 = new User();
        utilisateur1.setId(1L);
        utilisateur1.setNom("Utilisateur 1");
        utilisateur1.setEmail("user1@example.com");
        utilisateur1.setRole(User.Role.USER);

        // Configuration de l'utilisateur 2
        utilisateur2 = new User();
        utilisateur2.setId(2L);
        utilisateur2.setNom("Utilisateur 2");
        utilisateur2.setEmail("user2@example.com");
        utilisateur2.setRole(User.Role.USER);

        // Configuration de la tâche
        task = new Task();
        task.setId(1L);
        task.setTitre("Tâche de test");
        task.setDescription("Description de la tâche de test");
        task.setStatut(Task.Statut.A_FAIRE);
        task.setUtilisateurAssigne(utilisateur1);

        // Configuration de la requête de mise à jour
        updateRequest = new UpdateTaskStatusRequest();
        updateRequest.setStatut(Task.Statut.EN_COURS);
    }

    @Test
    public void testUpdateTaskStatus_Success() {
        // Configuration du mock repository pour retourner la tâche lors de la recherche
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        // Configuration du mock repository pour retourner la tâche mise à jour
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Exécution de la méthode à tester
        Task updatedTask = taskService.updateTaskStatus(1L, updateRequest, utilisateur1);

        // Vérifications
        assertNotNull(updatedTask);
        assertEquals(Task.Statut.EN_COURS, updatedTask.getStatut());
    }

    @Test
    public void testUpdateTaskStatus_UnauthorizedUser() {
        // Configuration du mock repository pour retourner la tâche lors de la recherche
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Vérification que l'exception est bien lancée lorsqu'un utilisateur non assigné tente de mettre à jour la tâche
        assertThrows(UnauthorizedActionException.class, () -> {
            taskService.updateTaskStatus(1L, updateRequest, utilisateur2);
        });
    }

    @Test
    public void testUpdateTaskStatus_TaskNotFound() {
        // Configuration du mock repository pour ne pas trouver la tâche
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Vérification que l'exception est bien lancée lorsque la tâche n'existe pas
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.updateTaskStatus(99L, updateRequest, utilisateur1);
        });
    }
}