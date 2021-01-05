package de.hska.acme.adapter;

import de.hska.acme.entity.Customer;
import de.hska.acme.exception.BusinessException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class KundendatenAbspeichern implements JavaDelegate {

    private final RestClient restClient;

    @Autowired
    public KundendatenAbspeichern(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Customer customer = new Customer().createFromProcessVariablesWithRiskScore(execution);
        try {
            Customer responseCustomer = restClient.getCustomer(customer);
            customer.setId(responseCustomer.getId());
            customer.setContracts(responseCustomer.getContracts());
            restClient.put(customer);
        } catch (BusinessException e) {
            restClient.post(customer);
        }
    }
}
