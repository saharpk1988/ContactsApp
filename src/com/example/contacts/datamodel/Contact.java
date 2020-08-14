package com.example.contacts.datamodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contact {
    private StringProperty firstName=new SimpleStringProperty();
    private StringProperty lastNames=new SimpleStringProperty();
    private StringProperty phoneNumber=new SimpleStringProperty();
    private StringProperty notes=new SimpleStringProperty();

    public Contact() {
    }

    public Contact(String firstName, String lastNames, String phoneNumber, String notes) {
        this.firstName.setValue(firstName);
        this.lastNames.setValue(lastNames);
        this.phoneNumber.setValue(phoneNumber);
        this.notes.setValue(notes);
    }

    public void setFirstName(String firstName) {
        this.firstName.setValue(firstName);
    }

    public void setLastName(String lastName) {
        this.lastNames.setValue(lastName);
    }

    public void setProperty(StringProperty property, String value){
        property.setValue(value);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.setValue(phoneNumber);
    }

    public void setNotes(String notes) {
        this.notes.setValue(notes);
    }

    public StringProperty getFirstName() {
        return firstName;
    }

    public StringProperty getLastName() {
        return lastNames;
    }

    public StringProperty getPhoneNumber() {
        return phoneNumber;
    }


    public StringProperty getNotes() {
        return notes;
    }
}
