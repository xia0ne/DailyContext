package org.example.dailycontext.service.serviceImpl;

import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.example.dailycontext.domain.entity.TaskJob;
import org.example.dailycontext.domain.mapper.TaskList;
import org.example.dailycontext.service.JobService;
import org.example.dailycontext.utils.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@Log
public class JobServiceImpl implements JobService {

	@Resource
	TaskList taskList;

	@Resource
	private ThreadPoolTaskScheduler taskScheduler;
	Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

	@Resource
	private SendMessage sendMessage;

	@Override
	public boolean addJob(TaskJob taskJob) {
		if (taskList.addJob(taskJob)) {
			ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(() -> {
				sendMessage.doJob(taskJob);
			}, new CronTrigger(taskJob.getCrontab()));
			scheduledTasks.put(taskJob.getJobName(), scheduledFuture);
			log.info(scheduledTasks.size() + "");
			return true;
		}
		return false;
	}

	@Override
	public boolean removeJob(String jobName) {
		if(taskList.removeJob(jobName)){
			ScheduledFuture<?> scheduledFuture = scheduledTasks.get(jobName);
			if(scheduledFuture != null){
				scheduledFuture.cancel(true);
				scheduledTasks.remove(jobName);
			}
			return true;
		}
		return false;
	}

	@Override
	public List<TaskJob> getJobs() {
		return taskList.getTaskJobs();
	}
}
