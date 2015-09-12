#Cell Society

##Members
* Jiawei Zhang (jz134)
* Ankit Kayastha (ak308)
* Jaidev Satish (js541)

##Introduction
Our team is trying to simulate the process of Cellular Automata as many problems and processes are in fact simply examples of Cellular Automata (CA). Conways' game of Life is perhaps the most simple and famous example, but there are of course many other examples. Our primary design goals are to make the code flexible so it is fairly simple to add new features if someone would want to do that. Our design and code is looking to be most flexible in terms of changing the type of simulation (i.e. Game of Live vs. Spreading Fire vs. Wa-Tor, etc), changing the type of "GridCell" object (i.e. square, triangle, etc), and changing the way the GridCells are updated (i.e. based on many characteristics which each have multiple states). The primary architecture of the design entails some application of the open-closed principle. Ideally, classes should be open to extension but closed to modification - their behavior should be able to be extended but the original code should not be modified. This is encapsulated in our adaptation of the different simulation types, where they will all share common behavior, but to add a new simulation, one can simply just add on to the original general simulation behavior. This idea is also similar for changing the type of GridCell object, as it will be easy to add a different "type" of grid cell. And lastly, adding different characteristics should also follow along with the open-closed principle as our program and design will hopefully allow the user to simply add on to the previous characteristics to create new ones as well as add states.


##Overview
Our program will have several classes. One of the classes will of course be Main, which will contain the start method and the main method (where launch(args)) is called. Also, we will create an instance of the GUI class in main, which requires interacting with the GUI class. Also, the Main class will have boilerplate code. Finally, the main class will handle the timeline and keyframes and the step method. 

Another class we will have is the Reader class, which will browse a directory to choose appropriate XML files, read and validate the file, and send data to the Sim class. So, we will have methods such as chooseFile(), readFile(), and validateFile(). If there is an error in validating the file, this class will send an error to the GUI (requiring interaction with the GUI class). Next is the Control class, which will essentially handle the buttons on the GUI. This includes play, pause, open file, settings, selecting the simulation, and adjusting the speed multiplier, which the user will be able to push buttons for each of these. 

Therefore, the Control class is handling the user input and handling the events from the GUI class, so the two classes will have to interact very closely. The Control class will interact with the Reader class for opening the XML file. The GUI class will initialize the root (or group), as well as initialize the user interface (adding the buttons for the different options). 

Also, the GUI class will update the GUI for every time the step method is called.Therefore, the GUI class will have to call the Control class. From this, the GUI class will have methods such as initialize(), updateGUI(), and initializeInterface(). 

Next is the Grid class, which will be an abstract class that can have subclasses. The Grid class will be responsible for initializng the grid using the data from the Sim class. It will also send the state of the GridCells to the GUI to display. Also, this class will update the cells with the rules from Sim, which governs the state of each cell. The Grid class will also have a findNeighbors method that will be used to locate the nearest neighbors, or the cells which determine the state of the current selected cell. The Grid class will be used to have two grids, one for the current state of each cell, and another grid showing the next state of each cell. Updating the cells will require relying on the current state of the cells (before they are changed), but then the next state will become the new current state, so we will essentially overwrite the current state grid and keep generating the next state grid. 

The Cell class will also be an abstract superclass, and it will hold attributes for each Cell object. It will hold such attributes as the location, type, current state, and it will also be used to determine the next state of the cell. There will be methods such as nextState(), which is based on the rules of each simulation. 

Finally, the Sim class will have all the rules for the different simulations; these rules govern the states of the cells, but they are different for each simulation. That is why this will be an abstract class, and each simulation will be a subclass. The Sim class will also create the characteristics/states from the XML output. We are thinking of using a map that will map a string (the characteristic), to a list, which will contain all of the possible states for that characteristic.  

The pictures show the relationships between all of the classes. An arrow indicates the relationship between the classes it is point to. For instance, an arrow pointing from Control to Reader indicates that Control will have to call Reader. These arrows simply serve to establish the relationships between the different classes. 

![alt text][logo]

[logo]: img/Overview.PNG?raw=true "Cell Society Overview"

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

[logo]: img/Interface.PNG?raw=true "Cell Society GUI"

Besides the toolbar, there will also be options for user-input toward the actual simulation, such as clicking a cell to reset it to its original state anytime during the simulation. 

Erroneous situations that will be reported to the user are listed below, along with descriptions. 

