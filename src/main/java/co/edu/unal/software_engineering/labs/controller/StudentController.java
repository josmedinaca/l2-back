package co.edu.unal.software_engineering.labs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unal.software_engineering.labs.model.Association;
import co.edu.unal.software_engineering.labs.model.Grade;
import co.edu.unal.software_engineering.labs.pojo.GradePOJO;
import co.edu.unal.software_engineering.labs.service.AssociationService;
import co.edu.unal.software_engineering.labs.service.GradeService;

@CrossOrigin
@RestController
public class StudentController{
	private final GradeService gradeService;
	
	private final AssociationService associationService;

	public StudentController(GradeService gradeService, AssociationService associationService) {
		this.gradeService = gradeService;
		this.associationService = associationService;
	}

	
	@PostMapping(value = { "/asignarNota" })
	public ResponseEntity asigarNota(@RequestBody GradePOJO gradePOJO) {
		Association asociacion = associationService.findById(gradePOJO.getAssociationId());
		Grade newGrade = new Grade();
		newGrade.setApproved(gradePOJO.isApproved());
		newGrade.setAssociation(asociacion);
		newGrade.setNote(gradePOJO.getNote());
		
		boolean isStudent = asociacion.getRole().getRoleName().toString().equals("Estudiante") ? true : false ;
		
		if (isStudent) {
			gradeService.save(newGrade);
			return new ResponseEntity<>("Nota creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("El usuario no es estudiante", HttpStatus.BAD_REQUEST);
		}
	}


}