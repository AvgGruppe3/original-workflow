package de.hska.acme.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connectjar.org.apache.http.client.utils.URIBuilder;
import de.hska.acme.adapter.entity.Customer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class KundendatenAbrufen implements JavaDelegate {

    @Value("${json.server.url}")
    private String url;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        URIBuilder builder = new URIBuilder(url);
        queryParams(execution).forEach(builder::addParameter);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(builder.build(), String.class);
        boolean isNC = isNewCustomer(execution, response);
        execution.setVariable("nk", isNC);
    }

    private boolean isNewCustomer(DelegateExecution execution, ResponseEntity<String> response) {
        boolean newCustomer = true;
        if(response.hasBody()){
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Customer> kunden = mapper.readValue(response.getBody(), new TypeReference<List<Customer>>(){});
                if(kunden.size()>0){
                    long riskScore = kunden.get(0).getRiskScore();
                    execution.setVariable("risikobewertung", riskScore);
                    newCustomer = false;
                }
            } catch (JsonProcessingException e) {
                return true;
            }
        }
        return newCustomer;
    }

    private Map<String, String> queryParams(DelegateExecution execution){

        var prename = (String) execution.getVariable("vorname");
        var surname = (String) execution.getVariable("nachname");
        var birthDate = getBirthDate(execution);

        return Map.of("prename", prename, "surname", surname, "birthDate", birthDate );
    }

    private static String getBirthDate(DelegateExecution execution) {
        var birthDate = (Date) execution.getVariable("geburtsdatum");

        var pattern = "yyyy-MM-dd";
        var simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(birthDate);
    }
}
