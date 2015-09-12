#Cell Society

##Members
* Jiawei Zhang
* Ankit
* Jaidev

##Introduction

##Overview

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

[logo]: Interface.PNG "Cell Society GUI"

Besides the toolbar, there will also be options for user-input toward the actual simulation, such as clicking a cell to reset it to its original state anytime during the simulation. 

Erroneous situations that will be reported to the user are listed below, along with descriptions. 

* "Cannot play without XML file" - The simulation cannot be run without the XML file. If a user attempts to click play (which will be grayed out) without opening an XML file, this message will appear.
* "Invalid XML file" - The XML file is not of the valid format required to play the selected simulation. A user has to select a simulation (the first simulation will be default) before opening an XML file.
* "Cannot apply speed change" - The speed change factor the user input is out of the legal bounds. The user has to use an input within the legal bounds or the speed change will not be applied.

##Design Details

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
* Work with _____ on the Grid getNext method.

###_____
* Create main boilerplate class with timeline and step method.
* Create controls class including 'Start', 'Pause', 'Stop', 'Open XML', 'Speed Up', 'Slow Down'.
* Create GUI that allows manipulation of all controls in addition to working with Jiawei to display XML validation messages.
* Work with _____ to implement the Cells' appearance on the Grid. 
* Set up the Grid. 

###_____
* Fill out the Sim rules and configuration using simulation data from Jiawei. 
* Initialize Grid using simulation configurations and data.
* Use Grid to manipulate Cells at every step to determine their next state based on the rules within Sim.  


