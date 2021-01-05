package de.hska.acme.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Customer {

    private long id;
    private String prename;
    private String surname;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    private long riskScore;
    private List<String> contracts;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getBirthDateAsString(){
        var pattern = "yyyy/MM/dd";
        return birthDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public long getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(long riskScore) {
        this.riskScore = riskScore;
    }

    public List<String> getContracts() {
        return contracts;
    }

    public void setContracts(List<String> contracts) {
        this.contracts = contracts;
    }

    public Customer createFromProcessVariablesWithRiskScore(DelegateExecution execution) throws ParseException {
        createFromProcessVariables(execution);
        setRiskScore((Long) execution.getVariable("risikobewertung"));
        return this;
    }

    public Customer createFromProcessVariables(DelegateExecution execution) throws ParseException {
        setPrename(String.valueOf(execution.getVariable("vorname")));
        setSurname(String.valueOf(execution.getVariable("nachname")));
        setBirthDate(parseBirthDate(String.valueOf(execution.getVariable("geburtsdatum"))));
        return this;
    }

    private LocalDate parseBirthDate(String birthDate) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        cal.setTime(sdf.parse(birthDate));
        return cal.getTime().toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();

    }

}
