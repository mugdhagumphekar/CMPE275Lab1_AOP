package edu.sjsu.cmpe275.aop.tweet;

import java.util.*;

public class TweetStatsServiceImpl implements TweetStatsService {

	public Map<String, HashSet<String>> followers = new HashMap<>();   //User to list of followers map
	public Map<UUID, String> tweets = new HashMap<>();   //UUID to tweet content map (tweet will be user:tweetcontent)
	public Map<UUID, HashSet<String>> tweetVisibility = new HashMap<>(); //tweet UUID to users shared with map
	public Map<UUID, HashSet<String>> likes = new HashMap<>();     //tweet UUID to likes map
	public Map<UUID, HashSet<UUID>> replies = new HashMap<>();   //message to replies map
	public Map<String, HashSet<String>> blockedUsers = new HashMap<>();  //user to list of blocked users map

	@Override
	public void resetStatsAndSystem() {
		System.out.println("Clearing stats");
		this.followers.clear();
		this.tweetVisibility.clear();
		this.replies.clear();
		this.tweets.clear();
		this.blockedUsers.clear();
		this.likes.clear();
	}
    
	@Override
	public int getLengthOfLongestTweet() {
		if(this.tweets.size() > 0){
			int lengthOfLongestTweet = 0;
			Collection<String> tweets = this.tweets.values();

			for(String tweet:tweets){
				if(tweet.split(":")[1].length() > lengthOfLongestTweet){
					lengthOfLongestTweet = tweet.split(":")[1].length();
				}
			}

			return lengthOfLongestTweet;
		}

		return 0;
	}

	@Override
	public String getMostFollowedUser() {
		String mostFollowed = "";
		int numOfFollowers = 0;

		if(!this.followers.isEmpty()){
			for(Map.Entry<String, HashSet<String>> entry : this.followers.entrySet()){
				if(entry.getValue().size() > numOfFollowers){
					mostFollowed = entry.getKey();
					numOfFollowers = entry.getValue().size();
				}

				else if(entry.getValue().size() == numOfFollowers){
					mostFollowed = entry.getKey().compareTo(mostFollowed) < 0 ? entry.getKey() : mostFollowed;
				}
			}

			return mostFollowed;
		}

		return null;
	}

	@Override
	public UUID getMostPopularMessage() {
		HashMap<UUID, Integer>replies = new HashMap<>();
		UUID mostPopularID = new UUID(0,0);
		int highestOutreach = 0;

		if(!this.replies.isEmpty()){
			for(Map.Entry<UUID, HashSet<UUID>> entry : this.replies.entrySet()){
				for(UUID reply : entry.getValue()){
					replies.put(reply, 1);
				}
			}

			for(Map.Entry<UUID, HashSet<String>> entry : this.tweetVisibility.entrySet()){
				int outreachOfTweet = entry.getValue().size();

				if(replies.containsKey(entry.getKey())){
					outreachOfTweet--;
				}

				if(outreachOfTweet > highestOutreach){
					mostPopularID = entry.getKey();
					highestOutreach = outreachOfTweet;
				}

				else if(outreachOfTweet == highestOutreach){
					mostPopularID = entry.getKey().compareTo(mostPopularID) < 0 ? entry.getKey() : mostPopularID;
				}
			}

			return mostPopularID;
		}

		return null;
	}
	
	@Override
	public String getMostProductiveReplier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID getMostLikedMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMostUnpopularFollower() {
		Map<String, Integer>blockedUsers = new HashMap<>();
		String mostUnpopular = "";
		int mostBlocks = 0;

		if(!this.blockedUsers.isEmpty()){
			for(Map.Entry<String, HashSet<String>> user : this.blockedUsers.entrySet()){
				for(String blocked : user.getValue()){
					if(blockedUsers.containsKey(blocked)){
						blockedUsers.put(blocked, new Integer(blockedUsers.get(blocked).intValue() + 1));
					}

					else{
						blockedUsers.put(blocked, new Integer(1));
					}
				}
			}

			for(Map.Entry<String, Integer>blockedUser : blockedUsers.entrySet()){
				if(blockedUser.getValue().intValue() > mostBlocks){
					mostUnpopular = blockedUser.getKey();
					mostBlocks = blockedUser.getValue().intValue();
				}

				else if(blockedUser.getValue().intValue() == mostBlocks){
					mostUnpopular = mostUnpopular.compareTo(blockedUser.getKey()) < 0 ? mostUnpopular : blockedUser.getKey();
				}
			}

			return mostUnpopular;
		}


		return null;
	}

	@Override
	public UUID getLongestMessageThread() {
		// TODO Auto-generated method stub
		return null;
	}

}



