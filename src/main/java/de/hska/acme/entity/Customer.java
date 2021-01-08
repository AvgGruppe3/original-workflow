package de.hska.acme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonIgnore
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private long id;
    private String prename;
    private String surname;
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

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @JsonProperty("birthDate")
    public String getBirthDateAsString() {
        return birthDate.format(DATE_TIME_FORMATTER);
    }

    @JsonProperty("birthDate")
    public void setBirthDateFromString(String birthDate) {
        this.birthDate = LocalDate.parse(birthDate, DATE_TIME_FORMATTER);
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
        long risikobewertung = Long.parseLong(String.valueOf(execution.getVariable("risikobewertung")));
        setRiskScore(risikobewertung);
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", prename='" + prename + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", riskScore=" + riskScore +
                ", contracts=" + contracts +
                '}';
    }
}
