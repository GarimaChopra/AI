/**
 * Programming Project 2
 * @author: Garima Chopra
 * @date: October 4 ,2021
 * @class:Maze Class
 * @brief:This class is used to play pacman game! 
 * 
 */

//Import Statements
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.*;


public class Maze extends JFrame
{
	//*** can keep track of visited cell positions here
	static boolean [][] visited;

	//*** the maze itself
	//***    0 means Power Pellet
	//***    1 means wall
	//***    2 means Stripes
	//***    3 means Pirate
	static int [][] mazePlan =
		{
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,0,1,0,0,3,0,0,0,0,0,0,0,0,0,0,1,0,1},
				{1,0,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,0,1},
				{1,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1},
				{1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,3,0,1},
				{1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,1},
				{1,1,1,0,0,0,0,1,0,0,0,1,0,0,0,0,1,1,1},
				{1,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,1},
				{1,2,1,1,1,0,0,0,0,3,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,1,0,0,1,1,1,1,1,0,0,0,0,0,0,1},
				{1,0,0,0,1,0,0,1,0,0,0,1,0,0,0,0,0,0,1},
				{1,0,0,0,1,0,0,1,0,0,0,1,0,3,1,1,1,0,1},
				{1,0,0,0,1,0,0,1,1,1,1,1,0,0,1,0,0,3,1},
				{1,1,1,0,1,0,0,0,0,0,0,0,0,0,1,0,1,1,1},
				{1,0,0,3,1,0,0,1,1,1,1,1,2,0,1,0,0,0,1},
				{1,0,0,0,1,0,0,1,0,0,0,1,0,0,1,0,0,0,1},
				{1,0,1,1,1,3,0,1,0,0,0,1,0,0,1,0,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		};

	//*** set up the maze wall positions and set all visited states to false
	static MazePanel mp = new MazePanel(mazePlan, visited);

	//*** set up and display main characters' initial maze positions
	static int  ghostX = 15, ghostY = 8;              //** Ghost

	static int  pacmanX = 1, pacmanY = 1;             //*** Pacman

	//*** each maze cell is 37 pixels long and wide
	static int panelWidth = 37;

	//*** a simple random number generator for random search
	static int randomInt(int n) {return (int)(n * Math.random());}


	//*** check to see if space is wall 
	static public boolean isWall (int pacX, int pacY)
	{
		return mazePlan[pacX][pacY] == 1;
	}



	//******************************************************
	//*** main constructor
	//******************************************************
	public Maze()
	{


		//*** set up the display panel
		getContentPane().setLayout(new GridLayout());
		setSize(mazePlan[0].length*panelWidth, mazePlan[0].length*panelWidth);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(mp);
	}

	// get ghost x coordinate
	public int getGhostX ()
	{
		return ghostX;
	}

	// get ghost y coordinate
	public int getGhostY ()
	{
		return ghostY;
	}

	// set ghost x coordinate
	public void setGhostX (int x)
	{
		ghostX = x;
	}

	// set ghost y coordinate
	public void setGhostY (int y)
	{
		ghostY = y;
	}



	//******************************************************
	//*** a delay routine
	//******************************************************
	public void wait(int milliseconds)
	{
		try
		{Thread.sleep(milliseconds);}
		catch (Exception e)
		{}
	}


	//******************************************************
	//*** move Pacman to position (i, j)
	//******************************************************

	public void movePacman(int i, int j)
	{
		mp.setupChar(i, j, "pacman.gif");
	}

	//******************************************************
	//*** move Ghost to position (i, j)
	//******************************************************


	public void moveGhost(int i, int j)
	{
		mp.setupChar(i, j, "ghost.gif");
	}

	//******************************************************
	//*** remove Pacman from position (i, j)
	//******************************************************
	public void removePacman(int i, int j)
	{
		mp.removeChar(i, j);
	}

	//******************************************************
	//*** remove Ghost from position (i, j)
	//******************************************************

	public void removeGhost(int i, int j)
	{
		mp.removeChar(i, j);
		mp.setupChar(i, j, "pellet.gif");
	}

