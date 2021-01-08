package de.hska.acme.adapter;

import de.hska.acme.service.RestClient;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntragAblehnen implements JavaDelegate {

    private final Logger logger = LoggerFactory.getLogger(AntragAblehnen.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        logger.info("AntragAblehnen entry");
        delegateExecution.setVariable("text", "Ihr Antrag wurde abgelehnt");
        logger.info("AntragAblehnen exit");
    }
}
