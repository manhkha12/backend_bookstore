package edu.tlu.book_store.presentation;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tlu.book_store.usecase.CreateUserUseCase;
import edu.tlu.book_store.usecase.DeleteUserUseCase;
import edu.tlu.book_store.usecase.GetAllUsersUseCase;
import edu.tlu.book_store.usecase.GetUserByIdUseCase;
import edu.tlu.book_store.usecase.UpdateUserUseCase;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(
        GetAllUsersUseCase getAllUsersUseCase,
        GetUserByIdUseCase getUserByIdUseCase,
        CreateUserUseCase createUserUseCase,
        UpdateUserUseCase updateUserUseCase,
        DeleteUserUseCase deleteUserUseCase
    ) {
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @GetMapping
    public Object getAll() {
        return getAllUsersUseCase.execute();
    }

    @GetMapping("/{id}")
    public Object getUser(@PathVariable String id) {
        return getUserByIdUseCase.execute(id);
    }

    @PostMapping
    public Object create(@RequestBody Map<String, String> body) {
        return createUserUseCase.execute(body);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable String id, @RequestBody Map<String, String> body) {
        return updateUserUseCase.execute(id, body);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        deleteUserUseCase.execute(id);
    }
}