	//******************************************************
	//*** is position (i,j) a power-pellet cell?
	//******************************************************
	public boolean openSpace(int i, int j)
	{
		return (mazePlan[i][j] == 0);
	}


	//******************************************************
	//*** can move down from (x,y)?
	//******************************************************
	public boolean canMoveDown (int x, int y) 
	{
		int downX = x + 1;
		if(downX >= mazePlan.length || downX < 0) return false;
		return !isWall(downX, y);
	}


	//******************************************************
	//*** can move up from (x,y)?
	//******************************************************
	public boolean canMoveUp(int x, int y) 
	{
		int upX = x - 1;
		if(upX < 0 || upX >= mazePlan.length) return false;
		return !isWall(upX, y);
	}

	//******************************************************
	//*** can move left from (x,y)?
	//******************************************************

	public boolean canMoveLeft(int x, int y)  
	{
		int leftY = y - 1;
		if(leftY < 0 || leftY >= mazePlan[0].length) return false;
		return !isWall(x, leftY);
	}

	//******************************************************
	//*** can move right from (x,y)?
	//******************************************************

	public boolean canMoveRight (int x, int y)  
	{
		int RightY = y + 1;
		if(RightY >= mazePlan[0].length || RightY < 0) return false;
		return !isWall(x, RightY);
	}


	//******************************************************
	//*** what is cost in position (x,y)? 
	// 0 for pellets, 20000 for stripes, 3000 for pirates
	//******************************************************


	public int getSpacecost(int x, int y)
	{

		int spaceCost = this.mazePlan[x][y];	
		if(spaceCost == 0) return 0;

		// return stripe cost
		if(spaceCost == 2) return 1000;

		// return pirate cost
		return 2000;
	}



	//******************************************************
	//***   MODIFY HERE --  MODIFY HERE  --  MODIFY HERE
	//******************************************************

	//Displays the path from pacman to ghost.
	public static void runMovingTarget(Maze mz, 
			int initialXPac, 
			int initialYPac,
			int initialXGhost,
			int initialYGhost)
	{


		mz.ghostX = initialXGhost;
		mz.ghostY= initialYGhost;
		mz.pacmanX = initialXPac;
		mz.pacmanY = initialYPac;

		PacMan start = new PacMan(pacmanX, pacmanY, mz);

		ArrayList[] paths = AStarMovingTarget.search(start, mz);

		ArrayList<PacMan> path = paths[0];
		ArrayList<Integer[]> ghostPath = paths[1];


		// need to sync up end of ghost path with pacman path
		int ghostIndex = ghostPath.size() - path.size();

		Integer[] coordinates = ghostPath.get(ghostIndex);
		PacMan state;


		//*** display the ghost
		mp.setupChar(coordinates[0], coordinates[1], "ghost.gif");

		//*** display Pacman
		mp.setupChar(pacmanX, pacmanY, "pacman.gif");


		int gbx;
		int gby;
		int pbx; 
		int pby;

		System.out.println("Moving Target Path:");

		for(int i =0; i< path.size(); i++)
		{

			coordinates = ghostPath.get(ghostIndex + i);

			state = path.get(i);

			System.out.println(state);

			gbx = coordinates[0];
			gby = coordinates[1];
			pbx = state.getX(); 
			pby = state.getY();

			//remove pacman and ghost when position are same.
			if(pbx == gbx && pby == gby)
			{

				mz.moveGhost(gbx, gby);
				mz.movePacman(pbx, pby);

				mz.wait(500);

				mz.removeGhost(gbx, gby);
				break;
			}

			mz.moveGhost(gbx, gby);
			mz.movePacman(pbx, pby);
			mz.wait(250);
			mz.removeGhost(gbx, gby);
			mz.removePacman(pbx, pby);
		}
		System.out.println("\n"); 
	}


	public static void main(String [] args)
	{

		//*** create a new frame and make it visible
		Maze mz = new Maze();
		mz.setVisible(true);
		Maze.runMovingTarget(mz, pacmanX, pacmanY, ghostX, ghostY);

	} // main

} // Maze