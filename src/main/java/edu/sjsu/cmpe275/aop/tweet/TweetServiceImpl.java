package edu.sjsu.cmpe275.aop.tweet;

import java.io.IOException;
import java.security.AccessControlException;
import java.util.UUID;

public class TweetServiceImpl implements TweetService {
	@Override
    public UUID tweet(String user, String message) throws IllegalArgumentException, IOException {
    	System.out.printf("User %s tweeted message: %s\n", user, message);
    	return UUID.randomUUID();
    }

	@Override
	public UUID reply(String user, UUID originalMessage, String message) throws AccessControlException, IllegalArgumentException, IOException {
    	System.out.printf("User %s tweeted replied to message %s with message: %s\n", user, originalMessage, message);
		return null;
	}

	@Override
    public void follow(String follower, String followee) throws IllegalArgumentException, IOException {
       	System.out.printf("User %s followed user %s \n", follower, followee);
    }

	@Override
	public void block(String user, String follower) throws IllegalArgumentException, IOException {
       	System.out.printf("User %s blocked user %s \n", user, follower);		
	}

	@Override
	public void like(String user, UUID messageId) throws AccessControlException, IllegalArgumentException, IOException {
		System.out.printf("User %s liked message with ID %s \n", user, messageId);				
	}
}
