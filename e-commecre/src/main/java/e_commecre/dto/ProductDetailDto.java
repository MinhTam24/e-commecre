package e_commecre.dto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import e_commecre.entity.ProductDetail;
import e_commecre.entity.ProductDetailSize;
import e_commecre.entity.Size;
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
    Set<String> size;
    String color;
    List<String> imageUrl;
    long product; // productId liên kết với product
    
    
    public ProductDetailDto(long id, String color, Set<String> sizes,List<String> imageUrl, double price) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.color = color;
        this.size = sizes;
        this.price = price;
    }

    // Phương thức để chuyển đổi từ ProductDetail sang ProductDetailDto
    public static ProductDetailDto convertToProductDetailDto(ProductDetail productDetail) {
    	
    	List<ProductDetailSize> listDetailSizes = productDetail.getProductDetailSize();
    	
    	Set<String> listSize = productDetail.getProductDetailSize().stream()
    		.map(detail -> detail.getSize().getName()).collect(Collectors.toSet());
    	

    	
        return ProductDetailDto.builder()
                .id(productDetail.getId()) 
                .stockQuantity(productDetail.getStockQuantity()) 
                .description(productDetail.getDescription())
                .price(productDetail.getPrice()) 
                .imageUrl(productDetail.getImageUrl())
                .color(productDetail.getColor())
                .size(listSize)
                .product(productDetail.getProductId().getId())
                .build();
    }
}
