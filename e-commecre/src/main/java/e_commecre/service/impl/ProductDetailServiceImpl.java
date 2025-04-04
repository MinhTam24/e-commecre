package e_commecre.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import jakarta.transaction.Transactional;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

	@Autowired
	ProductDetailRepository proDetailRepository;

	@Autowired
	ProductRepository productRepository;

	@Override
	public ProductDetailDto getProductDetailById(long id) {
		
		List<String> sizeName = proDetailRepository.findSizeNamesByProductDetailId(id);

		ProductDetail productDetail = proDetailRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Not found productdetai with id: " + id));
		
		return ProductDetailDto.convertToProductDetailDto(productDetail);
		
	
	}

	@Override
	public ProductDetailDto createProductDetail(ProductDetailDto dto) {

//		Product product = productRepository.findById(dto.getProduct())
//				.orElseThrow(() -> new ResouceNotFoundException("Not found Product with Id: " + dto.getProduct()));
//
//		ProductDetail productDetail = new ProductDetail();
//		productDetail.setColor(dto.getColor());
//		productDetail.setDescription(dto.getDescription());
//		productDetail.setPrice(dto.getPrice());
//		productDetail.setSize(dto.getSize());
//		productDetail.setStockQuantity(dto.getStockQuantity());
//		productDetail.setProductId(product);
//
//		proDetailRepository.save(productDetail);
//
//		return dto;
		return null;

	}

	@Override
	public ProductDetailDto updateProductDetail(ProductDetailDto dto) {
//		ProductDetail productDetail = proDetailRepository.findById(dto.getId())
//				.orElseThrow(() -> new ResouceNotFoundException("Not found ProductDetailDto with id: " + dto.getId()));
//		productDetail.setColor(dto.getColor());
//		productDetail.setDescription(dto.getDescription());
//		productDetail.setPrice(dto.getPrice());
//		productDetail.setSize(dto.getSize());
//		productDetail.setStockQuantity(dto.getStockQuantity());
//		proDetailRepository.save(productDetail);
//		return dto;
		return null;
	}

	@Override
	public void deleteProductDetail(long id) {

	}

	@Override
	public ProductDetailDto getProductByColorAndId(String color, long id) {
		ProductDetail productDetail = proDetailRepository.findByColorAndProductIdId(color, id)
				.orElseThrow(() -> new ResouceNotFoundException("Not found product " + id + "And color" + color));
		return ProductDetailDto.convertToProductDetailDto(productDetail);
	}
	
    @Transactional
	@Override
	public ProductDetailDto getProductBySizeAndId(String size, long id) {
//		ProductDetail productDetail = proDetailRepository.findSizeQuantitysByProductDetailId(id, size)
//				.orElseThrow(() -> new ResouceNotFoundException("Not found product " + id + "And size" + size));
//		return ProductDetailDto.convertToProductDetailDto(productDetail);
		return null;
	}

	@Override
	public int SizeQuantity(long id, String size) {
		Optional<Integer> quantity = proDetailRepository.findSizeQuantitysByProductDetailId(id, size);
		if (quantity.isPresent()) {
			return quantity.get();
		}
		return 0;
	}

}
