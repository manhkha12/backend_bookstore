package edu.tlu.book_store.presentation;

import edu.tlu.book_store.usecase.LoginUserUseCase;
import edu.tlu.book_store.usecase.RegisterUserUseCase;
import edu.tlu.book_store.usecase.LoginResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase,
                          LoginUserUseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(registerUserUseCase.register(
                req.username, req.email, req.password, req.name
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        // UseCase trả về: token + user
        LoginResult result = loginUserUseCase.login(req.email, req.password);

        // Trả về token + user info
        return ResponseEntity.ok(new LoginResponse(
                result.token(),
                result.user().getId(),
                result.user().getUsername(),
                result.user().getEmail(),
                result.user().getRole()
        ));
    }

    // ============================
    // Request + Response DTO
    // ============================

    record RegisterRequest(String username, String email, String password, String name) {}
    record LoginRequest(String email, String password) {}

    record LoginResponse(String token,String userId ,String username, String email, String role) {}
}
