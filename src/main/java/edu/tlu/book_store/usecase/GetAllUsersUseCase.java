package edu.tlu.book_store.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.tlu.book_store.domain.model.User;
import edu.tlu.book_store.domain.repository.UserRepository;

@Service
public class GetAllUsersUseCase {
    private final UserRepository repo;

    public GetAllUsersUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> execute() {
        return repo.findAll();
    }
}

