package musicalballs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.*;
import javax.swing.*;



public class BouncingBalls extends JPanel {
	static double a=100;
	static double b=100;
	static double c=50;
	static double d=50;
	public static Shape circle = new Ellipse2D.Double(a, b, c, d);
	public static Rectangle area;
	BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
	Graphics2D big;
	boolean firstTime = true;
	
	public BouncingBalls(){
			setBackground(Color.white);
	}
	public void update(Graphics g) {
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
	        big.setStroke(new BasicStroke(8.0f));
	        big.draw(BouncingBalls.circle);
	        firstTime = false;
	      }
	    a+=20;
	    b+=20;
	    
	    big.setColor(Color.white);
	    big.clearRect(0,0,area.width,area.height);
	    big.setColor(Color.blue);
	    big.draw(BouncingBalls.circle);
	    //g2.drawImage(bi, 0, 0, this);
	    
		/*g.setColor(Color.white);
		g.fillRect(0, 0,600,800);
		g.setColor(Color.blue);
		g.fillOval(x,y,100,100);
		//Drawing walls and floor:
		g.setColor(Color.black);
		g.drawLine(50,500,330,500);
		g.drawLine(50,50,50,500);
		g.drawLine(330,50,330,500);
		//Drawing second ball:
		g.setColor(Color.red);
		g.fillOval(x1,y1,100,100);*/
	}
	public void run(){
		int i = 0;

		while (i<1000){
		a+=10;
		b+=10;
		try {
			Thread.sleep(50);
		}
		catch(InterruptedException e) {}
		i++;
		repaint();
		}
		}

	public static void main (String[] args) throws java.lang.InterruptedException
	{
		final int HEIGHT = 800;
		final int WIDTH = 600;
		//Create frame
		Frame df = new Frame("Ball");
		df.setVisible(true);
		df.setSize(WIDTH, HEIGHT);
		//Room r = new Room ();
		//df.add(r);
		BouncingBalls b = new BouncingBalls ();
		df.add(b);
		b.run();
		df.repaint();
		
	}
}
/*
class Ball extends Canvas {
	private int x1 = 300;
	private int y1 = 0;
	private int x = 60;
	private int y = 0;
	private double tempx,tempy,tempx1,tempy1;//the actual position of the ball.
	private double g=10;
	private double dt=0.01;	
	private double speedX1,speedX,speedY1,speedY;
	BufferedImage bi = new BufferedImage(5,5,BufferedImage.TYPE_INT_RGB);
	Graphics2D big;	

	public boolean firstTime=true;
	
	Rectangle area;
	public Ball(){
		
	}
	
	public void paint(Graphics g){
		//Clear screen:
		Graphics2D g2 = (Graphics2D) g;
	    if (firstTime) {
	    	Dimension dim = getSize();
	    	int w = dim.width;
	    	int h = dim.height;
	    	area = new Rectangle(dim);
	        bi = (BufferedImage) createImage(w, h);
	        big = bi.createGraphics();
	        big.setStroke(new BasicStroke(8.0f));
	        big.draw(BouncingBalls.circle);
	        firstTime = false;
	      }
	    BouncingBalls.a+=20;
	    BouncingBalls.b+=20;
	    
	    big.setColor(Color.white);
	    big.clearRect(0,0,area.width,area.height);
	    big.setColor(Color.blue);
	    big.draw(BouncingBalls.circle);
	    g2.drawImage(bi, 0, 0, this);
	    
		/*g.setColor(Color.white);
		g.fillRect(0, 0,600,800);
		g.setColor(Color.blue);
		g.fillOval(x,y,100,100);
		//Drawing walls and floor:
		g.setColor(Color.black);
		g.drawLine(50,500,330,500);
		g.drawLine(50,50,50,500);
		g.drawLine(330,50,330,500);
		//Drawing second ball:
		g.setColor(Color.red);
		g.fillOval(x1,y1,100,100);
	}
	public void update(Graphics g){
		paint(g);
	}
	 public void BufferedDraw() {
		    setBackground(Color.white);
		  }
public void animate(){
	//First ball colliding with floor:
	if (y>=400){//<=
		speedY=-(speedY*0.8);
	}
	//First ball colliding with right wall:
	if (x>=230){
		if(speedX>=0){//Necessary so that it doesn't get stuck at a wall 
			//going back and forth because the direction change is not enough to stop it 
			//from changing direction again.
		speedX=-(speedX*0.8);
		}
	}
	//First ball colliding with left wall:
	if (x<=50){
		if(speedX<=0){
		speedX=-(speedX*0.8);
		}
	}
	//Second ball colliding with floor:
	if (y1>=400){//<=
		speedY1=-(speedY1*0.8);
	}
	//second ball colliding with right wall:
	if (x1>=230){
		if(speedX1>=0){//Necessary so that it doesnt get stuck at a wall 
			//going back and forth because the direction change is not enough to stop it 
			//from changing direction again.
		speedX1=-(speedX1*0.8);
		}
	}
	//Second ball colliding with left wall:
	if (x1<=50){
		if(speedX1<=0){
		speedX1=-(speedX1*0.8);
		}
	}
	
	if (((((x1-x)<=100))&&((x1-x)>=-100))&&(((y1-y)<=100)&&((y1-y)>=-100))){
	System.out.println("Collision");	
	/*Figure out what happens physically. Avoid overlapping circles.

	}
	tempx += speedX*dt;
	x=(int)tempx;
	//got this far
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

while (i<1000){
animate();	
try {
	Thread.sleep(50);
}
catch(InterruptedException e) {}
i++;
repaint();
}
}

}*/
