package bgu.spl.net.impl.Messages;

import java.util.ArrayList;

public abstract class Message {
    protected short opCode;
    protected String myName = "";

    public Message(short opCode) {
        this.opCode = opCode;
    }

    public short getOpCode() {
        return opCode;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public ArrayList<String> execute() {
        return  null;
    }


}
