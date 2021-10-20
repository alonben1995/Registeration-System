package bgu.spl.net.impl.Messages;

import java.util.ArrayList;

public class ServerMessage extends Message {
    ArrayList<String> data;

    public ServerMessage(ArrayList<String> data) {
        super((short) 12);
        this.data = data;
    }

    public ArrayList<String> getData() {
        return data;
    }


}
