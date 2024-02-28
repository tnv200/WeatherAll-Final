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

import com.fable.weatherall.Repos.FoodRepo;


@ExtendWith(MockitoExtension.class)
public class FoodServiceTest {

    @Mock
    private FoodRepo foodRepo;

    @InjectMocks
    private FoodService foodService;

    @Test
    public void testGetFoods() {
        // Mock data
        String state = "Sunny";
        Double temperature = 25.0;
        Integer categoryId = 1;
        List<Integer> mockFoodIds = Arrays.asList(1, 2, 3);
        List<String> mockFoodNames = Arrays.asList("Pizza", "Burger", "Salad");

        // Mock repository behavior
        when(foodRepo.findCategoryIdByTemperature(temperature)).thenReturn(categoryId);
        when(foodRepo.findFoodIdsByCategoryId(categoryId)).thenReturn(mockFoodIds);
        when(foodRepo.findFoodNamesByFoodIdsAndState(mockFoodIds, state)).thenReturn(mockFoodNames);

        // Call the service method
        List<String> result = foodService.getFoods(state, temperature);

        // Verify the result
        assertNotNull(result);
        assertEquals(3, result.size()); // Adjust the expected size based on your mock data
        assertTrue(result.containsAll(Arrays.asList("Pizza", "Burger", "Salad")));

        // Verify that the repository methods were called with the correct parameters
        verify(foodRepo, times(1)).findCategoryIdByTemperature(temperature);
        verify(foodRepo, times(1)).findFoodIdsByCategoryId(categoryId);
        verify(foodRepo, times(1)).findFoodNamesByFoodIdsAndState(mockFoodIds, state);
    }

    // Add more test cases as needed

}

