package e_commecre.service;

import org.springframework.stereotype.Service;

import e_commecre.dto.ProductDetailDto;
import e_commecre.entity.ProductDetail;

@Service
public interface ProductDetailService {
	
	ProductDetailDto getProductDetailById(long id);
	ProductDetailDto createProductDetail(ProductDetailDto dto);
	ProductDetailDto updateProductDetail(ProductDetailDto dto);
	void deleteProductDetail(long id);
	
}
