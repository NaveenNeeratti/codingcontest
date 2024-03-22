package com.crio.codingcontest.entities;

import java.util.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "users")
@Data
public class User {
    @Id
    private Integer userId;
    private String userName;
    private Integer score;
    private List<Badge> badges;

    public User( Integer userId, String userName){
        this.userId = userId;
        this.userName = userName;
        this.score = 0;
        this.badges = new ArrayList<>();
    }

    public void addBadge(Integer score){
        if(badges.size() < 3){
            if(score >= 1 && score <= 30 && !badges.contains(Badge.CODENINJA)){
                badges.add(Badge.CODENINJA);
            }else if(score >= 31 && score <= 60 && !badges.contains(Badge.CODECHAMP)){
                badges.add(Badge.CODECHAMP);
            }else{
                if(!badges.contains(Badge.CODEMASTER)){
                    badges.add(Badge.CODEMASTER);
                }
            }
        }
    }

}
