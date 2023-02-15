package com.zeusbe.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSwapDTO {
    private Long productId;

    private String productName;

    private String thumbnail;

    private String username;

    private String uid;

    private String phone;

    private Long reciverProfileId;

    private String avatarUrl;
}
