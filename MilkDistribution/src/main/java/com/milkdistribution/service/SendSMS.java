package com.milkdistribution.service;
import java.io.*;
import java.net.InetAddress;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Properties;
import java.util.Date;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
 
public class SendSMS {
    public SendSMS() {}
             //create an account on ipipi.com with the given username and password
 
    public void msgsend() {
    	String username = "srikanthreddy.kethiri@gmail.com";
        String password = "crocoile1";
        String smtphost = "smtp.gmail.com";
        String compression = "My SMS Compression Information";
        String from = "srikanthreddy.kethiri@gmail.com";
        String to = "sambasiva029@gmail.com";
        String body = "Hello SMS World!";
        Transport myTransport = null;

		  try {
		  Properties props = System.getProperties();
		  props.put("mail.smtp.host", "smtp.gmail.com");
		      props.put("mail.smtp.socketFactory.port", "465");
		      props.put("mail.smtp.socketFactory.class",
		              "javax.net.ssl.SSLSocketFactory");
		      props.put("mail.smtp.auth", "true");
		      props.put("mail.smtp.port", "465");
		
		  Session mailSession = Session.getDefaultInstance(props, null);
		  Message msg = new MimeMessage(mailSession);
		  msg.setFrom(new InternetAddress(from));
		  InternetAddress[] address = {new InternetAddress(to)};
		  msg.setRecipients(Message.RecipientType.TO, address);
		  msg.setSubject(compression);
		  msg.setText(body);
		  msg.setSentDate(new Date());
		
		   myTransport = mailSession.getTransport("smtp");
		    myTransport.connect(smtphost, username, password);
		    msg.saveChanges();
		    myTransport.sendMessage(msg, msg.getAllRecipients());
		    myTransport.close();
	   } catch (Exception e) {
	      e.printStackTrace();
	    }
    } 
    public static void main(String[] argv) { 
    	/*Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) -1);
    	calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+15);
    	System.out.println(calendar.getTime());*/
    	String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length; i++) {
          String month = months[i];
          System.out.println("month = " + month);
        }
           //SendSMS sms = new SendSMS(); 
           //sms.msgsend(); 
           //System.out.println("Successfull"); 
   } 
}

