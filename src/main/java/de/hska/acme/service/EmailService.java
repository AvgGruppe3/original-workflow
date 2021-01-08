package de.hska.acme.service;

import de.hska.acme.entity.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(Email email) {
        logger.info("Start sending an e-mail");
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(email.getFrom());
        msg.setTo(email.getTo());
        msg.setSubject(email.getSubject());
        msg.setText(email.getText());

        logger.info("send email to {}", email.getTo());
        javaMailSender.send(msg);
    }

}
