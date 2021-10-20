package bgu.spl.net.api;

import bgu.spl.net.impl.Messages.*;

import java.util.ArrayList;

public class MessageProtocol implements MessagingProtocol<Message> {

    private String myname;
    private boolean isTerminated = false;

    @Override
    public Message process (Message msg) {
        if (msg.getOpCode() == 1 | msg.getOpCode() == 2 | msg.getOpCode() == 3) {
            if (msg instanceof ADMINREG) {
                ADMINREG message = (ADMINREG) msg;
                if (myname == null) {
                    this.myname = message.getUsername();
                }
            }
            if (msg instanceof STUDENTREG) {
                STUDENTREG message = (STUDENTREG) msg;
                if (myname == null) {
                    this.myname = message.getUsername();
                }

            }
            if (msg instanceof LOGIN) {
                LOGIN message = (LOGIN) msg;
                if (myname == null) {
                    this.myname = message.getUsername();
                }

            }
        }
            msg.setMyName(myname);
            ArrayList<String> str = msg.execute();
            return new ServerMessage(str);
        }

    @Override
    public boolean shouldTerminate() { // if he logges out
        return isTerminated;
    }
}
