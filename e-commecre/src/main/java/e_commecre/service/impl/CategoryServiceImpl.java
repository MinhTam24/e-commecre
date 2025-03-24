package e_commecre.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e_commecre.dto.CategoryDto;
import e_commecre.dto.ProductDto;
import e_commecre.entity.Categories;
import e_commecre.entity.Product;
import e_commecre.exception.ResouceNotFoundException;
import e_commecre.repository.CategoriesRepository;
import e_commecre.repository.ProductRepository;
import e_commecre.service.CategoriesService;

@Service
public class CategoryServiceImpl implements CategoriesService {

	@Autowired
	CategoriesRepository categoriesRepository;

	@Override
	public List<ProductDto> getListProductByCategoryId(long id) {
		Categories categories = categoriesRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Not found categories with id: " + id));
		
		List<Product> listProduct = categories.getProduct();
		
		
		 List<ProductDto> listProductDto = listProduct.stream()
				.map(ProductDto::ConvertToProductDto)
				.collect(Collectors.toList());
		 
		 return listProductDto;
	}

	

	@Override
	public List<CategoryDto> getAll() {
		List<Categories> categories = categoriesRepository.findAll();
		if(!categories.isEmpty()) {
			return	categories.stream().map(cate -> 
			CategoryDto.builder()
			.id(cate.getId())
			.name(cate.getName())
			.description(cate.getDescription())
			.build()).toList();
		}
		throw new ResouceNotFoundException("Not found any Categories");
	}



	@Override
	public long createCategory(CategoryDto categoryDto) {
		Categories categories = new Categories();
		categories.setDescription(categories.getDescription());
		categories.setName(categories.getName());
		return categoriesRepository.save(categories).getId();
	}



	@Override
	public void deleteCategory(long id) {
		Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new ResouceNotFoundException("Not found categories with id: "+ id));
		categoriesRepository.deleteById(id); 
	}

}
