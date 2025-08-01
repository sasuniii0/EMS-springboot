package lk.ijse.gdse.bnauth.controller;

import lk.ijse.gdse.bnauth.dto.APIResponse;
import lk.ijse.gdse.bnauth.dto.AuthDTO;
import lk.ijse.gdse.bnauth.dto.UserDTO;
import lk.ijse.gdse.bnauth.entity.User;
import lk.ijse.gdse.bnauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> registerUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(new APIResponse(
                200,
                "User registered successfully",
                authService.register(userDTO)
        ));
    }

    @PostMapping("/signin")
    public ResponseEntity<APIResponse> login(@RequestBody AuthDTO authDTO){
        return ResponseEntity.ok(new APIResponse(
                200,
                "User authenticated successfully",
                authService.authenticate(authDTO)
        ));
    }
}
