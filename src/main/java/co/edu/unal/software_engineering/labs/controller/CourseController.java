package co.edu.unal.software_engineering.labs.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unal.software_engineering.labs.model.Course;
import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.model.User;
import co.edu.unal.software_engineering.labs.pojo.CoursePOJO;
import co.edu.unal.software_engineering.labs.service.CourseService;
import co.edu.unal.software_engineering.labs.service.UserService;

@RestController
public class CourseController {
	private final UserService userService;

	private final CourseService courseService;

	public CourseController(UserService userService, CourseService courseService) {
		this.userService = userService;
		this.courseService = courseService;
	}

	@PostMapping(value = { "/crearCurso/{userName}" })
	public ResponseEntity crearCurso(@RequestBody CoursePOJO coursePOJO, @PathVariable String userName) {
		User newUser = userService.findByUsername(userName);
		Course existingCourse = courseService.findByName(coursePOJO.getCourseName().toUpperCase());
		List<Role> roles = newUser.getRoles();
		boolean test = false;
		Course newCurso = new Course();
		newCurso.setCourseName(coursePOJO.getCourseName().toUpperCase());
		newCurso.setDurationHours(coursePOJO.getDurationHours());
		for (int i = 0; i < newUser.getRoles().size(); i++) {
			if (newUser.getRoles().get(i).getRoleName().equals("Profesor")) {
				test = true;
				break;
			}
		}
		if (test && existingCourse == null) {
			courseService.save(newCurso);
			return new ResponseEntity<>("Curso creado con éxito", HttpStatus.CREATED);
		} else if (!test) {
			return new ResponseEntity<>("El usuario no es profesor", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("El curso ya ha sido registrado", HttpStatus.BAD_REQUEST);

		}
	}
	
	

}
