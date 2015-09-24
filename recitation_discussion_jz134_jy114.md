# Code Refactoring in class for Recitation

## Things done

* Moved duplicated code to superclass (Simulation)
* Created Stats class that is initialized using Reader but is changeable
* Created additional classes for each simulation type
* Created NeighborFactory that wraps all the neighbor possibilities depending on shape and simulation

## Why things were done

* The classes we had repeated over different simulations so the duplicated code was consolidated into a superclass
* The Stats class allows for changing parameters without reloading an XML
* Additional classes help to reduce the amount of code in the update method for each simulation type
* NeighborFactory masks a ton of 'if' statements