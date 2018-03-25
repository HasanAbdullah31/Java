/* AnimationTest.java */

import javax.swing.*;
import java.awt.*;

public class AnimationTest {
    int x=70;
    int y=70;
    public static void main(String[] args) {
	AnimationTest test=new AnimationTest();
	test.go();
    }
    public void go() {
	JFrame frame=new JFrame();
	frame.setSize(300,300);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Panel panel=new Panel();
	frame.getContentPane().add(panel);
	frame.setVisible(true);
	for (int i=0; i<130; i++) {
	    x++;
	    y++;
	    panel.repaint();
	    try {
		Thread.sleep(50);
	    } catch (Exception e) { e.printStackTrace(); }
	}
    }
    public class Panel extends JPanel {
	public void paintComponent(Graphics g) {
	    g.setColor(Color.black);
	    g.fillRect(0,0,this.getWidth(),this.getHeight());
	    g.setColor(Color.red);
	    g.fillOval(x,y,40,40);
	}
    }
}
