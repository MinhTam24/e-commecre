package e_commecre.service;

import java.util.List;

import org.springframework.stereotype.Service;

import e_commecre.dto.ProductDto;

@Service
public interface ProductService {
	
	List<ProductDto> getListProductByCategoryId(long id);
	ProductDto getProductById(long id);
	List<ProductDto> getSortedProduct(String[] sortingCriteria);
	
}
