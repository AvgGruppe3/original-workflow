package de.hska.acme.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.acme.adapter.entity.Kunde;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class KundendatenAbrufen implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String nachname = (String) execution.getVariable("nachname");
        String url = String.format("http://localhost:3000/customers?prename=%s", nachname);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        boolean isNk = true;
        if(response.hasBody()){
            ObjectMapper mapper = new ObjectMapper();
            List<Kunde> kunden = mapper.readValue(response.getBody(), new TypeReference<List<Kunde>>(){});
            if(kunden.size()>0){
                long riskScore = kunden.get(0).getRiskScore();
                execution.setVariable("risikobewertung", riskScore);
                isNk = false;
            }
        }
        execution.setVariable("nk", isNk);
    }
}
