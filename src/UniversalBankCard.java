// Універсальна банківська карта
public class UniversalBankCard <T> {
    private T id; // Ідентифікатор клієнта
    private String fullNameOfTheClient; // ПІБ клієнта
    private int creditAccount; // Кредитний рахунок
    private float personalAccount; // Особистий рахунок

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public String getFullNameOfTheClient() {
        return fullNameOfTheClient;
    }

    public void setFullNameOfTheClient(String fullNameOfTheClient) {
        this.fullNameOfTheClient = fullNameOfTheClient;
    }

    public int getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(int creditAccount) {
        this.creditAccount = creditAccount;
    }

    public float getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(float personalAccount) {
        this.personalAccount = personalAccount;
    }
}
