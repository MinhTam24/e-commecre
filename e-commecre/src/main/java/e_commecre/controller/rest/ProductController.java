package e_commecre.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import e_commecre.dto.ProductDto;
import e_commecre.repository.ProductRepository;
import e_commecre.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	ProductService productService;

	@GetMapping("/api/product/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable("id") long id){
		try {
			return ResponseEntity.ok(productService.getProductById(id));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		
	} 
	
	@GetMapping("/api/category/{id}")
	public ResponseEntity<List<ProductDto>> getProductByCategoryId(@PathVariable("id") long id){
		try {
			return ResponseEntity.ok(productService.getListProductByCategoryId(id));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		
		
	}
	
}
