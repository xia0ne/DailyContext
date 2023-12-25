package org.example.dailycontext.domain.mapper;

import lombok.Data;
import org.example.dailycontext.domain.entity.TaskJob;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Data
public class TaskList {
	List<TaskJob> taskJobs = new ArrayList<>();

	public boolean addJob(TaskJob taskJob){
		Optional<TaskJob> jobToAdd = taskJobs.stream()
						.filter(x -> x.equals(taskJob))
						.findFirst();
		if(jobToAdd.isPresent()){
			return false;
		}
		taskJobs.add(taskJob);
		return true;
	}

	public boolean removeJob(String jobName){
		Optional<TaskJob> jobToRemove = taskJobs.stream()
						.filter(x -> x.getJobName().equals(jobName))
						.findFirst();
		if(jobToRemove.isPresent()){
			taskJobs.remove(jobToRemove.get());
			return true;
		}
		return false;
	}
}
