package de.hska.acme.adapter;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class KundeInformieren implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        execution.setVariable("text",
                "Ihr Antrag ist unvollständig, bitte füllen Sie den Antrag vollständig aus");
    }
}
