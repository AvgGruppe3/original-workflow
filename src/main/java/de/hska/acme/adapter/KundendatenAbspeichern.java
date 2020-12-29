package de.hska.acme.adapter;

import de.hska.acme.adapter.entity.Kunde;
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
    public void execute(DelegateExecution execution) throws Exception {
        Kunde neuKunde = new Kunde();
        neuKunde.setPrename(String.valueOf(execution.getVariable("nachname")));
        neuKunde.setSurname(String.valueOf(execution.getVariable("vorname")));
        neuKunde.setBirthDate(String.valueOf(execution.getVariable("geburtsdatum")));
        neuKunde.setRiskScore((Long) execution.getVariable("risikobewertung"));

        RestTemplate restTemplate = new RestTemplate();
        URI location = restTemplate.postForLocation(url, neuKunde);
    }
}
