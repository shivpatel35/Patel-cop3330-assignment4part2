/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Shiv Patel
 */


package ucf.assignmets;

//Error handling for user input
public class Error {
    private String message;
    private boolean err;

    //setter-getter method to send error messages to user for certain criteria not entered correctly
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isErr() {
        return err;
    }
    public void setErr(boolean err) {
        this.err = err;
    }

}