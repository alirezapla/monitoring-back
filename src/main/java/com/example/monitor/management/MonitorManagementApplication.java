package com.example.monitor.management;

import com.example.monitor.management.common.AppLogEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import com.example.monitor.management.common.MyLogger;


@EnableJpaAuditing
@EnableEnversRepositories
@EnableJpaRepositories(basePackages = {"com.example"}, repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@SpringBootApplication
public class MonitorManagementApplication {

	public static void main(String[] args) {
		MyLogger.doLog(LogLevel.INFO, AppLogEvent.MONITORING_APP_STARTED,"");
		SpringApplication.run(MonitorManagementApplication.class, args);
	}

}
