package com.cd.quizwhiz;

import com.cd.quizwhiz.Stats.Leaderboard;
import com.cd.quizwhiz.UserStuff.Auth;
import com.cd.quizwhiz.UserStuff.PasswordEncryption;
import com.cd.quizwhiz.UserStuff.User;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws Exception {
        User user1 = new User("jowwnn3y");
        user1.AddScore();
        user1.AddScore();
        user1.AddScore();
        user1.AddScore();
        System.out.println(user1.currentScore);
        user1.FinalScore();
        System.out.println(Auth.login("jonny","1233"));
        int count = 1;
        for(String[] i:Leaderboard.getLeaderboard("user1",0)) {
            System.out.println(count+": "+i[0]+"-"+i[1]);
            count++;
        }
    }
}



