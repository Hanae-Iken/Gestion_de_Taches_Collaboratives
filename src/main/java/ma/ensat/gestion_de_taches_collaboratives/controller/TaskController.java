package ma.ensat.gestion_de_taches_collaboratives.controller;

import jakarta.validation.Valid;
import ma.ensat.gestion_de_taches_collaboratives.dto.CreateTaskRequest;
import ma.ensat.gestion_de_taches_collaboratives.dto.UpdateTaskStatusRequest;
import ma.ensat.gestion_de_taches_collaboratives.entity.Task;
import ma.ensat.gestion_de_taches_collaboratives.service.TaskService;
import ma.ensat.gestion_de_taches_collaboratives.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Task> createTask(
            @Valid @RequestBody CreateTaskRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Task task = taskService.createTask(request, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/my-tasks")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Task>> getMyTasks(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Task> tasks = taskService.getTasksByUser(userDetails.getUser());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @taskSecurity.canAccessTask(#id, authentication)")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("@taskSecurity.canUpdateTask(#id, authentication)")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Task updatedTask = taskService.updateTaskStatus(id, request, userDetails.getUser());
        return ResponseEntity.ok(updatedTask);
    }
}