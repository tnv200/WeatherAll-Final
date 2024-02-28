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

import com.fable.weatherall.Repos.ClothRepo;
import com.fable.weatherall.Repos.ClothRepo.ClothingRecommendationProjection;


@ExtendWith(MockitoExtension.class)
public class ClothServiceTest {

    @Mock
    private ClothRepo clothRepo;

    @InjectMocks
    private ClothService clothService;

    @Test
    public void testGetCloths() {
        // Mock data
        String description = "Sunny";
        List<ClothingRecommendationProjection> mockResults = Arrays.asList(
                new ClothingRecommendationProjection() {
                    @Override
                    public String getItemName() {
                        return "T-Shirt";
                    }

                    @Override
                    public String getTypeName() {
                        return "Summer";
                    }
                }
                
        );

        // Mock repository behavior
        when(clothRepo.findByWeatherDescriptionDescription(description)).thenReturn(mockResults);

        // Call the service method
        List<ClothingRecommendationProjection> result = clothService.getCloths(description);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size()); 

        // Verify that the repository method was called with the correct parameter
        verify(clothRepo, times(1)).findByWeatherDescriptionDescription(description);
    }

   

}

