package org.example.dailycontext.controller;

import jakarta.annotation.Resource;
import org.example.dailycontext.domain.entity.TaskJob;
import org.example.dailycontext.domain.entity.TaskJobLog;
import org.example.dailycontext.service.serviceImpl.JobServiceImpl;
import org.example.dailycontext.service.serviceImpl.LogServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/yrh")
public class JobController {
	@Resource
	JobServiceImpl jobService;

	@Resource
	LogServiceImpl logService;

	@PostMapping("/addTask")
	public boolean addTask(@RequestBody TaskJob taskJob){
		return jobService.addJob(taskJob);
	}

	@PostMapping("/delTask/{taskName}")
	public boolean delTask(@PathVariable("taskName") String taskName){
		return jobService.removeJob(taskName);
	}

	@GetMapping("/getTask")
	public List<TaskJob> getTasks(){
		return jobService.getJobs();
	}

	@GetMapping("/getLogs")
	public List<TaskJobLog> getLogs(){
		return logService.getList();
	}
}
