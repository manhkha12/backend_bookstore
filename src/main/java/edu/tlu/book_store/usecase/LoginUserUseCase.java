package edu.tlu.book_store.usecase;

import edu.tlu.book_store.domain.model.User;
import edu.tlu.book_store.domain.repository.UserRepository;
import edu.tlu.book_store.infrastructure.sercurity.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginUserUseCase(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResult login(String email, String password) {

        System.out.println("👉 [Login] Request received:");
        System.out.println("   email = " + email);
        System.out.println("   input password = " + password);

        // 1. Tìm user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        System.out.println("✅ User found: userId=" + user.getId() + ", email=" + user.getEmail());

        // 2. Kiểm tra password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        // 3. ✅ Tạo token VỚI userId
        String token = jwtService.generateTokenWithUserId(
            user.getId(),  // ← QUAN TRỌNG: userId từ database
            user.getEmail()
        );

        System.out.println("🔐 Token generated with userId: " + user.getId());

        // 4. Trả về token + user
        return new LoginResult(token, user);
    }
}