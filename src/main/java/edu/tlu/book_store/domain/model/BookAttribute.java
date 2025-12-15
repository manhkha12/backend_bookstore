// package edu.tlu.book_store.domain.model;

// import jakarta.persistence.*;
// import lombok.*;

// @Entity
// @Table(name = "book_attributes")
// @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
// public class BookAttribute {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "attribute_id")
//     private Long id;

//     @Column(name = "specification_id")
//     private Long specificationId;

//     @ManyToOne
//     @JoinColumn(name = "specification_id", insertable = false, updatable = false)
//     private BookSpecification specification;

//     private String code;
//     private String name;
//     private String value;
// }
package edu.tlu.book_store.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_attributes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BookAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Integer id;

    @Column(name = "specification_id")
    private Integer specificationId;

    private String code;
    private String name;
    private String value;
}
