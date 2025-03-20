package e_commecre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e_commecre.dto.ProductDetailDto;
import e_commecre.dto.ProductDto;
import e_commecre.entity.Product;
import e_commecre.entity.ProductDetail;
import e_commecre.exception.ResouceNotFoundException;
import e_commecre.repository.ProductDetailRepository;
import e_commecre.repository.ProductRepository;
import e_commecre.service.ProductDetailService;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
	
	@Autowired
	ProductDetailRepository proDetailRepository;
	
	@Override
	public ProductDetailDto getProductDetailById(long id) {
		ProductDetail productDetail = proDetailRepository
				.findById(id).orElseThrow(() -> new ResouceNotFoundException("Not found productdetai with id: " + id));
		return ProductDetailDto.convertToProductDetailDto(productDetail);
	}

}
