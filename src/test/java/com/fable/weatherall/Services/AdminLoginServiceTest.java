package com.fable.weatherall.Services;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.Admin_User_Entities.WeatherApi;
import com.fable.weatherall.Repos.ApiKeyUrlRepo;
import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Repos.WapiRepo;
import com.fable.weatherall.Responses.LoginResponse;

@ExtendWith(MockitoExtension.class)
public class AdminLoginServiceTest {

	  @Mock
	    private UserRepo userRepo;
	  
	  @Mock
	    private PasswordEncoder passwordEncoder;

	    @InjectMocks
	    private UserService userService;
	  
	    @Mock
	    private ApiKeyUrlRepo apikeyurlrepo;
	    
	    @Mock
	    private WapiRepo wapirepo;

	    @InjectMocks
	    private AdminService Service;



	    @Test
	    void testLoginAdmin_WrongPassword() {
	        // Arrange
	        User user = new User();
	        user.setEmail("admin@example.com");
	        user.setPassword("wrong_password");
	        user.setUserType("admin");

	        User existingUser = new User();
	        existingUser.setEmail("admin@example.com");
	        existingUser.setPassword("$2a$10$1HJZ3XW7MhNLB7hPQD6qze1Tnp12Sj6TdBxSO4dXbEcZ1/W.jPF1C");
	        existingUser.setUserType("admin");

	        when(userRepo.findByEmail("admin@example.com")).thenReturn(existingUser);

	        // You can use lenient mode to ignore unnecessary stubbings
	        lenient().when(userRepo.findOneByEmailAndPassword("admin@example.com", existingUser.getPassword()))
	                .thenReturn(Optional.of(existingUser));

	        // Act
	        LoginResponse response = userService.loginAdmin(user);

	        // Assert
	        assertEquals("Login Failed. Email not exists or not an admin, or Password Not Match", response.getMessage());
	        assertFalse(response.getStatus());
	    }

	    @Test
	    void testLoginAdmin_UserNotFound() {
	        // Arrange
	        User user = new User();
	        user.setEmail("nonexistent@example.com");
	        user.setPassword("password");
	        user.setUserType("admin");

	        when(userRepo.findByEmail("nonexistent@example.com")).thenReturn(null);

	        // Act
	        LoginResponse response = userService.loginAdmin(user);

	        // Assert
	        assertEquals("Email not exists", response.getMessage());
	        assertFalse(response.getStatus());
	    }
	    @Test
	    void testLoginAdmin_NonAdminUser() {
	        // Arrange
	        User user = new User();
	        user.setEmail("nonadmin@example.com");
	        user.setPassword("password");
	        user.setUserType("user");

	        // Assuming the user does not exist in the repository
	        when(userRepo.findByEmail("nonadmin@example.com")).thenReturn(null);

	        // Act
	        LoginResponse response = userService.loginAdmin(user);

	        // Assert
	        assertEquals("Email not exists", response.getMessage());
	        assertFalse(response.getStatus());
		    }
	   
	    
	    @Test
	    public void testGetWapiKeyUrl() {
	        // Mock data
	        WeatherApi weatherApi = new WeatherApi();
	        weatherApi.setId(1L);
	        weatherApi.setWapikey("testWapiKey");
	        weatherApi.setWapiurl("testWapiUrl");

	        // Mock repository behavior
	        when(wapirepo.findById(1L)).thenReturn(Optional.of(weatherApi));

	        // Call the method
	        Map<String, String> result = Service.getWapiKeyUrl();

	        // Verify the result
	        Map<String, String> expected = new HashMap<>();
	        expected.put("wapiKey", "testWapiKey");
	        expected.put("wapiUrl", "testWapiUrl");

	        assertEquals(expected, result);
	    }
	}