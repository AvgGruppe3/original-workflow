package de.hska.acme.service;

import de.hska.acme.entity.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class FileServiceTest {
    private static final String FILE_NAME =  "localCustomers.json";

    private Customer customer;

    @InjectMocks
    private FileService classUnderTest;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setBirthDate(LocalDate.now());
        customer.setSurname("Peter");
        customer.setPrename("Hans");
        customer.setId(464);
        customer.setRiskScore(4945);
        customer.setContracts(List.of("4654", "469"));

        ReflectionTestUtils.setField(classUnderTest, "fileName", FILE_NAME);
    }

    @AfterEach
    void tearDown() {
        File file = new File(FILE_NAME);
        file.delete();
    }

    @Test
    void writeToFile() throws IOException {
        classUnderTest.writeToFile(customer);
        File file = new File(FILE_NAME);
        Assertions.assertTrue(file.exists());
    }

    @Test
    void writeToFile_emptyFileExists() throws IOException {
        File file = new File(FILE_NAME);
        file.createNewFile();
        classUnderTest.writeToFile(customer);
        Assertions.assertTrue(file.exists());
    }

    @Test
    void writeToFile_FileExists() throws IOException {
        writeToFile();
        classUnderTest.writeToFile(customer);
        File file = new File(FILE_NAME);
        Assertions.assertTrue(file.exists());
    }
}