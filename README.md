# Software Engineering Final Exam - AA 2020-2021
![alt text](src/main/resources/Masters-of-Renaissance_box3D.png)

Software version of the board game [Masters of the Renaissance](http://www.craniocreations.it/prodotto/masters-of-renaissance/).

## Documentation 

### UML
The following class diagrams respectively represent the initial model developed during the design phase and the diagrams of the final product in the critical parts found.
- [Initial UML](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/blob/master/deliveries/uml/initial_uml_model.png)
- [Final UMLs](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/tree/master/deliveries/uml)

### JavaDoc
The following documentation includes a description for most of the classes and methods used, follows Java documentation techniques, and can be found at the following address: [Javadoc](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/tree/master/deliveries/javadoc/index.html).

### Coverage report
At the following link you can see the coverage report of the tests done with Junit: [Coverage Report](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/tree/master/deliveries/report/CoverageReport.JPG)

### Communication Protocol
The following document represents the communication protocol implemented between client and server: [Communication Protocol](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/tree/master/deliveries/communication/CommunicationProtocol.pdf)

### Additional libraries used
|Library|Description|
|---------------|-----------|
|__Maven__|Compilation automation tool used primarily for Java projects.|
|__JavaFx__|Graphic library for building user interfaces.|
|__JUnit__|Unit testing framework.|
|__Gson__|Library that allows to serialize and deserialize objects through the use of json files.|

## Features
### Developed Functionalities
- Complete Rules
- CLI
- GUI
- Socket
- 2 FA (Advanced Features)
    - __Local Game:__ The client is designed so that it can run the game solo without making a
      a connection with the server.

    - __Multiple Matches:__ The server is built so that it can handle multiple matches simultaneously,
      after the creation procedure of the first game, the players who access the server are managed
      in a waiting room to create a second game and so on.

## Compilation and packaging
The jar was built with the help of Maven Shade Plugin.
Pre-compiled jar is provided below.
To compile the jar yourself, go to the root of the project and run the command:
```
mvn clean package
```
The compiled jar will be placed inside the folder ``target/`` with the name
``MoR-AM37.jar``.

### Jar
The project`s jar can be downloaded at the following link: [Jar](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/tree/master/deliveries/jar/MoR-AM37.jar).


## Execution
This project requires a version of Java 8 or higher to run correctly.

### Masters of Renaissance Client
To launch the Client type from terminal the following command:
```
java -jar MoR-AM37.jar -morClient [-cli -port <port_number> -host <host_number> -help]
```
#### Parameters
- `-cli`: allows to start the CLI. If not specified runs the GUI by default;
- `-port`: allows you to specify the server port to connect to. If not specified the default value is __1334__;
- `-host`: allows you to specify the server ip address. If not specified the default value is 127.0.0.1;
- `-help`: provides information about running the client;

### Masters of Renaissance Server
To launch the Server type from terminal the following command:
```
java -jar MoR-AM37.jar -morServer [-port <port_number> -demo -help].
```
#### Parameters
- `-port`: allows you to specify the server port. If not specified the default value is __1334__;
- `-demo`: allows you to start the server in demo mode. Demo mode provides ten resources of each type to each player;
- `-help`: provides information about running the server;

## Group members
- [__Riccardo Aielli__](https://github.com/riccardoaielli)
- [__Paolo Bisignano__](https://github.com/PaoloBisignano)
- [__Andrea Cerasani__](https://github.com/andreacerasani)

Italian README version [here](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/blob/master/README-ITA.md)