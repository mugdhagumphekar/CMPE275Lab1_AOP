package edu.sjsu.cmpe275.aop.tweet.aspect;

import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Aspect
@Order(2)
public class AccessControlAspect {
	@Autowired
	TweetStatsServiceImpl stats;

//	@After("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))")
//	public void blockUser(JoinPoint joinPoint){
//		String user = (String)joinPoint.getArgs()[0];
//		String follower = (String)joinPoint.getArgs()[1];
//
//		if(stats.blockedUsers.containsKey(user)){
//			stats.blockedUsers.get(user).add(follower);
//		}
//
//		else{
//			HashSet<String> blockedUser = new HashSet<>();
//			blockedUser.add(follower);
//			stats.blockedUsers.put(user, blockedUser);
//		}
//		System.out.println("after blocking: " + stats.blockedUsers.get(user) + " & followers = " + stats.followers.get(user));
//	}

	@Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.like(..))")
	public void checkLikePossible(JoinPoint joinPoint) throws AccessControlException {
		String user = (String)joinPoint.getArgs()[0];
		UUID message = (UUID)joinPoint.getArgs()[1];

		if(!stats.tweets.containsKey(message)){
			throw new AccessControlException("This message does not exist");
		}

		String tweeter = stats.tweets.get(message).split(":")[0];

		if(user.compareTo(tweeter) == 0){
			throw new AccessControlException("User cannot like his own message");
		}

//		if(stats.followers.get(tweeter).contains(user)){
//			if(stats.blockedUsers.containsKey(tweeter) && stats.blockedUsers.get(tweeter).contains(user)){
//				throw new AccessControlException("The user cannot like this message.");
//			}

		if(stats.likes.containsKey(message) && stats.likes.get(message).contains(user)){
			throw new AccessControlException("User has already liked this message");
		}

		if(stats.tweetVisibility.containsKey(message) && !stats.tweetVisibility.get(message).contains(user)){
			throw new AccessControlException("This message is not accessible to the user");
		}
		//}

//		else if(stats.blockedUsers.containsKey(tweeter) && stats.blockedUsers.get(tweeter).contains(user)){
//			throw new AccessControlException("The user cannot like this message.");
//		}
	}

//	@After("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.like(..))")
//	public void likeMessage(JoinPoint joinPoint) throws AccessControlException {
//		String user = (String)joinPoint.getArgs()[0];
//		UUID message = (UUID)joinPoint.getArgs()[1];
//
//		if(stats.likes.containsKey(message) && !stats.likes.get(message).contains(user)){
//			stats.likes.get(message).add(user);
//		}
//
//		else{
//			HashSet<String> likes = new HashSet<>();
//			likes.add(user);
//			stats.likes.put(message, likes);
//		}
//	}

//	@After("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
//	public void followUser(JoinPoint joinPoint){
//		String user = (String)joinPoint.getArgs()[1];
//		String follower = (String)joinPoint.getArgs()[0];
//
//		if(stats.followers.containsKey(user)){
//			System.out.println("appending to followers");
//			stats.followers.get(user).add(follower);
//		}
//
//		else{
//			System.out.println("creating followers");
//			HashSet<String> followers = new HashSet<>();
//			followers.add(follower);
//			System.out.println("adding " + follower + " to followers of "+user);
//			stats.followers.put(user, followers);
//		}
//
//	}

	@Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.reply(..))")
	public void checkReplyPossible(JoinPoint joinPoint){
		String user = (String) joinPoint.getArgs()[0];
		UUID message = (UUID) joinPoint.getArgs()[1];
		String reply = (String) joinPoint.getArgs()[2];

		if(!stats.tweets.containsKey(message)){
			throw new AccessControlException("This message does not exist");
		}

		String tweeter = stats.tweets.get(message).split(":")[0];

		if(stats.blockedUsers.containsKey(user) && stats.blockedUsers.get(user).contains(tweeter)){
			throw new AccessControlException("User cannot reply to this tweet as he has blocked the sender");
		}

		else{
			if(stats.tweetVisibility.containsKey(message) && !stats.tweetVisibility.get(message).contains(user)){
				throw new AccessControlException("This tweet is not accessible to the user");
			}
		}
	}

}
