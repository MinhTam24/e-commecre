package e_commecre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commecre.entity.Categories;
import e_commecre.entity.Product;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
