/*This program is a stupid hack. Parts of it can be reused, but a 
 * new program that is clean and tidy and not a mess of semantic wires should 
 * be created.
 * 
 * Simulate balls bouncing on a surface. For every ball, the user specifies bouncyness
 * parameters, whatever they are, I dont remember, I think it is just one parameter
 *  actually that defines how large percent of initial drop that the ball bounces up.
 *  There should also be spaces that give the balls new speed. In order to make 
 *  a simulation I think we need to you use the paint method and repaint it often
 *  enough that the eye perceives it as fluid motion.
 *  
 *  First we write the program for a single ball. When later adding several balls,
 *  we will need to check for collisions. The ball is to a first approximation
 *  treated as a point-like particle that moves in three dimensions and has bounciness
 *  "40 %". Actually we start in 2D, and later extend it to 3D.
 *  
 *  Ok, we have to figure out why the coord. systems do not seem to overlap, 
 *  and exactly what instructions give exactly which positions.
 *  
 */

package bouncers;

import java.awt.*;
import java.awt.Graphics.*;
import javax.swing.*;


import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class MainBouncingWindow extends JFrame {

	public double bounciness;
	public double[] r;//x,y,z
	public double[] rdot;//xspeed,yspeed,zspeed
	public double g=9.81;
	public double dt;
	public Thread runner;
	public int x=100;
	public int y=100;
	public static int DELAY = 90;
	public int [] s = {100,100};

	
	public static void main (String[] args) throws java.lang.InterruptedException
	{
		final int HEIGHT = 800;
		final int WIDTH = 600;

		//Create frame
		Frame df = new Frame("Ball");
		df.setVisible(true);
		df.setSize(WIDTH, HEIGHT);

	    /*JButton b1 = new JButton("test");

		
		b1.setActionCommand("add another ball");
		b1.addActionListener(this);*/
		Ball b = new Ball ();
		//Ball b2 = new Ball ();
		df.add(b);
		//df.add(b2);
		b.run();
		//b2.run();

		df.repaint();

	}

	
	/**
	 * @param args
	 */
	public MainBouncingWindow() {
		//Empty Constructor	
	}

	

	
}

class Ball extends Canvas {
	private int x=70;
	private int y=30;
	private int x1=80;
	private int y1=200;
	private double prevx;
	private double prevy;
	private double prevx1;
	private double prevy1;
	
	private double speedX=25;//coord per s
	private double speedY=150;//coord per s
	private double g=10;
	private double dt=0.01;
	
	private double tempAddx;
	private double tempAddy;
	private double tempAddx1;
	private double tempAddy1;
	
	private double tempx=70;
	private double tempy=30;
	private double tempx1=300;
	private double tempy1=300;
	

	private double speedX1=-5;
	private double speedY1=100;
	private boolean firstTime=true;
	private boolean collisionDetected=false;
	Graphics2D big;
	BufferedImage bi = new BufferedImage(5,5,BufferedImage.TYPE_INT_RGB);
	Rectangle area;
	public Ball(){
		//empty constructor	
	}
	public void update(Graphics g){
		paint(g);
	}
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
	    if (firstTime) {
	    	Dimension dim = getSize();
	    	int w = dim.width;
	    	int h = dim.height;
	    	area = new Rectangle(dim);
	        bi = (BufferedImage) createImage(w, h);
	        big = bi.createGraphics();
	        firstTime = false;
	      }
	     
	    big.setColor(Color.white);
	    big.clearRect(0,0,area.width,area.height);
	    big.setColor(Color.blue);
	    big.fillOval(x,y,100,100);
	    big.setColor(Color.red);
	    big.fillOval(x1,y1,100,100);
	    big.setColor(Color.black);
		big.drawLine(50,500,330,500);
		big.drawLine(50,50,50,500);
		big.drawLine(330,50,330,500);
	    g2.drawImage(bi, 0, 0, this);
	}
	public void animate(){
		//Saving current position values:
		prevx=x;
		prevy=y;
		prevx1=x1;
		prevy1=y1;
		//If the first ball collides with the floor: (works well)
		if (y>=400){//<=
			if (speedY>=0){
			speedY=-((int)(speedY*0.8));
			}
			else {
				//Do nothing.
			}
		}
		//First ball colliding with right wall (works well).
		if (x>=230){
			if(speedX>=0){//Necessary so that it doesn't get stuck at a wall 
				//going back and forth because the direction change is not enough to stop it 
				//from changing direction again.
			speedX=-((int)(speedX*0.8));
			}
		}
		//First ball colliding with left wall.
		if (x<=50){
			if(speedX<=0){
			speedX=-((int)(speedX*0.8));
			}
		}
		//Second ball colliding with floor:
		if (y1>=400){//<=
			if (speedY1>=0){
			speedY1=-((int)(speedY1*0.8));
			}
			else {
				//Do nothing.
			}
		}
		//second ball colliding with right wall:
		if (x1>=230){
			if(speedX1>=0){
				speedX1=-((int)(speedX1*0.8));
			}
			else {
				//Do nothing.
			}
		}
		//Second ball colliding with left wall:
		if (x1<=50){
			if(speedX1<=0){
			speedX1=-((int)(speedX1*0.8));
			}
		}
		
		if (((((x1-x)<=100))&&((x1-x)>=-100))&&(((y1-y)<=100)&&((y1-y)>=-100))){
		System.out.println("Collision");
		if (!collisionDetected){
			double temp1=0;
			double temp2=0;
			//the same condition as with walls.
			temp1=speedX1;
			speedX1=speedX;
			speedX=temp1;
		
			temp2=speedY1;
			speedY1=speedY;
			speedY=temp2;
			
			if (speedY*speedY1>=0)
			{
				speedY=-speedY1;
			}
			if (speedX*speedX1>=0)
			{
				speedX=-speedX1;
			}
			tempx=prevx;
			tempy=prevy;
			tempx1=prevx1;
			tempy1=prevy1;
			x=(int)tempx;
			y=(int)tempy;
			x1=(int)tempx1;
			y1=(int)tempy1;
			collisionDetected=true;
			}	
		}
		else 
		{
			collisionDetected=false;
		}
		
		//x=x+(int)(speedX*dt);
		//performing the timestep.
		tempAddx=speedX*dt;
		tempx += tempAddx;
		x=(int)tempx;
		//
		speedY+=g*dt;
		System.out.println("this is speedY:"+speedY);
		tempy += speedY*dt;//approximation
		y=(int)tempy;	
		//
		tempx1+=speedX1;
		x1=(int)tempx1;
		//
		speedY1+=g*dt;
		System.out.println("this is speedY1"+speedY1);
		tempy1+=speedY1*dt;
		y1=(int)tempy1;
		//
		}
		
	
	

	
	public void run(){
	int i = 0;
	
	while (i<2000){
	animate();	
	try {
		Thread.sleep(50);
	}
	catch(InterruptedException e) {}
	i++;
	repaint();
	}
	}



}



class Room extends Canvas {
	private int floorLength=800;
	private int wallLength=600;
	
	public Room(){
		//Empty Constructor
	}
	public void paint (Graphics g){
		g.setColor(Color.black);
		g.drawLine(50,400,400,400);
		//g.drawLine(arg0, arg1, arg2, arg3)
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
