package com.fable.weatherall.Controller;
import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Map;

import com.fable.weatherall.Admin_User_Entities.User;
import com.fable.weatherall.Configs.CustomUserDetail;
import com.fable.weatherall.Controllers.Weather_Home_RestControllers;
import com.fable.weatherall.DTOs.LoginDTO;
import com.fable.weatherall.DTOs.UserDTO;
import com.fable.weatherall.DTOs.VerifyOtpDTO;
import com.fable.weatherall.Repos.UserRepo;
import com.fable.weatherall.Responses.Feedback;
import com.fable.weatherall.Responses.LoginResponse;
import com.fable.weatherall.Services.EmailService;
import com.fable.weatherall.Services.UserService;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;




@RunWith(MockitoJUnitRunner.class)
public class Weather_Home_RestControllersTest {

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private Weather_Home_RestControllers weatherHomeRestControllers;
    
    

    
    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        when(userService.addUser(userDTO)).thenReturn("userId123");

        String response = weatherHomeRestControllers.saveUser(userDTO);

        assertEquals("userId123", response);
    }

    @Test
    public void testLoginUser() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password");

        when(userService.loginUser(loginDTO)).thenReturn(new LoginResponse("Login Success", true));

        ResponseEntity<?> response = weatherHomeRestControllers.loginUser(loginDTO, session);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testVerifyOtp() {
        VerifyOtpDTO verifyOtpDTO = new VerifyOtpDTO();
        verifyOtpDTO.setEmail("test@example.com");
        verifyOtpDTO.setOtp("123456");

        when(userService.verifyOtp("test@example.com", "123456")).thenReturn(true);

        ResponseEntity<String> response = weatherHomeRestControllers.verifyOtp(verifyOtpDTO);

        assertEquals("OTP verified successfully", response.getBody());
    }
    
    
    @Test
    public void testSendOtpToMail() {
        // Mocking the void method
        doNothing().when(userService).sendOtpService("test@example.com");

        // Calling the method under test
        String response = weatherHomeRestControllers.sendOtpToMail("test@example.com");

        // Asserting that the method was called successfully
        assertEquals("otp send successfully", response);
    }

    // Add more test cases for other methods as needed
    
    @Test
    void saveUser_Success() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        when(userService.addUser(userDTO)).thenReturn("userId123");

        // Act
        String result = weatherHomeRestControllers.saveUser(userDTO);

        // Assert
        assertEquals("userId123", result);
    }

    @Test
    void loginUser_Success() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        when(userService.loginUser(loginDTO)).thenReturn(new LoginResponse("Login Success", true));

        // Act
        ResponseEntity<?> result = weatherHomeRestControllers.loginUser(loginDTO, session);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody() instanceof LoginResponse);
        assertEquals("Login Success", ((LoginResponse) result.getBody()).getMessage());
        assertTrue(((LoginResponse) result.getBody()).getStatus());
    }

    @Test
    void verifyOtp_ValidOtp_Success() {
        // Arrange
        VerifyOtpDTO verifyOtpDTO = new VerifyOtpDTO();
        when(userService.verifyOtp(any(), any())).thenReturn(true);

        // Act
        ResponseEntity<String> result = weatherHomeRestControllers.verifyOtp(verifyOtpDTO);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("OTP verified successfully", result.getBody());
    }

    @Test
    void verifyOtp_InvalidOtp_BadRequest() {
        // Arrange
        VerifyOtpDTO verifyOtpDTO = new VerifyOtpDTO();
        when(userService.verifyOtp(any(), any())).thenReturn(false);

        // Act
        ResponseEntity<String> result = weatherHomeRestControllers.verifyOtp(verifyOtpDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid OTP or OTP expired", result.getBody());
    }

    @Test
    void sendOtpToMail_Success() {
        // Arrange
        String email = "test@example.com";

        // Act
        String result = weatherHomeRestControllers.sendOtpToMail(email);

        // Assert
        assertEquals("otp send successfully", result);
        verify(userService).sendOtpService(email);
    }

    @Test
    void loginAdmin_Success() {
        // Arrange
        User user = new User();
        when(userService.loginAdmin(user)).thenReturn(new LoginResponse("Login Success", true));

        // Act
        ResponseEntity<?> result = weatherHomeRestControllers.loginAdmin(user, session);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody() instanceof LoginResponse);
        assertEquals("Login Success", ((LoginResponse) result.getBody()).getMessage());
        assertTrue(((LoginResponse) result.getBody()).getStatus());
    }
    
    
    @Test
    public void testCheckEmailExists() {
        String testEmail = "test@example.com";
        User testUser = new User();
        when(userRepo.findByEmail(testEmail)).thenReturn(testUser);

        ResponseEntity<Map<String, Boolean>> responseEntity = weatherHomeRestControllers.checkEmailExists(testEmail);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(true, responseEntity.getBody().get("exists"));
    }
    
    @Test
    public void testSaveEmailAndOTP() {
        VerifyOtpDTO verifyOtpDTO = new VerifyOtpDTO();
        verifyOtpDTO.setEmail("test@example.com");
        verifyOtpDTO.setOtp("123456");

        doNothing().when(userService).saveEmailAndOTP(verifyOtpDTO);

        ResponseEntity<String> responseEntity = weatherHomeRestControllers.saveEmailAndOTP(verifyOtpDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Email and OTP saved successfully!", responseEntity.getBody());

        // Verify that the service method is called with the correct argument
        verify(userService).saveEmailAndOTP(verifyOtpDTO);
    }
    
    @Test
    public void testSubmitFeedback() {
        Feedback feedback = new Feedback();
        feedback.setSuggestion("Test suggestion");

        CustomUserDetail user = new CustomUserDetail(new User()); // Create a User object and pass it to CustomUserDetail
        user.setEmail("test@example.com");

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);

        Map<String, Object> response = weatherHomeRestControllers.submitFeedback(feedback, session, mock(Model.class));

        assertEquals(true, response.get("success"));
        assertEquals("Feedback submitted successfully!", response.get("message"));

        // Verify that the emailService.sendFeedbackEmail is called with the correct arguments
        verify(emailService).sendFeedbackEmail(eq("weatherfable@gmail.com"), eq("Feedback Suggestion"), contains("User:"));
    }


}
