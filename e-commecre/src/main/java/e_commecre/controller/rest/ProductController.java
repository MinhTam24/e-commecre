package e_commecre.controller.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import e_commecre.dto.ProductDto;
import e_commecre.entity.Product;
import e_commecre.repository.ProductRepository;
import e_commecre.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
	
	@Autowired
	ProductService productService;

	@GetMapping("/api/product/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable("id") long id){
			ProductDto productDto =  productService.getProductById(id);
			return ResponseEntity.ok(productDto);
	} 
	
	@GetMapping("/api/products/filter")
	public ResponseEntity<Page<ProductDto>> filterProducts(
	        @RequestParam(name = "keyword", required = false) String keyword,
	        @RequestParam(name = "categoryId", required = false) List<Long> categoryIds,
	        @RequestParam(name = "colors", required = false) List<String> colors, 
	        @RequestParam(name = "sizes", required = false) List<String> sizes, 
	        @RequestParam(name = "minPrice", required = false) Double minPrice,
	        @RequestParam(name = "maxPrice", required = false) Double maxPrice,
	        @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
	        @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection,
	        @RequestParam(name = "page", defaultValue = "0") int page,
	        @RequestParam(name = "size", defaultValue = "10") int size) {

	    Page<ProductDto> result = productService.filterProducts(
	            keyword, categoryIds, colors,sizes, minPrice, maxPrice, sortBy, sortDirection, page, size);

	    return ResponseEntity.ok(result);
	}

	
	@GetMapping("/api/product/all")
	public ResponseEntity<?> getALLProduct(
			@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "2") int pageSize){
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		return ResponseEntity.ok(productService.getAllProduct(pageRequest));
	}
	
	@GetMapping("/api/all-color")
	public ResponseEntity<?> getAllColor(){
		return ResponseEntity.ok(productService.getAllColor());
	}

	
	@PostMapping("/api/create/product")
	public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){
			return ResponseEntity.ok(productService.createProduct(productDto));
	}
	
	@GetMapping("/api/category/{id}")
	public ResponseEntity<?> getProductByCategoryId(
			@PathVariable("id") long id,
			 @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
		     @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
		
			PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
			Page<ProductDto> pageProductDto = productService.getListProductByCategoryId(id, pageRequest);
			
			return ResponseEntity.ok(pageProductDto);
	}
	
}
