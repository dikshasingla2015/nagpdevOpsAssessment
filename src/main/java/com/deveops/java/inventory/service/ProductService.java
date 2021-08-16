package com.deveops.java.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deveops.java.inventory.dto.InventoryBEObjectBaseResponse;
import com.deveops.java.inventory.model.Product;

import static com.deveops.java.inventory.utils.ApplicationHelper.getErrorResponse;
import static com.deveops.java.inventory.utils.ApplicationHelper.getSuccessResponse;

@Service
public class ProductService {

	private List<Product> products = new ArrayList<>();

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public InventoryBEObjectBaseResponse addProduct(Product product) {
		int id = products.size() + 1;
		product.setProductId(id);
		products.add(product);
		return getSuccessResponse(product);
	}

	public InventoryBEObjectBaseResponse getProductById(String id) {
		try {
			int productId = Integer.parseInt(id);
			Optional<Product> response = products.stream().filter(product -> product.getProductId() == productId)
					.findFirst();
			return response.isPresent() ? getSuccessResponse(response.get())
					: getErrorResponse("No Product Found By Id.");
		} catch (NumberFormatException exception) {
			return getErrorResponse("Invalid Product Id.");
		}
	}

	public InventoryBEObjectBaseResponse getAllProducts() {
		if (products.size() == 0) {
			return getErrorResponse("No Products Found.");
		}
		return getSuccessResponse(products);
	}

}
