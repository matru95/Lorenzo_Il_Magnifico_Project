# Lorenzo Il Magnifico

## Team Members:
* Endi Sukaj - endi.sukaj@mail.polimi.it - 811883
* Leonardo Panerai - leonardo.panerai@mail.polimi.it - 828473
* Matteo Rubiu - matteo.rubiu@mail.polimi.it - 827117

## How to play:
In order to play this game you'll have to run a CLIENT and a SERVER:
* <b>SERVER</b>: to start server you've to run the main method of GameServer's class
that can be found in the path: [it.polimi.ingsw.gc31.server].
* <b>CLIENT</b>: the client can be start in three different classes in the path: [it.polimi.ingsw.gc31.client]

    * ClientApp: start a client and choose to use CLI or JAVAFX;
    
    * ClientCLI: start a client using Command Line Interface (CLI);
    
    * ClientFX: start a client using JAVAFX;
  
  After choosing a UI, independently from where did you start it,
  you'll be asked to enter a <i>Username</i>, followed by the <i>serverIP</i>
  to which connect, and finally the <i>Connection Method</i> that can be <i>SOCKET</i> or <i>RMI</i>.

## Project Structure: 
The project adopts a Model View Controller (MVC) Pattern and is divided into several packages:
* <b>SERVER</b>: this side uses to following packages in [it.polimi.ingsw.gc31]:
    * controller
    * enumerations
    * exceptions
    * messages
    * model
* <b>CLIENT</b>:
    * client
    * enumerations
    * messages
    * view    
  
 You can find UML's diagrams for this project:
 
   * uml_diagram1: only classes and relationships;
   
   * uml_diagram2: classes with methods;
   
   * uml_diagram3: classes with methods and attributes (Not package "view", because is too big).   
 
## Configuration Files:

Configuration files can be found in `src/config`. 
They are used to define the various configurable elements of the game such as the 
cards, board, player tiles and excommunication tiles.

- **Card.json**: defines all the cards with their values.
- **Settings.json**: 
    general settings for the game, it has three main parts:
    - `gameSettings`: in here you can define 
    the wait time (in seconds) before starting a new game and the wait time for a player move.
    `serverWait` and `playerWait` respectively
    - `parchments`: the various parchment effects.
    - `gameBoard`: the game board.
- **PlayerTile.json** 
    defines the five player tiles that can be used. The first one (ID = 0) 
    is the default one when playing with simplified rules.
    - `rules`: defines the rules whether to use the default player tile or advanced one. 
        To use the default player tile for all players, set this to `default`.
- **FaithTile.json** 
    defines all the faith tiles.
    
- **FaithPoints**
    Defines the number of victory points you get for an amount of faith points.