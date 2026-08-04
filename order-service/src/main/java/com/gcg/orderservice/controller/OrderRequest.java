package com.gcg.orderservice.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
	private Long productId;
    private int quantity;
}
