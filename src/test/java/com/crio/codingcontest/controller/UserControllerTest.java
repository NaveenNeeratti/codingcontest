package com.crio.codingcontest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crio.codingcontest.entities.User;
import com.crio.codingcontest.exceptions.UserNotFoundException;
import com.crio.codingcontest.exchanges.UserRegistrationRequest;
import com.crio.codingcontest.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper();
    mvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

    @Test
    public void testGetUser_Success() throws Exception {
        Integer userId = 123;
        String userName = "TestUser";
        User user = new User(userId, userName);
        when(userService.findUserByUserId(userId)).thenReturn(user);
        MockHttpServletResponse response =
        mvc.perform(MockMvcRequestBuilders.get("/users/"+userId)).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(userService, times(1)).findUserByUserId(userId);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testGetUser_UserNotFoundException() throws Exception {
        Integer userId = 123;
        when(userService.findUserByUserId(userId)).thenThrow(new UserNotFoundException("User not found!"));
        MockHttpServletResponse response =
        mvc.perform(MockMvcRequestBuilders.get("/users/"+userId)).andReturn().getResponse();
        verify(userService, times(1)).findUserByUserId(userId);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void testGetUser_InvalidUserIdFormat() throws Exception {
    String invalidUserId = "abc"; // Invalid user ID format
    
    MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/users/" + invalidUserId)).andReturn().getResponse();
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
    UserRegistrationRequest request = new UserRegistrationRequest(1, "TestUser");
    User registeredUser = new User(1, "TestUser");
    String expected = "{\"userId\":1,\"userName\":\"TestUser\",\"score\":0,\"badges\":[]}";
    when(userService.isRegisteredUser(request.getUserId())).thenReturn(false);
    when(userService.registerUser(request.getUserId(), request.getUserName())).thenReturn(registeredUser);

    //ResponseEntity<User> response = userController.registerUser(request);
    MockHttpServletResponse response = mvc
    .perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(request)))
    .andReturn().getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(expected, response.getContentAsString());
    }

    @Test
    public void testRegisterUser_UserIdAlreadyRegistered() throws Exception {
    UserRegistrationRequest request = new UserRegistrationRequest(1, "TestUser");
    when(userService.isRegisteredUser(request.getUserId())).thenReturn(true);

    //ResponseEntity<User> response = userController.registerUser(request);
    MockHttpServletResponse response = mvc
    .perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
    .content(objectMapper.writeValueAsString(request)))
    .andReturn().getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

}
