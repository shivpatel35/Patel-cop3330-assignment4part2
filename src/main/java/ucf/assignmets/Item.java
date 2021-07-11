/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Shiv Patel
 */


package ucf.assignmets;

//Item Class for Tableview
public class Item {

    //String declaration for description,dueDate, status, and statusDate
        private String description;
        private String dueDate;
        private String status;
        private String statusDate;

        //constructor
        public Item() {
            description = "";
            dueDate ="";
            status = "";
            statusDate = "";
        }


        //setter and getter type method for class to recieve and return strings
        public Item(String description,String dueDate, String status, String statusDate) {
            this.description=description;
            this.dueDate=dueDate;
            this.status=status;
            this.statusDate=statusDate;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setStatusDate(String statusDate) {
            this.statusDate = statusDate;
        }

        public String getDescription() {
            return this.description;
        }

        public String getDueDate() {
            return this.dueDate;
        }

        public String getStatus() {
            return this.status;
        }

        public String getStatusDate() {
            return this.statusDate;
        }


        //creates a string for the save file
        public String getSaveString() {
            String str;
            str = this.description + "\n"
                    + this.dueDate + "\n"
                    + this.status + "\n"
                    + this.statusDate;
            return str;
        }

      //creates a formatted return string of item components
        public String getPrintString() {
            String desc = this.description;
            if(desc.length() > 20) {
                desc = desc.substring(0, 19) + "...";
            }
            String str = String.format("|%-2d|%-23s|%-10s|%-20s|",desc, this.dueDate,
                    this.status);
            return str;
        }


    }

