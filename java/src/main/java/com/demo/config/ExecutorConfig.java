package com.demo.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

/*
 * Author    :  baiye
   Time      :  2021/06/30
   Function  :  线程池
*/
@Slf4j
@Configuration
@EnableAsync
public class ExecutorConfig {

	@Bean("asyncServiceExecutor")
	public Executor asyncServiceExecutor() {

		log.info("start asyncServiceExecutor");

      /**
	   *   ★★★★★ThreadPoolTaskExecutor 和 ThreadPoolExecutor★★★★★
	   * 1、ThreadPoolTaskExecutor extends (2)ExecutorConfigurationSupport
	   * 	   implements (3)AsyncListenableTaskExecutor, (4)SchedulingTaskExecutor
	   * 2、ExecutorConfigurationSupport extends CustomizableThreadFactory implements BeanNameAware, InitializingBean, DisposableBean
	   * 3、public interface AsyncListenableTaskExecutor extends AsyncTaskExecutor
	   * 4、public interface SchedulingTaskExecutor extends AsyncTaskExecutor
	   * 从上继承关系可知：
	   * ThreadPoolExecutor是一个java类不提供spring生命周期和参数装配。
	   * ThreadPoolTaskExecutor实现了InitializingBean, DisposableBean ，xxaware等，具有spring特性
	   * AsyncListenableTaskExecutor提供了监听任务方法(相当于添加一个任务监听，提交任务完成都会回调该方法)
	   * 简单理解：
	   * 1、ThreadPoolTaskExecutor使用ThreadPoolExecutor并增强，扩展了更多特性
	   * 2、ThreadPoolTaskExecutor只关注自己增强的部分，任务执行还是ThreadPoolExecutor处理。
	   * 3、前者spring自己用着爽，后者离开spring我们用ThreadPoolExecutor爽。
	   * 注意：ThreadPoolTaskExecutor 不会自动创建ThreadPoolExecutor需要手动调initialize才会创建
	   * 如果@Bean 就不需手动，会自动InitializingBean的afterPropertiesSet来调initialize
	   */
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		// 配置核心线程数
		executor.setCorePoolSize(5);
		// 配置最大线程数
		executor.setMaxPoolSize(5);
		// 配置队列大小
		executor.setQueueCapacity(9999);
		// 配置空闲时间
		executor.setKeepAliveSeconds(1000);

		// 配置线程池中的线程的名称前缀
		executor.setThreadNamePrefix("async-service");

		/**
		 * 用于被拒绝任务的处理程序
		 *AbortPolicy(默认策略)：它直接将抛出RejectedExecutionException异常
		 *CallerRunsPolicy：用调用者所在的线程来执行任务
		 *DiscardOldestPolicy：它放弃最旧的未处理请求，然后重试execute执行当前任务。
		 *DiscardPolicy：默认情况下它将丢弃被拒绝的任务。
		 */
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

		// 执行初始化
		executor.initialize();

		return executor;
	}
}
