package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;

import java.io.IOException;
import java.security.AccessControlException;
import java.util.HashSet;
import java.util.UUID;

@Aspect
@Order(3)
public class StatsAspect {
	@Autowired TweetStatsServiceImpl stats;

    @After("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.like(..))")
    public void likeMessage(JoinPoint joinPoint) throws AccessControlException {
        String user = (String)joinPoint.getArgs()[0];
        UUID message = (UUID)joinPoint.getArgs()[1];

        if(stats.likes.containsKey(message) && !stats.likes.get(message).contains(user)){
            stats.likes.get(message).add(user);
        }

        else{
            HashSet<String> likes = new HashSet<>();
            likes.add(user);
            stats.likes.put(message, likes);
        }
    }

    @After("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))")
    public void blockUser(JoinPoint joinPoint){
        String user = (String)joinPoint.getArgs()[0];
        String follower = (String)joinPoint.getArgs()[1];

        if(stats.blockedUsers.containsKey(user)){
            stats.blockedUsers.get(user).add(follower);
        }

        else{
            HashSet<String> blockedUser = new HashSet<>();
            blockedUser.add(follower);
            stats.blockedUsers.put(user, blockedUser);
        }
        System.out.println("after blocking: " + stats.blockedUsers.get(user) + " & followers = " + stats.followers.get(user));
    }

    @After("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
    public void followUser(JoinPoint joinPoint){
        String user = (String)joinPoint.getArgs()[1];
        String follower = (String)joinPoint.getArgs()[0];

        if(stats.followers.containsKey(user)){
            System.out.println("appending to followers");
            stats.followers.get(user).add(follower);
        }

        else{
            System.out.println("creating followers");
            HashSet<String> followers = new HashSet<>();
            followers.add(follower);
            System.out.println("adding " + follower + " to followers of "+user);
            stats.followers.put(user, followers);
        }

    }




}
