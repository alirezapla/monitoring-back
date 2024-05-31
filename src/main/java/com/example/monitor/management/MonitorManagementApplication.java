package com.example.monitor.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;


@EnableJpaAuditing
@EnableEnversRepositories
@EnableJpaRepositories(basePackages = {"com.example"}, repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@SpringBootApplication
public class MonitorManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorManagementApplication.class, args);
	}

}
