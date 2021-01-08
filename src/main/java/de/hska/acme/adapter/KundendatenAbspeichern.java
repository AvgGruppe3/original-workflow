package de.hska.acme.adapter;

import de.hska.acme.entity.Customer;
import de.hska.acme.exception.BusinessException;
import de.hska.acme.service.RestClient;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class KundendatenAbspeichern implements JavaDelegate {

    private final Logger logger = LoggerFactory.getLogger(KundendatenAbspeichern.class);

    private final RestClient restClient;

    @Autowired
    public KundendatenAbspeichern(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("KundendatenAbspeichern entry");

        Customer customer = new Customer().createFromProcessVariablesWithRiskScore(execution);
        try {
            Customer responseCustomer = restClient.getCustomer(customer);
            customer.setId(responseCustomer.getId());
            customer.setContracts(responseCustomer.getContracts());
            restClient.put(customer);
        } catch (BusinessException e) {
            restClient.post(customer);
        } catch(Exception e){
            throw new BpmnError("Error");
        }
        logger.info("KundendatenAbspeichern exit");
    }
}
