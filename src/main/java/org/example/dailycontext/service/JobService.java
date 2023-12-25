package org.example.dailycontext.service;

import org.example.dailycontext.domain.entity.TaskJob;

import java.util.List;

public interface JobService {
	public boolean addJob(TaskJob taskJob);
	public boolean removeJob(String jobName);
	public List<TaskJob> getJobs();

}
