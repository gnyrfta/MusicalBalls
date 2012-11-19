/*Simulate balls bouncing on a surface. For every ball, the user specifies bouncyness
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
		Room r = new Room ();
		df.add(r);
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

	

	public void calculatePosition(double[] r, double[] rdot)
	{
	r[0]=r[0]+rdot[0]*dt;
	r[1]=r[1]+rdot[1]*dt;
	r[2]=r[2]+rdot[2]*dt;//eulers stepmethod. if not acurate enough we can change.
	rdot[2]=rdot[2]-rdot[2]*g*dt;
		if(r[2]==0){
			rdot[2]=-rdot[2]*bounciness;
		}
	}
	//function to draw ball in 3 d.
	
	//paint function: if r[2]==0, give sound through chuck.
	//the balls should be clickable, so you can bounce them up and down, or delete them.
	//should be used by several computers at the same time, as several different
	//instruments.	
}

class Ball extends Canvas {
	private int x=70;
	private int y=30;
	private int speedX=10;//coord per s
	private int speedY=60;//coord per s
	private int g=10;
	private double dt=0.1;
	
	private int x1=100;
	private int y1=200;
	private int speedX1=-12;
	private int speedY1=50;
	
	public Ball(){
		//empty constructor	
	}
	public void paint(Graphics g){
		//reference lines
		g.setColor(Color.cyan);
		g.drawLine(0,0,100,0);
		g.drawLine(100, 0, 100, 100);
		g.drawLine(0, 100, 100, 100);
		g.drawLine(0, 0, 0, 100);
		g.setColor(Color.pink);
		//reference circle
		g.fillOval(0, 0, 100, 100);
		//defining circle area: 
		//if x1 is between x and x+100
		//and y1 is between y and y+100 
		//then the circles intersect.
		//real program code
		g.setColor(Color.blue);
		g.fillOval(x,y,100,100);
		g.setColor(Color.black);
		g.drawLine(50,500,330,500);
		g.drawLine(50,50,50,500);
		g.drawLine(330,50,330,500);
		g.setColor(Color.red);
		g.fillOval(x1,y1,100,100);
	}
	public void animate(){
		//x=x+(int)(speedX*dt);
		x=x+speedX;
		speedY=speedY+(int)(g*dt);
		System.out.println("speed in y-direction:"+speedY);
		System.out.println("speed in x-direction:"+speedX);
		System.out.println("y is"+y);
		System.out.println("x is"+x);
		if (y>=400){//<=
			speedY=-((int)(speedY*0.8));
		}
	
		if (x>=230){
			if(speedX>=0){//Necessary so that it doesn't get stuck at a wall 
				//going back and forth because the direction change is not enough to stop it 
				//from changing direction again.
			System.out.println("changing direction horizontally");
			speedX=-((int)(speedX*0.8));
			}
		}
		if (x<=60){
			if(speedX<=0){
			speedX=-((int)(speedX*0.8));
			}
		}
		y=y+(int)(speedY*dt);
		/*x1=x1+speedX;
		y1=y1+speedY;*/
		
		x1=x1+speedX;
		speedY1=speedY1+(int)(g*dt);
		System.out.println("speed in y1-direction:"+speedY1);
		System.out.println("speed in x1-direction:"+speedX1);
		System.out.println("y1 is"+y1);
		System.out.println("x1 is"+x1);
		if (y1>=400){//<=
			speedY1=-((int)(speedY1*0.8));
		}
	
		if (x1>=230){
			if(speedX1>=0){//Necessary so that it doesnt get stuck at a wall 
				//going back and forth because the direction change is not enough to stop it 
				//from changing direction again.
			System.out.println("changing direction horizontally");
			speedX1=-((int)(speedX1*0.8));
			}
		}
		if (x1<=60){
			if(speedX1<=0){
			speedX1=-((int)(speedX1*0.8));
			}
		}
		y1=y1+(int)(speedY1*dt);
		
		if (((((x1-x)<=100))&&((x1-x)>=-100))&&(((y1-y)<=100)&&((y1-y)>=-100))){
		System.out.println("Collision");
		int temp1=0;
		int temp2=0;
		
		temp1=speedX1;
		speedX1=-speedX;
		speedX=-temp1;
		
		temp2=speedY1;
		speedY1=-speedY;
		speedY=-temp2;
		}
		repaint();
	}
	
	public void collision(int x1, int y1, int xdot1, int ydot1, int xdot2, int ydot2){
		/*v1+v2 = v1+v2 after collision and before collision, equal mass.
		 * (v1^2)/2+y*g + v2^2/2 + y2*g is also the same before and after the collision.
		 * Elastic collision: the same speed, but direction as the other ball had
		 * So direction changes, but speed remains the same.
		 * 
		 */
	}
	
	public void run(){
	int i = 0;
	
	while (i<1000){
	animate();	
	try {
		Thread.sleep(70);
	}
	catch(InterruptedException e) {}
	i++;
	}
	}

	public static void main(String[] args) {
		

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
