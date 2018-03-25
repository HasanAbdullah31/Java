/* Panel.java */

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public void paintComponent(Graphics g) {
	Graphics2D G=(Graphics2D)g;
	G.setColor(Color.black);
	G.fillRect(0,0,this.getWidth(),this.getHeight());
	int red=(int)(Math.random()*255);
	int green=(int)(Math.random()*255);
	int blue=(int)(Math.random()*255);
	Color start=new Color(red,green,blue);
	red=(int)(Math.random()*255);
	green=(int)(Math.random()*255);
	blue=(int)(Math.random()*255);
	Color end=new Color(red,green,blue);
	GradientPaint grad=new GradientPaint(40,40,start,80,80,end);
	G.setPaint(grad);
	G.fillOval(10,10,100,100);
    }
}
