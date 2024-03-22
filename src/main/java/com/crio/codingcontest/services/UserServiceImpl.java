package com.crio.codingcontest.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crio.codingcontest.entities.User;
import com.crio.codingcontest.exceptions.UserNotFoundException;
import com.crio.codingcontest.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;

    public User registerUser(Integer userId, String userName){
        // User latestRegisteredUser = userRepositoryService.findLatestRegisteredUser();
        // if(latestRegisteredUser != null){
        //     sequenceId = latestRegisteredUser.getSequenceId();
        // }
        User user = new User(userId, userName);
        userRepository.save(user);
        return user;
    }

    public Boolean isRegisteredUser(Integer userId){
        User user = userRepository.findByUserId(userId);
        if(user != null){
            return true;
        }
        return false;
    }

    public List<User> getLeaderBoardBasedOnScore() {
       List<User> leaderBoard = userRepository.findAll();
       Collections.sort(leaderBoard, Comparator.comparing(User :: getScore).reversed());
       return leaderBoard;
    }

    @Override
    public User findUserByUserId(Integer userId) throws UserNotFoundException{
        User user = userRepository.findByUserId(userId);
        if(user == null){
            throw new UserNotFoundException("User Not found!");
        }
        return user;
    }

    public User updateUserBadges(Integer userId, Integer score) throws UserNotFoundException{
        User user = findUserByUserId(userId);
        user.setScore(score);
        user.addBadge(score);
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUserByUserId(Integer userId) {
        User user = userRepository.findByUserId(userId);
        userRepository.delete(user);
    }
}
