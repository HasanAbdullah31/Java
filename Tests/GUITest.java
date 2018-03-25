/* GUITest.java */

import java.awt.event.*;   //ActionListener, ActionEvent
import java.awt.*;   //BorderLayout
import javax.swing.*;   //JButton, JLabel, JFrame

public class GUITest {
    int counter;
    JFrame frame;
    Panel panel;
    JLabel label;
    JButton colorButton;
    JButton labelButton;
    public GUITest() {
	counter=0;
	frame=new JFrame();
	frame.setSize(300,300);
	frame.setVisible(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel=new Panel();
	label=new JLabel("Counter=0");
	colorButton=new JButton("Change color");
	colorButton.addActionListener(new ColorListener());
	labelButton=new JButton("Counter++");
	labelButton.addActionListener(new LabelListener());
    }
    public static void main(String[] args) {
	GUITest test=new GUITest();
	test.start();
    }
    public void start() {
	frame.setVisible(true);
	frame.getContentPane().add(BorderLayout.CENTER,panel);
	frame.getContentPane().add(BorderLayout.WEST,label);
	frame.getContentPane().add(BorderLayout.SOUTH,colorButton);
	frame.getContentPane().add(BorderLayout.EAST,labelButton);
    }
    public class ColorListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    frame.repaint();
	    colorButton.setText("Color changed");
        }
    }
    public class LabelListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    counter++;
	    label.setText("Counter="+counter);
        }
    }
}
