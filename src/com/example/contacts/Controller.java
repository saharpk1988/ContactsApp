package com.example.contacts;

import com.example.contacts.datamodel.Contact;
import com.example.contacts.datamodel.ContactData;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;
public class Controller {
    @FXML
    private MenuItem exit;
    @FXML
    private TableView<Contact> myTable;

    @FXML
    private BorderPane borderPane;

    private ContextMenu contextMenu;

    private ObservableList<Contact> contactList;
    public void initialize(){
        contextMenu=new ContextMenu();
        MenuItem delete=new MenuItem("delete");
        delete.setOnAction((ActionEvent actionEvent)->{
            Contact selectedContact=myTable.getSelectionModel().getSelectedItem();
            deleteSelectedContact(selectedContact);
        });
        contextMenu.getItems().addAll(delete);
        contactList= ContactData.getInstance().getContacts();
        myTable.setItems(contactList);
        myTable.setEditable(true);
        TableColumn<Contact,String> firstNameCol=new TableColumn<Contact,String>(" First Name ");
        TableColumn<Contact,String> lastNameCol=new TableColumn<Contact,String>(" Last Name ");
        TableColumn<Contact,String> phoneNumberCol=new TableColumn<Contact,String>(" Phone Number ");
        TableColumn<Contact,String> notesCol=new TableColumn<Contact,String>(" Notes ");
        myTable.getColumns().setAll(firstNameCol,lastNameCol,phoneNumberCol,notesCol);
        firstNameCol.setCellValueFactory(cellData->cellData.getValue().getFirstName());
        lastNameCol.setCellValueFactory(cellData->cellData.getValue().getLastName());
        phoneNumberCol.setCellValueFactory(cellData->cellData.getValue().getPhoneNumber());
        notesCol.setCellValueFactory(cellData ->cellData.getValue().getNotes());
        myTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        myTable.setRowFactory(new Callback<TableView<Contact>, TableRow<Contact>>() {
            @Override
            public TableRow<Contact> call(TableView<Contact> contactTableView) {
                    TableRow<Contact> row=new TableRow<Contact>();
                    row.emptyProperty().addListener((obs, wasEmpty, isEmpty)->{
                        if(isEmpty){
                            row.setContextMenu(null);
                        } else{
                            row.setContextMenu(contextMenu);
                        }
                    });

                return row;
            }
        });

        myTable.setEditable(true);
        editCell(firstNameCol);
        editCell(lastNameCol);
        editCell(phoneNumberCol);
        editCell(notesCol);

    }

    public void editCell(TableColumn<Contact,String> column){
        column.setCellFactory((tableColumn)-> new TextFieldTableCell<>());
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit((event)->{
            SimpleStringProperty property=(SimpleStringProperty) column.getCellObservableValue(event.getTablePosition().getRow());
            Contact currentItem=myTable.getSelectionModel().getSelectedItem();
            currentItem.setProperty(property,event.getNewValue());
        });
    }

    @FXML
    public void newContact(){
        Dialog<ButtonType> dialog=new Dialog<ButtonType>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("Add a new contact");
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newdialog.fxml"));
        try{
           dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Could not laod the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result= dialog.showAndWait();
        if(result.isPresent() && result.get().equals(ButtonType.OK)){
            DialogController controller=fxmlLoader.getController();
            Contact newContact=controller.processResult();
            System.out.println("Ok pressed");
        } else {
            System.out.println("Cancel pressed");
        }




    }

    public void deleteSelectedContact(Contact contact){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete contact");
        alert.setHeaderText("deleting "+ contact.getFirstName().getValue()+" "+contact.getLastName().getValue());
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent() && result.get().equals(ButtonType.OK)){
            ContactData.getInstance().deleteContact(contact);
        }

    }
    @FXML
    public void handleKey(KeyEvent key){
        Contact selectedContact=myTable.getSelectionModel().getSelectedItem();
        if(selectedContact!=null){
            if(key.getCode().equals(KeyCode.DELETE)|| key.getCode().equals(KeyCode.BACK_SPACE)){
                deleteSelectedContact(selectedContact);
            }
        }


    }

    public void handleExit(){
        Platform.exit();
    }
}
