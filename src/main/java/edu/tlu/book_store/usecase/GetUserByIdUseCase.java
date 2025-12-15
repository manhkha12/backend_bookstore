package edu.tlu.book_store.usecase;

import org.springframework.stereotype.Service;

import edu.tlu.book_store.domain.model.User;
import edu.tlu.book_store.domain.repository.UserRepository;

@Service
public class GetUserByIdUseCase {
    private final UserRepository repo;

    public GetUserByIdUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public User execute(String id) {
        return repo.findById(id);
    }
}

