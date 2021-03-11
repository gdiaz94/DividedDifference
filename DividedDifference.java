// Programmer:	Gabriel Diaz
// Date:		03/10/2021
// Course:		Numerical Analysis CS384
// Purpose:		Implement the Newton's Divided-Difference method to get the polynomial for interpolation
// Usage:		Get the polynomial from a file in this order, fist line is an integer mentioning how many
//				points there are in the file, after that you have the x and y values each on one line:
//				2
//				1.0 0.7651977
//				1.3 0.6200860

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DividedDifference {
	public static void main(String[] args)
	{
		Polynomial poly = null;
		int choice = 0;
		Scanner console = new Scanner(System.in);
		boolean goodInput = false;
		
		// Get initialization file and pass it to Polynomial object
		try
		{
			File initFile = new File("init.txt");
			poly = new Polynomial(initFile);
		}
		catch (Exception e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		while (true)
		{
			goodInput = false;
			System.out.println("Menu Options:");
			System.out.println("1 - Show the polynomial");
			System.out.println("2 - Approximate the polynomial");
			System.out.println("3 - Exit");
			System.out.print("Enter your choice: ");
			
			while(!goodInput)
			{
				try
				{
					choice = console.nextInt();
					
					if (choice >= 1 && choice <= 3)
						goodInput = true;
					else
						System.out.print("Your choice must be in the range [1,3]. Try again: ");
				}
				catch (Exception e)
				{
					System.out.print("The choice must be an integer. Try again: ");
					console.nextLine();
				}
			}
			
			System.out.println();
			
			if (choice == 1)
			{
				poly.printPolynomial();
			}
			else if (choice == 2)
			{
				float point = 0;
				goodInput = false;
				System.out.print("Enter a point for approximation: ");
				while (!goodInput)
				{
					try
					{
						point = console.nextFloat();
						goodInput = true;
					}
					catch (Exception e)
					{
						System.out.print("The point must be a float value. Try again: ");
						console.nextLine();
					}
					
				}
				
				System.out.printf("The approximation is %.7f\n", poly.approximatePolynomial(point));
			}
			else
			{
				System.out.println("Exiting...\n");
				console.close();
				System.exit(0);
			}
			
			System.out.println();
		}
		
	}
	
}

class Polynomial
{
	// Create arrays to hold x and y values (including calculated values)
	float[] xArr;
	float[][] yArr;
	int numPoints = 0;
	// Constructor
	Polynomial(File initFile)
	{
		try
		{
			Scanner myScanner = new Scanner(initFile);
			// Read number of points
			if(myScanner.hasNextLine())
				numPoints = Integer.parseInt(myScanner.nextLine());
		
			// Initialize xArr and yArr to size of numPoints & each element to 0
			xArr = new float[numPoints];
			yArr = new float[numPoints][numPoints];
			
			for (int i = 0; i < numPoints; i++)
			{
				xArr[i] = 0;
				for (int j = 0; j < numPoints; j++)
				{
					yArr[i][j] = 0;
				}
			}
			
			// Read the next numPoints lines from the file into xArr & yArr
			String data = "";
			String[] dataArr;
			int index = 0;
			while (myScanner.hasNextLine())
			{
				data = myScanner.nextLine();
				dataArr = data.split(" ");
				if (dataArr.length == 2)
				{
					try
					{
						xArr[index] = Float.parseFloat(dataArr[0]);
						yArr[index][0] = Float.parseFloat(dataArr[1]);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.exit(-1);
					}
					index++;
				}
				else
				{
					System.out.println("Invalid file format.");
					System.exit(-1);
				}
			}
			
			myScanner.close();
			calculateDividedDiffs();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
			System.exit(-1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	} // END Polynomial(File init)
	
	private void calculateDividedDiffs()
	{
		for (int i = 1; i < numPoints; i++)
		{
			for (int j = 1; j <= i; j++)
			{
				yArr[i][j] = (yArr[i][j - 1] - yArr[i - 1][j - 1]) / (xArr[i] - xArr[i - j]);
			}
		}
	}
	
	public void printPolynomial()
	{
		System.out.print("P(x) =");
		
		for (int i = 0; i < numPoints; i++)
		{
			if (yArr[i][i] < 0)
			{
				if (i != 0)
					System.out.printf(" - %.7f", java.lang.Math.abs(yArr[i][i]));
				else
					System.out.printf(" -%.7f", java.lang.Math.abs(yArr[i][i]));
			}
			else
			{
				if (i != 0)
					System.out.printf(" + %.7f", yArr[i][i]);
				else
					System.out.printf(" %.7f", yArr[i][i]);
			}
			
			for (int j = 0; j < i; j++)
			{
				System.out.printf("(x-%.1f)", xArr[j]);
			}
		}
		
		System.out.println();
	}
	
	public float approximatePolynomial(float x)
	{
		float approximation = 0;
		
		for (int i = 0; i < numPoints; i++)
		{
			float product = yArr[i][i];
			
			for (int j = 0; j < i; j++)
			{
				product *= (x - xArr[j]);
			}
			
			approximation += product;
		}
		
		return approximation;
	}
}
