****************
* Project 1 CS 221
* Class: Section 2
* Date September 19 2019
* Robbie Gill
**************** 

OVERVIEW:

 GridMonitor is intended to read textfiles which represent 2D arrays, GridMonitor then 
compares these values and returns true or false depending on their relative differences. 
true representing numbers within the failed range, and false for numbers within the safe range.
GridMonitor implements the GridMonitorInterface.java

INCLUDED FILES:

--ReadMe This README serves a brief guide to understanding and getting started with using the GridMonitor program.

---The GridMonitor class instantiates 2D arrays which will then be used to determine whether a cell value is within acceptable range, and returns a 2D boolean array representing each tested index.

 * GridMonitor.java 
 * README


COMPILING AND RUNNING:
The following commands will be used to run the GridMonitor program. 
$Javac 
$Java 

 From the directory containing all source files, compile the
 driver class (and all dependencies) with the command:
 $ javac GridMonitor.java

 Run the compiled class file with the command:
 $ java GridMonitor

 Console output will give the results after the program finishes.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:


GridMonitor Reads in a .txt file then formats it into a 2D array, which is then fed through a loop that performs mathematical operations which will find the following information about the original 2D grid: sums of the 4 surrounding values, averages and Delta Values relative to each indexed element of the 2D grid.

GridMonitors constructor handles the instantiation of all grids with the exception of the boolean DangerGrid.
GridMonitor Constructor
*Attempts to read in a .txt file.

*While the .txt file has another line to read, each line of the text file is concatenated into a string.

*This String will represent the .txt file. The purpose of this is to convert .txt information into a format that the java compiler can understand, and we in turn can manipulate.

*The String is then split apart and converted to a Double, so that each numeric value inside of it represents an index in an array of Double values. 
NOTE( The grid Monitor program assumes that if a file is found and is of type .txt that the containing values are correct. )

*The first two elements are pulled from this array to represent the rows and columns of the 2D array we will create, NOTE (This 2D array will be called our baseGrid).

*The rest of the functionality of this program will depend on this 2D Array (BaseGrid).
*NOTE(The instructions dictate that we must find the sum average and delta values of the adjacent elements for each index in the array, then save these values in their own 2D arrays)
*We will instantiate 3 new arrays gridOfAdjacentPositions, gridOFAverageAdjacentPositions and gridOfMaximumDeltaOfAverage.

*The Program then loops through all elements of the original array attempting to retrieve values adjacent to each current index.

*(If) the program is successful it will add the adjacent index’s value to a variable.

 *(Else if) the adjacent value is out of bounds. This indicates to the program that it should then alternatively assign the current index’s value to a variable.

*The program then Sums these values and saves them into a variable representing the sum of the surrounding values at the current index.

*Next the program takes a reference to this sum and takes the average of it and then saving this average value into a variable representing the averageOfSum.

*Next the program takes a reference to this average and divides it by 2 this gives the maximum delta value which it then saves into a variable representing the acceptable Delta from the original averageOfSum.

*These three variables (sumVal,  averageOfSum, maxDeltaOfAverage) are then set to their respective graphs current index.

NOTE (Because the boolean values representing the relative deviation from adjacent positions of each index of the 2D arrays cannot be determined while the 2D arrays are still being populated. I have decided to instantiate the 2D array get danger grid in  getDangerGrid() method ) 

getDangerGrid method

*The booleanDangerGrid which represents if the AverageAdjacent positions are within acceptable range is instantiated

*The program then beings a loop to determine whether the AverageAdjacent positions are within acceptable range

*These true or false values of each index are then added to booleanDangerGrid which is then returned.
 
 If you were responsible for designing the program's classes and choosing
 how they work together, why did you design the program this way? What, if 
 anything, could be improved? 

 This is the sort of information someone who really wants to
 understand your program - possibly to make future enhancements -
 would want to know.

TESTING:

I tested my program by testing each method and file before moving on to the next step. I did this by using a seperate driver class with specific filename fed into my GridMonitor class, then viewing the outputs of my method and comparing it to the expected results. For the most part the program is robust in regards to the requirements. I handled all of my mathematical operations which are dependent upon each other in the constructor.This way none of the getter methods can be called before the dependent information has been entered.


NOTE( The program could be broken if a read in .txt file is not in the expected format, for instance if the rows and columns where incorrect from the number of values in the grid.)



DISCUSSION:
 
 Discuss the issues you encountered during programming (development)
 and testing. What problems did you have? What did you have to research
 and learn on your own? What kinds of errors did you get? How did you 
 fix them?
I encountered a few issues the main issue I faced was my 2D array resetting itself on each iteration. There was a failure to read negative values correctly, I found that by checking the absolute values I could work around the headache of adding and comparing negative numbers. 

I had to reread some of the 2D array chapter from the java foundations textbook. It was conceptually confusing to me at first the order of my rows and columns so I solved this by drawing out on grid paper the populated values of my arrays at different steps in the program.
 
 What parts of the project did you find challenging? Is there anything
 that finally "clicked" for you in the process of working on this project?
 The most challenging part of this project was reducing method dependency and not reusing code. I spent a lot of time reading the instructions and drawing out the program and list of steps I needed to accomplish.



