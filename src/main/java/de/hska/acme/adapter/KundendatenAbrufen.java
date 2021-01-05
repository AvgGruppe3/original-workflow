package de.hska.acme.adapter;

import de.hska.acme.entity.Customer;
import de.hska.acme.exception.BusinessException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class KundendatenAbrufen implements JavaDelegate {

    private final RestClient restClient;

    @Autowired
    public KundendatenAbrufen(RestClient restClient) {
        this.restClient = restClient;
    }


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Customer customer = new Customer().createFromProcessVariables(execution);
        boolean newCustomer;
        try {
            Customer responseCustomer = restClient.getCustomer(customer);
            execution.setVariable("risikobewertung", responseCustomer.getRiskScore());
            newCustomer = false;
        } catch (BusinessException e) {
            newCustomer = true;
        }
        execution.setVariable("nk", newCustomer);
    }
}
