package e_commecre.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e_commecre.dto.CartDto;
import e_commecre.dto.CartItemsDto;
import e_commecre.dto.ProductDetailDto;
import e_commecre.entity.Account;
import e_commecre.entity.Cart;
import e_commecre.entity.CartItems;
import e_commecre.entity.ProductDetail;
import e_commecre.exception.ResouceNotFoundException;
import e_commecre.repository.AccountRepository;
import e_commecre.repository.CartItemsRepository;
import e_commecre.repository.CartRepository;
import e_commecre.repository.ProductDetailRepository;
import e_commecre.service.CartService;
import e_commecre.service.ProductDetailService;
import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {
	private static final Object LOCK = new Object();

	@Autowired
	CartRepository cartRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CartItemsRepository cartItemsRepository;

	@Autowired
	ProductDetailRepository productDetailRepository;
	
	@Autowired
	ProductDetailService productDetailService;

	@Override
	public CartDto getCartById(long id) {
		Cart cart = cartRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Not found cart with id: " + id));
		return CartDto.convertToCartDto(cart, productDetailService);
	}

	@Override
	public CartDto getCartByAccountId(long id) {
		Cart cart = cartRepository.findByAccountId(id)
				.orElseThrow(() -> new ResouceNotFoundException("Not found cart with AccountID: " + id));
		return CartDto.convertToCartDto(cart, productDetailService);
	}

	@Override
	public CartDto createCart(CartDto cartDto) {
		// lấy account
		Account account = accountRepository.findById(cartDto.getAccountId()).orElseThrow(
				() -> new ResouceNotFoundException("Not found account with id: " + cartDto.getAccountId()));
		// lấy list cart items
		Cart cart = new Cart();
		cart.setAccount(account);
		cartRepository.save(cart);

		List<CartItems> listCartItems = cartDto.getCartItems().stream().map(dto -> {
			ProductDetail productDetail = new ProductDetail();
			productDetail.setId(dto.getProductDetailDto().getId());
			return CartItems.builder().cart(cart).price(dto.getPrice()).size(dto.getSize()).quantity(dto.getQuantity())
					.productDetail(productDetail).build();
		}).toList();
		// tạo cart

		cart.setCartItems(listCartItems);
		cartRepository.save(cart);

		return cartDto;
	}

	@Override
	@Transactional
	public CartDto mergeCart(CartDto cartDto) {
		try {
			Account account = accountRepository.findById(cartDto.getAccountId()).orElseThrow(
					() -> new ResouceNotFoundException("Not found account with ID: " + cartDto.getAccountId()));

			Cart cart = cartRepository.findByAccountId(cartDto.getAccountId()).orElseThrow(
					() -> new ResouceNotFoundException("Not found cart with ID: " + cartDto.getAccountId()));

			Map<String, CartItems> cartMap = new HashMap<>();

			for (CartItems dbItem : cart.getCartItems()) {
				String key = dbItem.getProductDetail().getId() + "-" + dbItem.getSize();
				cartMap.put(key, dbItem);
			}

			System.out.println("CartDto" + cartDto);

			for (CartItemsDto dtoItem : cartDto.getCartItems()) {
				String key = dtoItem.getProductDetailDto().getId() + "-" + dtoItem.getSize();
				if (cartMap.containsKey(key)) {
					CartItems existingItem = cartMap.get(key);
					// Kiểm tra số lượng tối đa trước khi cộng dồn
					int newQuantity = existingItem.getQuantity() + dtoItem.getQuantity();
					
					if (newQuantity > dtoItem.getProductDetailDto().getStockQuantity()) {
						System.out.println("Size " + existingItem.getSize() + " số lượng mới là: " + newQuantity);
						System.out.println("Size " + existingItem.getSize() + " số lượng tồn kho là: " +  dtoItem.getProductDetailDto().getStockQuantity());
						existingItem.setQuantity(dtoItem.getProductDetailDto().getStockQuantity());
					}else {
	                	  existingItem.setQuantity(newQuantity);
	                }
					cartItemsRepository.saveAndFlush(existingItem);
					
				} else {
					ProductDetail productDetail = productDetailRepository
							.findById(dtoItem.getProductDetailDto().getId())
							.orElseThrow(() -> new ResouceNotFoundException(
									"Not found ProductDetail with Id: " + dtoItem.getProductDetailDto().getId()));
					
							CartItems newItem = CartItems.builder().cart(cart)
							.price(dtoItem.getQuantity() * productDetail.getPrice())
							.size(dtoItem.getSize())
							.quantity(dtoItem.getQuantity()).productDetail(productDetail).build();
							CartItems cartItme = cartItemsRepository.saveAndFlush(newItem);
							cartMap.put(key, cartItme);
				}
			}
			cartRepository.save(cart);

			return CartDto.convertToCartDto(cart, productDetailService);
		} catch (IllegalArgumentException e) {
			System.err.println("Lỗi: " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println("Lỗi: " + e);
			return null;
		}
	}

	@Override
	public boolean deleteCartById(long id) {
		Cart cart = cartRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Not found Exception" + id));
		cartRepository.delete(cart);
		return false;
	}

}
