package com.fable.weatherall.Services;

	import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.extension.ExtendWith;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.junit.jupiter.MockitoExtension;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.mail.javamail.JavaMailSender;

	import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.DTOs.LoginDTO;
import com.fable.weatherall.DTOs.UserDTO;
	import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Responses.LoginResponse;

	@ExtendWith(MockitoExtension.class)
	public class UserServiceLoginTest {

	    @Mock
	    private UserRepo userRepo;

	    @Mock
	    private PasswordEncoder passwordEncoder;

	    @Mock
	    private JavaMailSender javaMailSender;

	    @InjectMocks
	    private UserService userService;

	    @BeforeEach
	    public void setUp() {
	        // You can perform any setup operations here if needed
	    }

	    @Test
	    public void testAddUserInAdmin() {
	        // Arrange
	        UserDTO userDTO = new UserDTO();
	        userDTO.setEmail("test@example.com");
	        userDTO.setUsername("testuser");
	        userDTO.setPassword("testpassword");
	        userDTO.setUserType("admin");

	        when(userRepo.findByEmail("test@example.com")).thenReturn(null);
	        when(passwordEncoder.encode("testpassword")).thenReturn("encodedPassword");

	        // Act
	        String result = userService.addUserinAdmin(userDTO);

	        // Assert
	        assertEquals("testuser", result);
	        verify(userRepo, times(1)).save(any(User.class));
	    }

	    @Test
	    public void testAddUser() {
	        // Arrange
	        UserDTO userDTO = new UserDTO();
	        userDTO.setEmail("test@example.com");
	        userDTO.setUsername("testuser");
	        userDTO.setPassword("testpassword");
	        userDTO.setUserType("user");
	        userDTO.setOtp("123456");

	        User existingUser = new User();
	        existingUser.setOtp("123456");

	        when(userRepo.findByEmail("test@example.com")).thenReturn(existingUser);
	        when(passwordEncoder.encode("testpassword")).thenReturn("encodedPassword");

	        // Act
	        String result = userService.addUser(userDTO);

	        // Assert
	        assertEquals("testuser", result);
	        verify(userRepo, times(1)).save(any(User.class));
	    }

	    @Test
	    void testLoginUser_ValidCredentials() {
	        // Arrange
	        LoginDTO loginDTO = new LoginDTO();
	        loginDTO.setEmail("test@example.com");
	        loginDTO.setPassword("password");

	        User user = new User();
	        user.setEmail("test@example.com");
	        user.setPassword(passwordEncoder.encode("password"));

	        when(userRepo.findByEmail("test@example.com")).thenReturn(user);
	        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);

	        // Act
	        LoginResponse loginResponse = userService.loginUser(loginDTO);

	        // Assert
	        assertNotNull(loginResponse);
	        assertTrue(loginResponse.getStatus());
	        assertEquals("Login Success", loginResponse.getMessage());
	    }

	    @Test
	    void testLoginUser_InvalidCredentials() {
	        // Arrange
	        LoginDTO loginDTO = new LoginDTO();
	        loginDTO.setEmail("test@example.com");
	        loginDTO.setPassword("wrong_password");

	        User user = new User();
	        user.setEmail("test@example.com");
	        user.setPassword(passwordEncoder.encode("correct_password"));

	        when(userRepo.findByEmail("test@example.com")).thenReturn(user);
	        when(passwordEncoder.matches("wrong_password", user.getPassword())).thenReturn(false);

	        // Act
	        LoginResponse loginResponse = userService.loginUser(loginDTO);

	        // Assert
	        assertNotNull(loginResponse);
	        assertFalse(loginResponse.getStatus());
	        assertEquals("Password Not Match", loginResponse.getMessage());
	    }

	    @Test
	    void testLoginUser_UserNotFound() {
	        // Arrange
	        LoginDTO loginDTO = new LoginDTO();
	        loginDTO.setEmail("nonexistent@example.com");
	        loginDTO.setPassword("password");

	        when(userRepo.findByEmail("nonexistent@example.com")).thenReturn(null);

	        // Act
	        LoginResponse loginResponse = userService.loginUser(loginDTO);

	        // Assert
	        assertNotNull(loginResponse);
	        assertFalse(loginResponse.getStatus());
	        assertEquals("Email not exists", loginResponse.getMessage());
	    }


	   
	}
