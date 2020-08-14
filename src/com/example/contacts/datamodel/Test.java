package com.example.contacts.datamodel;

import javafx.collections.ObservableList;

public class Test {
    private static ObservableList<Contact> contacts;
    public static void main(String[] args) {
        //ContactData newContact=new ContactData();
        contacts=ContactData.getInstance().getContacts();
        System.out.println(contacts.size());
        //System.out.println(newContact.getContacts().size());

    }


}
