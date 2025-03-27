package e_commecre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import e_commecre.entity.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

}
