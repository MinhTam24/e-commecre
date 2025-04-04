package e_commecre.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import e_commecre.dto.CartDto;
import e_commecre.exception.ResouceNotFoundException;
import e_commecre.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

	@Autowired
	CartService cartService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCartById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(cartService.getCartById(id));
	}
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<?> getByCustomerId(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(cartService.getCartByAccountId(id));
	}
	
	@PutMapping("/merge")
	public ResponseEntity<?> mergeCart(@RequestBody CartDto cartDto) {
	    try {
	        CartDto updatedCart = cartService.mergeCart(cartDto);
	        return ResponseEntity.ok(updatedCart);
	    } catch (ResouceNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Có lỗi xảy ra khi merge giỏ hàng." + e);
	    }
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable("id") long id){
		return ResponseEntity.ok(cartService.deleteCartById(id));
	}
	
	
}
