package Utilities;
import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.PropertyConfigurator;

import com.sun.mail.iap.Protocol;

public class SendMail {
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
  
	public static void mailsender(String filename)  {
    
    	 final String username = "growbydata.qa@gmail.com";
         final String password = "growbydataqa2016";

         Properties props = new Properties();
         props.put("mail.smtp.starttls.enable", "true");
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.host", "smtp.gmail.com");
         props.put("mail.smtp.port", "587");
         
         
         

         Session session = Session.getInstance(props,
           new javax.mail.Authenticator() {
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(username, password);
             }
           });

         try {
        	 
        	// Read Mailing list
        	ExcelUtils.setExcelFile("./"+filename);
     		int totalList = ExcelUtils.getRowCount("Mail_List"); 
        	 //System.out.println(totalList);
        	 
             MimeMessage message = new MimeMessage(session);
             message.setFrom(new InternetAddress(username));
             
             
             //Declare array of mailing list
             String[] mailId = new String[totalList-1];;
             int counter =0;
             InternetAddress[] add = new InternetAddress[totalList-1];
             for(int startlist=1;startlist<totalList;startlist++)
             {
            	 mailId[counter] = ExcelUtils.getCellData(startlist,0,"Mail_List");   
            	// System.out.println(mailId[counter]);
            	 add[counter]=new InternetAddress(mailId[counter]);
            	 counter++;
             }
            //add array list to mail sender                     
             message.setRecipients(Message.RecipientType.TO,add);
             message.setSubject("Automation Report");
           
             BodyPart messageBodyPart = new MimeBodyPart();
             //Mail Body message
             messageBodyPart.setText("Attached file is the report to previously run Automation Test. "
            		 + "\n \n This is an automated mail. Do not reply to this mail."
            		 + "\n \n \nRegards, \nQA Team");

             // Create a multipart message
             Multipart multipart = new MimeMultipart();

             // Set text message part
             multipart.addBodyPart(messageBodyPart);
             // Set Attachment Initiator
             messageBodyPart = new MimeBodyPart();
             String attachment = "./"+filename;
             DataSource source = new FileDataSource(attachment);
             messageBodyPart.setDataHandler(new DataHandler(source));
             messageBodyPart.setFileName(attachment);
             multipart.addBodyPart(messageBodyPart);

             // Send the complete message parts
             message.setContent(multipart);

             // Send message
             Transport.send(message);

             //System.out.println("Mail Sent Successful");
             Log.info("Mail Sent Successful" );

         } catch (MessagingException e) {
        	 e.printStackTrace();
         } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
 }
    	
    