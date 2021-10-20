//
// Created by spl211 on 04/01/2021.
//

#include <thread>
#include "outputThread.h"


outputThread::outputThread(ConnectionHandler* connectionHandler1,bool* t):connectionHandler(connectionHandler1),terminated(t) {}

short bytesToShort1(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}

void outputThread::getMessage() {
char msgtype[]={'\0','\0'}; //will get ack or err
char opc[2]; //will get message opcode
connectionHandler->getBytes(opc,2);
connectionHandler->getBytes(msgtype,2);
short type=bytesToShort1(msgtype);
short opcode=bytesToShort1(opc);
if(opcode==12) {
    string data = "";
    if((type==11)|(type==9)|(type==7)|(type==6)){
        connectionHandler->getFrameAscii(data, '\0');
    }
    if(type==8){
        connectionHandler->getLine(data);
        connectionHandler->getFrameAscii(data,'\0');
    }

    if(type==4){
        (*terminated)= true;
        connectionHandler->close();
    }
    if (data == "") {
        string output="ACK " + to_string(type);
        cout << output  << endl;
    } else {
        string output ="ACK " + to_string(type) + "\n"+ data;
        cout << output << endl;
    }
}
    else{
        string output="ERR "+ to_string(type);
        cout<<output<<endl;
        }
    }

void outputThread::run() {
    while (!*terminated) {
        getMessage();
        this_thread::sleep_for(std::chrono::milliseconds(100));
    }
}






