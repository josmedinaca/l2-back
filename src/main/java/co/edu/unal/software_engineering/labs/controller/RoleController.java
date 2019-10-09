package co.edu.unal.software_engineering.labs.controller;

import co.edu.unal.software_engineering.labs.model.Course;
import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.pojo.RolePOJO;
import co.edu.unal.software_engineering.labs.service.RoleService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class RoleController{

    private RoleService roleService;


    public RoleController( RoleService roleService ){
        this.roleService = roleService;
    }
    
    @PostMapping(value = { "/crearRol" })
    public ResponseEntity crearRole( @RequestBody RolePOJO rolPOJO ){
    	Role newRol = new Role();
    	Role existingRole = roleService.findByName(rolPOJO.getRoleName());
        if( existingRole != null){
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }
        newRol.setRoleName(rolPOJO.getRoleName());
    	roleService.save(newRol);
    	return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping( value = { "/roles" } )
    public List<Role> getAllRoles( ){
        return roleService.getAll( );
    }
    
    @GetMapping(value = {"/find/{id}"})
    public Role getRole(@PathVariable Integer id) {
    	return roleService.findById(id);
    }
}
