package tdtu.edu.sellticket;

public class Cinema {
    private String documentId; // Unique identifier for the cinema
    private String address;     // Address of the cinema
    private String contact;     // Contact information for the cinema
    private String name;        // Name of the cinema

    // Default constructor (required for Firestore)
    public Cinema() {
    }

    // Parameterized constructor
    public Cinema(String documentId, String address, String contact, String name) {
        this.documentId = documentId;
        this.address = address;
        this.contact = contact;
        this.name = name;
    }

    public Cinema(String name, String address, String contact) {
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    // Getters and Setters
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}