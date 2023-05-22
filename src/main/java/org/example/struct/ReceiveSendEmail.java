package org.example.struct;

import org.example.struct.JSON.Flag;

public class ReceiveSendEmail {
    String Flag;
    String Email;

    public ReceiveSendEmail () {
        this.Flag = org.example.struct.JSON.Flag.FlagSendEmail;
    }

    public String getEmail() {
        return this.Email;
    }
}
