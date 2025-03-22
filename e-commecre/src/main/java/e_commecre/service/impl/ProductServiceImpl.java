package e_commecre.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import e_commecre.dto.ProductDto;
import e_commecre.entity.Product;
import e_commecre.entity.ProductDetail;
import e_commecre.exception.ResouceNotFoundException;
import e_commecre.repository.CategoriesRepository;
import e_commecre.repository.ProductDetailRepository;
import e_commecre.repository.ProductRepository;
import e_commecre.service.ProductService;
import e_commecre.ultil.ConstUltil;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.ListJoin;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductDetailRepository productDetailRepository;

	@Autowired
	CategoriesRepository categoriesRepository;

	@Override
	public ProductDto getProductById(long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Not found product with id: " + id));
		return ProductDto.ConvertToProductDto(product);
	}


	@Override
	public Page<ProductDto> getListProductByCategoryId(long id, Pageable pageable) {
		Page<Product> pageProduct = productRepository.findByCategoryId(id, pageable);
		if (pageProduct.hasContent()) {
			return pageProduct.map(product -> ProductDto.builder().id(product.getId()).name(product.getName())
					.price(product.getProductDetail().isEmpty() ? 0.0 : product.getProductDetail().get(0).getPrice())
					.createAt(product.getCreateAt().format(ConstUltil.DATE_TIME_FORMATTER)).build());
		}
	    return Page.empty();
	}
		
	@Override
	public long createProduct(ProductDto productDto) {	
		Product product = new Product();
		product.setName(productDto.getName());
		
		Product savedProduct = productRepository.saveAndFlush(product); // luu product vao database truoc 

		List<ProductDetail> listProduct = productDto.getProductDetail().stream().map(detailDto -> {
			ProductDetail productDetail = new ProductDetail();
			productDetail.setColor(detailDto.getColor());
			productDetail.setDescription(detailDto.getDescription());
			productDetail.setPrice(detailDto.getPrice());
			productDetail.setSize(detailDto.getSize());
			productDetail.setStockQuantity(detailDto.getStockQuantity());
	        productDetail.setProductId(savedProduct);
			return productDetail;
			
		}).toList();
		
		productDetailRepository.saveAll(listProduct); // luu cac productDetail vao database

		return savedProduct.getId();
	}
	
	//Hàm lọc theo các điều kiện >= Giá Nhỏ nhất, Giá Lớn Nhất <=
	// Lọc Giá từ cao đến Thấp, Từ Thấp Đến Cao
	// Lọc tên từ a đến z, từ z đến a
	// lọc tho danh mục
	// lọc theo ngày khoảng ngày
	@Override
	public Page<ProductDto> filterProducts(
	        String keyword, 
	        Long categoryId, 
	        Double minPrice, 
	        Double maxPrice, 
	        LocalDate startDate, 
	        LocalDate endDate, 
	        String sortBy, 
	        String sortDirection, 
	        int page, 
	        int size) {

	    // Xây dựng danh sách điều kiện lọc
	    Specification<Product> spec = Specification.where(null);

	    if (keyword != null && !keyword.isEmpty()) {
	        spec = spec.and((root, query, cb) -> cb.like(root.get("name"), "%" + keyword + "%"));
	    }
	    if (categoryId != null) {
	        spec = spec.and((root, query, cb) -> cb.equal(root.join("categories").get("id"), categoryId));
	    }
	    if (minPrice != null) {
	        spec = spec.and((root, query, cb) -> {
	            ListJoin<Product, ProductDetail> productDetailJoin = root.joinList("productDetail", JoinType.INNER);
	            return cb.greaterThanOrEqualTo(productDetailJoin.get("price"), minPrice);
	        });
	    }
	    if (maxPrice != null) {
	        spec = spec.and((root, query, cb) -> {
	            ListJoin<Product, ProductDetail> productDetailJoin = root.joinList("productDetail", JoinType.INNER);
	            return cb.lessThanOrEqualTo(productDetailJoin.get("price"), maxPrice);
	        });
	    }
	    if (startDate != null) {
	        spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createAt"), startDate));
	    }
	    if (endDate != null) {
	        spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("createAt"), endDate.plusDays(1)));
	    }

	    // Nếu sắp xếp theo giá, xử lý riêng sắp sếp sao khi chuyển giá sang cho productdto, còn lại để Hibernate xử lý
	    Sort sort;
	    if ("price".equalsIgnoreCase(sortBy)) {
	        sort = Sort.unsorted(); // Hibernate không thể sắp xếp theo giá nên để rỗng
	    } else {
	        Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
	        sort = Sort.by(direction, sortBy != null ? sortBy : "name"); // Mặc định sắp xếp theo tên
	    }

	    Pageable pageable = PageRequest.of(page, size, sort);
	    Page<Product> productPage = productRepository.findAll(spec, pageable);

	    // Chuyển đổi sang DTO và xử lý giá
	    List<ProductDto> productDtoList = productPage.stream().map(product -> {
	        Double price = 0.0;

	        if (product.getProductDetail() != null && !product.getProductDetail().isEmpty()) {
	            Stream<Double> priceStream = product.getProductDetail().stream()
	                    .map(ProductDetail::getPrice);

	            if (minPrice != null) { 
	                price = priceStream.filter(p -> p >= minPrice).min(Double::compare).orElse(0.0); 
	            } else if (maxPrice != null) { 
	                price = priceStream.filter(p -> p <= maxPrice).max(Double::compare).orElse(0.0);
	            } else { 
	                price = priceStream.min(Double::compare).orElse(0.0); // Mặc định lấy giá thấp nhất
	            }
	        }

	        return ProductDto.builder()
	                .id(product.getId())
	                .name(product.getName())
	                .price(price)
	                .createAt(product.getCreateAt().format(ConstUltil.DATE_TIME_FORMATTER))
	                .build();
	    }).collect(Collectors.toList());

	    // Nếu sắp xếp theo giá thì xử lý thủ công
	    if ("price".equalsIgnoreCase(sortBy)) {
	        if ("desc".equalsIgnoreCase(sortDirection)) {
	            productDtoList.sort(Comparator.comparing(ProductDto::getPrice).reversed()); // Giá cao → thấp
	        } else {
	            productDtoList.sort(Comparator.comparing(ProductDto::getPrice)); // Giá thấp → cao
	        }
	    }

	    // Chuyển danh sách đã sắp xếp thành Page<ProductDto>
	    return new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());
	}

	    
	}



	
	


