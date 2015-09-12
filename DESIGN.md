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



