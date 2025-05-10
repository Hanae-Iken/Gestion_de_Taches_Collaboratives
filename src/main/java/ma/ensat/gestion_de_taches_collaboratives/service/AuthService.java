package ma.ensat.gestion_de_taches_collaboratives.service;

import ma.ensat.gestion_de_taches_collaboratives.dto.JwtResponse;
import ma.ensat.gestion_de_taches_collaboratives.dto.LoginRequest;
import ma.ensat.gestion_de_taches_collaboratives.entity.User;
import ma.ensat.gestion_de_taches_collaboratives.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = ((UserDetailsImpl) userDetails).getUser();

        return new JwtResponse(
                jwt,
                user.getId(),
                user.getNom(),
                user.getEmail(),
                user.getRole()
        );
    }
}