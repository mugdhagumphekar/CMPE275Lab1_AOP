package edu.sjsu.cmpe275.aop.tweet;

import java.util.UUID;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		/***
		 * Following is a dummy implementation of App to demonstrate bean creation with
		 * Application context. You may make changes to suit your need, but this file is
		 * NOT part of the submission.
		 */

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
		TweetService tweeter = (TweetService) ctx.getBean("tweetService");
		TweetStatsService stats = (TweetStatsService) ctx.getBean("tweetStatsService");

		try {
//			tweeter.follow("bob", "alice");
//			UUID msg = tweeter.tweet("alice", "first tweet");
//			System.out.println("msg = " + msg);
//			UUID reply=tweeter.reply("bob", msg, "that was brilliant");
//			System.out.println("reply = " +reply);
//			tweeter.like("bob", msg);
//			tweeter.reply("alice", reply, "no comments!");
//			tweeter.block("alice", "bob");
//			tweeter.tweet("alice", "second tweet");

//			tweeter.follow("rohan", "elon");
//			UUID msg = tweeter.tweet("elon", "hello world");
//			tweeter.follow("soham", "rohan");
//			UUID reply = tweeter.reply("rohan", msg, "hello to you too");
//			UUID reply1 = tweeter.reply("soham", reply, "hi guys");
//			//UUID reply21 = tweeter.reply("elon", reply1, "yo soham");
//			tweeter.follow("bob", "alice");
//			UUID msg1 = tweeter.tweet("alice", "first tweet");
//			UUID reply2 = tweeter.reply("bob", msg1, "reply to msg");
//			UUID reply3 = tweeter.reply("alice", reply2, "reply to reply 1");
//
//			tweeter.block("alice", "bob");
//
//			UUID reply4 = tweeter.reply("bob", reply3, "reply to reply 2");

			tweeter.follow("bob", "alice");
			tweeter.follow("james", "alice");
			//tweeter.follow("james", "alice");
			//tweeter.block("alice", "james");
			UUID msg =tweeter.tweet("alice", "Hi");
			//tweeter.follow("james", "bob");
			UUID reply1= tweeter.reply("bob", msg, "Hello ALice");
			UUID reply2 = tweeter.reply("alice", reply1, "How are you doing?");
			tweeter.block("alice", "bob");
			UUID reply3 = tweeter.reply("alice", reply1, "After alice blocked Bob. hahaha");
			tweeter.like("bob", msg);
			UUID reply4 = tweeter.reply("bob", reply2, "no comments!");
			UUID reply5 = tweeter.reply("bob", reply3, "this message shouldn't reach!");


		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Most productive user: " + stats.getMostProductiveReplier());
		System.out.println("Most popular user: " + stats.getMostFollowedUser());
		System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweet());
		System.out.println("Most popular message: " + stats.getMostPopularMessage());
		System.out.println("Most liked message: " + stats.getMostLikedMessage());
		System.out.println("Most unpopular follower: " + stats.getMostUnpopularFollower());
		System.out.println("Longest message thread: " + stats.getLongestMessageThread());
		ctx.close();
	}
}
