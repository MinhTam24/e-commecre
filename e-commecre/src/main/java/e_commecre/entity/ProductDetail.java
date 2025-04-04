package e_commecre.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import e_commecre.dto.ProductDetailDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "product_detail")
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	int stockQuantity;

	String description;

	double price;

	@OneToMany(mappedBy = "productDetail")
	List<ProductDetailSize> productDetailSize;

	String color;

	@ManyToOne
	@JoinColumn(name = "product_id")
	Product productId;

	@OneToMany(mappedBy = "productDetail")
	List<OrderDetail> orderDetail;

	@OneToMany(mappedBy = "productDetail")
	List<Image> images;

	@OneToMany(mappedBy = "productDetail")
	List<CartItems> listCartItems;

	public int getStockQuantity() {
		return this.getProductDetailSize().stream().mapToInt(quantitySize -> quantitySize.getQuantity()).sum();

	}

	public Set<String> getSizes() {
		return this.productDetailSize.stream().map(productDetailSize -> productDetailSize.getSize().getName()) // Lấy
																												// tên
																												// size
																												// từ
																												// `Size`
				.collect(Collectors.toSet()); // Chuyển thành Set để tránh trùng lặp
	}

	public List<String> getImageUrl() {
		List<Image> listImage = this.getImages();
		List<String> listImageUrl = new ArrayList<String>();
		if (!listImage.isEmpty() && listImage != null) {
			listImage.stream().forEach(image -> listImageUrl.add(image.getImageUrl()));
		} else {
			listImageUrl.add(
					"https://res.cloudinary.com/dopwq7ciu/image/upload/v1742913444/image-not-available_hkgevr.png");
		}
		return listImageUrl;

	}

	public static ProductDetailDto converToProductDetailDto(ProductDetail productDetail) {
			
		return	ProductDetailDto.builder()
				.id(productDetail.getId())
				.color(productDetail.getColor())
				.description(productDetail.getDescription())
				.price(productDetail.getPrice())
				.stockQuantity(productDetail.getStockQuantity())
				.imageUrl(productDetail.getImageUrl())
				.size(productDetail.getSizes())
				.product(productDetail.getProductId().getId())
				.build();
	}

}
