package e_commecre.service;

import org.springframework.stereotype.Service;

import e_commecre.dto.ProductDetailDto;

@Service
public interface ProductDetailService {
	
	ProductDetailDto getProductDetailById(long id);
	
}
