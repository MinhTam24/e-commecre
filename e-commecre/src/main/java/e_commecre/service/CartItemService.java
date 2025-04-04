package e_commecre.service;

import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
	boolean deleteById(long id);
	int changQuantity(int quantity, long id);
}
