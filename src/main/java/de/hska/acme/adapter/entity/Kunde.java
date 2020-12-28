package de.hska.acme.adapter.entity;

import java.time.LocalDate;
import java.util.List;

public class Kunde {

    private long id;
    private String prename;
    private String surname;
    private String birthDate;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
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
}
