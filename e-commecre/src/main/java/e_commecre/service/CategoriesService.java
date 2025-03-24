package e_commecre.service;

import java.util.List;

import org.springframework.stereotype.Service;

import e_commecre.dto.CategoryDto;
import e_commecre.dto.ProductDto;

@Service
public interface CategoriesService {
	
	List<ProductDto> getListProductByCategoryId(long id);
	List<CategoryDto> getAll();
	long createCategory(CategoryDto categoryDto);
	void deleteCategory(long id);
	
}
