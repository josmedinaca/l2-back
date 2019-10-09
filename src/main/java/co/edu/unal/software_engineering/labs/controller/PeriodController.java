package co.edu.unal.software_engineering.labs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unal.software_engineering.labs.model.Period;
import co.edu.unal.software_engineering.labs.pojo.PeriodPOJO;
import co.edu.unal.software_engineering.labs.service.PeriodService;

@CrossOrigin
@RestController
public class PeriodController {
	
	private final PeriodService periodService;
	
	
    public PeriodController(PeriodService periodService) {
		super();
		this.periodService = periodService;
	}


	@PostMapping( value = { "/crearPeriodo" } )
    public ResponseEntity crearPeriodo(@RequestBody PeriodPOJO periodoPOJO) {
    	Period existingPeriod = periodService.findByName(periodoPOJO.getName());
    	if(existingPeriod==null) {

        	Period newPeriod = new Period();
        	newPeriod.setPeriodName(periodoPOJO.getName());
        	periodService.save(newPeriod);
        	return new ResponseEntity<>("Periodo creado con éxito", HttpStatus.CREATED);
    	}
    	return new ResponseEntity<>("El periodo ya ha sido registrado", HttpStatus.BAD_REQUEST);
    	
    	
    }
}
