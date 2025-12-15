// package edu.tlu.book_store.domain.model;

// import jakarta.persistence.*;
// import lombok.*;

// @Entity
// @Table(name = "book_images")
// @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
// public class BookImage {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "book_image_id")
//     private Long id;

//     @Column(name = "book_id")
//     private String bookId;

//     @ManyToOne
//     @JoinColumn(name = "book_id", insertable = false, updatable = false)
//     private Book book;

//     @Column(name = "base_url")
//     private String baseUrl;

//     @Column(name = "large_url")
//     private String largeUrl;

//     @Column(name = "medium_url")
//     private String mediumUrl;

//     @Column(name = "small_url")
//     private String smallUrl;

//     @Column(name = "thumbnail_url")
//     private String thumbnailUrl;

//     @Column(name = "is_gallery")
//     private Boolean isGallery;

//     private String label;
//     private Integer position;
// }
package edu.tlu.book_store.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_images")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BookImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_image_id")
    private Integer id;

    @Column(name = "book_id")
    private String bookId;

    private String baseUrl;
    private String largeUrl;
    private String mediumUrl;
    private String smallUrl;
    private String thumbnailUrl;

    private Boolean isGallery;
    private String label;
    private Integer position;
}
