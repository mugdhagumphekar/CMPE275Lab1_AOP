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

@Aspect
@Order(3)
public class StatsAspect {
//	@Autowired TweetStatsServiceImpl stats;

//	@Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetStatsService.getLengthOfLongestTweet(..))")
//	public void checkTweetsExist(JoinPoint joinPoint) throws IOException {
//		if(stats.tweets == null){
//			throw new IOException("Tweets do not exist");
//		}
//	}
}
