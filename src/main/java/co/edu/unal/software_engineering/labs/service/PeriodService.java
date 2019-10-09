package co.edu.unal.software_engineering.labs.service;

import org.springframework.stereotype.Service;

import co.edu.unal.software_engineering.labs.model.Period;
import co.edu.unal.software_engineering.labs.repository.PeriodRepository;

@Service
public class PeriodService {
	

    private final PeriodRepository periodRepository;

    public PeriodService( PeriodRepository periodRepository ){
        this.periodRepository = periodRepository;
    }
	
	public void save(Period period) {
		periodRepository.save(period);
	}
	
	public Period findByName(String name) {
		return periodRepository.findByPeriodName(name);
	}
	
	
}
