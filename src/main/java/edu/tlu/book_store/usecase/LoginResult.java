package edu.tlu.book_store.usecase;

import edu.tlu.book_store.domain.model.User;

public record LoginResult(String token, User user) {}
