package e_commecre.dto;

import java.util.List;
import java.util.stream.Collectors;

import e_commecre.entity.Categories;
import e_commecre.entity.Product;
import e_commecre.ultil.ConstUltil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	long id;

	String name;
	
	int totalQuantity;
	
	double price;

	List<ProductDetailDto> productDetail;
	
	String createAt;
	
	public static ProductDto ConvertToProductDto(Product product) {
		List<ProductDetailDto> productDetailDtos = product.getProductDetail().stream()
				.map(ProductDetailDto::convertToProductDetailDto)
				.collect(Collectors.toList());
		
		return ProductDto.builder()
				.id(product.getId())
				.createAt(product.getCreateAt().format(ConstUltil.DATE_TIME_FORMATTER))
				.name(product.getName())
				.productDetail(productDetailDtos)
				.totalQuantity(product.getTotalStock() > 0 ? product.getTotalStock() : 0)
				.build();
	}

}
