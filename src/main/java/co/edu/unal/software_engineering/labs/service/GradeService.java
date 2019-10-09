package co.edu.unal.software_engineering.labs.service;

import org.springframework.stereotype.Service;

import co.edu.unal.software_engineering.labs.model.Grade;
import co.edu.unal.software_engineering.labs.repository.GradeRepository;

@Service
public class GradeService{
	private final GradeRepository gradeRepository; 
    public GradeService( GradeRepository gradeRepository ){
        this.gradeRepository = gradeRepository;
    }
    
    public void save(Grade grade) {
    	gradeRepository.save(grade);
    }
}
