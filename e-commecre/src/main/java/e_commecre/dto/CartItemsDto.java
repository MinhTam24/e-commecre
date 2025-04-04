package e_commecre.dto;

import org.springframework.beans.factory.annotation.Autowired;

import e_commecre.entity.Cart;
import e_commecre.entity.CartItems;
import e_commecre.entity.ProductDetail;
import e_commecre.service.ProductDetailService;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsDto {
	long id;

	long cartId;
	
	String name;

	ProductDetailDto productDetailDto;

	String size;

	int quantity;
	
	String imageUrl;

	double price;
		
	public  CartItemsDto converToCartItemsDto(CartItems cartItems, ProductDetailService productDetailService) {
        int stockQuantity = productDetailService.SizeQuantity(cartItems.getProductDetail().getId(), cartItems.getSize());

		
		ProductDetailDto productDetailDto = ProductDetailDto.builder()
				.id(cartItems.getProductDetail().getId())
				.color(cartItems.getProductDetail().getColor())
				.price(cartItems.getPrice())
				.stockQuantity(stockQuantity)
				.build();
				
		return CartItemsDto.builder()
				.imageUrl(cartItems.getProductDetail().getImageUrl().get(0))
				.id(cartItems.getId())
				.cartId(cartItems.getCart().getId())
				.price(cartItems.getPrice())
				.name(cartItems.getProductDetail().getProductId().getName())
				.quantity(cartItems.getQuantity())
				.size(cartItems.getSize())
				.productDetailDto(productDetailDto)
				.build();
	}
	
}
