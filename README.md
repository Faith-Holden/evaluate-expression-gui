# evaluate-expression-gui

My solution for Chapter 8 Exercise 5 of “Introduction to Programming Using Java”.

NOTE: This is a javafx program. It requires the javafx library as a dependency. (See bottom of this README for javafx instructions).

Problem Description:
This exercise uses the class Expr, which was described in Exercise 8.4 and which is defined
in the source code file Expr.java. For this exercise, you should write a GUI program that
can graph a function, f(x), whose definition is entered by the user. The program should
have a text-input box where the user can enter an expression involving the variable x, such
as x^2 or sin(x-3)/x. This expression is the definition of the function. When the user
clicks an “Enter” button or presses return, the program should use the contents of the
text input box to construct an object of type Expr. If an error is found in the definition,
then the program should display an error message. Otherwise, it should display a graph of
the function. (Recall: If you make a button into the default button for the window, then
pressing return will be equivalent to clicking the button (see the end of Subsection 6.4.2).)
The program will need a Canvas for displaying the graph. To keep things simple,
the canvas should represent a fixed region in the xy-plane, defined by -5 <= x <= 5
and -5 <= y <= 5. To draw the graph, compute a large number of points and connect
them with line segments. (This method does not handle discontinuous functions properly;
doing so is very hard, so you shouldn’t try to do it for this exercise.) My program
divides the interval -5 <= x <= 5 into 300 subintervals and uses the 301 endpoints of
these subintervals for drawing the graph. Note that the function might be undefined at
one of these x-values. In that case, you have to skip that point.
A point on the graph has the form (x,y) where y is obtained by evaluating the user’s
expression at the given value of x. You will have to convert x and y values in the range
from -5 to 5 to the pixel coordinates that you need for drawing on the canvas. The formulas
for the conversion are:
double a = ( (x + 5)/10 * width );
double b = ( (5 - y)/10 * height );
where a and b are the horizontal and vertical coordinates that you can use for drawing on
the canvas. The values of width and height give the size of the canvas.

Javafx setup instructions:
Download javafx from: https://gluonhq.com/products/javafx/ (I used javafx 12). Save it to a location of your choice.
Unpack the zip folder.
Open my project with your IDE of choice (I use intellij IDEA).
Add the javafx/lib folder as an external library for the project. For intellij, this means going to "project structure" -> "libraries" -> "add library" ->{javafx location}/lib
Add the following as a VM argument for the project: --module-path "{full path to your javafx/lib folder}" --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.media,javafx.swing,javafx.web
Build and run the project as normal.

Note: This project relies on the Expr.java class provided by the textbook author.
