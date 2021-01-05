package de.hska.acme.entity;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import java.util.Date;
import java.util.List;

public class Customer {

    private long id;
    private String prename;
    private String surname;
    private Date birthDate;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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

    public Customer createFromProcessVariablesWithRiskScore(DelegateExecution execution){
        setPrename(String.valueOf(execution.getVariable("nachname")));
        setSurname(String.valueOf(execution.getVariable("vorname")));
        setBirthDate((Date) execution.getVariable("geburtsdatum"));
        setRiskScore((Long) execution.getVariable("risikobewertung"));
        return this;
    }

    public Customer createFromProcessVariables(DelegateExecution execution){
        setPrename(String.valueOf(execution.getVariable("nachname")));
        setSurname(String.valueOf(execution.getVariable("vorname")));
        setBirthDate((Date) execution.getVariable("geburtsdatum"));
        return this;
    }
}
