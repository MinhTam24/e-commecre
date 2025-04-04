package e_commecre.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
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

import e_commecre.dto.ProductDetailDto;
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
	public Set<String> getAllColor() {
		Set<String> allColor = productDetailRepository.findAll().stream().map(ProductDetail::getColor)
				.collect(Collectors.toSet());
		return allColor;
	}

	@Override
	public long createProduct(ProductDto productDto) {
//		Product product = new Product();
//		product.setName(productDto.getName());
//
//		Product savedProduct = productRepository.saveAndFlush(product); // luu product vao database truoc
//
//		List<ProductDetail> listProduct = productDto.getProductDetail().stream().map(detailDto -> {
//			ProductDetail productDetail = new ProductDetail();
//			productDetail.setColor(detailDto.getColor());
//			productDetail.setDescription(detailDto.getDescription());
//			productDetail.setPrice(detailDto.getPrice());
//			productDetail.setSize(detailDto.getSize());
//			productDetail.setStockQuantity(detailDto.getStockQuantity());
//			productDetail.setProductId(savedProduct);
//			return productDetail;
//
//		}).toList();
//
//		productDetailRepository.saveAll(listProduct); // luu cac productDetail vao database
//
//		return savedProduct.getId();
		return 0;
	}

	// Hàm lọc theo các điều kiện >= Giá Nhỏ nhất, Giá Lớn Nhất <=
	// Lọc Giá từ cao đến Thấp, Từ Thấp Đến Cao
	// Lọc tên từ a đến z, từ z đến a
	// lọc tho danh mục
	// lọc theo ngày khoảng ngày
	
	@Override
	public Page<ProductDto> filterProducts(
	        String keyword,
	        List<Long> categoryIds,
	        List<String> colors,
	        List<String> sizes,
	        Double minPrice,
	        Double maxPrice,
	        String sortBy,
	        String sortDirection,
	        int page,
	        int size) {

	    // Bước 1: Lọc Product trước (dựa vào category, keyword)
	    Specification<Product> specProduct = Specification.where(null);

	    if (categoryIds != null && !categoryIds.isEmpty()) {
	        specProduct = specProduct.and((root, query, cb) ->
	            root.join("categories", JoinType.INNER).get("id").in(categoryIds));
	    }

	    if (keyword != null && !keyword.isEmpty()) {
	        specProduct = specProduct.and((root, query, cb) -> cb.like(root.get("name"), "%" + keyword + "%"));
	    }

	    Pageable pageable = PageRequest.of(page, size);
	    Page<Product> productPage = productRepository.findAll(specProduct, pageable);

	    // Lấy danh sách productId thỏa mãn
	    Set<Long> productIds = productPage.stream().map(Product::getId).collect(Collectors.toSet());

	    if (productIds.isEmpty()) {
	        return new PageImpl<>(Collections.emptyList(), pageable, 0);
	    }

	    // Kiểm tra có điều kiện lọc theo ProductDetail không
	    boolean hasProductDetailFilter = (colors != null && !colors.isEmpty()) || minPrice != null || maxPrice != null;

	    // Bước 2: Lọc ProductDetail
	    Specification<ProductDetail> specDetail = Specification.where((root, query, cb) -> root.get("productId").get("id").in(productIds));

	    if (colors != null && !colors.isEmpty()) {
	        specDetail = specDetail.and((root, query, cb) -> root.get("color").in(colors));
	    }

	    if (minPrice != null) {
	        specDetail = specDetail.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
	    }

	    if (maxPrice != null) {
	        specDetail = specDetail.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
	    }
	    
	    if (sizes != null && !sizes.isEmpty()) {
	        specDetail = specDetail.and((root, query, cb) -> 
	            root.join("productDetailSize", JoinType.INNER)
	                .join("size", JoinType.INNER)
	                .get("name").in(sizes) // ✅ Lọc theo size từ bảng trung gian
	        );
	    }

	    List<ProductDetail> filteredProductDetails = productDetailRepository.findAll(specDetail);

	    // Nhóm ProductDetail theo ProductId
	    Map<Long, List<ProductDetail>> productDetailMap = filteredProductDetails.stream()
	        .collect(Collectors.groupingBy(detail -> detail.getProductId().getId()));

	    // Bước 3: Duyệt danh sách product để tạo ProductDto
	    List<ProductDto> productDtoList = productPage.stream().map(product -> {
	        List<ProductDetail> productDetails = productDetailMap.getOrDefault(product.getId(), new ArrayList<>());

	        List<ProductDetailDto> productDetailDtos;
	        
	        if (hasProductDetailFilter) {
	            // Nếu có điều kiện lọc ProductDetail → Lấy hết các ProductDetail thỏa mãn
	            productDetailDtos = productDetails.stream()
	                .map(detail -> new ProductDetailDto(detail.getId(), detail.getColor(), detail.getSizes(), detail.getImageUrl(), detail.getPrice()))
	                .collect(Collectors.toList());
	        } else {
	            // Nếu chỉ lọc Product → Chỉ lấy 1 ProductDetail (chọn giá thấp nhất)
	            ProductDetail bestDetail = productDetails.stream()
	                .min(Comparator.comparing(ProductDetail::getPrice))
	                .orElse(null);

	            productDetailDtos = (bestDetail != null) 
	                ? List.of(new ProductDetailDto(bestDetail.getId(), bestDetail.getColor(), bestDetail.getSizes(), bestDetail.getImageUrl(), bestDetail.getPrice()))
	                : new ArrayList<>();
	        }

	        if (productDetailDtos.isEmpty()) {
	            return null; // Bỏ qua sản phẩm nếu không còn ProductDetail nào hợp lệ
	        }

	        return ProductDto.builder()
	            .id(product.getId())
	            .name(product.getName())
	            .createAt(product.getCreateAt().format(ConstUltil.DATE_TIME_FORMATTER))
	            .productDetailFilter(productDetailDtos)
	            .build();
	    }).filter(Objects::nonNull).collect(Collectors.toList());

	    // Bước 4: Sắp xếp danh sách theo `price` hoặc `createAt`
	    if ("price".equalsIgnoreCase(sortBy)) {
	        productDtoList.sort("desc".equalsIgnoreCase(sortDirection)
	            ? Comparator.comparing((ProductDto dto) -> dto.getProductDetailFilter()
	                                                          .stream()
	                                                          .map(ProductDetailDto::getPrice)
	                                                          .min(Double::compare)
	                                                          .orElse(Double.MAX_VALUE))
	                        .reversed()
	            : Comparator.comparing((ProductDto dto) -> dto.getProductDetailFilter()
	                                                          .stream()
	                                                          .map(ProductDetailDto::getPrice)
	                                                          .min(Double::compare)
	                                                          .orElse(Double.MAX_VALUE))
	        );
	    } else if ("createAt".equalsIgnoreCase(sortBy)) {
	        productDtoList.sort("desc".equalsIgnoreCase(sortDirection)
	            ? Comparator.comparing(ProductDto::getCreateAt).reversed()
	            : Comparator.comparing(ProductDto::getCreateAt));
	    }
	    
	    else if ("name".equalsIgnoreCase(sortBy) || sortBy == null) {
	        productDtoList.sort("desc".equalsIgnoreCase(sortDirection)
	            ? Comparator.comparing(ProductDto::getName).reversed()
	            : Comparator.comparing(ProductDto::getName)
	        );
	    }



	    return new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());
	}


	@Override
	public Page<ProductDto> getAllProduct(Pageable pageable) {
		Page<Product> pageProduct = productRepository.findAll(pageable);
		if (pageProduct.hasContent()) {
			return pageProduct.map(product -> ProductDto.builder().id(product.getId()).name(product.getName())
					.price(product.getProductDetail().isEmpty() ? 0.0 : product.getProductDetail().get(0).getPrice())
					.imageUrl(product.getProductDetail().get(0).getImageUrl().get(0))
					.createAt(product.getCreateAt().format(ConstUltil.DATE_TIME_FORMATTER)).build());
		}
		return Page.empty();
	}

}
