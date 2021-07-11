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

public class AddItemController implements Initializable {
    @FXML
    private TextField inputDesc;

    @FXML
    private DatePicker inputDueDate;

    @FXML
    private Button cancel;
    @FXML
    private Button save;

    @FXML
    private ChoiceBox<String> inputStatus;

    @FXML
    private DatePicker inputDateFinished;

    @FXML
    private DatePicker inputDateStarted;

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

        inputDesc.setText(description);
        inputDueDate.setValue(LocalDate.parse(dueDate, formatDate));


        indexOfUpdated = index;
    }

    @FXML
    private void saveButtonClicked(ActionEvent event) throws Exception {// changes to add item scene
        if (event.getSource() == cancel) {
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        }

        if (event.getSource() == save) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            Error err = checkForErrors(inputDesc.getText(), inputDueDate.getValue(), inputStatus.getValue());
            if(err.isErr()) {
                errorAlert.setContentText(err.getMessage());
                errorAlert.showAndWait();
            }else {
                description = inputDesc.getText();
                dueDate = inputDueDate.getValue().format(formatDate);
                status = inputStatus.getValue();
                statusDate = "";

                boolean unique = isUnique(description, table);

                if (unique)// decides whether or not to display error message
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

    private Error checkForErrors(LocalDate date) {
        Error error = new Error();
        if(date == null) {
            error.setErr(true);
            error.setMessage("Please enter the date");
        }

        return error;
    }

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
                    error = true;
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
