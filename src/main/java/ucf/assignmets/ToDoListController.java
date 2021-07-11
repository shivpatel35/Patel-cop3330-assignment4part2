/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Shiv Patel
 */

package ucf.assignmets;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ToDoListController implements Initializable {

    //observable list declartion
    private ObservableList<Item> tasks=FXCollections.observableArrayList();

    //Right click menu items to either delete or update tasks
    MenuItem update= new MenuItem("Update");
    MenuItem delete= new MenuItem("Delete");
    private final ContextMenu contextMenu=new ContextMenu(update,delete);

    //declaration of buttons in program for adding new item, saving, and restoring the original save file
    @FXML
    public Button addNew;

    @FXML
    public Button save;

    @FXML
    public Button restore;

    //Tableview connection with Item Class and Fxml File
    @FXML private TableView<Item> table;
    @FXML private TableColumn<Item,String>Description;
    @FXML private TableColumn<Item,String>DueDate;
    @FXML private TableColumn<Item,String>Status;

    //function opens up another window and allows to add a new task
    @FXML private void AddItemClicked(ActionEvent event) {

        //try-catch statement to open fxml file
        try {
            //opens FXML scene to add a new item
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ucf/assignmets/AddItem.fxml"));
            Parent root = loader.load();
            AddItemController control=loader.getController();
            control.setTableItems(table.getItems());
            Stage stage = new Stage();
            stage.setTitle("Add Task");
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }


    public void initialize(URL location, ResourceBundle resources) {

        //Setups cells for tableview
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        DueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));


        table.setRowFactory(new Callback<>() {
            public TableRow<Item> call(TableView<Item> tableView) {
                final TableRow<Item> row = new TableRow<>();

                //Delete action-right click
                delete.setOnAction(event -> {

                    //removes item from table based on selected item
                    Item remove = table.getSelectionModel().getSelectedItem();
                    tasks.remove(remove);
                });

                //update action-right click
                update.setOnAction(new EventHandler<>() {

                    @Override
                    public void handle(ActionEvent event) {

                        //gets the selected item from view
                        Item update = table.getSelectionModel().getSelectedItem();

                        //try catch statement reopens additem.fxml to allow user to update item
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ucf/assignmets/AddItem.fxml"));
                            Parent root = loader.load();
                            AddItemController control = loader.getController();
                            control.infoToUpdate(update, tasks.indexOf(update));
                            control.setTableItems(table.getItems());
                            Stage stage = new Stage();
                            stage.setTitle("Update Task");
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    }
                });

                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
                return row;
            }
        });

        table.setItems(getItem());
    }


    private ObservableList<Item> getItem(){
        return tasks;
    }

    @FXML
    private void saveButtonClicked(ActionEvent event) {
        try {
            PrintWriter pw = new PrintWriter("src/main/resources/Todolist.txt", "UTF-8");
            String itemString = "";
            int listSize = table.getItems().size();
            ObservableList<Item> tempList = table.getItems();
            pw.println(listSize);   // include number of items at beginning of file
            if(listSize > 0) {
                for(int i = 0; i < listSize; i++) {
                    itemString = tempList.get(i).getSaveString();
                    pw.println(itemString);
                }
            }
            pw.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.err.println("PrintWriter exception occured");
            e.printStackTrace();
        }

    }

    @FXML
    private void restoreButtonClicked(ActionEvent event) {
        if(this.table.getItems().size() < 1) { // check to make sure list is empty
            try {
                InputStream in = getClass().getResourceAsStream("ucf/src/Todolist.txt");
                BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
                readFile(bfr);
                bfr.close();
            } catch (FileNotFoundException e) {
                System.err.println("FILE NOT FOUND EXCEPTION" + e.toString());
            } catch (IOException e) {
                System.err.println("IO exception in restore");
            }
        }
    }


    private void readFile(BufferedReader bfr) {
        int listSize;
        try {
            listSize = Integer.parseInt(bfr.readLine());
            for(int i = 0; i < listSize; i++) {
                String description = bfr.readLine();
                int priority = Integer.parseInt(bfr.readLine());
                String dueDate = bfr.readLine();
                String status = bfr.readLine();
                String statusDate = bfr.readLine();
                tasks.add(new Item(description, dueDate,status,
                        statusDate));
                this.table.setItems(tasks);
            }
        } catch (NumberFormatException e) {
            System.err.println("Number Format Exception occurred");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO Exception occurred");
            e.printStackTrace();
        }

    }


}