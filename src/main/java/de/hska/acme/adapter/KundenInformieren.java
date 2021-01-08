package de.hska.acme.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KundenInformieren implements JavaDelegate {

    private final Logger logger = LoggerFactory.getLogger(KundenInformieren.class);

    @Override
    public void execute(DelegateExecution execution) {
        logger.info("KundenInformieren entry");

        execution.setVariable("text",
                "Ihr Antrag ist unvollständig, bitte füllen Sie den Antrag vollständig aus");

        logger.info("KundenInformieren exit");

    }
}
