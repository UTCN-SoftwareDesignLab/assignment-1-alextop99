package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientValidator {
    private static final String NAME_PATTERN = "[A-Z][a-zA-Z]+";
    private static final String CNP_PATTERN = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
    private static final String ID_PATTERN = "^[A-Z]{2}[0-9]{6}";

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private final Client client;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateName(client.getSurname());
        validateName(client.getFirstname());
        validateCNP(client.getCnp());
        validateID(client.getIDCardNumber());
        return errors.isEmpty();
    }

    private void validateName (String name) {
        Pattern p = Pattern.compile(NAME_PATTERN);
        Matcher m = p.matcher(name);
        if(!m.find()) {
            errors.add("Name is not right!");
        }
    }

    private void validateCNP(String cnp) {
        Pattern p = Pattern.compile(CNP_PATTERN);
        Matcher m = p.matcher(cnp);
        if(!m.find()) {
            errors.add("CNP is not right!");
        }
    }

    private void validateID(String idCardNumber) {
        Pattern p = Pattern.compile(ID_PATTERN);
        Matcher m = p.matcher(idCardNumber);
        if(!m.find()) {
            errors.add("ID Card Number is not right!");
        }
    }
}
