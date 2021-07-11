package ucf.assignmets;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import ucf.assignmets.Item;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Controller for Add Item Window that updates tasks and adds new items into List
public class AddItemController implements Initializable {

    //text field for user input declaration
    @FXML
    private TextField inputDesc;

    //due-date inputter using date picker
    @FXML
    private DatePicker inputDueDate;

    //cancel button in sceneview window to cancel item being added
    @FXML
    private Button cancel;

    //save button in add task or update task in sceneview window to save item being added
    @FXML
    private Button save;

    //Status to show if tasks are finished, in progress, or started
    @FXML
    private ChoiceBox<String> inputStatus;

    //finished date input
    @FXML
    private DatePicker inputDateFinished;

    //date started input
    @FXML
    private DatePicker inputDateStarted;

    //Labels for drop down menu
    @FXML
    private Label startedLabel;
    @FXML
    private Label finishedLabel;

    //table declaration
    private ObservableList<Item> table;

    //creates newTask based on format of the Item class
    private Item newTask = new Item();

    //Date time formatter for DatePicker based on gregorian calendar
    private DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //string declarations for each type of criteria in addItem sceneview
    private String description = "";
    private String dueDate = "";
    private String status = "";
    private String statusDate = "";
    private int indexOfUpdated = 0;

    //gets table info from TodoListController
    public void setTableItems(ObservableList<Item> table)
    {
        this.table = table;

    }

    //Gets info from selected item to update
    public void infoToUpdate(Item item, int index)
    {
        //get description using setter to declared string
        description = item.getDescription();
        dueDate = item.getDueDate();

        //sets text to description entered and formats date based on gregorian calendar format
        inputDesc.setText(description);
        inputDueDate.setValue(LocalDate.parse(dueDate, formatDate));

        //updates index
        indexOfUpdated = index;
    }

    //save button action/ cancel button
    @FXML
    private void saveButtonClicked(ActionEvent event) throws Exception
    {

        //cancel button handler
        if (event.getSource() == cancel) {
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        }

        //if save is clicked
        if (event.getSource() == save) {

            //error handler if else statement
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            Error err = checkForErrors(inputDesc.getText(), inputDueDate.getValue(), inputStatus.getValue());
            if(err.isErr()) {
                errorAlert.setContentText(err.getMessage());
                errorAlert.showAndWait();

                //transfers each crtieria to strings and formats based on table
            }else {
                description = inputDesc.getText();
                dueDate = inputDueDate.getValue().format(formatDate);
                status = inputStatus.getValue();
                statusDate = "";

                //sends to another if else statement
                boolean unique = isUnique(description, table);


//makes sure descriptions are not the same in if statement
                if (unique)
                {
                    Stage stage = (Stage) save.getScene().getWindow();
                    if (stage.getTitle().contentEquals("Update Task")) {
                        Save();
                    } else {
                        inputDesc.setText("Description must be Unique");
                    }
                } else {
                    Save();
                }
            }

        }
    }


    //error handling
    private Error checkForErrors(String desc, LocalDate dueDate, String status) {
        Error error = new Error();
        if(desc == null || desc.isEmpty()) {
            error.setErr(true);
            error.setMessage("Please enter the description");

        }else if(dueDate == null) {
            error.setErr(true);
            error.setMessage("Due Date not entered");

        }else if(status == null || status.isEmpty()) {
            error.setErr(true);
            error.setMessage("Please enter the status");
        }

        return error;
    }

    //error handling
    private Error checkForErrors(LocalDate date) {
        Error error = new Error();
        if(date == null) {
            error.setErr(true);
            error.setMessage("Please enter the date");
        }

        return error;
    }

    //save function
    private void Save() {

        Stage stage = (Stage) save.getScene().getWindow();
        boolean onlyPriority = false;
        boolean error = false;
        Error err = null;


        if (!status.equals("Not started")) {
            if (status.contentEquals("Started")) {
                err = checkForErrors(inputDateStarted.getValue());
                if(err.isErr()) {
                    error = true;
                }else {
                    statusDate = inputDateStarted.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }

            } else {
                err = checkForErrors(inputDateStarted.getValue());
                if(err.isErr()) {
                    error = false;
                }else {
                    statusDate = inputDateFinished.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }

            }
            status = status + "(" + statusDate + ")";
        }
        if(!error) {
            newTask = new Item(description, dueDate, status, statusDate);
            if (stage.getTitle().contentEquals("Update Task")) {
                table.set(indexOfUpdated, newTask);
            } else {
                table.add(newTask);
            }
            stage.close();
        }else {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            String msg = err == null ? "Invalid entry" : err.getMessage();
            errorAlert.setContentText(msg);
            errorAlert.showAndWait();
        }

    }

    //function to make sure descriptions are unique
    private boolean isUnique(String description, ObservableList<Item> list)// checks if description is unique
    {

        boolean notUnique = false;
        for (int index = 0; (index < list.size()) && (notUnique != true); index++) {
            notUnique = description.equalsIgnoreCase(list.get(index).getDescription());
        }

        return notUnique;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputStatus.getItems().addAll("Not started", "Started", "Finished");
        inputStatus.getSelectionModel().selectedItemProperty().addListener(new// listener for status menu
                                                                                   ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String value, String newValue) {// changes
                // visibility of
                // items
                if (value.equals(newValue)) {

                } else if ((newValue.equals("Started") && value.equals("Finished"))) {
                    finishedLabel.setVisible(false);
                    inputDateFinished.setVisible(false);
                    startedLabel.setVisible(true);
                    inputDateStarted.setVisible(true);
                }

                else if ((newValue.equals("Finished") && value.equals("Started"))) {
                    startedLabel.setVisible(false);
                    inputDateStarted.setVisible(false);
                    finishedLabel.setVisible(true);
                    inputDateFinished.setVisible(true);
                } else {
                    if (newValue.equals("Finished")) {
                        finishedLabel.setVisible(true);
                        inputDateFinished.setVisible(true);
                    }
                    if (newValue.equals("Started")) {
                        startedLabel.setVisible(true);
                        inputDateStarted.setVisible(true);
                    } else {
                        startedLabel.setVisible(false);
                        inputDateStarted.setVisible(false);
                        finishedLabel.setVisible(false);
                        inputDateFinished.setVisible(false);
                    }
                }
            }
        });

    }

}