* "Cannot play without XML file" - The simulation cannot be run without the XML file. If a user attempts to click play (which will be grayed out) without opening an XML file, this message will appear.
* "Invalid XML file" - The XML file is not of the valid format required to play the selected simulation. A user has to select a simulation (the first simulation will be default) before opening an XML file.
* "Cannot apply speed change" - The speed change factor the user input is out of the legal bounds. The user has to use an input within the legal bounds or the speed change will not be applied.

##Design Details

###Main
The main class will be quite short but responsible for a few important things. First of all, it will have boilerplate code such as constants that are needed for the program to function. It will also have a start() method that will show the stage and essentially display the GUI. In doing this, it will create an instance of the GUI class by calling the GUI's initialization() method which will return a scene that can then be shown through the start method. The Main class will also handle the timeline, keyframes, and animation, which will have to update through the step() method, also in this class. The step() method will be executed every certain amount of milliseconds so the grid and program in general is being updated every x milliseconds. This main class is needed even though it should not be theoretically very long because this is where the main game loop will be run and updating, which is obviously essential to the program functioning properly.

The Main class will interact with the GUI class because it has to create an instance of it, and will also interact with the Grid class due to the step method. The step method will be used to update the grid, which directly ties into the Grid class where the behind the scenes updating is being done.

###Reader

The Reader class is the main handler for the XML. This class will have a method such as readFile(), which will read and parse the XML file to extract all of the information out of it. It will also have a method such as chooseFile(), which will load a directory and allow the user to choose a certain XML file. This class will also have a validateFile() method which will ensure the file has the proper information, and doesn't pass along any information that may lead to bugs or problems in other parts of the program. For instance, validateFile() is necessary because if wrong information is passed, then this affects the entire rest of the program and may lead to everything completely wrong. Therefore, this method is very important to ensure the information is being passed around properly. The Reader will be general enough to handle different information obviously. The XML files will not all be the same, so the Reader will have to be general enough to be handle any type of information within the XML file, as long as the structure of the XML file is also general enough to be extended. 

This class will interact with the Sim class and the GUI class, as it will have to send the data regarding the type of simulation to the Sim class, and if there is an error to be thrown, then this error will have to be sent to the GUI regarding an invalid file. 

###Control
The Control class is used to handle the key and mouse input form the GUI. So, it will essentially take care of the options that the user can select on the GUI, including Play, Pause, Speed Multiply, Open, Simulation, and Settings. These buttons will allow the user to do specific tasks, as described above in the User Interface section of this Design document. The Control class will have to interact with the Reader class, specifically for when the user selects the type of simulation. This class can easily be added onto if the user wants to add more to the GUI because this class will mainly have a handler method, which will handle mouse and key input. So, these methods can easily be updated if someone wants to add to the GUI control or change something. This class is necessary because it centralizes the handling of all of the possible events, instead of having the handler within the GUI class. This will allow for a clear division of responsibility between the Control and GUI class, and thus will lead to less complications within the GUI class. 

###GUI 
The GUI class will be responsible for handling a few important parts of the User Interface. It will of course have an initialize() method that will return a scene in which it calls on the Grid class to pass in the grid object for initialization. This class will also initialize the interface for the user; all the different buttons/drop down menus explained above in the User Interface section will have to be initialized in the GUI class so that they can be displayed. This will involve simply adding things to the Group, so we can have an addToGroup() method. And then, the GUI will have to be updated every time the step method is called, so this can be done through an update() method. This is merely just updating the display of the GUI to the user, so it is not responsible for updating the back-end attributes, such as the state of the cell or the next state or anything along those lines. 

The GUI class will have to interact with the Reader class, Control class, and the Grid class. The GUI class is a key part of our implementation because it is the means by which we are displaying everything to the user. This class should be flexible enough if someone would want to add to it. For instance, if someone wanted to add more buttons and thus more functionality to the GUI, then it should be fairly simple with the addToGroup() method.



###Use Cases

1. This use case will be handled by our design because we will be using the Grid, Cell, and Sim class to determine the current state, next state, update the state, and locate the neighbors. The Sim class will specify which simulation we are using (in this case, Game of Life), which will then dictate what constitutes the neighbors of the cell. The neighbors are found in the Grid class. Because it is a middle cell, we don't have to worry about checking the boundaries of the window, but of course, those checks will be there to handle the next use case. The information for rules and how they are applied will come from the Sim class, while the application of the rules themselves will come from the Cell and Grid classes. The Map described above that maps characteristics to a list of possible states will be used to determine the next state for the middle cell.

2. For the edge cell case, this will be able to be adapted because we will have a simple out of bounds check in the Grid class, and that will ensure that the neighbors are being determined properly. The rest of the updating as described above will be done by by the same classes (Sim, Grid, and Cell), also as described above.

