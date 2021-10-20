package bgu.spl.net.impl.BGRSServer;
import bgu.spl.net.api.MessageEncDec;
import bgu.spl.net.api.MessageProtocol;
import bgu.spl.net.impl.Database;
import bgu.spl.net.impl.Messages.Message;
import bgu.spl.net.srv.Server;
import java.io.IOException;


public class ReactorMain {

    public static void main(String[] args) { // Integer.parseInt(args[1]) = 2 i guess, Integer.parseInt(args[0]) = 7777
        Database db = Database.getInstance();
        db.initialize("Courses.txt");
        try (Server<Message> server = Server.reactor(Integer.parseInt(args[1]),
                Integer.parseInt(args[0]),
                ()->new MessageProtocol(),
                ()->new MessageEncDec())) {
                server.serve();
        }
        catch  (IOException e) {e.printStackTrace();}
    }

}
