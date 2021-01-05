package de.hska.acme.entity;

public class Email {

    private String from;
    private String text;
    private String to;
    private String subject;

    public Email(String text) {
        this.text = text;
        from = "versicherung@vs.de";
        to = "kunde@gmail.com";
        subject = "Benachrichtigung";
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


}
