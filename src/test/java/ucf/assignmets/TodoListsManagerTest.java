package ucf.assignmets;

import org.junit.jupiter.api.Test;
import ucf.assignmets.Item;
import ucf.assignmets.AddItemController;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TodoListsManagerTest {

    @Test
    void infotoUpdateTest(Item item, int index) {



        //call method addList()
        //Assert if file exists
    }

    @Test
    void removeList() {
        //create file pointer and use locaton of user's list
        //Assert that file deleted is null
    }

    @Test
    void editTitleofaList() {
        //create one string for unedited title and edited title
        //if statement(string u_title equals string n_title)
            //assert false
        //else statement(string u_title does not equal string n_title)
            //assert true
    }

    @Test
    void editDescriptionofItem() {
        //create one string for unedited description and edited description
        //if statement(string u_description equals string n_description)
        //assert false
        //else statement(string u_description does not equal string n_description)
        //assert true

    }

    @Test
    void markAnItem() {
        //declare one string list array for unedited list and one for edited list
        //use string length to determine marks on unedited and marks on edited
        //if statement(u_mark equals n_mark)
        //assert false
        //else statement( u_mark does not equal  n_mark)
        //assert true
    }

    @Test
    void editDueDateofItem() {
        //declare one string list array for unedited list and one for edited list
        //use string length to determine marks on unedited and marks on edited
        //if statement(u_ddate equals n_ddate)
        //assert false
        //else statement( u_ddate does not equal  n_ddate)
        //assert true
    }

    @Test
    void displayAllofCurrentList() {
        //three strings array lists declared for title, due date, and description
        //call method displayAllofCurrentList
        //assert that values of lists are not null

    }

    @Test
    void displayCompletedTasks() {
        //declare string array list for tasks
        //call method displayCompletedTasks
        //get the string values of completed tasks
        //assert that values are not null

    }

    @Test
    void displayIncompletedTasks() {
        //declare string array list for incompleted tasks
        //call method displayIncompletedTasks
        //get the string values of incompleted tasks
        //assert that values are not null

    }

    @Test
    void saveItemsofListtoStorage() {
        //declare string array list for items
        //call method saveItemsofListtoStorage()
        //get the string values
        //assert that the values are not null

    }

    @Test
    void saveAllListstoStorage() {
        //declare string array lists for items, descriptions, due date, title , task
        //call method saveAllListstoStorage()
        //get the string values
        //assert that the values are not null

    }

    @Test
    void loadLists() {
        //declare string array lists for all required elements in to do list
        //call method LoadLists()
        //assert List is not null

    }

    @Test
    void loadMultipleLists() {
        //declare string array lists for all required elements in to do list
        //call method LoadMultipleLists()
        //assert Lists is not null
    }

    @Test
    void removeItem() {
        //declare one string list array for unedited list and one for edited list
        //use string length to determine marks on unedited and marks on edited
        //if statement(u_item equals n_item)
        //assert false
        //else statement( u_item does not equal  n_item)
        //assert true
    }
}