package bgu.spl.net.api;

import bgu.spl.net.impl.Messages.*;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class MessageEncDec implements MessageEncoderDecoder<Message> {

    // Fields
    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private int currentPosition =0;
    private ArrayList<String> data = new ArrayList<>();
    private short opCode = -1;



    @Override
    public Message decodeNextByte(byte nextByte) {

        if (opCode < 0) { // We still dont know the operation
            if (len == 2) { // this is exactly the opCode
                opCode = bytesToShort(bytes);
                currentPosition=2;
                if (opCode == 4 | opCode == 11) {
                    Short ourOpCode = opCode;
                    clear();
                    switch (ourOpCode) {
                        case 4:
                            return new LOGOUT(ourOpCode);
                        case 11:
                            return new MYCOURSES(ourOpCode);
                    }
                }
            }
            pushByte(nextByte);
            return null;
        }
        else if (opCode == 1 | opCode == 2 | opCode == 3) {
            if (nextByte == '\0') {
                popString();
                if (data.size() == 2) { // we have user name and password
                    String userName = data.get(0);
                    String password = data.get(1);
                    Short ourOpCode = opCode;
                    clear();
                    switch (ourOpCode) {
                        case 1:
                            return new ADMINREG(userName, password, ourOpCode);
                        case 2:
                            return new STUDENTREG(userName, password, ourOpCode);
                        case 3:
                            return new LOGIN(userName, password, ourOpCode);
                    }
                }
            }

            pushByte(nextByte);
            return null;
        }
        else if (opCode == 5 | opCode == 6 | opCode == 7 | opCode == 9 | opCode == 10) {

            if (len==4) {

                    short courseNum = popCourseNum();
                    Short ourOpCode = opCode;
                    clear();
                    switch (ourOpCode) {
                        case 5:
                            return new COURSEREG(courseNum, ourOpCode);
                        case 6:
                            return new KDAMCHECK(courseNum, ourOpCode);
                        case 7:
                            return new COURSESTAT(courseNum, ourOpCode);
                        case 9:
                            return new ISREGISTERED(courseNum, ourOpCode);
                        case 10:
                            return new UNREGISTER(courseNum, ourOpCode);
                    }
                }

            pushByte(nextByte);
            return null;
        }
        else if (opCode == 8) {

            if (nextByte == '\0') {
                popString();
                if (data.size() == 1) {
                    String requestedUserName = data.get(0);
                    short ourOpCode = opCode;
                    clear();
                    return new STUDENTSTAT(requestedUserName,ourOpCode);
                }
            }
            pushByte(nextByte);
            return null;
        }
        return null;
    }

    @Override
    public byte[] encode(Message message) {

        ServerMessage m= (ServerMessage) message;
        return readMessage(m);
    }

    private byte[] readMessage(ServerMessage Message){
        byte[] output=new byte[4];
        String toAdd="";
        for(int i=0;i< Message.getData().size();i++){
            String m = Message.getData().get(i);
            if (i==0) {
                short s1 = Short.parseShort(m.substring(0, 2));
                byte[] op1 = shortToBytes(s1);
                short s2 = Short.parseShort(m.substring(2));
                byte[] op2 = shortToBytes(s2);
                output = join(op1,op2);
            }
            else {
                toAdd=toAdd+m+'\0';
            }
        }
        byte[] output1=join(output,toAdd.getBytes());
        return output1;
    }

    private static byte[] join(byte[] arr1, byte[] arr2)
    {
        byte[] output = new byte[arr1.length + arr2.length];

        System.arraycopy(arr1, 0, output, 0, arr1.length);
        System.arraycopy(arr2, 0, output, arr1.length, arr2.length);
        return output;
    }




    // Encode short to 2 bytes - haven"t used this method
    public byte[] shortToBytes(short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }


    // We used those sub-methods
    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }

    private void popString() {
        String result = new String(bytes, currentPosition, len-2, StandardCharsets.UTF_8);
        data.add(result);
        currentPosition = len+1;
    }
    private short popCourseNum(){
        String result = new String(bytes, currentPosition, len, StandardCharsets.UTF_8);
        byte[] n= result.substring(0,2).getBytes();
        short courseNum= bytesToShort(n);
        currentPosition = len;
        return courseNum;
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }
        bytes[len] = nextByte;
        len++;
    }

    private void clear() {
        bytes = new byte[1<<10];
        data = new ArrayList<String>();
        len = 0;
        currentPosition = 0;
        opCode = -1;
    }

}
