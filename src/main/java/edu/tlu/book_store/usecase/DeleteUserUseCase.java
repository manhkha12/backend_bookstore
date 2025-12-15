package edu.tlu.book_store.usecase;

import org.springframework.stereotype.Service;

import edu.tlu.book_store.domain.repository.UserRepository;

@Service
public class DeleteUserUseCase {
    private final UserRepository repo;

    public DeleteUserUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public void execute(String id) {
        repo.delete(id);
    }
}
