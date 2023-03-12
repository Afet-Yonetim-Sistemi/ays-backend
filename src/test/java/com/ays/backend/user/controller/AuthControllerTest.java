package com.ays.backend.user.controller;

import com.ays.backend.base.BaseRestControllerTest;
import com.ays.backend.user.controller.payload.request.RegisterRequest;
import com.ays.backend.user.controller.payload.response.AuthResponse;
import com.ays.backend.user.service.AuthService;
import com.ays.backend.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthControllerTest extends BaseRestControllerTest {

    private final String ADMIN_CONTROLLER_BASEURL = "/api/v1/admin";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserService userService;


    @Test
    void shouldRegisterForAdmin() throws Exception {

        // Given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("testadmin")
                .password("testadmin")
                .countryCode("1")
                .lineNumber("1234567890")
                .firstName("First Name Admin")
                .lastName("Last Name Admin")
                .email("testadmin@afet.com")
                .organizationId(1L)
                .build();

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken("test_token")
                .message("success")
                .build();

        // when
        when(authService.register(registerRequest)).thenReturn(authResponse);

        // then - Perform the POST request and assert the response
        mockMvc.perform(post(ADMIN_CONTROLLER_BASEURL + "/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value(authResponse.getAccessToken()))
                .andExpect(jsonPath("$.message").value(authResponse.getMessage()));

    }

}
