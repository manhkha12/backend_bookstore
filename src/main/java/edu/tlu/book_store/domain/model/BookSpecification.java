// package edu.tlu.book_store.domain.model;

// import jakarta.persistence.*;
// import lombok.*;
// import java.util.List;

// @Entity
// @Table(name = "book_specifications")
// @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
// public class BookSpecification {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "specification_id")
//     private Long id;

//     @Column(name = "book_id")
//     private String bookId;

//     @ManyToOne
//     @JoinColumn(name = "book_id", insertable = false, updatable = false)
//     private Book book;

//     private String name;

//     @OneToMany(mappedBy = "specification", fetch = FetchType.EAGER)
//     private List<BookAttribute> attributes;
// }
package edu.tlu.book_store.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "book_specifications")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BookSpecification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specification_id")
    private Integer id;

    @Column(name = "book_id")
    private String bookId;

    private String name;

    @OneToMany
    @JoinColumn(name = "specification_id")
    private Set<BookAttribute> attributes;
}
