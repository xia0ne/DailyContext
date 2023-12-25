package org.example.dailycontext.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.example.dailycontext.domain.entity.FeedItems;
import org.example.dailycontext.domain.entity.TaskJob;
import org.example.dailycontext.domain.entity.TaskJobLog;
import org.example.dailycontext.service.serviceImpl.LogServiceImpl;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SendMessage {

	@Resource
	LogServiceImpl logService;
	@Resource
	GetRss getRss;
	public void doJob(TaskJob taskJob){
		List<FeedItems> feedItems = getRss.rssReader(taskJob.getUrl());
		if(feedItems == null)
			return;
		StringBuilder resultBuilder = new StringBuilder("| Title | URL |\n");
		resultBuilder.append("|-------|-----|\n");

		for(FeedItems item : feedItems){
			String title = item.getTitle();
			String url = item.getUrl();
			resultBuilder.append(String.format("| %s | %s |\n", title, url));
		}

		String result = resultBuilder.toString();
		sendTo(taskJob.getSendTo(), taskJob.getTitle(), result, taskJob.getJobName());
	}

	public void sendTo(String sendTo, String title, String context, String jobName){
		HttpResponse httpResponse = null;
		try{
			JSONObject json = new JSONObject();
			json.put("title", title);
			json.put("content", context);
			Map<String, String > heads = new HashMap<>();
			heads.put("Content-Type", "application/json;charset=UTF-8");
			httpResponse = HttpRequest.post(sendTo)
							.headerMap(heads, false)
							.body(json.toJSONString())
							.timeout(5 * 60 * 1000)
							.execute();
			TaskJobLog taskJobLog = new TaskJobLog();
			taskJobLog.setJobName(jobName);
			taskJobLog.setTime(new Date());
			logService.addLog(taskJobLog);

		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
