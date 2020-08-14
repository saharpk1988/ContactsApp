package com.example.contacts;

import com.example.contacts.datamodel.Contact;
import com.example.contacts.datamodel.ContactData;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DialogController {
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNr;
    @FXML
    private TextArea notes;

    public Contact processResult(){
        String firstN=firstName.getText();
        String lastN=lastName.getText();
        String phoneNum=phoneNr.getText();
        String note=notes.getText();
        Contact contact=new Contact(firstN,lastN,phoneNum,note);
        ContactData.getInstance().addContact(contact);
        return contact;
    }
}
