Simulation

The Simulation API contains methods associated with the specific simulations being run.

Classes containing the Simulation API are listed here:

Classes extending CellShape
Classes extending Simulation

// Specific simulation extending abstract Simulation
public class GameOfLife extends Simulation { 
    public GameOfLife(Stats stats) // constructor
    public void update(Grid myGrid, Stats myStats) // update the grid - used from within the loop 
    public void setParam(Double thisDouble) } // used to change simulation parameters
}

// Class used within WaTor simulation
public abstract class Creature { 
    public Creature(Stats stats) // constructor
    public List<Cell> generateNeighbors(Cell[] grid, int index, Stats stats, int animal) // method used for Creature’s subclasses
    public void reproduce(int index, Cell[] grid, Stats stats, int animal, int eaten) // method only required by package - protected
    public void eat(int index, Cell[] grid, Stats stats, int animal, int eaten) // method only required by package - protected
    public boolean hasAnimal(int index, Cell[] grid, Stats myStats, int animal) // method only required by package - protected
    public abstract void animalAction(Cell[] oldGrid, Cell[] myGridGrid, Stats stats); // method only required by package - protected
}

The methods within the extended classes are all part of the API because it helps future coders extend these classes. Some Simulations have classes that contain methods that should be part of the API but only because other classes within the same package require them in order to function (extract method). An example of such a class if the NeighborFactory class which only serves as a “helper”. 

Configuration
The Configuration API contains the Reader, Stats, Grid, and NeighborFactory classes. These classes are responsible for setting up the successful run of simulations, but are not specific to the simulation classes themselves. It can be broken down into three categories:

Reader Class
public Map<String, Integer> getGlobalChars() 
public Map<String, Integer> getCellData(int index) 
public List<Map<String, Integer>> getData()

These methods are all get methods that we think should be part of the API because they are needed in other parts of the program. They wouldn’t really be extended because these methods are just there to return a piece of information or data that another part of the project needs for proper execution. 


Stats Class
public void flipType() 
public void putDimensions(int height, int width)

These methods are methods of the Stats class. The flipType changes whether the grid should be wrap around (toroidal) or the traditional grid. It is a very simple method that deals with a boolean. The putDimensions method is used to initialize the grid with the corresponding dimensions. These should be part of the API but not needed for extension because they are simply called by other parts of the program to ensure proper execution of the program. 

Grid Class
public Cell[] getGrid() 
public Cell getCell(int i) 
public void changeCell(int index, Stats myStats)


The getGrid and getCell methods, as the names indicate, are simply used to return the grid object as well as return a cell object given the index in the grid. These methods should be part of the internal API because they are only there to be called on by other parts of the project. The third method, changeCell, should be part of the external API because it can be extended by other users for future use. 

Visualization
The configuration API contains the classes GuiClass, ButtonHandler, GraphHandler, and all classes that extend GraphTemplate. The methods in these classes are public because they must all talk to GuiClass, and GuiClass, in some cases, needs to be called by the ButtonHandler.

GuiClass:

    public GuiClass(ResourceBundle myResources) 
    public void step() 
    public Scene init(Stage stage, Timeline animation) 
    public void initializeGrid() 
    public void reset(Timeline animation) 
    public void toggleType() 
    private void click(double x, double y) 
    public void initDisplay() 
    public void display()

Most of these methods are called by Main or ButtonHandler. As such, they should be in the API, but the purpose is so that it can be extended in the future.

ButtonHandler:

    public ButtonHandler(GuiClass gui, Group root, Timeline animation, ResourceBundle myResources, int height,
    public TextField createParam(Simulation thisSim) 
    public void createButtons() 


These are called by GuiClass, so they are visible for the purpose of extension.

GraphHandler:

public GraphHandler(Stats myStats) 
    public void updateGraph(Cell[] cells, Stats myStats, int stepNum) 
    public LineChart<Number, Number> createGraph(int top, int height, int width, Scene myScene)

Same as with Buttonhandler, they’re visible for the purpose of extension.

GraphTemplate:
public GraphTemplate(Scene myScene) 
    public abstract void update(Cell[] cells, Stats myStats, int stepNum,

These methods are called by GuiClass. So, for extension.
