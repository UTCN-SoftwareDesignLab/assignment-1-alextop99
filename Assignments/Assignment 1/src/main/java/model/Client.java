package model;

public class Client {
    private Long id;
    private String surname;
    private String firstname;
    private String IDCardNumber;
    private String cnp;
    private String address;
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getFirstname() { return firstname;    }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getIDCardNumber() { return IDCardNumber; }

    public void setIDCardNumber(String IDCardNumber) { this.IDCardNumber = IDCardNumber; }

    public String getCnp() { return cnp; }

    public void setCnp(String cnp) { this.cnp = cnp; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public Account getAccount() { return account; }

    public void setAccount(Account account) { this.account = account; }
}
