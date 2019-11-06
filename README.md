# 9x9-Sudoku-Solver
Author: Richard Wu

Background:
A Java implemented recursive algorithm to solve a 9x9 sudoku. The recursive algorithm utilizes backtracking in order to determine the possible values of a given coordinate on the sudoku board until the entire board is filled with valid values. It would then print the solution. Traditionally, to check if a value is valid within a sudoku, for loops are implemented to check the row, column, and 3x3 box. However, this is a O(n) time complexity and O(1) memory, where n is 9, the length of a row, column or box to check. An alterantive solution, although uses O(N) memory, where N is 81 the number of values within the sudoku, implements HashMaps of type Integer corresponding to a row, column or box and a HashSet to store the valid values gives an O(1) time complexity for checking. Within Sudoku.java I have implemented both methods of checking if a number is valid.

The recursive algorithm can also be changed to display every possible valid sudoku combinations by removing the return statement if a solution has been found. This would proceed to find every solution and print them out to console if they were found.


Other:
Sudoku.java main function does not contain exception handlers such as NumberFormatException where a value other then a number is inputed or an error handler if a number is greater then 9.
Sudoku.java main takes in a sudoku of 9x9 numbers between 0 and 9 and is seperated by a comma. Below is an example.

0,0,0,0,0,0,0,0,0<br/>
0,0,0,0,0,0,0,0,0<br/>
0,0,0,0,0,0,0,0,0<br/>
0,0,0,0,0,0,0,0,0<br/>
0,0,0,0,0,0,0,0,0<br/>
0,0,0,0,0,0,0,0,0<br/>
0,0,0,0,0,0,0,0,0<br/>
0,0,0,0,0,0,0,0,0<br/>
0,0,0,0,0,0,0,0,0

SudokuBoard.java is a simple JavaFx UI that displays a 9x9 sudoku UI that allows for keyboard input and displays a solution if found. There is a solve button and a clear button located at the bottom and also a error box that will display a error that fades out of view after a period of time. It also handles number checking that Sudoku.java main does not. 

Future:
I can improve upon the UI to include an arrow key event listener to traverse the sudoku board and input values rather then having to click on the box that I want to input a value. This can help with user experience since it is tedious to switch from keyboard to mouse everytime you want to input a value.

