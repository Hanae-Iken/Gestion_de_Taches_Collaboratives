package ma.ensat.gestion_de_taches_collaboratives.config;

import ma.ensat.gestion_de_taches_collaboratives.entity.User;
import ma.ensat.gestion_de_taches_collaboratives.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @Value("${app.admin.default-email:admin@system.com}")
    private String defaultAdminEmail;

    @Value("${app.admin.default-name:System Admin}")
    private String defaultAdminName;

    @Bean
    public CommandLineRunner initializeAdmin() {
        return args -> {
            if (userRepository.countByRole(User.Role.ADMIN) == 0) {
                String adminPassword = environment.getProperty("APP_ADMIN_PASSWORD");
                boolean isGeneratedPassword = false;

                if (adminPassword == null || adminPassword.trim().isEmpty()) {
                    adminPassword = generateSecurePassword();
                    isGeneratedPassword = true;
                }

                User admin = new User();
                admin.setNom(defaultAdminName);
                admin.setEmail(defaultAdminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(User.Role.ADMIN);

                userRepository.save(admin);

                if (isGeneratedPassword) {
                    System.out.println("=================================================================");
                    System.out.println("UN ADMINISTRATEUR PAR DÉFAUT A ÉTÉ CRÉÉ AVEC LES IDENTIFIANTS :");
                    System.out.println("Email: " + defaultAdminEmail);
                    System.out.println("Mot de passe: " + adminPassword);
                    System.out.println("VEUILLEZ CHANGER CE MOT DE PASSE IMMÉDIATEMENT APRÈS LA CONNEXION");
                    System.out.println("=================================================================");
                }
            }
        };
    }

    private String generateSecurePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        StringBuilder sb = new StringBuilder();
        java.security.SecureRandom random = new java.security.SecureRandom();

        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }
}