package e_commecre.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import e_commecre.dto.CategoryDto;
import e_commecre.dto.ProductDto;
import e_commecre.repository.CategoriesRepository;
import e_commecre.service.CategoriesService;
import lombok.experimental.PackagePrivate;

@RestController
public class CategoryController {

	@Autowired
	CategoriesService categoriesService;

	@GetMapping("/api/category")
	public ResponseEntity<?> getAllCategory() {
		return ResponseEntity.ok(categoriesService.getAll());
	}

	@PostMapping("/api/category/create")
	public ResponseEntity<?> createCateGory(@RequestBody CategoryDto categoryDto) {
		return ResponseEntity.ok(categoriesService.createCategory(categoryDto));
	}

	@DeleteMapping("/api/category/{id}")
	public ResponseEntity<?> deleteCategories(@PathVariable("id") long id) {
		categoriesService.deleteCategory(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
