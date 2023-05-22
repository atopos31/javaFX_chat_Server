package org.example.struct;

public class ReceiveMessage {
    String Flag;
    String Email;

    String Message;

    public ReceiveMessage() {
        this.Flag = org.example.struct.JSON.Flag.FlagReceiveMessage;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void  setMessage(String message) {
        this.Message = message;
    }
}
