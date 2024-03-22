package com.crio.codingcontest.service;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.crio.codingcontest.entities.Badge;
import com.crio.codingcontest.entities.User;
import com.crio.codingcontest.exceptions.UserNotFoundException;
import com.crio.codingcontest.repositories.UserRepository;
import com.crio.codingcontest.services.UserService;
import com.crio.codingcontest.services.UserServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegisterUser_Success() {
    when(userRepositoryMock.save(any(User.class))).thenReturn(new User(1, "TestUser"));
    User registeredUser = userService.registerUser(1, "TestUser");

    assertNotNull(registeredUser);
    assertEquals(1, registeredUser.getUserId());
    assertEquals("TestUser", registeredUser.getUserName());
    }

    @Test
    public void testIsRegisteredUser_True() {
    when(userRepositoryMock.findByUserId(1)).thenReturn(new User(1, "TestUser"));
    boolean isRegistered = userService.isRegisteredUser(1);

    assertTrue(isRegistered);
    }

    @Test
    public void testGetLeaderBoardBasedOnScore() {
        User user1 = new User(1, "User1");
        user1.setScore(30);
        User user2 = new User(2, "User2");
        user2.setScore(60);
        User user3 = new User(3, "User3");
        user3.setScore(90);
    List<User> userList = Arrays.asList(user1, user2, user3);
    when(userRepositoryMock.findAll()).thenReturn(userList);

    List<User> leaderBoard = userService.getLeaderBoardBasedOnScore();

    assertEquals(3, leaderBoard.size());
    assertEquals("User3", leaderBoard.get(0).getUserName()); 
    assertEquals("User2", leaderBoard.get(1).getUserName()); 
    }

    @Test
    public void testUpdateUserBadges() throws UserNotFoundException {
    User user = new User(1, "TestUser");
    when(userRepositoryMock.findByUserId(1)).thenReturn(user);

    User updatedUser = userService.updateUserBadges(1, 60);

    assertEquals(60, updatedUser.getScore());
    assertTrue(updatedUser.getBadges().contains(Badge.CODECHAMP));
    }

    @Test
    public void testDeleteUserByUserId() {
    User user = new User(1, "TestUser");
    when(userRepositoryMock.findByUserId(1)).thenReturn(user);

    userService.deleteUserByUserId(1);

    verify(userRepositoryMock, times(1)).delete(user);
    }
}
