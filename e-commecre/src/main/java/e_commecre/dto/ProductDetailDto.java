package e_commecre.dto;

import java.util.List;

import e_commecre.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
	
    long id;
    int stockQuantity;
    String description;
    double price;
    int size;
    List<Long> categoriesId;
    String color;
    long product; // productId liên kết với product

    // Phương thức để chuyển đổi từ ProductDetail sang ProductDetailDto
    public static ProductDetailDto convertToProductDetailDto(ProductDetail productDetail) {
        return ProductDetailDto.builder()
                .id(productDetail.getId()) 
                .stockQuantity(productDetail.getStockQuantity()) 
                .description(productDetail.getDescription())
                .price(productDetail.getPrice()) 
                .size(productDetail.getSize())
                .color(productDetail.getColor())
                .product(productDetail.getProductId().getId())
                .build();
    }
}
