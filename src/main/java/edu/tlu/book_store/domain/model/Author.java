package edu.tlu.book_store.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authors")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Author {

    @Id
    @Column(name = "author_id")
    private String id;

    private String name;
    private String slug;
}
