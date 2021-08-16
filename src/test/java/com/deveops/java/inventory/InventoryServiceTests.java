package com.deveops.java.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.deveops.java.inventory.dto.InventoryBEObjectBaseResponse;
import com.deveops.java.inventory.model.Product;
import com.deveops.java.inventory.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceTests {

	private List<Product> products;

	private InventoryBEObjectBaseResponse inventoryResponseSuccess;

	private InventoryBEObjectBaseResponse inventoryResponseError;

	@InjectMocks
	private ProductService productService;

	@Before
	public void init() {
		products = getListOfProducts();
		inventoryResponseSuccess = getSuccessResponse();
		inventoryResponseError = getErrorResponse();
	}

	@Test
	public void getAllProductsSuccess() {
		inventoryResponseSuccess.setData(products);
		productService.setProducts(products);
		InventoryBEObjectBaseResponse response = productService.getAllProducts();
		assertEquals("200", response.getCode());
		assertEquals(false, response.isError());
	}

	@Test
	public void getAllProductsWhenListIsEmpty() {
		productService.setProducts(Collections.emptyList());
		inventoryResponseError.setErrorMessage("No Products Found.");

		InventoryBEObjectBaseResponse response = productService.getAllProducts();
		assertEquals("101", response.getCode());
		assertEquals(true, response.isError());
		assertEquals("No Products Found.", response.getErrorMessage());
	}

	@Test
	public void addProduct() {
		Product product = new Product(1, "samsung", 3000, 24);
		inventoryResponseSuccess.setData(product);
		InventoryBEObjectBaseResponse response = productService.addProduct(product);
		assertEquals("200", response.getCode());
		assertEquals(false, response.isError());
	}
	
	@Test
	public void getProductByIdWhenIdIsInvalid() {
		productService.setProducts(products);
		inventoryResponseError.setData("Invalid Product Id.");
		InventoryBEObjectBaseResponse response = productService.getProductById("text");
		assertEquals("101", response.getCode());
		assertEquals(true, response.isError());
		assertEquals("Invalid Product Id.", response.getErrorMessage());
	}
	
	@Test
	public void getProductByIdWhenProductIsNotFound() {
		productService.setProducts(products);
		inventoryResponseError.setData("No Product Found By Id.");
		InventoryBEObjectBaseResponse response = productService.getProductById("3");
		assertEquals("101", response.getCode());
		assertEquals(true, response.isError());
		assertEquals("No Product Found By Id.", response.getErrorMessage());
	}
	
	@Test
	public void getProductByIdSuccess() {
		productService.setProducts(products);
		inventoryResponseError.setData(products.get(0));
		InventoryBEObjectBaseResponse response = productService.getProductById("1");
		assertEquals("200", response.getCode());
		assertEquals(false, response.isError());
	}

	private List<Product> getListOfProducts() {
		List<Product> products = new ArrayList<>();
		products.add(new Product(1, "Mobile", 3000, 24));
		products.add(new Product(2, "Air Purifier", 5000, 10));
		return products;
	}

	private InventoryBEObjectBaseResponse getSuccessResponse() {
		InventoryBEObjectBaseResponse response = new InventoryBEObjectBaseResponse();
		response.setCode("200");
		response.setError(false);
		return response;
	}

	private InventoryBEObjectBaseResponse getErrorResponse() {
		InventoryBEObjectBaseResponse response = new InventoryBEObjectBaseResponse();
		response.setCode("101");
		response.setError(true);
		return response;
	}

}
