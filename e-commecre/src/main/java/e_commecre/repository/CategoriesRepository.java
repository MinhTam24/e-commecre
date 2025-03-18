package e_commecre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commecre.entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

}
