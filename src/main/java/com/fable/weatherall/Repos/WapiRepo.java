package com.fable.weatherall.Repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.fable.weatherall.Admin_User_Entities.WeatherApi;

@EnableJpaRepositories
@Repository
public interface WapiRepo extends JpaRepository<WeatherApi, Long>{
	
    List<WeatherApi> findAll();
    Optional<WeatherApi> findById(long id);

	
    //WeatherApi findById(long id);

}
