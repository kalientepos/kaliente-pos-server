package com.kaliente.pos.application.requests.productcatalogue;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductCatalogueUpdateRequestDto {
	private UUID id;
	private String title;
	private String description;
	private UUID parentCatalogueId;
}
