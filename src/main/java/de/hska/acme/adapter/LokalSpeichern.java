package de.hska.acme.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.acme.adapter.entity.Customer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LokalSpeichern implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Customer newCustomer = new Customer().createFromProcessVariables(execution);

        writeToFile(newCustomer);
    }

    private void writeToFile(Customer newCustomer) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String fileName = "localCustomers.json";
        File log = new File(fileName);
        List<Customer> customers = new ArrayList<>();
        if(log.exists()){
            customers.addAll(mapper.readValue(Paths.get(fileName).toFile(), new TypeReference<List<Customer>>(){}));
            log.delete();
        }

        log.createNewFile();
        customers.add(newCustomer);

        String string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customers);
        PrintWriter out = new PrintWriter(new FileWriter(log, true));
        out.append(string);
        out.close();
    }

}
