package org.example.dailycontext.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class CrontabConfig implements SchedulingConfigurer {


	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(50);
		taskScheduler.setThreadNamePrefix("-DailyContext-");
		taskScheduler.initialize();
		taskRegistrar.setTaskScheduler(taskScheduler);
		taskScheduler.getScheduledThreadPoolExecutor().getQueue().clear();
	}
}
