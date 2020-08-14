module Contacts {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.xml;
    opens com.example.contacts;
    opens com.example.contacts.datamodel;
}