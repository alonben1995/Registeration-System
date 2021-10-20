//
// Created by spl211 on 04/01/2021.
//

#ifndef BOOST_ECHO_CLIENT_INPUTTHREAD_H
#define BOOST_ECHO_CLIENT_INPUTTHREAD_H

#include <string>
#include <iostream>
#include <boost/asio.hpp>
#include "../include/connectionHandler.h"
using namespace std;

class inputThread {

private:
    ConnectionHandler *connectionHandler ;
    bool* terminated;



public:
    inputThread(ConnectionHandler* connectionHandler1,bool* t);
    int encodeMessage(std::string str,char arr[]);
    void sendMessage();
    void run();


    };







#endif //BOOST_ECHO_CLIENT_INPUTTHREAD_H
