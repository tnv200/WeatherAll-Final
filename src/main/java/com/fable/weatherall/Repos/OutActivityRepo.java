package com.fable.weatherall.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.fable.weatherall.OutEntities.Activity;

@EnableJpaRepositories
@Repository
public interface OutActivityRepo extends JpaRepository<Activity, Integer>{
	
	 List<Activity> findAll(); 
	 
	 void deleteByActivityid(int a);
	 
	 Activity findByActivityname(String name);
	 
	 Activity findByActivityid(int id);

}
