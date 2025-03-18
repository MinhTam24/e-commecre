package e_commecre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commecre.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
