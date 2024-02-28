package com.fable.weatherall.Services;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fable.weatherall.Repos.TravelRepo;
import com.fable.weatherall.Repos.TravelRepo.TravelRecommendationProjection;

@ExtendWith(MockitoExtension.class)
public class TravelServiceTest {

    @Mock
    private TravelRepo travelRepo;

    @InjectMocks
    private TravelService travelService;

    @Test
    public void testGetActivities() {
        // Mock data
        String description = "Sunny";
        List<TravelRecommendationProjection> mockResults = Arrays.asList(
                new TravelRecommendationProjection() {
                    @Override
                    public String getTravelname() {
                        return "Beach Vacation";
                    }

                    @Override
                    public String getLevel() {
                        return "Easy";
                    }
                }
                // Add more mock results as needed
        );

        // Mock repository behavior
        when(travelRepo.findByWeatherDescriptionDescription(description)).thenReturn(mockResults);

        // Call the service method
        List<TravelRecommendationProjection> result = travelService.getActivities(description);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size()); // Adjust the expected size based on your mock data

        // Verify that the repository method was called with the correct parameter
        verify(travelRepo, times(1)).findByWeatherDescriptionDescription(description);
    }

    // Add more test cases as needed

}
