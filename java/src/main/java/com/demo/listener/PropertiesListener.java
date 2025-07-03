package com.demo.listener;

import com.demo.config.PropertiesListenerConfig;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;


/*
 * Author  : LIU
   Time    : 2021/05/26
   Function: Propertie文件监听器
*/
public class PropertiesListener implements ApplicationListener<ApplicationStartedEvent> {
	private String propertyFileName;

	public PropertiesListener(String propertyFileName) {
		this.propertyFileName = propertyFileName;
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {

		PropertiesListenerConfig.loadAllProperties(propertyFileName);
	}
}