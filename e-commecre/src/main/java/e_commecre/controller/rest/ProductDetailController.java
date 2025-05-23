package e_commecre.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import e_commecre.dto.ProductDetailDto;
import e_commecre.dto.ProductDto;
import e_commecre.service.ProductDetailService;

@RestController
@RequestMapping("/api/productDetail")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductDetailController {

	@Autowired
	ProductDetailService productDetailService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductDetailById(@PathVariable("id") long id) {
		return ResponseEntity.ok(productDetailService.getProductDetailById(id));
	}

	@PostMapping
	public ResponseEntity<?> createProductDetail(@RequestBody ProductDetailDto dto) {
		return ResponseEntity.ok(productDetailService.createProductDetail(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProductDetail(@RequestBody ProductDetailDto dto) {
		return ResponseEntity.ok(productDetailService.updateProductDetail(dto));
	}

	@GetMapping("/color")
	public ResponseEntity<?> getProductWithIdAndColor(@RequestParam(name = "color") String color,
			@RequestParam(name = "id") long id) {
		return ResponseEntity.ok(productDetailService.getProductByColorAndId(color, id));
	}
	
	@GetMapping("/size")
	public ResponseEntity<?> getProductWithIdAndSize(@RequestParam(name = "size") String size,
			@RequestParam(name = "id") long id) {
		return ResponseEntity.ok(productDetailService.getProductBySizeAndId(size, id));
	}
	
	@GetMapping("/quantity")
	public ResponseEntity<?> getQuantitySize(@RequestParam(name = "size") String size,
			@RequestParam(name = "id") long id) {
		return ResponseEntity.ok(productDetailService.SizeQuantity(id , size));
	}

}
