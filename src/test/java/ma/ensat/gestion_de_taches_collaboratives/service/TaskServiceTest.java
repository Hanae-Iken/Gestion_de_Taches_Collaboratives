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
        utilisateur1 = new User();
        utilisateur1.setId(1L);
        utilisateur1.setNom("Utilisateur 1");
        utilisateur1.setEmail("user1@example.com");
        utilisateur1.setRole(User.Role.USER);

        utilisateur2 = new User();
        utilisateur2.setId(2L);
        utilisateur2.setNom("Utilisateur 2");
        utilisateur2.setEmail("user2@example.com");
        utilisateur2.setRole(User.Role.USER);

        task = new Task();
        task.setId(1L);
        task.setTitre("Tâche de test");
        task.setDescription("Description de la tâche de test");
        task.setStatut(Task.Statut.A_FAIRE);
        task.setUtilisateurAssigne(utilisateur1);

        updateRequest = new UpdateTaskStatusRequest();
        updateRequest.setStatut(Task.Statut.EN_COURS);
    }

    @Test
    public void testUpdateTaskStatus_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = taskService.updateTaskStatus(1L, updateRequest, utilisateur1);

        assertNotNull(updatedTask);
        assertEquals(Task.Statut.EN_COURS, updatedTask.getStatut());
    }

    @Test
    public void testUpdateTaskStatus_UnauthorizedUser() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertThrows(UnauthorizedActionException.class, () -> {
            taskService.updateTaskStatus(1L, updateRequest, utilisateur2);
        });
    }

    @Test
    public void testUpdateTaskStatus_TaskNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.updateTaskStatus(99L, updateRequest, utilisateur1);
        });
    }
}