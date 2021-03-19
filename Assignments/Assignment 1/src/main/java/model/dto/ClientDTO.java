package model.dto;

public class ClientDTO {
    private Long id;
    private String surname;
    private String firstname;
    private String IDCardNumber;
    private String cnp;
    private String address;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String surname, String firstname, String IDCardNumber, String cnp, String address) {
        this.id = id;
        this.surname = surname;
        this.firstname = firstname;
        this.IDCardNumber = IDCardNumber;
        this.cnp = cnp;
        this.address = address;
    }

    public ClientDTO(String surname, String firstname, String IDCardNumber, String cnp, String address) {
        this.surname = surname;
        this.firstname = firstname;
        this.IDCardNumber = IDCardNumber;
        this.cnp = cnp;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getIDCardNumber() {
        return IDCardNumber;
    }

    public void setIDCardNumber(String IDCardNumber) {
        this.IDCardNumber = IDCardNumber;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
