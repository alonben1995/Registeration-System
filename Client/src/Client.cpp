//
// Created by spl211 on 05/01/2021.
//
#include <string>
#include <iostream>
#include <thread>
#include "../include/connectionHandler.h"
#include "inputThread.h"
#include "outputThread.h"

using namespace std;

int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
//    string host = "127.0.0.1";
//    short port = (7777);
    std::string host = argv[1];
    short port = atoi(argv[2]);
    ConnectionHandler connectionHandler(host,port);

    bool isTerminated(false);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }

    inputThread inputThread (&connectionHandler,&isTerminated);
    outputThread outputThread (&connectionHandler,&isTerminated);

    std::thread ourIn (&inputThread::run,inputThread);
    std::thread ourOut (&outputThread::run,outputThread);

    ourIn.join();
    ourOut.join();

    return 0;
}
