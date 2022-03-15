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

//			tweeter.follow("bob", "alice");
//			tweeter.follow("james", "alice");
//			//tweeter.follow("james", "alice");
//			//tweeter.block("alice", "james");
//			UUID msg =tweeter.tweet("alice", "Hi");
//			//tweeter.follow("james", "bob");
//			UUID reply1= tweeter.reply("bob", msg, "Hello ALice");
//			UUID reply2 = tweeter.reply("alice", reply1, "How are you doing?");
//			tweeter.block("alice", "bob");
//			UUID reply3 = tweeter.reply("alice", reply1, "After alice blocked Bob. hahaha");
//			tweeter.like("bob", msg);
//			UUID reply4 = tweeter.reply("bob", reply2, "no comments!");
//			UUID reply5 = tweeter.reply("bob", reply3, "this message shouldn't reach!");

//			tweeter.follow("derek", "alice");
//			tweeter.follow("cath", "alice");
//			tweeter.follow("earl", "alice");
//			tweeter.block("alice", "fez");
//			tweeter.follow("alice", "bob");
//			tweeter.follow("fez", "bob");
//			UUID u1 = tweeter.tweet("bob", "Aiyo");
//			UUID u2 = tweeter.reply("alice", u1, "reply from a");
//			tweeter.block("alice", "bob");
//			tweeter.reply("bob", u2, "reply from b");



			//UUID EM_tweet2 = tweeter.tweet("Elon Musk", "72346836jvWoiYGtcvCQu2G4v08XWQP9aOseEUnosVSDdxLivuciZNya5iX5WVLw7smJW6Md7WCmlbFZgKp9X6EfAnI9LAovzgP2C9PUYM1LqswRQzDzC3Orrsgdcqans5D3wfl4XxfpXyS7in7v");
			/*User is null or empty; will throw IAE*/
			//UUID EM_tweet3 = tweeter.tweet("", "Elon musk ka tweet number 3");
//			UUID EM_tweet4 = tweeter.tweet(null, "Elon musk ka tweet number 4");
			/*Message is null or empty; will throw IAE*/
			//UUID EM_tweet5 = tweeter.tweet("Elon Musk", "");
			//UUID EM_tweet6 = tweeter.tweet("Elon Musk", null);

//			====================================reply before test cases====================================
//			UUID random_UUID = UUID.randomUUID();
//			tweeter.follow("Harry", "Elon Musk");
//			UUID EM_tweet7 = tweeter.tweet("Elon Musk", "Elon musk ka tweet number 7");
//			UUID reply1=tweeter.reply("Harry", EM_tweet7, "Harry's reply number 1 to Elon Musk"); //valid case
//			/*UUID null*/
			//UUID reply2 = tweeter.reply("Harry", null, "reply");
			/*User null or empty*/
			//UUID reply3 = tweeter.reply("", EM_tweet7, "reply");
			//UUID reply4 = tweeter.reply(null, EM_tweet7, "reply");
			/*reply null or empty*/
			//UUID reply5 = tweeter.reply("Harry", EM_tweet7, "");
			//UUID reply6 = tweeter.reply("Harry", EM_tweet7, null);
			/*Invalid message/tweet does not exist*/
			//UUID reply7 = tweeter.reply("Harry", random_UUID, "reply");
			/*user not shared with the original message; should throw AccessControl Exception*/
			//UUID reply8 = tweeter.reply("Ron", EM_tweet7, "reply 8 from Ron to Elon Musk");
			/*User trying to reply to themself*/
			//UUID reply9 = tweeter.reply("Elon Musk", EM_tweet7, "reply 9 to Elon Musk");
			/* will throw error as reply length is greater than 140 characters; will throw IAE/
//			UUID reply10 = tweeter.reply("Harry", EM_tweet7, "72346836jvWoiYGtcvCQu2G4v08XWQP9aOseEUnosVSDdxLivuciZNya5iX5WVLw7smJW6Md7WCmlbFZgKp9X6EfAnI9LAovzgP2C9PUYM1LqswRQzDzC3Orrsgdcqans5D3wfl4XxfpXyS7in7v");

//			====================================follow before test cases====================================
			/*User1 null or empty*/
			//tweeter.follow(null, "Elon Musk");
			//tweeter.follow("", "Elon Musk");
			/*User2 null or empty*/
			//tweeter.follow("Hermione", "");
			//tweeter.follow("Hermione", null);
			/*Both users null or empty*/
			//tweeter.follow("", "");
			//tweeter.follow(null, null);
			/*User trying to follow themself*/
			//tweeter.follow("Hermione", "Hermione");
//			tweeter.follow("Harry", "Harry");
//			====================================block before test cases====================================
			/*User1 null or empty*/
		//tweeter.block(null, "Elon Musk");
			//tweeter.block("", "Elon Musk");
			/*User2 null or empty*/
			//tweeter.block("Hermione", "");
			//tweeter.block("Hermione", null);
			/*Both users null or empty*/
			//tweeter.block("", "");
			//tweeter.block(null, null);
			/*User trying to block themself*/
			//tweeter.block("Hermione", "Hermione");
//			tweeter.block("Harry", "Harry");

//			====================================like before test cases====================================
//			tweeter.follow("Snape", "Jobs");
//			UUID SV_tweet1 = tweeter.tweet("Jobs", "Jobs ka tweet number 1");
//			tweeter.like("Snape", SV_tweet1);
			/*UUID null*/
			//tweeter.like("Snape", null);
			/*User null or empty*/
			//tweeter.like("", SV_tweet1);
			//tweeter.like(null, SV_tweet1);
			/*Invalid message/tweet does not exist*/
			//tweeter.like("Harry", random_UUID);
			/*user not shared with the original message; should throw AccessControl Exception*/
			//tweeter.like("Slughorn", SV_tweet1);
			/*User trying to like their own tweet*/
			//tweeter.like("Jobs", SV_tweet1);
			/*User trying to like a tweet again/more than one time*/
			//tweeter.like("Snape", SV_tweet1);


//			System.out.println("In the end");


			tweeter.follow("bob", "alice");
			tweeter.follow("divyaraj", "raj");
			tweeter.follow("bob", "raj");
			tweeter.follow("raj", "bob");
			tweeter.follow("dummy", "raj");
			tweeter.follow("dummy1", "raj");

			// should throw IllegalArgumentException
//	            tweeter.follow("", "raj");

//			UUID msg1 = tweeter.tweet("bob", "first tweet");
//			UUID reply_msg2 = tweeter.reply("raj", msg1, "second tweet");
//			UUID reply_reply2 = tweeter.reply("bob", reply_msg2, "bob reply to reply of raj");
//			tweeter.block("raj", "bob");
//			UUID reply_first = tweeter.reply("bob", reply_msg2, "reply after block by blocked");
//			//should throw exception
			//UUID reply_second = tweeter.reply("raj", reply_first, "trying to reply after blocking to new message");
			//should throw exception or not?
			//UUID reply_third = tweeter.reply("raj", reply_reply2, "sending reply to old message sent before blocking");

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
			//UUID reply5 = tweeter.reply("bob", reply3, "this message shouldn't reach!");
			UUID reply5 = tweeter.reply("bob",msg,"First Messag Commebt");
			UUID reply6 = tweeter.reply("alice", reply5, "Testing");



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
