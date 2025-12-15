package edu.tlu.book_store.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Category {

    @Id
    @Column(name = "category_id")
    private String id;

    private String name;
}
