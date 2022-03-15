package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
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

    @AfterReturning(value = "execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))", returning = "tweetID")
    public void addTweet(JoinPoint joinPoint, UUID tweetID) {
        String tweet = (String) joinPoint.getArgs()[1];
        String user = (String) joinPoint.getArgs()[0];
        stats.tweets.put(tweetID, user + ":" + tweet);
        System.out.println("tweetid = " + tweetID);
        System.out.println("**************");
        System.out.println("tweet put in hashmap- " + stats.tweets.get(tweetID));

        if (stats.followers.containsKey(user)) {
            stats.tweetVisibility.put(tweetID, (HashSet<String>) stats.followers.get(user).clone());
            System.out.println("the user's followers are: " + stats.followers.get(user));
            System.out.println("This tweet is visible to: " + stats.tweetVisibility.get(tweetID));

            if(stats.blockedUsers.containsKey(user) && stats.tweetVisibility.containsKey(tweetID)){
                HashSet<String> users = stats.tweetVisibility.get(tweetID);
                for (String visibileTo : users) {
                    if (stats.blockedUsers.get(user).contains(visibileTo)) {

                        users.remove(visibileTo);
                        System.out.println("new visibility after blocking = " + stats.tweetVisibility.get(tweetID));
                        System.out.println("followers = " + stats.followers.get(user));
                    }
                }
            }

        } else {
            stats.followers.put(user, new HashSet<String>());
        }
    }

    @AfterReturning(value = "execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.reply(..))", returning = "tweetID")
    public void addReply(JoinPoint joinPoint, UUID tweetID) {
        String reply = (String) joinPoint.getArgs()[2];
        String user = (String) joinPoint.getArgs()[0];
        UUID originalMessageID = (UUID) joinPoint.getArgs()[1];
        String tweeter = stats.tweets.get(originalMessageID).split(":")[0];

        stats.tweets.put(tweetID, user + ":" + reply);
        //System.out.println(stats.tweets.get(tweetID));

        if(stats.followers.containsKey(user)){
            stats.tweetVisibility.put(tweetID, (HashSet<String>) stats.followers.get(user).clone());
            stats.tweetVisibility.get(tweetID).add(tweeter);
        }

        else{
            HashSet<String> visibility = new HashSet<>();
            visibility.add(tweeter);
            stats.tweetVisibility.put(tweetID, visibility);
        }

        if(stats.replies.containsKey(originalMessageID)){
            stats.replies.get(originalMessageID).add(tweetID);
        }

        else{
            HashSet<UUID> replies = new HashSet<>();
            replies.add(tweetID);
            stats.replies.put(originalMessageID, replies);
        }

        if(stats.blockedUsers.containsKey(user)){
            for (String visibileTo : stats.tweetVisibility.get(tweetID)) {
                if (stats.blockedUsers.get(user).contains(visibileTo)) {
                    stats.tweetVisibility.get(tweetID).remove(visibileTo);
                    System.out.println(stats.tweetVisibility.get(tweetID));
                }
            }
        }
    }

}
