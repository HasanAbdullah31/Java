import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class TeemoDriver {
    int teemoDeaths=0;
    boolean revTeemo=false;
    JFrame frame=new JFrame("Kill Teemo App");
    JPanel panel=new JPanel();
    JButton button=new JButton("Kill Teemo");
    //JTextArea text=new JTextArea(10,20);
    TextArea text=new TextArea("teemo2.jpeg");
    JScrollPane scroller=new JScrollPane(text);
    JLabel label=new JLabel("Teemo Deaths: "+teemoDeaths);
    JCheckBox check=new JCheckBox("Revive Teemo");
    public static void main(String[] args) {
	TeemoDriver foo=new TeemoDriver();
	foo.go();
    }
    public class ButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    if (!revTeemo) {
		teemoDeaths++;
		if (teemoDeaths<=0)
		    text.append("You killed Teemo "+teemoDeaths+" times (WHAT'S WRONG WITH YOU!?)\n");
		else if (teemoDeaths==1)
		    text.append("You killed Teemo 1 time!\n");
		else if (teemoDeaths<=10)
		    text.append("You killed Teemo "+teemoDeaths+" times :)!\n");
		else
		    text.append("You killed Teemo "+teemoDeaths+" times :D!\n");
	    }
	    else {
		teemoDeaths--;
		text.append("You revived Teemo >.<\n");
	    }
	    label.setText("Teemo Deaths: "+teemoDeaths);
	}
    }
    public class CheckListener implements ItemListener {
	public void itemStateChanged(ItemEvent event) {
	    revTeemo=!revTeemo;
	    if (revTeemo)
		button.setText("Revive Teemo");
	    else
		button.setText("Kill Teemo");
	}
    }
    public void go() {
	Font bigFont=new Font("serif",Font.BOLD,15);
	text.setFont(bigFont);
	text.setEnabled(false);
	text.setDisabledTextColor(Color.darkGray);
	text.setText("You killed Teemo 0 times :(\n");
	text.setLineWrap(true);
	scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.
					    VERTICAL_SCROLLBAR_ALWAYS);
	scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.
					      HORIZONTAL_SCROLLBAR_NEVER);
	panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
	panel.add(scroller);
	panel.add(label);
	panel.add(check);
	check.addItemListener(new CheckListener());
	button.addActionListener(new ButtonListener());
	frame.getContentPane().add(BorderLayout.CENTER,panel);
	frame.getContentPane().add(BorderLayout.SOUTH,button);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(280,350);
	frame.setVisible(true);
    }
}
