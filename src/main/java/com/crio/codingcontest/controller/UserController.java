package com.crio.codingcontest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.crio.codingcontest.entities.User;
import com.crio.codingcontest.exceptions.UserNotFoundException;
import com.crio.codingcontest.exchanges.UserRegistrationRequest;
import com.crio.codingcontest.exchanges.UserScoreUpdateRequest;
import com.crio.codingcontest.services.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class UserController {
    private static final String USER_API_ENDPOINT = "/users";
    @Autowired
    private UserService userService;

    @GetMapping(USER_API_ENDPOINT)
    public ResponseEntity<List<User>> getUsers(){
        List<User> leaderBoard = userService.getLeaderBoardBasedOnScore();
        return ResponseEntity.ok().body(leaderBoard);
    }

    @GetMapping(USER_API_ENDPOINT+"/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Integer userId){
        try{
        User userDetails = userService.findUserByUserId(userId);
        return ResponseEntity.ok().body(userDetails);
        }catch(UserNotFoundException exp){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(null);
        }
        
    }

    @PostMapping(USER_API_ENDPOINT)
    public ResponseEntity<User> registerUser(@RequestBody @Valid UserRegistrationRequest request){
        Integer userId = request.getUserId();
        String userName = request.getUserName();
        if(userId <= 0 || userName == null || userService.isRegisteredUser(userId)){
            return ResponseEntity.badRequest().body(null);
        }
        User registeredUser = userService.registerUser(userId, userName);
        
        return ResponseEntity.ok().body(registeredUser);
    }

    @PutMapping(USER_API_ENDPOINT+"/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody @Valid UserScoreUpdateRequest request){

        Integer score = request.getScore();
        if(score <= 0 || score > 100){
            ResponseEntity.badRequest().body(null);
        }
        try{
            User userDetails = userService.findUserByUserId(userId);
            userService.updateUserBadges(userId, score);
            return ResponseEntity.ok().body(userDetails);
        }catch(UserNotFoundException exp){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(null);
        }
    }
    
    @DeleteMapping(USER_API_ENDPOINT+"/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId){
        try{
            userService.findUserByUserId(userId);
            userService.deleteUserByUserId(userId);
            return ResponseEntity.ok().body("Deleted Successfully");
            }catch(UserNotFoundException exp){
                return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(null);
            }
    }
}
