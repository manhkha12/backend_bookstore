// package edu.tlu.book_store.domain.model;

// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.JoinTable;
// import jakarta.persistence.ManyToMany;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;


// import java.util.Set;

// import jakarta.persistence.Column;
// import lombok.AllArgsConstructor;
// import lombok.Getter;

// import lombok.Setter;
// import lombok.ToString;

// @Entity
// @Getter
// @Setter
// @AllArgsConstructor

// @ToString
// public class Book {

//     @Id
//     @Column(name = "book_id")
//     private String id;

//     private String name;
//     private String description;
//     @Column(name = "short_description")
//     private String shortDescription;

//     @Column(name = "list_price")
//     private Integer listPrice;

//     @Column(name = "original_price")
//     private Integer originalPrice;

//     @Column(name = "rating_average")
//     private Float ratingAverage;

//     @Column(name = "category_id")
//     private String categoryId;

//     public Book() {
//     }
//      // Category
//     @ManyToOne(fetch = FetchType.EAGER)
//     @JoinColumn(name = "category_id", insertable = false, updatable = false)
//     private Category category;

//     // Authors
//     @ManyToMany(fetch = FetchType.EAGER)
//     @JoinTable(
//             name = "book_authors",
//             joinColumns = @JoinColumn(name = "book_id"),
//             inverseJoinColumns = @JoinColumn(name = "author_id")
//     )
//     private Set<Author> authors;

//     // Images
//     @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
//     private Set<BookImage> images;

//     // Specifications
//     @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
//     private Set<BookSpecification> specifications;

//     // Sellers (qua bảng book_seller)
//     @ManyToMany(fetch = FetchType.EAGER)
//     @JoinTable(
//             name = "book_seller",
//             joinColumns = @JoinColumn(name = "book_id"),
//             inverseJoinColumns = @JoinColumn(name = "seller_id")
//     )
//     private Set<Seller> sellers;
// }
package edu.tlu.book_store.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Book {

    @Id
    @Column(name = "book_id")
    private String id;

    private String name;
    private String description;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "list_price")
    private Integer listPrice;

    @Column(name = "original_price")
    private Integer originalPrice;

    @Column(name = "rating_average")
    private Float ratingAverage;

    @Column(name = "category_id")
    private String categoryId;

    // -------------------------------
    // CATEGORY (N-1)
    // -------------------------------
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    // -------------------------------
    // AUTHORS (N-N)
    // -------------------------------
    @ManyToMany
    @JoinTable(
        name = "book_authors",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    // -------------------------------
    // IMAGES (1-N)
    // -------------------------------
    @OneToMany
    @JoinColumn(name = "book_id")
    private Set<BookImage> images;

    // -------------------------------
    // SPECIFICATIONS (1-N)
    // -------------------------------
    @OneToMany
    @JoinColumn(name = "book_id")
    private Set<BookSpecification> specifications;

    // -------------------------------
    // SELLERS (N-N)
    // -------------------------------
    @ManyToMany
    @JoinTable(
        name = "book_seller",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "seller_id")
    )
    private Set<Seller> sellers;
}
