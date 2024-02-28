package com.fable.weatherall.Controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fable.weatherall.Controllers.Weather_Home_Controller;
import com.fable.weatherall.Services.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class Weather_Home_ControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private Weather_Home_Controller weatherHomeController;

    private MockMvc mockMvc;

    @Test
    void testDisplayHome() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherHomeController).build();

        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("Homepage"));
    }

    @Test
    void testDisplayLogin() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherHomeController).build();

        mockMvc.perform(get("/comloginn"))
                .andExpect(status().isOk())
                .andExpect(view().name("comlogin"));  // Make sure the view name matches the actual view file
    }


    @Test
    void testDisplaySignup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherHomeController).build();

        mockMvc.perform(get("/signupp"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }

    @Test
    void testDisplayUserDashboard() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherHomeController).build();

        mockMvc.perform(get("/user_dash"))
                .andExpect(status().isOk())
                .andExpect(view().name("user"));
    }

    @Test
    void testDisplayMap() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherHomeController).build();

        mockMvc.perform(get("/map-googlee"))
                .andExpect(status().isOk())
                .andExpect(view().name("map-google"));
    }

    @Test
    void testDisplayAboutPage() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherHomeController).build();

        mockMvc.perform(get("/aboutt"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    @Test
    void testForgetPassword() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherHomeController).build();

        mockMvc.perform(get("/forgetPasswordd"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgetPassword"));
    }

    @Test
    void testDisplayFeedbackPage() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherHomeController).build();

        mockMvc.perform(get("/feedback"))
                .andExpect(status().isOk())
                .andExpect(view().name("s_feedback"));
    }

    @Test
    void testDisplayForecast() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherHomeController).build();

        mockMvc.perform(get("/forecast"))
                .andExpect(status().isOk())
                .andExpect(view().name("Forecast"));
    }

}
