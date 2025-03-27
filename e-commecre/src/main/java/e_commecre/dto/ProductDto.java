package e_commecre.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import e_commecre.entity.Categories;
import e_commecre.entity.Product;
import e_commecre.entity.ProductDetail;
import e_commecre.entity.Size;
import e_commecre.ultil.ConstUltil;
import io.jsonwebtoken.lang.Objects;
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
	
	String description;

	List<Long> productDetail;
	
	List<ProductDetailDto> productDetailFilter;

	Set<String> color;

	Set<String> size;

	String createAt;

	String imageUrl;

	public static ProductDto ConvertToProductDto(Product product) {
		
		List<Long> listProductDetails = product.getProductDetail().stream()
				.map(ProductDetail::getId).toList();
		
		Set<String> color = product.getProductDetail().stream()
				.map(ProductDetail::getColor).collect(Collectors.toSet());
				
		 Set<String> uniqueSizeNames = product.getProductDetail().stream()
			        .flatMap(productDetail -> productDetail.getProductDetailSize().stream()) // Lấy danh sách ProductDetailSize
			        .map(productDetailSize -> productDetailSize.getSize().getName()) // Lấy tên Size từ Size entity
			        .collect(Collectors.toSet());
		

		return ProductDto.builder().id(product.getId())
				.createAt((product.getCreateAt() != null) ? product.getCreateAt().format(ConstUltil.DATE_TIME_FORMATTER): "N/A")
				.name(product.getName())
				.totalQuantity(Math.max(product.getTotalStock(), 0)) 
				.productDetail(listProductDetails)
				.size(uniqueSizeNames)
				.description(product.getDescription())
				.color(color)
				.build();
	}
	

}
