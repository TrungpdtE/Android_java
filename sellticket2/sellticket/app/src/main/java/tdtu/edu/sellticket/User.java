package tdtu.edu.sellticket;

public class User {
    private String documentId; // Firestore document ID
    private String accountCreationDate; // Date the account was created
    private String email; // User's email
    private String fullName; // User's full name
    private String password; // User's password
    private String phoneNumber; // User's phone number
    private String role; // User's role

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String documentId, String accountCreationDate, String email, String fullName, String password, String phoneNumber, String role) {
        this.documentId = documentId;
        this.accountCreationDate = accountCreationDate;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public User(String fullName, String email, String phoneNumber, String role) {

        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    // Getters and setters for each attribute

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(String accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}