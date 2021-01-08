package de.hska.acme.adapter;

import de.hska.acme.entity.Customer;
import de.hska.acme.exception.BusinessException;
import de.hska.acme.service.EmailService;
import de.hska.acme.service.RestClient;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class KundendatenAbrufen implements JavaDelegate {

    private final Logger logger = LoggerFactory.getLogger(KundendatenAbrufen.class);

    private final RestClient restClient;

    @Autowired
    public KundendatenAbrufen(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("KundendatenAbrufen entry");
        Customer customer = new Customer().createFromProcessVariables(execution);
        boolean newCustomer;
        try {
            Customer responseCustomer = restClient.getCustomer(customer);
            execution.setVariable("risikobewertung", responseCustomer.getRiskScore());
            newCustomer = false;
        } catch (BusinessException e) {
            newCustomer = true;
        } catch (Exception e) {
            logger.info("Failed to connect to rest-server: {}", e.getMessage());
            throw new BpmnError("Error");
        }
        execution.setVariable("nk", newCustomer);
        logger.info("KundendatenAbrufen exit");
    }
}
