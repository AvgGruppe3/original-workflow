package de.hska.acme.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.acme.entity.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Value("${file.name}")
    private String fileName;

    public void writeToFile(Customer newCustomer) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File log = new File(fileName);
        List<Customer> customers = new ArrayList<>();
        if (log.exists()) {
            customers.addAll(
                    mapper.readValue(Paths.get(fileName).toFile(), new TypeReference<List<Customer>>() {
                    }));
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
