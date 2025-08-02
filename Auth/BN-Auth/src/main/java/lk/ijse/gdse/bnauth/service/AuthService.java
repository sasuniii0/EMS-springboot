package lk.ijse.gdse.bnauth.service;

import lk.ijse.gdse.bnauth.dto.AuthDTO;
import lk.ijse.gdse.bnauth.dto.AuthResponse;
import lk.ijse.gdse.bnauth.dto.UserDTO;
import lk.ijse.gdse.bnauth.entity.Role;
import lk.ijse.gdse.bnauth.entity.User;
import lk.ijse.gdse.bnauth.repository.UserRepository;
import lk.ijse.gdse.bnauth.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AuthResponse authenticate(AuthDTO authDTO){
        User user = userRepository.findByUsername(authDTO.getUsername()).orElseThrow(()-> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(authDTO.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }
        String token = jwtUtil.generateToken(authDTO.getUsername());
        return  new AuthResponse(token);
    }
    public String register(UserDTO userDTO){
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new RuntimeException("User Already exists");
        }
        User user = User.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return "User registered successfully";
    }
}
