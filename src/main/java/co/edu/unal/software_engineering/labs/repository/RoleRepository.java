package co.edu.unal.software_engineering.labs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unal.software_engineering.labs.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Role findByRoleName(String roleName);
	
}
