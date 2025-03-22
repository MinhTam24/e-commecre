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
	
	@Autowired 
	ProductRepository productRepository;
	
	@Override
	public ProductDetailDto getProductDetailById(long id) {
		ProductDetail productDetail = proDetailRepository
				.findById(id).orElseThrow(() -> new ResouceNotFoundException("Not found productdetai with id: " + id));
		return ProductDetailDto.convertToProductDetailDto(productDetail);
	}

	@Override
	public ProductDetailDto createProductDetail(ProductDetailDto dto) {
		
		Product product = productRepository.findById(dto.getProduct())
				.orElseThrow(() -> new ResouceNotFoundException("Not found Product with Id: " + dto.getProduct()));
		
		
		ProductDetail productDetail = new ProductDetail();
		productDetail.setColor(dto.getColor());
		productDetail.setDescription(dto.getDescription());
		productDetail.setPrice(dto.getPrice());
		productDetail.setSize(dto.getSize());
		productDetail.setStockQuantity(dto.getStockQuantity());
		productDetail.setProductId(product);
		
		proDetailRepository.save(productDetail);
		
		return dto;
	}

	@Override
	public ProductDetailDto updateProductDetail(ProductDetailDto dto) {
		ProductDetail productDetail = proDetailRepository.findById(dto.getId())
				.orElseThrow(() -> new ResouceNotFoundException("Not found ProductDetailDto with id: "+ dto.getId()));
		productDetail.setColor(dto.getColor());
		productDetail.setDescription(dto.getDescription());
		productDetail.setPrice(dto.getPrice());
		productDetail.setSize(dto.getSize());
		productDetail.setStockQuantity(dto.getStockQuantity());	
		proDetailRepository.save(productDetail);
		return dto;
	}

	@Override
	public void deleteProductDetail(long id) {
		
		
	}
	
	
	
	

}
