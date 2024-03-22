package com.crio.codingcontest.services;

import java.util.List;

import com.crio.codingcontest.entities.User;
import com.crio.codingcontest.exceptions.UserNotFoundException;

public interface UserService {
    //It registers a new user details in database
    public User registerUser(Integer userId, String userName);
    //It finds whether the user is already registered or not
    public Boolean isRegisteredUser(Integer userId);
    //It return the list of users in descending order based on score
    public List<User> getLeaderBoardBasedOnScore();
    //It find user by userId
    public User findUserByUserId(Integer userId);
    //It deletes user based on userId
    public void deleteUserByUserId(Integer userId);
    //It updates Badges of user based on userId and score.
    public User updateUserBadges(Integer userId, Integer score) throws UserNotFoundException;
    
}
