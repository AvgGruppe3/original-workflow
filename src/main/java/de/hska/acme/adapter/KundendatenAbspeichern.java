package de.hska.acme.adapter;

import de.hska.acme.adapter.entity.Customer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class KundendatenAbspeichern implements JavaDelegate {

    @Value("${json.server.url}")
    private String url;

    @Override
    public void execute(DelegateExecution execution) {
        Customer neuCustomer = new Customer().createFromProcessVariables(execution);

        RestTemplate restTemplate = new RestTemplate();
        URI location = restTemplate.postForLocation(url, neuCustomer);
    }
}
