package com.fable.weatherall.Repos;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fable.weatherall.ClothEntites.ClothingRecommendation;
import com.fable.weatherall.OutEntities.ActivityRecommendation;


@EnableJpaRepositories
@Repository
public interface OutRepo extends JpaRepository<ActivityRecommendation, Integer> {
	
	public interface ActivityRecommendationProjection {
		
        String getActivityname();
        String getLevel();
    }

    public interface ActivityRecommendationDetailsProjection {
		
	    Integer getRecommendation_Id();
        String getDescription_Id();
        String getActivity_Id();
        String getLevel_Id();
    }
    
    @Query("SELECT ar.activityRecommendationid "+"FROM ActivityRecommendation ar " + "JOIN ar.activity a " +"WHERE a.activityid = :activityid")
	Integer findActivityRecommendationidByActivity_Activityid(Integer activityid);
    
    
    @Query("SELECT CASE WHEN COUNT(ar) > 0 THEN true ELSE false END " +
            "FROM ActivityRecommendation ar " +
            "JOIN ar.activity a " +
            "WHERE a.activityid = :activityid")
    boolean existsByActivityid(@Param("activityid") int activityid);

	
    @Query("SELECT a.activityname as activityname, rlo.level as level " +
           "FROM ActivityRecommendation ar " +
           "JOIN ar.activity a " +
           "JOIN ar.recommendationLevel rlo " +
           "JOIN ar.weatherDescription wd " +
           "WHERE wd.description = :description")
    List<ActivityRecommendationProjection> findByWeatherDescriptionDescription(@Param("description") String description);

    
    @Query("SELECT ar.activityRecommendationid AS recommendation_Id, wd.weatherDescriptionId AS description_Id, a.activityid AS activity_Id, rlo.recommendationLevelId AS level_Id " +
	           "FROM ActivityRecommendation ar " +
	           "JOIN ar.weatherDescription wd " +
	           "JOIN ar.recommendationLevel rlo " +
	           "JOIN ar.activity a")
	 List<ActivityRecommendationDetailsProjection> findAllActivityRecommendationsWithDetails();
    
    
       default List<ActivityRecommendationDetailsProjection> findAllActivityRecommendationsWithDetailsSortedByRecommendationId() {
    	
        List<ActivityRecommendationDetailsProjection> results = findAllActivityRecommendationsWithDetails();
        
        results.sort(Comparator.comparing(ActivityRecommendationDetailsProjection::getRecommendation_Id));
        return results;
    }
	  
       ActivityRecommendation findByActivityRecommendationid(int id);
       
       void deleteByActivityRecommendationid(int a);

}
