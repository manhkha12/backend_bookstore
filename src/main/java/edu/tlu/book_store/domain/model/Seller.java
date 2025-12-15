// package edu.tlu.book_store.domain.model;

// import jakarta.persistence.*;
// import lombok.*;

// @Entity
// @Table(name = "sellers")
// @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
// public class Seller {

//     @Id
//     @Column(name = "seller_id")
//     private String id;

//     private String sku;
//     private String name;
//     private String link;
//     private String logo;

//     private Integer price;

//     @Column(name = "product_id")
//     private String productId;

//     @Column(name = "store_id")
//     private String storeId;

//     @Column(name = "is_best_store")
//     private Boolean isBestStore;

//     @Column(name = "is_offline_installment_supported")
//     private Boolean isOfflineInstallmentSupported;
// }
package edu.tlu.book_store.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sellers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Seller {

    @Id
    @Column(name = "seller_id")
    private String id;

    private String sku;
    private String name;
    private String link;
    private String logo;
    private Integer price;
    private String productId;
    private String storeId;

    private Boolean isBestStore;
    private Boolean isOfflineInstallmentSupported;
}
