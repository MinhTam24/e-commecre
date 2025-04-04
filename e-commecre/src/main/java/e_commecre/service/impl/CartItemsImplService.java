package e_commecre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import e_commecre.entity.Cart;
import e_commecre.entity.CartItems;
import e_commecre.exception.ResouceNotFoundException;
import e_commecre.repository.CartItemsRepository;
import e_commecre.repository.ProductDetailRepository;
import e_commecre.service.CartItemService;

public class CartItemsImplService implements CartItemService {
	
	@Autowired
	CartItemsRepository cartItemsRepository;
	
	
	@Autowired
	ProductDetailRepository productDetailRepository;

	@Override
	public boolean deleteById(long id) {
		try {
			 cartItemsRepository.deleteById(id);
			 return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public int changQuantity(int quantity, long id) {
		CartItems item = cartItemsRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Not found cartItem with id: " + id));
		
		item.setQuantity(quantity);
		cartItemsRepository.save(item);
		
		return quantity;
	}

}
