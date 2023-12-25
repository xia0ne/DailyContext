package org.example.dailycontext.service.serviceImpl;

import jakarta.annotation.Resource;
import org.example.dailycontext.domain.entity.TaskJobLog;
import org.example.dailycontext.domain.mapper.LogList;
import org.example.dailycontext.service.LogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
	@Resource
	LogList logList;

	@Override
	public void addLog(TaskJobLog taskJobLog) {
		logList.addLog(taskJobLog);
	}

	@Override
	public List<TaskJobLog> getList() {
		return logList.getList();
	}
}
