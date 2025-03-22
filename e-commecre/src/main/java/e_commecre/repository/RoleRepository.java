package e_commecre.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commecre.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}
