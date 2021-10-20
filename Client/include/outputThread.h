//
// Created by spl211 on 04/01/2021.
//

#ifndef BOOST_ECHO_CLIENT_OUTPUTTHREAD_H
#define BOOST_ECHO_CLIENT_OUTPUTTHREAD_H
#include <string>
#include <iostream>
#include <boost/asio.hpp>
#include "../include/connectionHandler.h"

using namespace std;

class outputThread {

public:
    outputThread (ConnectionHandler* connectionHandler1, bool* t);
    void getMessage();
    void run();

private:

    ConnectionHandler* connectionHandler;
    bool* terminated;
};




#endif //BOOST_ECHO_CLIENT_OUTPUTTHREAD_H
