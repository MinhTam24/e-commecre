package e_commecre.service;

import org.springframework.stereotype.Service;

import e_commecre.dto.CartDto;
import e_commecre.entity.Cart;

@Service
public interface CartService {
	 CartDto getCartById(long id);
	 CartDto getCartByAccountId(long id);
	 CartDto createCart(CartDto cartDto);
	 
	 // đây cũng là hàm update Cart khi thêm productDetail thì cart sẽ lưu vào local rùi sao đó mới merge vào db
	 CartDto mergeCart(CartDto cartDto); 
	 
	 // xóa cart
	 boolean deleteCartById(long id);
}	



