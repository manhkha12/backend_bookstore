package edu.tlu.book_store.presentation.dto;

import lombok.Data;



@Data
public class AddToCartRequest {
    private String bookId;
    // private String userId;
    private String sellerId;
    private int quantity;
    private int price;
}


