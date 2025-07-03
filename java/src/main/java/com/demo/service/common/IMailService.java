package com.demo.service.common;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 
*/
public interface IMailService {

	public void sendSimpleMail(String to, String subject, String content);

	public void sendHtmlMail(String to, String subject, String content);

	public void sendAttachmentsMail(String to, String subject, String content, String filePath);

	public void sendInlinResourceMail(String to, String subject, String content, String rscPath, String rscId);
}
