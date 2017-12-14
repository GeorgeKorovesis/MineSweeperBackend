package com.mine.sweeper;
import java.util.HashMap;

public class mineSweeperBackend {

	HashMap<String,Integer> Table = new HashMap<>();
	Integer [][] Bombs,Duplicate;
	Integer Dimension;
	
	
	mineSweeperBackend(Integer n, Integer [][] bomb)
	{
		Dimension=n;
		Bombs=bomb;
		createTable();
	}
	
	/*Method to return value of adjacent values to given cell 
	 *or -2 in case of bomb 
	 *or -1 in case cell is out of bounds*/
	public Integer getCoordinate(Integer x, Integer y)
	{
		
		if(isOutOfBounds(x,y))
			return -1;
		
		String hashKey = x+","+y;
		
		if(!Table.containsKey(hashKey))
			return 0;
		
		return Table.get(hashKey);
		
	}

	/*Method to create hash Table*/
	private void createTable()
	{
		
		for (Integer[] bomb:Bombs)
			addAdjacentCellValuesToHash(bomb);
		
	}
	
	/*Method to add adjacent cells of a bomb to hash*/
	private void addAdjacentCellValuesToHash(Integer[] bomb)
	{
		Integer x=bomb[0],y=bomb[1];
		String hashKey = x+","+y;

		/* skip adding if cell is a bomb and already added in hash*/
		if(!(isBomb(x,y) && Table.containsKey(hashKey)))
		{	
		
			if(isBomb(x,y) && !Table.containsKey(hashKey))
				Table.put(hashKey,-2);
		
		
			for (Integer i=-1;i<2;i++)
				for (Integer j=-1;j<2;j++)
				{
					if(!isOutOfBounds(x+i,y+j))
						addValueToHash(x+i,y+j);
				}
		}
	}

	/*Method to add cell to Hash*/
	private void addValueToHash(Integer x, Integer y)
	{
		
		String hashKey = x+","+y;
		
		/*If cell is a bomb and already in hash return*/
		if(!isBomb(x,y))
		{
		
		/*If cell is a bomb and not already in hash add and return*/
		//else if(isBomb(x,y) && !Table.containsKey(hashKey))
		//{
		//	Table.put(hashKey,-2);
		//	return;
		//}	
		
		/*Replace or add value of specific key*/
			if(Table.containsKey(hashKey))
			{
			//Integer val = Table.get(hashKey);
				Table.put(hashKey, Table.get(hashKey)+1);
			}
			else
				Table.put(hashKey,1);
		}			
	}
	
	/*Method to check if cell is a bomb*/
	private boolean isBomb(Integer x, Integer y)
	{
		for (Integer[] bomb:Bombs)
		{
			if(x==bomb[0] && y==bomb[1])
				return true;
		}
		return false;
	}
	/*Method to check if cell is out of bounds*/
	private boolean isOutOfBounds(Integer x, Integer y)
	{
		if(x>=0 && x<Dimension && y>=0 && y<Dimension)
			return false;
		else
			return true;
	}
	
	
	
	/*Method for printing minesweeper - Mines are printed with @*/
	public final void printMinesweeper(Integer n,mineSweeperBackend mS)
	{
		for(Integer i=0;i<n;i++)
		{
			System.out.println(" ");
			for(Integer j=0;j<n;j++)
			{
			/*getCoordinate returns -2 for bombs, 
			 *-1 for cells out of bounds and 
			 *value of adjacent bombs for other cells 
			 */
			
				if(mS.getCoordinate(i,j)==-2)
					System.out.print("|@|");
				else
					System.out.print("|"+mS.getCoordinate(i,j)+"|");

			}
		}
	}
	/* In this method we check for every cell if the sum of adjacent cells that contain
	   bombs are equal to the value of the cell*/
	public final boolean runTestCase1(mineSweeperBackend mockMS,Integer nsize)
	{
		for(Integer i=0;i<nsize;i++)
			for(Integer j=0;j<nsize;j++)
			{
				if(mockMS.getCoordinate(i, j)>=0) 
				{
					/*sumAdjacentBombCells return the sum of sourrounding bombs*/
					int sum = sumAdjacentBombCells(i,j,mockMS);
					if (sum != mockMS.getCoordinate(i,j))
						//System.out.println("Sum="+sum+" was for ["+i+","+j+"]");
						return false;
				}
				else
				{
					/*in case this cell is a bomb, do not count sum of surrounding bombs*/
					if(!mockMS.isBomb(i, j))
						return false;
				}
			}
		return true;
	}
	
	/*This method sums the adjacent bomb cells of a given cell*/
	private Integer sumAdjacentBombCells(Integer x, Integer y, mineSweeperBackend mockMS)
	{
		Integer sum=0;
		for(Integer i=-1;i<2;i++)
			for(Integer j=-1;j<2;j++)
			{   /* Sum only if surrounding cell is a bomb and inside boundary*/
				if(!mockMS.isOutOfBounds(x+i,y+j) && (mockMS.getCoordinate(x+i,y+j)==-2))
					sum++;
			}
		return sum;
	}
	
	/*This method checks if there are more bombs than cells in matrix*/
	public final boolean runTestCase2(Integer numOfBombs, Integer nSize)
	{
		if(numOfBombs>(nSize*nSize))
				return false;
		return true;
	}
	
	/*This method checks if cells provided as bombs are valid cells*/
	public final boolean runTestCase3(mineSweeperBackend mockMS,Integer [][] myBombs)
	{
		/*For each bomb check if is out of bounds*/
		for(Integer[] bomb:myBombs)
		{
			if(mockMS.isOutOfBounds(bomb[0],bomb[1]))
				return false;
		}
		return true;
	}
	
	
}
