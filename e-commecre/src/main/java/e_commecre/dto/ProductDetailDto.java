package e_commecre.dto;

import e_commecre.entity.ProductDetail;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailDto {

    long id;
    int stockQuantity;
    String description;
    double price;
    int size;
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
