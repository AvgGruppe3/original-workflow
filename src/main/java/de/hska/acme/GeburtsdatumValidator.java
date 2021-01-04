package de.hska.acme;

import org.camunda.bpm.engine.impl.form.FormException;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.joda.time.LocalDateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;

public class GeburtsdatumValidator implements FormFieldValidator {

    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext validatorContext) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime date = LocalDateTime.parse(submittedValue.toString(),
                DateTimeFormat.forPattern("dd/MM/yyyy"));

        long difference = Math.abs(Years.yearsBetween(now, date).getYears());

        if (difference >= 18) {
            return true;
        } else {
            throw new FormException("Kunde muss mind. 18 Jahre alt sein!");
        }
    }
}
