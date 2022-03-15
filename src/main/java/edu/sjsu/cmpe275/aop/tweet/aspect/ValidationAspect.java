package edu.sjsu.cmpe275.aop.tweet.aspect;

import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

@Aspect
@Order(1)
public class ValidationAspect {
	@Autowired
	TweetStatsServiceImpl stats;

	@Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))")
	public void checkTweetLength(JoinPoint joinPoint) throws IllegalArgumentException {
		String user = (String) joinPoint.getArgs()[0];
		String tweet = (String) joinPoint.getArgs()[1];

		if (tweet.length() > 140) {
			throw new IllegalArgumentException("Tweet length cannot be longer than 140 characters");
		}

		if (tweet == null || tweet.length() == 0) {
			throw new IllegalArgumentException("Invalid tweet entered");
		}

		if (user.length() == 0 || user == null) {
			throw new IllegalArgumentException("Invalid user name entered");
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
				for (String visibileTo : stats.tweetVisibility.get(tweetID)) {
					if (stats.blockedUsers.get(user).contains(visibileTo)) {
						stats.tweetVisibility.get(tweetID).remove(visibileTo);
						System.out.println("new visibility after blocking = " + stats.tweetVisibility.get(tweetID));
						System.out.println("followers = " + stats.followers.get(user));
					}
				}
			}

		} else {
			stats.followers.put(user, new HashSet<String>());
		}
	}

	@Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.block(..)) || execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
	public void checkBlockPossible(JoinPoint joinPoint) throws IllegalArgumentException {
		String user, follower;
		if(joinPoint.getSignature().getName() == "block"){
			user = (String) joinPoint.getArgs()[0];
			follower = (String) joinPoint.getArgs()[1];
		}

		else{
			user = (String) joinPoint.getArgs()[1];
			follower = (String) joinPoint.getArgs()[0];
		}


		if (user == null || user.length() == 0) {
			throw new IllegalArgumentException("User name cannot be empty");
		}

		if (follower == null || follower.length() == 0) {
			throw new IllegalArgumentException("Follower name cannot be empty");
		}

		if (user.compareTo(follower) == 0) {
			if(joinPoint.getSignature().getName() == "block") {
				throw new IllegalArgumentException("User cannot block himself");
			}

			else{
				throw new IllegalArgumentException("User cannot follow himself");
			}
		}
	}

	@Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.like(..))")
	public void checkLikePossible(JoinPoint joinPoint) throws IllegalArgumentException {
		String user = (String) joinPoint.getArgs()[0];
		UUID message = (UUID) joinPoint.getArgs()[1];

		if(user ==null||user.length() ==0) {
			throw new IllegalArgumentException("User name cannot be empty");
		}

		if(message ==null||message.toString().length() == 0) {
			throw new IllegalArgumentException("Invalid message ID");
		}
	}

	@Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.reply(..))")
	public void checkReplyPossible(JoinPoint joinPoint) throws IllegalArgumentException {
		String user = (String) joinPoint.getArgs()[0];
		UUID message = (UUID) joinPoint.getArgs()[1];
		String reply = (String) joinPoint.getArgs()[2];

		if(!stats.tweets.containsKey(message)){
			throw new IllegalArgumentException("This message does not exist");
		}

		String tweeter = stats.tweets.get(message).split(":")[0];

		if(user ==null||user.length() ==0) {
			throw new IllegalArgumentException("User name cannot be empty");
		}

		if(reply ==null||reply.length() ==0) {
			throw new IllegalArgumentException("Reply cannot be empty");
		}

		if(message ==null||message.toString().length() == 0) {
			throw new IllegalArgumentException("Invalid message ID");
		}

		if(user.compareTo(tweeter) == 0){
			throw new IllegalArgumentException("User cannot reply directly to his own message");
		}

		if (reply.length() > 140) {
			throw new IllegalArgumentException("Tweet length cannot be longer than 140 characters");
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
