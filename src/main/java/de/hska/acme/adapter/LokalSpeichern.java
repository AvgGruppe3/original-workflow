package de.hska.acme.adapter;

import de.hska.acme.entity.Customer;
import de.hska.acme.service.FileService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LokalSpeichern implements JavaDelegate {

    private final Logger logger = LoggerFactory.getLogger(LokalSpeichern.class);

    private final FileService fileService;

    @Autowired
    public LokalSpeichern(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("LokalSpeichern entry");

        Customer newCustomer = new Customer().createFromProcessVariablesWithRiskScore(execution);
        fileService.writeToFile(newCustomer);

        logger.info("LokalSpeichern exit");
    }

}
