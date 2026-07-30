package com.gcg.orderservice.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
	private String productId;
    private int quantity;
}
