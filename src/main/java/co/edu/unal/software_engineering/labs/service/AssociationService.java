package co.edu.unal.software_engineering.labs.service;

import org.springframework.stereotype.Service;

import co.edu.unal.software_engineering.labs.model.Association;
import co.edu.unal.software_engineering.labs.repository.AssociationRepository;


@Service
public class AssociationService{
	
	private final AssociationRepository asociacionRepository;
    public AssociationService( AssociationRepository asociacionRepository ){
        this.asociacionRepository = asociacionRepository;
    }
	public void save( Association asociacion) {
		asociacionRepository.save(asociacion);
	}
	
	public Association findById(int id) {
		return asociacionRepository.findById(id);
	}
}
