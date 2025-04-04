package e_commecre.dto;

import java.util.List;

import e_commecre.entity.Account;
import e_commecre.entity.Cart;
import e_commecre.entity.CartItems;
import e_commecre.service.ProductDetailService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
	
	long id; 
	
	long accountId;
	
	List<CartItemsDto> cartItems;
		
	
	public static CartDto convertToCartDto(Cart cart, ProductDetailService productDetailService) {
		List<CartItemsDto> listCartItemsDtos = cart.getCartItems().stream()
				.map(cartItem -> new CartItemsDto().converToCartItemsDto(cartItem, productDetailService)).toList();
		return CartDto.builder()
			.id(cart.getId())
			.accountId(cart.getAccount().getId())
			.cartItems(listCartItemsDtos)
			.build();
	}
	
}
