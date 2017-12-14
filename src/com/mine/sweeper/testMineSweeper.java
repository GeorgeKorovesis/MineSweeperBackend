package com.mine.sweeper;

import java.util.Random;

public class testMineSweeper {

	/*how many different mock objects to be created and tested*/
	public static final int mockObjects = 1;

	public static void main (String [] args)
	{
		Integer remainingObjects=mockObjects;
		Integer passedCases=0;
		Integer totalCases=3*remainingObjects; /*for each mock object we have 3 test cases*/
		
		
		while(remainingObjects>0)
		{
		/*Create some Random Values for Matrix and Bombs,
		 *and print values
		 */		
		/*Initialize random values for array and bombs*/
			Random rand = new Random();
			Integer n = rand.nextInt(20) + 2;
			Integer numOfBombs = rand.nextInt(20) + 2;
			Integer [][] myBombs= new Integer[numOfBombs][2];
		
			for(Integer i=0;i<numOfBombs;i++)
			{
				myBombs[i][0]=rand.nextInt(n);
				myBombs[i][1]=rand.nextInt(n);		
			}
		
		/*Create new mock object mineSweeperBackeng*/
			mineSweeperBackend mS = new mineSweeperBackend(n,myBombs);
				
		/*Print minesweeper - Mine is printed with @*/
			mS.printMinesweeper(n,mS);
		
		/*Test mock object*/
			System.out.println("\n\nMock Object "+(mockObjects-remainingObjects+1));
			passedCases+=runTestCases(mS, n, myBombs);
			remainingObjects--;
			}
		System.out.println("Test Cases Executed:"+totalCases);
		System.out.println("Test Cases Passed:"+passedCases);
		System.out.println("Test Cases Failed:"+(totalCases-passedCases));
		}
	
	public static Integer runTestCases(mineSweeperBackend mS, Integer n, Integer [][] myBombs)
	{
		boolean [] result = new boolean[3];
		Integer numOfBombs = myBombs[0].length;
		Integer passed=0;
		/*Now we have to test our mineSweeper*/
		/*Test case 1 - Check all matrix if done correctly*/
		result[0] = mS.runTestCase1(mS,n);
		/*Test case 2 - Check what happens if bombs are more than available cells*/
		result[1] = mS.runTestCase2(numOfBombs,n);
		/*Test case 3 - Check what happens if for some unexpected reason getCoordinate 
		  is called upon an index which is found out of bounds*/ 
		result[2] = mS.runTestCase3(mS,myBombs);

		System.out.println(""); /*just change line*/
		
		for (Integer i=0;i<result.length;i++)
		{
			System.out.println("\nTestCase"+(i+1)+":"+(result[i]?"Passed":"Not Passed"));
			if(result[i])
				passed++;
		}
		return passed;
	}
	
}
