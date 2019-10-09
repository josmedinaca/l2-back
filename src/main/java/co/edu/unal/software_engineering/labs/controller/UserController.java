package co.edu.unal.software_engineering.labs.controller;

import co.edu.unal.software_engineering.labs.model.Association;
import co.edu.unal.software_engineering.labs.model.Course;
import co.edu.unal.software_engineering.labs.model.Period;
import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.model.User;
import co.edu.unal.software_engineering.labs.pojo.AssociationPOJO;
import co.edu.unal.software_engineering.labs.pojo.LoginUserPOJO;
import co.edu.unal.software_engineering.labs.pojo.PeriodPOJO;
import co.edu.unal.software_engineering.labs.pojo.RegisterUserPOJO;
import co.edu.unal.software_engineering.labs.service.AssociationService;
import co.edu.unal.software_engineering.labs.service.CourseService;
import co.edu.unal.software_engineering.labs.service.PeriodService;
import co.edu.unal.software_engineering.labs.service.RoleService;
import co.edu.unal.software_engineering.labs.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin
@RestController
public class UserController {

	private final UserService userService;

	private final RoleService roleService;

	private final CourseService courseService;

	private final PeriodService periodService;
	
	private final AssociationService asociacionService;

	public UserController(UserService userService, RoleService roleService, CourseService courseService,
			PeriodService periodService, AssociationService asociacionService) {
		this.userService = userService;
		this.roleService = roleService;
		this.courseService = courseService;
		this.periodService = periodService;
		this.asociacionService = asociacionService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@PostMapping(value = { "/registro/{roleId}" })
	public ResponseEntity register(@PathVariable Integer roleId, @RequestBody RegisterUserPOJO userPOJO) {
		Role role = roleService.findById(roleId);
		User existingUser = userService.findByUsername(userPOJO.getUsername());
		if (role == null || existingUser != null || !userService.isRightUser(userPOJO)) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		User newUser = new User();
		newUser.setNames(userPOJO.getNames().toUpperCase());
		newUser.setSurnames(userPOJO.getSurnames().toUpperCase());
		newUser.setUsername(userPOJO.getUsername().toLowerCase());
		newUser.setPassword(passwordEncoder().encode(userPOJO.getPassword()));
		newUser.setRoles(Collections.singletonList(role));
		userService.save(newUser);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PostMapping(value = { "/registro/nuevo-rol/{roleId}" })
	public ResponseEntity registerRoleToUser(@PathVariable Integer roleId, @RequestBody LoginUserPOJO userPOJO) {
		Role role = roleService.findById(roleId);
		User existingUser = userService.findByUsername(userPOJO.getUsername());
		if (role == null || existingUser == null || existingUser.getRoles().contains(role)) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		} else if (!passwordEncoder().matches(userPOJO.getPassword(), existingUser.getPassword())) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		existingUser.addRole(role);
		userService.save(existingUser);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PostMapping(value = { "/login" })
	public ResponseEntity loginApp(@RequestBody LoginUserPOJO userPOJO) {
		User existingUser = userService.findByUsername(userPOJO.getUsername());
		boolean matchPassword = passwordEncoder().matches(userPOJO.getPassword(), existingUser.getPassword());
		if (existingUser != null && matchPassword) {
			return new ResponseEntity<>("Successful Login", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping( value = { "/asociarCurso/{courseName}/{userName}" } )
    public ResponseEntity asociarCurso(@PathVariable String courseName, @PathVariable String userName,
    		@RequestBody PeriodPOJO periodPOJO) { 
		User existingUser = userService.findByUsername(userName);
		Course existingCourse = courseService.findByName(courseName.toUpperCase());
		Period existingPeriod = periodService.findByName(periodPOJO.getName());
    	Association asociacion = new Association();
    	asociacion.setCourse(existingCourse);
    	asociacion.setPeriod(existingPeriod);
    	asociacion.setRole(existingUser.getRoles().get(0));
    	asociacion.setUser(existingUser);
    	asociacionService.save(asociacion);    	 	
    	return new ResponseEntity<>("Asociacion creada con exito", HttpStatus.CREATED );
    }

}