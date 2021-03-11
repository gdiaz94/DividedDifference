# DividedDifference
Implements the Newton's Divided-Difference method to get the polynomial for interpolation.

This was done as an assignment in my Numerical Analysis course. The program takes data from a file and creates a polynomial for interpolation. The format of the file should follow:
Get the polynomial from a file in this order, first line is an integer mentioning how many points there are in the file, after that you have the x and y’s each in one lineForexample:  
5
1.0 0.7651977
1.3 0.6200860
1.6 0.4554022
1.9 0.2818186
2.2 0.1103623

I chose to use a single-dimensional array to hold the x-values, and a two-dimensional array to hold the y-values + the calculated divided difference values.
