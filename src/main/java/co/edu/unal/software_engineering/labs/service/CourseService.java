package co.edu.unal.software_engineering.labs.service;

import org.springframework.stereotype.Service;

import co.edu.unal.software_engineering.labs.model.Course;
import co.edu.unal.software_engineering.labs.pojo.LoginUserPOJO;
import co.edu.unal.software_engineering.labs.pojo.RegisterUserPOJO;
import co.edu.unal.software_engineering.labs.repository.CourseRepository;

@Service
public class CourseService {

	private final CourseRepository courseRepository;
    public CourseService( CourseRepository courseRepository ){
        this.courseRepository = courseRepository;
    }
    
    public void save(Course course) {
    	courseRepository.save(course);
    }
    
    public Course findByName(String name) {
    	return courseRepository.findByCourseName(name);
    }
    
	/*
	 * public boolean isTeacherUser( LoginUserPOJO user ){
	 * 
	 * boolean correctness = user.getNames(); if( correctness ){ correctness =
	 * !user.getNames( ).trim( ).isEmpty( ) && !user.getPassword( ).trim( ).isEmpty(
	 * ) && !user.getUsername( ).trim( ).isEmpty( ) && !user.getSurnames( ).trim(
	 * ).isEmpty( ); } return correctness; }
	 */
}
