/**
 * Programming Project 2
 * @author: Garima Chopra
 * @date: October 4 ,2021
 * @class:Ghost Class. 
 * @brief:Class to contain and organize position of Ghost in the maze.
 * 
 */


public class Ghost {


	private int newGhostX; // x coordinate 
	private int newGhostY; // y coordinate 
	private int generation; // keep generation for step cost
	private Ghost parent; // link each state to 
	private int cost; // store total cost

	private Maze maze; // maze this Ghost is part off 



	// constructor
	public Ghost (int x, int y, Maze maze)
	{

		this.newGhostX = x;
		this.newGhostY = y;
		this.generation = 0;
		this.cost = 0;
		this.maze = maze;

	}

	// return parent for path
	public Ghost getParent ()
	{
		return this.parent;
	}

	// set parent 
	public void setParent(Ghost parent)
	{
		this.parent = parent;
	}

	// get x coordinate 
	public int getX ()
	{
		return this.newGhostX;
	}

	// get y coordinate
	public int getY ()
	{
		return this.newGhostY;
	}

	// set cost 
	public void setCost (int cost)
	{
		this.cost = cost;
	}

	//get cost
	public int getCost ()
	{
		return this.cost;
	}

	// get generation 
	public int getGeneration ()
	{
		return this.generation;
	}

	// set generation
	public void setGeneration (int gen)
	{
		this.generation = gen;
	}



	//GhostDown

	public Ghost generateDown ()
	{
		Ghost down = new Ghost (newGhostX + 1, newGhostY, this.maze);
		down.setGeneration(this.generation + 1);
		down.setParent(this);
		return down;
	}

	//Ghost Up
	public Ghost generateUp ()
	{
		Ghost up = new Ghost (newGhostX - 1, newGhostY, this.maze);
		up.setGeneration(this.generation + 1);
		up.setParent(this);
		return up;
	}


	//Ghost left

	public Ghost generateLeft ()
	{
		Ghost left = new Ghost (newGhostX, newGhostY - 1, this.maze);
		left.setGeneration(this.generation + 1);
		left.setParent(this);
		return left;
	}

	//Ghost Right
	public Ghost generateRight ()
	{
		Ghost right = new Ghost (newGhostX, newGhostY + 1, this.maze);
		right.setGeneration(this.generation + 1);
		right.setParent(this);
		return right;
	}


	//get ghost f(n)
	@Override
	public String toString ()
	{

		int g = getGeneration() + maze.getSpacecost(newGhostX, newGhostY);
		return "Ghost located at : (" + newGhostX + "," + newGhostY + ")"
		+ " g: " + g 
		+ " f(n): " + cost;
	}


	//compare for better ghost state
	@Override
	public boolean equals (Object object)
	{
		Ghost compare = (Ghost)object;

		if(compare.getX() != this.newGhostX ||
				compare.getY() != this.newGhostY ) return false;

		return true;
	}
}