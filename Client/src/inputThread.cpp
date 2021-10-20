//
// Created by spl211 on 04/01/2021.
//
#include "inputThread.h"
#include <iostream>
#include <cstring>
#include <thread>
using namespace std;
enum class byte : std::uint8_t {};

// Decode 2 bytes to short !!!
short bytesToShort(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}

// Encode short to 2 bytes
void shortToBytes(short num, char* bytesArr)
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}

std::vector<std::string> split (std::string str,std::string sep){
    char* cstr = const_cast<char*>(str.c_str());
    char* current;
    std::vector<std::string> arr;
    current=strtok(cstr,sep.c_str());
    while(current!=nullptr){
        arr.emplace_back(current);
        current=strtok(nullptr,sep.c_str());
    }
    return arr;
}
    int inputThread::encodeMessage(std::string str,char arr[]) {
    vector<string> words = split(str, " ");
    char opc[]={'\0','\0'};
    string word = words.at(0);
    if (word.compare("ADMINREG") == 0) {
        opc[1] = 1;
    }
    if (word.compare("STUDENTREG") == 0) {
        opc[1] = 2;
    }
    if (word.compare("LOGIN") == 0) {
        opc[1] = 3;
    }
    if (word.compare("LOGOUT") == 0) {
        opc[1] = 4;
    }
    if (word.compare("COURSEREG") == 0) {
        opc[1] = 5;
    }
    if (word.compare("KDAMCHECK") == 0) {
        opc[1] = 6;
    }
    if (word.compare("COURSESTAT") == 0) {
        opc[1] = 7;
    }
    if (word.compare("STUDENTSTAT") == 0) {
        opc[1] = 8;
    }
    if (word.compare("ISREGISTERED") == 0) {
        opc[1] = 9;
    }
    if (word.compare("UNREGISTER") == 0) {
        opc[1] = 10;
    }
    if (word.compare("MYCOURSES") == 0) {
        opc[1] = 11;
    }
    short opCode= bytesToShort(opc);

    if ((opCode == 4) | (opCode==11)) {
        copy(opc,opc+2,arr);
        arr[2]='\0';

        return 3;
    }

    if ( (opCode == 5) | (opCode == 6) | (opCode == 7) | (opCode == 9)| (opCode == 10)) {
        string str1=words.at(1);
        short cn= (short)stoi(str1);
        char m1ToBytes[2];
        shortToBytes(cn,m1ToBytes);
        copy(opc,opc+2,arr);
        copy(m1ToBytes,m1ToBytes+2,arr+2);
        arr[4]='\0';
        return 5;

    }
    if(opCode==8){
        copy(opc,opc+2,arr);
        string str1=words.at(1)+'\0';
        const char* m1ToBytes= str1.c_str();
        copy(m1ToBytes,m1ToBytes+strlen(m1ToBytes)+1,arr+2);
        return strlen(m1ToBytes)+3;
    }
    

        if ((opCode == 1) | (opCode == 2) | (opCode == 3)) {
            string str1=words.at(1)+'\0';
            const char* m1ToBytes= str1.c_str();
            string str2=words.at(2)+'\0';
            const char* m2ToBytes= str2.c_str();
            copy(opc, opc+2, arr);
            copy(m1ToBytes, m1ToBytes+strlen(m1ToBytes)+1, arr+2);
            copy (m2ToBytes,m2ToBytes+strlen(m2ToBytes)+1, arr+2+strlen(m1ToBytes)+1);


            return 2+strlen(m1ToBytes)+strlen(m2ToBytes)+2;
        }
        return -1;
    }




void inputThread::sendMessage() {
    string input;
    std::getline(cin,input);
    char msg[1024];
    int numOfBytes= encodeMessage(input,msg);
    char toSend[numOfBytes];
    for(int i=0;i<numOfBytes;i++){
        toSend[i]=msg[i];
    }
    connectionHandler->sendBytes(toSend,numOfBytes);
}

inputThread::inputThread(ConnectionHandler* connectionHandler1,bool * t): connectionHandler(connectionHandler1),terminated(t) {
}

void inputThread::run() {
    while(!*terminated){
        sendMessage();
        this_thread::sleep_for(std::chrono::milliseconds(100));

    }

}

