package com.demo.serviceImpl.common;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.demo.service.common.IMailService;

import lombok.extern.slf4j.Slf4j;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 
*/
@Service
@Slf4j
public class MailServiceImpl implements IMailService {

	// 421 HL : ICC IP同时并发连接数过大
	// 451 Requested mail action not taken 登录次数过多，临时禁止访问
	// 553 authentication is required 认证失败
	@Value("${spring.mail.username}")
	private String from;

	@Autowired
	private JavaMailSender mailSender;

	@Resource
	TemplateEngine templateEngine;

	/**
	 * 发送简单的text邮件
	 */
	@Async("asyncServiceExecutor")
	public void sendSimpleMail(String to, String subject, String content) {


		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		message.setFrom(from);
		try {

			log.info("当前运行的线程名称：" + Thread.currentThread().getName());

			// 邮件发送
			mailSender.send(message);

			log.info("邮件发送成功: {},{}", to, subject);

		} catch (Exception e) {


			log.info("邮件发送失败: {},{}", to, subject);

		} finally {

			log.info("邮件发送日志ID: {}", "邮件发送成功");
		}
	}

	public void sendHtmlMail(String to, String subject, String content) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {

			// logger.info("邮件发送开始:{},{},{}",to,subject,content);

			helper = new MimeMessageHelper(mimeMessage, true);

			// test content
			String contenTest = "<html>\n" + "<body>\n" + "<h3> Test HTML Mail !!! </h3>" + "</body>\n" + "</html";

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(contenTest, true);
			helper.setFrom(from);

			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			log.error("邮件发送失败,{},{},{},{}", to, subject, content, e);
			// e.printStackTrace();
		}

	}

	public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage, true);

			// test content
			String contenTest = "<html>\n" + "<body>\n" + "<h3> Test HTML Mail !!! </h3>" + "</body>\n" + "</html";

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(contenTest, true);
			helper.setFrom(from);

			FileSystemResource file = new FileSystemResource(new File(filePath));

			String fileName = file.getFilename();

			helper.addAttachment(fileName, file);
			// L发送多个邮件
			// helper.addAttachment(fileName+ "_test", file);

			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			log.error("邮件发送失败", e);
			// e.printStackTrace();
		}
	}

	public void sendInlinResourceMail(String to, String subject, String content, String rscPath, String rscId) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage, true);

			// test content
			String contenTest = "<html>\n" + "<body>\n" + "<img src=\'cid" + rscId + "\'>" + "</body>\n" + "</html";

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(contenTest, true);
			helper.setFrom(from);

			FileSystemResource file = new FileSystemResource(new File(rscPath));

			helper.addInline(rscId, file);

			// L发送多个邮件
			// helper.addAttachment(fileName+ "_test", file);

			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			log.error("邮件发送失败", e);
			// e.printStackTrace();
		}
	}

	public void templatesMail(String to, String subject, String content, String id) {

		Context context = new Context();

		context.setVariable("id", id);

		String emailContext = templateEngine.process("mailTemplates", context);

		this.sendHtmlMail(to, subject, emailContext);
	}
}
