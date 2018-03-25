import java.awt.*;   //TexturePaint, Graphics, Graphics2D, Rectangle
import java.awt.image.*;   //BufferedImage
import javax.imageio.*;   //ImageIO
import java.io.*;   //File
import javax.swing.*;   //JTextArea

public class TextArea extends JTextArea {
    BufferedImage bufferedImage;
    TexturePaint texturePaint;
    public TextArea(String s) {
	super();
	try {
	    File file=new File(s);
	    bufferedImage=ImageIO.read(file);
	    Rectangle rect=new Rectangle(0,0,bufferedImage.getWidth(null),
					 bufferedImage.getHeight(null));
	    texturePaint=new TexturePaint(bufferedImage,rect);
	    setOpaque(false);
	} catch (Exception e) { e.printStackTrace(); }
    }
    public void paintComponent(Graphics g) {
	Graphics2D g2=(Graphics2D)g;
	g2.setPaint(texturePaint);
	g.fillRect(0,0,this.getWidth(),this.getHeight());
	super.paintComponent(g);
    }
}
