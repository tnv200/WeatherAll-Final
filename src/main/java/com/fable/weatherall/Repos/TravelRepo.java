package com.fable.weatherall.Repos;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fable.weatherall.OutEntities.ActivityRecommendation;
import com.fable.weatherall.Repos.OutRepo.ActivityRecommendationDetailsProjection;
import com.fable.weatherall.TravelEntities.TravelRecommendation;


public interface TravelRepo extends JpaRepository<TravelRecommendation, Integer> {
	
    public interface TravelRecommendationProjection {
		
        String getTravelname();
        String getLevel();
    }

    public interface TravelRecommendationDetailsProjection {
	
    	Integer getRecommendation_Id();
        String getDescription_Id();
        String getTrvlname_Id();
        String getLevel_Id();
    }
    
    
    
    @Query("SELECT tr.travelRecommendationid "+"FROM TravelRecommendation tr " + "JOIN tr.travelnames tn "  + "WHERE tn.travelid = :travelid")
   	Integer findTravelRecommendationidByTravelnames_Travelid(Integer travelid);

    @Query("SELECT CASE WHEN COUNT(tr) > 0 THEN true ELSE false END " +
            "FROM TravelRecommendation tr " +
            "JOIN tr.travelnames tn " +
            "WHERE tn.travelid = :travelid")
    boolean existsByTravelid(@Param("travelid") int travelid);

    @Query("SELECT tn.travelname as travelname, rlo.level as level " +
           "FROM TravelRecommendation tr " +
           "JOIN tr.travelnames tn " +
           "JOIN tr.recommendationLevel rlo " +
           "JOIN tr.weatherDescription wd " +
           "WHERE wd.description = :description")
    List<TravelRecommendationProjection> findByWeatherDescriptionDescription(@Param("description") String description);
    
    @Query("SELECT tr.travelRecommendationid AS recommendation_Id, wd.weatherDescriptionId AS description_Id, tn.travelid AS trvlname_Id, rlo.recommendationLevelId AS level_Id " +
	           "FROM TravelRecommendation tr " +
	           "JOIN tr.weatherDescription wd " +
	           "JOIN tr.recommendationLevel rlo " +
	           "JOIN tr.travelnames tn")
	 List<TravelRecommendationDetailsProjection> findAllTravelRecommendationsWithDetails();
    

    default List<TravelRecommendationDetailsProjection> findAllTravelRecommendationsWithDetailsSortedByRecommendationId() {
 	
     List<TravelRecommendationDetailsProjection> results = findAllTravelRecommendationsWithDetails();
     
     results.sort(Comparator.comparing(TravelRecommendationDetailsProjection::getRecommendation_Id));
     return results;
     
    }
    
      TravelRecommendation findByTravelRecommendationid(int id);
    
      void  deleteByTravelRecommendationid(int a);

}
