package edu.tlu.book_store.infrastructure.exeption;




import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // Xử lý lỗi authentication (chưa login, token hết hạn)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of(
                "error", "Unauthorized",
                "message", "Vui lòng đăng nhập để tiếp tục",
                "requireLogin", true
            ));
    }
    
    // Xử lý lỗi phân quyền (đã login nhưng không đủ quyền)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(Map.of(
                "error", "Forbidden",
                "message", "Bạn không có quyền truy cập"
            ));
    }
    
    // Xử lý lỗi business logic
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of(
                "error", "Bad Request",
                "message", ex.getMessage()
            ));
    }
    
    // Xử lý lỗi chung không xác định
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of(
                "error", "Internal Server Error",
                "message", "Đã có lỗi xảy ra, vui lòng thử lại sau"
            ));
    }
}