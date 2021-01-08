package de.hska.acme.adapter;

import de.hska.acme.entity.Email;
import de.hska.acme.service.EmailService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailVersenden implements JavaDelegate {

    private final Logger logger = LoggerFactory.getLogger(EmailVersenden.class);

    private final EmailService emailService;

    @Autowired
    public EmailVersenden(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("EmailVersenden entry");
        try {
            emailService.sendEmail(new Email(String.valueOf(execution.getVariable("text"))));
            logger.info("successfully sent an e-mail");
        } catch (Exception e) {
            logger.info("Failed to send an e-mail: {}", e.getMessage());
            throw new BpmnError("Failed to send an e-mail");
        }
    }
}
