package sample;

import flockbase.*;

import java.util.*;
import java.lang.Math;
public class Bird2017028 extends Bird{
	private static double neighborhoodRadius = 200.00;
	private static double collisionRadius = 30.0;
	private String name;
	private int collisionNeighbours = 0;
	private int resultantX = 0;
	private int resultantY = 0;
	boolean amLeader = false;
	private ArrayList<Bird> neighbours;
	private Position nextPos;
	public Bird2017028()
	{
	
		super();
		neighbours = new ArrayList<Bird>();
		nextPos = new Position(0,0);

	}
	public Bird2017028(String name)
	{
		super();
		neighbours = new ArrayList<Bird>();
		nextPos = new Position(0,0);
		this.name = new String(name);		
	}
	public ArrayList<Bird> getNeighbours()
	{
		return this.neighbours;
	}

	@Override
	public String getName()
	{
		if(name!=null)
		return this.name;
		return "028";
	}

	public String toString(){
		return name+" at "+this.getPos()+" leader status "+amLeader;
	}

	public double distance(Position b1 ,Position b2)
	{
		return Math.sqrt((b1.getX()-b2.getX())*(b1.getX()-b2.getX())+(b1.getY()-b2.getY())*(b1.getY()-b2.getY()));
	}

	public void collisionCheck()
	{
		collisionNeighbours = 0;
		resultantX = 0;
		resultantY = 0;
	
		for(Bird bird: getFlock().getBirds())
		{

					/*
						if bird is a possible candiadte for collision then add the vector pointing from "this" bird to
						bird to the resultant vector
					*/
					if(distance(this.getPos(),bird.getPos())<=collisionRadius)
					{	
						resultantX += bird.getPos().getX()-this.getPos().getX();
						resultantY += bird.getPos().getY()-this.getPos().getY();
						collisionNeighbours += 1;
					}	
		}	

	}
	public void computeTarget()
	{
		Flock f = getFlock();
		int index = f.getBirds().indexOf(this);
		/*
			uncomment the below line to get a straight line flock simulation
		*/
		// Bird successor = f.getBirds().get(index-1);
		Bird successor = f.getLeader();
		setTarget(successor.getPos().getX(),successor.getPos().getY());
	}
	/*
		helper function to set the nextpos used in update pos
	*/
	public void setNextPos()
	{
		double unitX,unitY;
		double velX,velY;
		if(collisionNeighbours>1)
		{

			velX = (-1*resultantX);
			velY = (-1*resultantY);
		}
		else
		{
			velX = getTarget().getX()-getPos().getX();
			velY = getTarget().getY()-getPos().getY();
		}
		unitX = (velX)/(Math.sqrt(velX*velX + velY*velY )+1);
		unitY = (velY)/(Math.sqrt(velX*velX + velY*velY ))+1;
		if(getPos().getY()+(int)(unitY*getMaxSpeed())<0)
		{
			velY = -1*velY;
		}
		if(getPos().getX()+(int)(unitX*getMaxSpeed())<0)
		{
			velX = -1*velX;
		}
		unitX = (velX)/(Math.sqrt(velX*velX + velY*velY ));
		unitY = (velY)/(Math.sqrt(velX*velX + velY*velY ));
		if(collisionNeighbours>1)
		{
			nextPos.setPos(getPos().getX()+(int)(unitX*getMaxSpeed()/2),
			getPos().getY()+(int)(unitY*getMaxSpeed()/2));
		}		
		else
		{
			nextPos.setPos(getPos().getX()+(int)(unitX*getMaxSpeed()),
			getPos().getY()+(int)(unitY*getMaxSpeed()));
		}

		/*
		*/
	}
	
	public void becomeLeader()
	{
		this.amLeader = true;
	}
	public void retireLead()
	{
		this.amLeader = false;
	}
	public void updatePos()
	{
		if(!amLeader)
			computeTarget();
		collisionCheck();
		setNextPos();
		this.setPos(nextPos.getX(),nextPos.getY());
	}
}