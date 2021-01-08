package de.hska.acme.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connectjar.org.apache.http.client.utils.URIBuilder;
import de.hska.acme.entity.Customer;
import de.hska.acme.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RestClient {

    private final Logger logger = LoggerFactory.getLogger(RestClient.class);

    private final RestTemplate restTemplate;
    @Value("${json.server.url}")
    private String url;

    public RestClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<String> get(Customer customer) throws Exception {
        logger.info("GET-Request");
        URIBuilder builder = new URIBuilder(url);
        queryParams(customer).forEach(builder::addParameter);

        ResponseEntity<String> entity = restTemplate.getForEntity(builder.build(), String.class);

        if (!HttpStatus.OK.equals(entity.getStatusCode())) {
            logger.info("GET-Request failed:" + entity.getBody());
            throw new BusinessException("GET-Request failed: " + entity.getStatusCode());
        }
        return entity;
    }

    public Customer getCustomer(Customer customer) throws Exception {
        ResponseEntity<String> entity = get(customer);
        if (entity.hasBody()) {
            ObjectMapper mapper = new ObjectMapper();
            List<Customer> customers = mapper.readValue(entity.getBody(), new TypeReference<List<Customer>>() {
            });
            if (customers.size() > 0) {
                logger.info("GET-Request: Customer found");
                return customers.get(0);
            }
        }
        logger.info("GET-Request: Body is empty");
        throw new BusinessException("GET-Request: body is empty");
    }

    public void post(Customer customer) throws BusinessException {
        logger.info("POST-Request");
        ResponseEntity<String> entity = restTemplate.postForEntity(url, customer, String.class);

        if (!HttpStatus.CREATED.equals(entity.getStatusCode())) {
            logger.info("POST-Request failed:" + entity.getBody());
            throw new BusinessException("POST-Request Failed: " + entity.getStatusCode());
        }
    }

    public void put(Customer customer) {
        logger.info("PUT-Request");
        restTemplate.put(url + "/" + customer.getId(), customer);
    }

    private Map<String, String> queryParams(Customer customer) {

        var prename = customer.getPrename();
        var surname = customer.getSurname();
        var birthDate= customer.getBirthDateAsString();

        return Map.of("prename", prename, "surname", surname, "birthDate", birthDate);
    }
}
