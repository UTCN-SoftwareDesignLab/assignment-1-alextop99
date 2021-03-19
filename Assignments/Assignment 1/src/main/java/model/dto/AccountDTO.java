package model.dto;

public class AccountDTO {
    private Long id;
    private String number;
    private String type;
    private Double amountOfMoney;

    public AccountDTO() {
    }

    public AccountDTO(Long id, String number, String type, Double amountOfMoney) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.amountOfMoney = amountOfMoney;
    }

    public AccountDTO(Long id, String number, Double amountOfMoney) {
        this.id = id;
        this.number = number;
        this.amountOfMoney = amountOfMoney;
    }

    public AccountDTO(String number, String type, Double amountOfMoney) {
        this.number = number;
        this.type = type;
        this.amountOfMoney = amountOfMoney;
    }

    public AccountDTO(String number, Double amountOfMoney) {
        this.number = number;
        this.amountOfMoney = amountOfMoney;
    }

    public AccountDTO(Double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(Double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }
}
