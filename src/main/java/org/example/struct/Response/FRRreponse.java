package org.example.struct.Response;

import org.example.struct.JSON.Flag;

import java.util.HashMap;
import java.util.Map;

public class FRRreponse {
    String Flag;
    Map<String,String> onlineUser;

    public FRRreponse(Map<String,String> onlineUser) {
        this.Flag = org.example.struct.JSON.Flag.FlagFriendRequire;
        this.onlineUser = new HashMap<>(onlineUser);
    }
}
