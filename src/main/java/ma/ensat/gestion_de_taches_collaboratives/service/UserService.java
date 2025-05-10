package ma.ensat.gestion_de_taches_collaboratives.service;

import ma.ensat.gestion_de_taches_collaboratives.dto.ChangePasswordRequest;
import ma.ensat.gestion_de_taches_collaboratives.dto.CreateUserRequest;
import ma.ensat.gestion_de_taches_collaboratives.entity.User;
import ma.ensat.gestion_de_taches_collaboratives.exception.ResourceNotFoundException;
import ma.ensat.gestion_de_taches_collaboratives.exception.UnauthorizedActionException;
import ma.ensat.gestion_de_taches_collaboratives.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }

        User user = new User();
        user.setNom(request.getNom());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.USER);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id: " + id));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User createAdministrator(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }

        User admin = new User();
        admin.setNom(request.getNom());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(User.Role.ADMIN);

        return userRepository.save(admin);
    }

    public User changePassword(Long userId, ChangePasswordRequest request, User currentUser) {
        if (!currentUser.getId().equals(userId)) {
            throw new UnauthorizedActionException("Vous n'êtes pas autorisé à modifier ce mot de passe");
        }

        if (!request.getNouveauMotDePasse().equals(request.getConfirmationMotDePasse())) {
            throw new IllegalArgumentException("Le nouveau mot de passe et sa confirmation ne correspondent pas");
        }

        if (!passwordEncoder.matches(request.getAncienMotDePasse(), currentUser.getPassword())) {
            throw new BadCredentialsException("L'ancien mot de passe est incorrect");
        }

        currentUser.setPassword(passwordEncoder.encode(request.getNouveauMotDePasse()));

        return userRepository.save(currentUser);
    }
}