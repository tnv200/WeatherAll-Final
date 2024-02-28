package com.fable.weatherall.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fable.weatherall.OutEntities.Activity;
import com.fable.weatherall.TravelEntities.TravelNames;

public interface TravelNameRepo  extends JpaRepository<TravelNames, Integer>{

	
	List<TravelNames> findAll(); 
	 
	void deleteByTravelid(int a);
	 
	TravelNames findByTravelname(String name);
	 
	TravelNames findByTravelid(int id);
}
