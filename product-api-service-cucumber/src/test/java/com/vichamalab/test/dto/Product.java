package com.vichamalab.test.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class Product {
	private Long id;
	private String name;
	private String sku;
	private String description;
	private float price;
}
