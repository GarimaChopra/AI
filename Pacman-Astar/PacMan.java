/**
 * Programming Project 2
 *@author: Garima Chopra
 * @date: October 4 ,2021
 * @class:PacMan Class
 * @brief: evaluates state space for Pacman
 * 
 * 
 */

public class PacMan implements Comparable {

	private int pacX; // x coordinate 
	private int pacY; // y coordinate 
	private int generation; // keep generation for step cost
	private PacMan parent; // link each state to 
	private int cost; // store total cost
	private Maze maze; // maze this pacman is part off 



	// constructor
	public PacMan (int x, int y, Maze maze)
	{
		try {

		this.pacX = x;
		this.pacY = y;
		this.generation = 0;
		this.cost = 0;
		this.maze = maze;
		}
		catch(Exception E)
		{
			System.out.print(" e");
		}

	}

	// return parent for path
	public PacMan getParent ()
	{
		return this.parent;
	}

	// set parent 
	public void setParent(PacMan parent)
	{
		this.parent = parent;
	}

	// get x coordinate 
	public int getX ()
	{
		return this.pacX;
	}

	// get y coordinate
	public int getY ()
	{
		return this.pacY;
	}

	// set cost 
	public void setcost (int cost)
	{
		this.cost = cost;
	}

	//get cost
	public int getcost ()
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

	//calculate heuristic function h(n)
	public int calculateManhattanDistance (int gX, int gY)
	{
		return (Math.abs(pacX - gX) + Math.abs(pacY - gY));  // subtract pacman location from ghost location
	}


	// pacman down
	public PacMan generateDown ()
	{
		try
		{
		PacMan down = new PacMan (pacX + 1, pacY, this.maze);
		down.setGeneration(this.generation + 1);
		down.setParent(this);
		return down;
		}
		catch(Exception E)
		{
			System.out.print(" e");
		}
		
		return null;
	}

	//pacman up
	public PacMan generateUp ()
	{
		PacMan up = new PacMan (pacX - 1, pacY, this.maze);
		up.setGeneration(this.generation + 1);
		up.setParent(this);
		return up;
	}


	//pacman left
	public PacMan generateLeft ()
	{
		PacMan left = new PacMan (pacX, pacY - 1, this.maze);
		left.setGeneration(this.generation + 1);
		left.setParent(this);
		return left;
	}

	//pacman right
	public PacMan generateRight ()
	{
		PacMan right = new PacMan (pacX, pacY + 1, this.maze);
		right.setGeneration(this.generation + 1);
		right.setParent(this);
		return right;
	}


	//calculate value of f(n)= g(cost from start to current position)+ h (Manhattan distance)
	public void evaluate ()
	{

		// step cost from start to current position, plus the cost of the current position (enemy in square)?
		int g = generation + maze.getSpacecost(pacX, pacY);

		int ghostX = maze.getGhostX();
		int ghostY = maze.getGhostY();

		int h = calculateManhattanDistance(ghostX, ghostY) * 10;
		cost = g + h;

	}


	// print h,g,f
	@Override
	public String toString ()
	{

		int g = getGeneration() + maze.getSpacecost(pacX, pacY);
		int h = calculateManhattanDistance(maze.getGhostX(), maze.getGhostY()) * 10;
		return "PacMan located at : (" + pacX + "," + pacY + ")"
		+ " h: " + h
		+ " g: " + g 
		+ " f(n): " + cost;
	}

	//compare pacman states
	@Override
	public int compareTo(Object o) {
		PacMan compare = (PacMan)o;

		if(compare.getX() != this.pacX ||
				compare.getY() != this.pacY ) return 0;

		return 1;
	}

}