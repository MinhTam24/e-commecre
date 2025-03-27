package e_commecre.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import e_commecre.dto.ProductDto;

@Service
public interface ProductService {
	Page<ProductDto> getListProductByCategoryId(long id, Pageable pageable);

	ProductDto getProductById(long id);

	long createProduct(ProductDto productDto);

	Page<ProductDto> getAllProduct(Pageable pageable);

	Page<ProductDto> filterProducts(String keyword, List<Long> categoryIdS, List<String> colors, List<String> sizes,
			Double minPrice, Double maxPrice, String sortBy, String sortDirection, int page, int size);

	Set<String> getAllColor();

}
