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

import com.fable.weatherall.Repos.OutRepo;
import com.fable.weatherall.Repos.OutRepo.ActivityRecommendationProjection;

@ExtendWith(MockitoExtension.class)
public class OutServiceTest {

    @Mock
    private OutRepo outRepo;

    @InjectMocks
    private OutService outService;

    @Test
    public void testGetActivities() {
        // Mock data
        String description = "Sunny";
        List<ActivityRecommendationProjection> mockResults = Arrays.asList(
                new ActivityRecommendationProjection() {
                    @Override
                    public String getActivityname() {
                        return "Hiking";
                    }

                    @Override
                    public String getLevel() {
                        return "Moderate";
                    }
                }
                // Add more mock results as needed
        );

        // Mock repository behavior
        when(outRepo.findByWeatherDescriptionDescription(description)).thenReturn(mockResults);

        // Call the service method
        List<ActivityRecommendationProjection> result = outService.getActivities(description);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size()); // Adjust the expected size based on your mock data

        // Verify that the repository method was called with the correct parameter
        verify(outRepo, times(1)).findByWeatherDescriptionDescription(description);
    }

    // Add more test cases as needed

}
