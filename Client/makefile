CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -lboost_thread -pthread

all: bin/BGRSclient

bin/BGRSclient: bin/Client.o bin/connectionHandler.o bin/inputThread.o bin/outputThread.o
	@echo 'Building target: Client'
	@echo 'Invoking: C++ Linker'
	g++ -Wall -Weffc++ -o bin/BGRSclient bin/Client.o bin/connectionHandler.o bin/inputThread.o bin/outputThread.o $(LDFLAGS)
	@echo 'Finished building target: Client'
	@echo ' '

bin/Client.o: src/Client.cpp
	g++ $(CFLAGS) -o bin/Client.o src/Client.cpp
bin/inputThread.o: src/inputThread.cpp
	g++ $(CFLAGS) -o bin/inputThread.o src/inputThread.cpp
bin/outputThread.o: src/outputThread.cpp
	g++ $(CFLAGS) -o bin/outputThread.o src/outputThread.cpp
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp


clean:
	rm -f bin/*