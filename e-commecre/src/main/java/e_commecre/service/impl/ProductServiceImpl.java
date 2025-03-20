package e_commecre.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import e_commecre.dto.ProductDetailDto;
import e_commecre.dto.ProductDto;
import e_commecre.entity.Categories;
import e_commecre.entity.Product;
import e_commecre.exception.ResouceNotFoundException;
import e_commecre.repository.CategoriesRepository;
import e_commecre.repository.ProductRepository;
import e_commecre.service.ProductService;
import e_commecre.ultil.ConstUltil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoriesRepository categoriesRepository;

	@Override
	public ProductDto getProductById(long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Not found product with id: " + id));
		return ProductDto.ConvertToProductDto(product);
	}

	@Override
	public List<ProductDto> getSortedProduct(String[] sortingCriteria) {

//		List<Sort.Order> orders = new ArrayList<>();
//
//		// Lặp qua tất cả các điều kiện sắp xếp
//		for (String criterion : sortingCriteria) {
//			// Tách điều kiện sắp xếp thành tên trường và hướng sắp xếp
//			String[] parts = criterion.split(":");
//			String field = parts[0]; // Tên trường (ví dụ: "price", "name")
//			String direction = parts.length > 1 ? parts[1] : "asc"; // Mặc định là "asc" nếu không có hướng
//
//			// Thêm điều kiện sắp xếp vào danh sách
//			if ("desc".equalsIgnoreCase(direction)) {
//				orders.add(Sort.Order.desc(field)); // Sắp xếp giảm dần
//			} else {
//				orders.add(Sort.Order.asc(field)); // Sắp xếp tăng dần
//			}
//		}
//
//		// Nếu không có điều kiện sắp xếp nào, mặc định sẽ sắp xếp theo "name"
//		if (orders.isEmpty()) {
//			orders.add(Sort.Order.asc("name"));
//		}
		return null;
	}

	@Override
	public List<ProductDto> getListProductByCategoryId(long id) {
		Categories categories = categoriesRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Not found categories with id: " + id));

		List<Product> listProduct = categories.getProduct();

		List<ProductDto> listProductDto = listProduct.stream().map(product -> {

			double price = 0.0;
			if (!product.getProductDetail().isEmpty()) {
				price = product.getProductDetail().get(0).getPrice(); 
			}

			return ProductDto.builder()
					.id(product.getId())
					.name(product.getName())
					.price(product.getProductDetail().get(0).getPrice())
					.createAt(product.getCreateAt().format(ConstUltil.DATE_TIME_FORMATTER))
					.build();

		}).collect(Collectors.toList());

		return listProductDto;
	}

}
