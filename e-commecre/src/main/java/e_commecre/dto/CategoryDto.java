package e_commecre.dto;

import java.util.List;
import java.util.stream.Collectors;

import e_commecre.entity.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	long id;
	
	String name;
	
    private List<ProductDto> productDto;
	
	String description;
	
	
	public static CategoryDto convertToCategoryDto(Categories categories) {
	    // Convert the list of Product objects to a list of ProductDto objects
	    List<ProductDto> listProductDtos = categories.getProduct().stream()
	            .map(ProductDto::ConvertToProductDto)
	            .collect(Collectors.toList());
	    
	    // Build and return CategoryDto
	    return CategoryDto.builder()
	            .id(categories.getId())
	            .description(categories.getDescription())
	            .name(categories.getName())
	            .productDto(listProductDtos)  // Set the list of ProductDto objects
	            .build();  
	}

}
