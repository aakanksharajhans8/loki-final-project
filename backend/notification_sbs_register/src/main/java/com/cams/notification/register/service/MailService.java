package com.cams.notification.register.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class MailService {
  private final JavaMailSender mailSender;
  @Value("${spring.mail.username}") private String username;
  @Value("${app.mail.from:}") private String fromProp;
  public MailService(JavaMailSender mailSender){ this.mailSender=mailSender; }
  private static String clean(String s){ if(s==null)return null; String x=s.trim().replaceAll("[\r\n]",""); x=x.replaceAll("^['\"]|['\"]$",""); return x; }
  private String resolveFrom(){ String f=clean(fromProp); return (f==null||f.isBlank())? clean(username): f; }
  public void sendPlainText(String toRaw, String subject, String body){
    String from=resolveFrom(); String to=clean(toRaw);
    if(from==null || !from.matches("^\\S+@\\S+\\.\\S+$")) throw new IllegalArgumentException("Invalid 'from' email");
    if(to==null || !to.matches("^\\S+@\\S+\\.\\S+$")) throw new IllegalArgumentException("Invalid 'to' email");
    SimpleMailMessage msg=new SimpleMailMessage(); msg.setFrom(from); msg.setTo(to); msg.setSubject(subject); msg.setText(body);
    mailSender.send(msg);
  }
}
