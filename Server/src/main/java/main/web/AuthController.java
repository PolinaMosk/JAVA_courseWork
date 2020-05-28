package main.web;

import main.entity.User;
import main.repository.UserRepository;
import main.security.jwt.JwtTokenProvider;
import main.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wholesalecomp/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRep;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailsService userController;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public String signIn(@RequestBody AuthRequest request) {
        try {
            User user = (User) userController.loadUserByUsername(request.getUserName());
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }
            String name = request.getUserName();
            String token = jwtTokenProvider.createToken(
                    name,
                    userRep.findUserByUserName(name)
                    .orElseThrow(()->new UsernameNotFoundException("User not found")).getRoles()
            );
            Map<Object, Object> model = new HashMap<>();
            model.put("userName", name);
            model.put("token", token);
            return token;
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