3. To update the cells, the Cell class will be used to hold the current state and to determine the next state. These states will be determined using the Sim class, as this class has the rules for determining the states and it also contains the Map we are using to map the characteristic to the list of possible states. Then, this updated state will be sent to the Grid class, which will update the cell object being acted upon. Finally, the Grid class will interact with the GUI to display this update. 

4. Simulation parameters can be set by simply parsing the XML file using the Reader class and then that information will be sent to the Sim class. Based on the information and the type of simulation, the exchange of information will continue and the parameter will be accounted for in determining the current/next states.

5. The GUI can easily be used to change the simulation type. This can be done using the Simulation drop down menu in the GUI, which will allow the user to choose which simulation they would like to run. Once the user chooses a different simulation, this will cause the open file pop-up to display, which will allow the user to choose a corresponding XML file for the simulation they want to run. 

##Design Considerations

Design issues that remain involve only extraneous cases that could be added, such as infinite grid, hexagonal cells, multi-level and 'patterned' neighbors and custom user input. Nearly all of these design issues relate to the three abstract classes (Sim, Cell, and Grid). These issues should be resolved when the classes are extended into subclasses. 

Design decisions that the group discussed at length include (1) which classes to make abstract, (2) whether to separate GUI from main class, (3) where to implement all possible rules, (4) how to take user input and controls, and (5) where to keep the all the possible states a cell could have at any given time. 

Ultimately, three classes were made abstract classes which are meant to be extended with every different simulation. The first such class is the Grid class which contains a datastructure for storing all the Cells and updates them at every turn. The pro for making this abstract was that the Grid class can easily be modified to accommodate different-shaped or sized Grids. The con was an additional class that would have to be made for every simulation and that infinite grids would be difficult to support. The second abstract class is the Sim class. This class is extended to hold all the simulation rules and possible Cell states for a given simulation. The pro for making this abstract class was having a central location where rules can be called. There was no identifiable con to having this class. The third class was the Cell class. This class holds the simulation state for any Cell in the Grid. This makes the Cells easy to manipulate but renders Grids difficult if the Grid is infinite. 

The second decision was to separate the GUI from the main class. We were initially under the impression that GUI would be a very short class and could easily be put in main using a few lines but then thought to potentially extend the GUI class for potential user GUI options (such as color themes). 

The third decision was to determine where and how the rules are implemented. The choices were to implement the rules within the individual Cell or Grid or to implement the rules within a separate Sim class. The Sim class was chosen to isolate the non-required components from the Cell and Grid implementations. The con was that an extra class would have to be created, but the pros heavily outweighed the con.

The fourth decision was how to take in user inputs. The decision was to take most user input through a toolbar at the top allowing 'Play', 'Pause', 'Open', 'Speed Multiply', 'Simulation', and 'Settings'. These six options encompass most of the possible user inputs as 'Settings' is a dropdown and more options (such as theme selection) can be hidden there. Additionally, mouse input on the Grid will be allowed to change the state of Cell(s) depending on a pre-set option under 'Settings'.

The fifth decision was where to keep the possible states of a Cell. Initially, the decision was made to have a HashMap for that in Cell, but then it was determined that it was unnecessary and repetitive and the HashMap was moved to Sim because it is simulation-specific. This had no real con except that Sim could potentially grow large if there are a large number of possible states. 

Assumptions made during design include (1) the Grid will be two-dimensional but can be non-rectangular, (2) the Cells can have multiple configurations and states at the same time, (3) the time difference between state-changing can vary either periodically or randomly with different timelines for different Cells, (4) the Cells potentially have different shapes, and (5) neighbors can vary in terms of surrounding area, number of surrounding neighbors, patterns of surrounding neighbors, and whether neighbors are randomly determined. 

##Team Responsibilities

###Jiawei Zhang
* Create test XML files with different starting configurations. 
* Create the XML reader and pass the simulation configuration and data  to _____ to use in Sim and Grid. 
* Work with Ankit Kayastha on the Grid getNext method.

###Jaidev Satish
* Create main boilerplate class with timeline and step method.
* Create controls class including 'Start', 'Pause', 'Stop', 'Open XML', 'Speed Up', 'Slow Down'.
* Create GUI that allows manipulation of all controls in addition to working with Jiawei to display XML validation messages.
* Work with Ankit Kayastha to implement the Cells' appearance on the Grid. 
* Set up the Grid. 

###Ankit Kayastha
* Fill out the Sim rules and configuration using simulation data from Jiawei. 
* Initialize Grid using simulation configurations and data.
* Use Grid to manipulate Cells at every step to determine their next state based on the rules within Sim.  


