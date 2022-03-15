package edu.sjsu.cmpe275.aop.tweet;

import java.util.*;

public class TweetStatsServiceImpl implements TweetStatsService {

	public Map<String, HashSet<String>> followers = new HashMap<>();   //User to list of followers map
	public Map<UUID, String> tweets = new HashMap<>();   //UUID to tweet content map (tweet will be user:tweetcontent)
	public Map<UUID, HashSet<String>> tweetVisibility = new HashMap<>(); //tweet UUID to users shared with map
	public Map<UUID, HashSet<String>> likes = new HashMap<>();     //tweet UUID to likes map
	public Map<UUID, HashSet<UUID>> replies = new LinkedHashMap<>();   //message to replies map
	public Map<String, HashSet<String>> blockedUsers = new HashMap<>();  //user to list of blocked users map
	Map<UUID, Integer> messageThreads = new HashMap<>(); //stores last reply in thread to thread length map
	Map<UUID, Integer> visitedTweets = new HashMap<>();  //marks the visited replies while calculating the longest thread

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
		StringBuilder mostFollowed = new StringBuilder();
		int numOfFollowers = 0;

		if(!this.followers.isEmpty()){
			System.out.println("Some users have followers");
			System.out.println("followers map: " + this.followers);
			for(Map.Entry<String, HashSet<String>> entry : this.followers.entrySet()){
				System.out.println("Entry: " + entry.getKey() + " " + entry.getValue().size());
				if(entry.getValue().size() > numOfFollowers){
					mostFollowed.replace(0, mostFollowed.length(), entry.getKey());
					numOfFollowers = entry.getValue().size();
					System.out.println("most followed is now " + mostFollowed);
				}

				else if(entry.getValue().size() == numOfFollowers){
					if(entry.getKey().compareTo(mostFollowed.toString()) < 0){
						mostFollowed.replace(0, mostFollowed.length(), entry.getKey());
					}
					System.out.println("There was a tie and most followed is now " + mostFollowed);
				}
			}

			System.out.println("Most followed is finally " + mostFollowed);
			return mostFollowed.toString();
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
		int highestCharCount = 0;
		String replier = "";

		if(!this.replies.isEmpty()){
			for(Map.Entry<UUID, HashSet<UUID>> reply : this.replies.entrySet()){
				for(UUID replyID : reply.getValue()){
					if(this.tweets.containsKey(replyID) && this.tweets.get(replyID).length() > highestCharCount){
						highestCharCount = this.tweets.get(replyID).split(":")[1].length();
						replier = this.tweets.get(replyID).split(":")[0];
					}

					else if(this.tweets.containsKey(replyID) && this.tweets.get(replyID).length() == highestCharCount){
						String currentReplier = this.tweets.get(replyID).split(":")[0];
						replier = currentReplier.compareTo(replier) < 0 ? currentReplier : replier;
					}
				}
			}

			return replier;
		}

		return null;
	}

	@Override
	public UUID getMostLikedMessage() {
		UUID mostLikedMessage = new UUID(0,0);
		int numOfLikes = 0;

		if(!this.likes.isEmpty()){
			for(Map.Entry<UUID, HashSet<String>> entry : this.likes.entrySet()){
				if(entry.getValue().size() > numOfLikes){
					mostLikedMessage = entry.getKey();
					numOfLikes = entry.getValue().size();
				}

				else if(entry.getValue().size() == numOfLikes){
					mostLikedMessage = entry.getKey().compareTo(mostLikedMessage) < 0 ? entry.getKey() : mostLikedMessage;
				}
			}

			return mostLikedMessage;
		}
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
		System.out.println("******Starting longest thread*******");
		if(!this.replies.isEmpty()){
			for(Map.Entry<UUID, HashSet<UUID>> tweet : this.replies.entrySet()){
				//System.out.println(this.tweets.get(tweet.getKey()));
				this.visitedTweets.put(tweet.getKey(), 1);
				searchLongestThread(1, tweet.getKey());
			}

			Integer longestThreadLen = new Integer(0);
			UUID lastReply = new UUID(0, 0);

			for(Map.Entry<UUID, Integer> thread : this.messageThreads.entrySet()){
				System.out.println("thread last msg: " + this.tweets.get(thread.getKey()));
				System.out.println("thread len = " + thread.getValue());
				if(thread.getValue() > longestThreadLen){
					longestThreadLen = thread.getValue();
					lastReply = thread.getKey();
				}

				else if(thread.getValue() == longestThreadLen){
					lastReply = thread.getKey().compareTo(lastReply) < 0 ? thread.getKey() : lastReply;
				}
			}

			return lastReply;
		}

		return null;
	}

	HashMap searchLongestThread(int currentThreadLen, UUID currentReply){
		System.out.println("next msg in thread: " + this.tweets.get(currentReply));
		if(this.replies.containsKey(currentReply)){
			this.visitedTweets.put(currentReply, 1);

			for(UUID reply : this.replies.get(currentReply)){
				if(!this.visitedTweets.containsKey(reply)){
					this.visitedTweets.put(reply, 1);
					HashMap thread = searchLongestThread(currentThreadLen+1, reply);
					if(thread!=null){
						Map.Entry<UUID,Integer> entry = (Map.Entry<UUID, Integer>) thread.entrySet().iterator().next();
						UUID lastReply = entry.getKey();
						Integer numOfMessages = entry.getValue();
						this.messageThreads.put(lastReply, numOfMessages);
					}
				}
			}
		}

		else{
			HashMap<UUID, Integer> threadDetails = new HashMap<>();
			System.out.println("creating hashmap: " + currentReply + " " + currentThreadLen);
			threadDetails.put(currentReply, new Integer(currentThreadLen));
			System.out.println(threadDetails.get(currentReply));
			return threadDetails;
		}

		return null;
	}

}



