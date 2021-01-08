package de.hska.acme.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.acme.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(RestClient.class);

    @Value("${file.name}")
    private String fileName;

    public void writeToFile(Customer newCustomer) throws IOException {
        logger.info("Start writing customer to a file");
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(fileName);
        List<Customer> customers = new ArrayList<>();

        if (file.exists()) {
            try {
                customers.addAll(mapper.readValue(Paths.get(fileName).toFile(), new TypeReference<List<Customer>>() {
                }));
            }catch(IOException e){
                logger.info("The file is empty or contains an invalid JSON");
            }finally {
                file.delete();
            }
        }

        file.createNewFile();
        customers.add(newCustomer);

        String string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customers);
        PrintWriter out = new PrintWriter(new FileWriter(file, true));
        out.append(string);
        out.close();

        logger.info("Writing is completed");
    }
}
