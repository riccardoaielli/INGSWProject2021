# Prova Finale di Ingegneria del Software - AA 2020-2021
![alt text](src/main/resources/Masters-of-Renaissance_box3D.png)

Versione software del gioco da tavolo [Maestri del Rinascimento](http://www.craniocreations.it/prodotto/masters-of-renaissance/).

## Documentazione

### UML
I seguenti diagrammi delle classi rappresentano rispettivamente il modello iniziale sviluppato durante la fase di progettazione e i diagrammi del prodotto finale nelle parti critiche riscontrate.
- [UML Iniziale](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/blob/master/deliveries/uml/initial_uml_model.png)
- [UML Finali](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/tree/master/deliveries/uml)

### JavaDoc
La seguente documentazione include una descrizione per la maggior parte delle classi e dei metodi utilizzati, segue le tecniche di documentazione di Java e può essere consultata al seguente indirizzo: [Javadoc](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/tree/master/deliveries/javadoc/index.html)

### Coverage report
Al seguente link è possibile consultare il report della coverage dei test effettuati con Junit: [Report](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/tree/master/deliveries/report/CoverageReport.JPG)

### Protocollo di comunicazione
Il seguente documento rappresenta il protocollo di comunicazione implementato tra client e server: [Protocollo di Comunicazione](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/tree/master/deliveries/communication/CommunicationProtocol.pdf)

### Librerie aggiuntive utilizzate
|Libreria|Descrizione|
|---------------|-----------|
|__Maven__|Strumento di automazione della compilazione utilizzato principalmente per progetti Java.|
|__JavaFx__|Libreria grafica per realizzare interfacce utente.|
|__JUnit__|Framework di unit testing.|
|__Gson__|Libreria che permette di serializzare e deserializzare oggetti tramite l'utilizzo di files json.|

## Funzionalità
### Funzionalità Sviluppate
- Regole Complete
- CLI
- GUI
- Socket
- 2 FA (Funzionalità Avanzate)
    - __Partita locale:__ Il client è realizzato in modo che possa gestire la partita in solitario senza effettuare
      una connessione con il server.

    - __Partite multiple:__  Il server è realizzato in modo che possa gestire più partite contemporaneamente,
      dopo la procedura di creazione della prima partita, i giocatori che accedono al server vengono gestiti
      in una sala d’attesa per creare una seconda partita e così via.

## Compilazione e packaging
Il jar è stati realizzato con l'ausilio di Maven Shade Plugin.
Di seguito è fornito il jar precompilato.
Per compilare il jar autonomamente, posizionarsi nella root del progetto e lanciare il comando:
```
mvn clean package
```
Il jar compilato verrà posizionato all'interno della cartella ```target/``` con il nome
```MoR-AM37.jar```.

### Jar
Il Jar del progetto può essere scaricato al seguente link: [Jar](https://github.com/PaoloBisignano/ingswAM2021-Aielli-Bisignano-Cerasani/tree/master/deliveries/jar/MoR-AM37.jar).


## Esecuzione
Questo progetto richiede una versione di Java 8 o superiore per essere eseguito correttamente.

### Masters of Renaissance Client
Per lanciare il Client digitare da terminale il comando:
```
java -jar MoR-AM37.jar -morClient [-cli -port <port_number> -host <host_number> -help]
```
#### Parametri
- `-cli`: permette di avviare la CLI. Se non specificato viene avviata la GUI di default;
- `-port`: permette di specificare la porta del server al quale connettersi. Se non specificato il valore di default è __1334__;
- `-host`: permette di specificare l'ip del server al quale connettersi. Se non specificato il valore di default è 127.0.0.1;
- `-help`: fornisce informazioni riguardo l'esecuzione del client;

### Masters of Renaissance Server
Per lanciare il Server digitare da terminale il comando:
```
java -jar MoR-AM37.jar -morServer [-port <port_number> -demo -help]
```
#### Parametri
- `-port`: permette di specificare la porta del server. Se non specificato il valore di default è __1334__;
- `-demo`: permette di avviare il server in mmodalità demo. La modalità demo fornisce in partenza dieci risorse per ogni tipo ad ogni giocatore;
- `-help`: fornisce informazioni riguardo l'esecuzione del server;

## Componenti del gruppo
- [__Riccardo Aielli__](https://github.com/riccardoaielli)
- [__Paolo Bisignano__](https://github.com/PaoloBisignano)
- [__Andrea Cerasani__](https://github.com/andreacerasani)
