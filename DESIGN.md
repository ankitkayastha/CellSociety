#Cell Society

##Members
* Jiawei Zhang (jz134)
* Ankit Kayastha (ak308)
* Jaidev Satish (js541)

##Introduction
Our team is trying to simulate the process of Cellular Automata as many problems and processes are in fact simply examples of Cellular Automata (CA). Conways' game of Life is perhaps the most simple and famous example, but there are of course many other examples. Our primary design goals are to make the code flexible so it is fairly simple to add new features if someone would want to do that. Our design and code is looking to be most flexible in terms of changing the type of simulation (i.e. Game of Live vs. Spreading Fire vs. Wa-Tor, etc), changing the type of "GridCell" object (i.e. square, triangle, etc), and changing the way the GridCells are updated (i.e. based on many characteristics which each have multiple states). The primary architecture of the design entails some application of the open-closed principle. Ideally, classes should be open to extension but closed to modification - their behavior should be able to be extended but the original code should not be modified. This is encapsulated in our adaptation of the different simulation types, where they will all share common behavior, but to add a new simulation, one can simply just add on to the original general simulation behavior. This idea is also similar for changing the type of GridCell object, as it will be easy to add a different "type" of grid cell. And lastly, adding different characteristics should also follow along with the open-closed principle as our program and design will hopefully allow the user to simply add on to the previous characteristics to create new ones as well as add states.


##Overview
Our program will have several classes. One of the classes will of course be Main, which will contain the start method and the main method (where launch(args)) is called. Also, we will create an instance of the GUI class in main, which requires interacting with the GUI class. Also, the Main class will have boilerplate code. Finally, the main class will handle the timeline and keyframes and the step method. Another class we will have is the Reader class, which will browse a directory to choose appropriate XML files, read and validate the file, and send data to the Sim class. So, we will have methods such as chooseFile(), readFile(), and validateFile(). If there is an error in validating the file, this class will send an error to the GUI (requiring interaction with the GUI class). Next is the Control class, which will essentially handle the buttons on the GUI. This includes play, pause, open file, settings, selecting the simulation, and adjusting the speed multiplier, which the user will be able to push buttons for each of these. Therefore, the Control class is handling the user input and handling the events from the GUI class, so the two classes will have to interact very closely. The Control class will interact with the Reader class for opening the XML file. The GUI class will initialize the root (or group), as well as initialize the user interface (adding the buttons for the different options). Also, the GUI class will update the GUI for every time the step method is called.Therefore, the GUI class will have to call the Control class. From this, the GUI class will have methods such as initialize(), updateGUI(), and initializeInterface(). Next is the Grid class, which will be an abstract class that can have subclasses. The Grid class will be responsible for initializng the grid using the data from the Sim class. It will also send the state of the GridCells to the GUI to display. Also, this class will update the cells with the rules from Sim, which governs the state of each cell. The Grid class will also have a findNeighbors method that will be used to locate the nearest neighbors, or the cells which determine the state of the current selected cell. The Grid class will be used to have two grids, one for the current state of each cell, and another grid showing the next state of each cell. Updating the cells will require relying on the current state of the cells (before they are changed), but then the next state will become the new current state, so we will essentially overwrite the current state grid and keep generating the next state grid. The GridCell class will also be an abstract superclass, and it will hold attributes for each GridCell object. It will hold such attributes as the location, type, current state, and it will also be used to determine the next state of the cell. There will be methods such as nextState(), which is based on the rules of each simulation. Finally, the Sim class will have all the rules for the different simulations; these rules govern the states of the cells, but they are different for each simulation. That is why this will be an abstract class, and each simulation will be a subclass. The Sim class will also create the characteristics/states from the XML output. We are thinking of using a map that will map a string (the characteristic), to a list, which will contain all of the possible states for that characteristic.  

The pictures show the relationships between all of the classes. An arrow indicates the relationship between the classes it is point to. For instance, an arrow pointing from Control to Reader indicates that Control will have to call Reader. These arrows simply serve to establish the relationships between the different classes. 

##User Interface

The user interface will be a 540px high and 500px wide. The bottom 500 by 500 pixels will be the grid where the cells reside. The 40px bar along the top will be the toolbar with the following controls:

* Play (button)- Start the game from a new or paused state.
* Pause (button) - Pause the running game with the ability to restart.
* Speed Multiply (text box with button)- Increase the speed of the game by a user-defined factor.
* Open (button) - Open a file in the user's computer's file system. Can only click when game is paused or not started.
* Simulation (dropdown) - Allow users to select a simulation to run. The simulation will only run if the XML files matches the format required by the simulation.
* Settings (dropdown) - Allow users to make customizations, such as color schemes and cell shapes (appearance, not functional). 

The image below shows the GUI. 

![alt text][logo]

[logo]: https://github.com/adam-p/markdown-here/raw/master/src/common/images/icon48.png "Cell Society GUI"

Besides the toolbar, there will also be options for user-input toward the actual simulation, such as clicking a cell to reset it to its original state anytime during the simulation. 

Erroneous situations that will be reported to the user are listed below, along with descriptions. 

* "Cannot play without XML file" - The simulation cannot be run without the XML file. If a user attempts to click play (which will be grayed out) without opening an XML file, this message will appear.
* "Invalid XML file" - The XML file is not of the valid format required to play the selected simulation. A user has to select a simulation (the first simulation will be default) before opening an XML file.
* "Cannot apply speed change" - The speed change factor the user input is out of the legal bounds. The user has to use an input within the legal bounds or the speed change will not be applied.

##Design Details

##Design Considerations

##Team Responsibilities

###Jiawei Zhang
* Create test XML files with different starting configurations. 
* Create the XMLParser, initialize the grid with the starting configurations
* Work with _____ on the cells' getNext method.

###_____
* Create main boilerplate class.
* Create main game class with step method and timeline.
* Create controls class including 'Start', 'Pause', 'Stop', 'Open XML', 'Speed Up', 'Slow Down'.

###_____
* Create cells with different configurations with Jiawei's help. 
* Create getNext method which determines its next state.



