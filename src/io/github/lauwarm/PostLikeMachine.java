/**
 * 
 */
package io.github.lauwarm;

import java.io.IOException;
import java.util.ArrayList;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Fabian
 *
 */
public class PostLikeMachine {
	
	private static ReadUsernames readUsernames;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ArrayList<Long> ids = new ArrayList<Long>();
		ArrayList<String> usernames = new ArrayList<String>();
		long[] id;
				
		try {
			readUsernames = new ReadUsernames();
			usernames = readUsernames.getUsernames();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(PropertiesTwitter.getProperties().getProperty("oauth.consumerKey"))
		  .setOAuthConsumerSecret(PropertiesTwitter.getProperties().getProperty("oauth.consumerSecret"))
		  .setOAuthAccessToken(PropertiesTwitter.getProperties().getProperty("oauth.accessToken"))
		  .setOAuthAccessTokenSecret(PropertiesTwitter.getProperties().getProperty("oauth.accessTokenSecret"));
		
		TwitterFactory twitterFactory = new TwitterFactory(cb.build());
		Twitter twitter = twitterFactory.getInstance();
		
		try {
			twitter.verifyCredentials();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			twitter.getHomeTimeline();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		try {
			for (String username1 : usernames) {
				ids.add(twitter.showUser(username1).getId());
			}
		} catch (TwitterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		id = new long[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			id[i] = ids.get(i);
		}
		
		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
				for (long a : id) {
					if (status.getUser().getId() == a) {
						System.out.println(status.getUser().getName() + " : " + status.getText());
						try {
							System.out.println("--- Liking Post ---" );
							twitter.createFavorite(status.getId());
						} catch (TwitterException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}

			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				// TODO Auto-generated method stub
				
			}
		};
		
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);
		FilterQuery query = new FilterQuery();
		query.follow(id);
		twitterStream.filter(query);
		
	}
	
}
