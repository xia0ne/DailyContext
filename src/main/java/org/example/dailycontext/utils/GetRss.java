package org.example.dailycontext.utils;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.example.dailycontext.domain.entity.FeedItems;
import org.springframework.stereotype.Component;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class GetRss {
	private static final int TIMEOUT_SECONDS = 8;
	public List<FeedItems> rssReader(String url){
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		List<FeedItems> result = null;
		try{
			Callable<List<FeedItems>> task = () -> {
				URL feedSources = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) feedSources.openConnection();
				SyndFeedInput input = new SyndFeedInput();
				SyndFeed feed = input.build(new XmlReader(connection));
				List<FeedItems> items = new ArrayList<>();
				List<SyndEntry> sb = feed.getEntries();
				for (SyndEntry syndEntry : sb) {
					FeedItems temp = new FeedItems();
					temp.setTime(syndEntry.getPublishedDate());
					temp.setUrl(syndEntry.getUri());
					temp.setTitle(syndEntry.getTitle());
					items.add(temp);
				}
				return items;
			};
			Future<List<FeedItems>> future = executorService.submit(task);
			result = future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
		}catch (Exception e){
			return null;
		}finally {
			executorService.shutdown();
		}
		return result;
	}

}
