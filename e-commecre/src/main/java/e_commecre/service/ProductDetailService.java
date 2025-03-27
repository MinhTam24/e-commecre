package e_commecre.service;

import org.springframework.stereotype.Service;

import e_commecre.dto.ProductDetailDto;
import e_commecre.entity.ProductDetail;

@Service
public interface ProductDetailService {
	
	ProductDetailDto getProductDetailById(long id);
	ProductDetailDto createProductDetail(ProductDetailDto dto);
	ProductDetailDto updateProductDetail(ProductDetailDto dto);
	ProductDetailDto getProductByColorAndId(String color, long id);
	ProductDetailDto getProductBySizeAndId(String size, long id);
	int SizeQuantity(long id, String size);
	void deleteProductDetail(long id);
	
}
