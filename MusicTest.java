/* MusicTest.java */

import javax.sound.midi.*;   //ControllerEventListener
import java.awt.*;   //Graphics, Graphics2D, Color
import javax.swing.*;   //JFrame, JPanel

public class MusicTest {
    private JFrame frame;
    private Panel panel;
    private Sequencer player;
    private Sequence CD;
    private Track track;
    public MusicTest() {
	try {
	    frame=new JFrame("My MusicTest App");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    panel=new Panel();
	    frame.setContentPane(panel);
	    frame.setBounds(30,30,300,300);
	    frame.setVisible(false);
	    player=MidiSystem.getSequencer();
	    CD=new Sequence(Sequence.PPQ,4);
	    track=CD.createTrack();
	    player.open();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	    System.exit(0);
	}
    }
    public static void main(String[] args) {
	if (args.length!=1) {
	    System.out.println("Use: java MusicTest <instrument>");
	    return;
	}
	int instrument=Integer.parseInt(args[0]);
	if (instrument<0 || instrument>127) {
	    System.out.println("Instrument must be in range [0,127]");
	    return;
	}
	MusicTest test=new MusicTest();
	test.play(instrument);
    }
    public MidiEvent makeEvent(int type, int channel, int note,
			       int velocity, int beat) {
	//types: 192=CHANGE_INSTRUMENT [instrument in note], 144=ON, 128=OFF
	//176=EVENT_TYPE_IS_CONTROLLEREVENT [event number in note]
	MidiEvent event=null;
	try {
	    ShortMessage message;
	    message=new ShortMessage(type,channel,note,velocity);
	    event=new MidiEvent(message,beat);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	} finally {
	    return event;
	}
    }
    public void play(int instrument) {
	try {
	    player.addControllerEventListener(panel,new int[] {127});
	    track.add(makeEvent(192,1,instrument,0,0));
	    int r=0;
	    for (int i=0; i<60; i+=4) {
		r=(int)((Math.random()*50)+1);
	        track.add(makeEvent(144,1,r,100,i));
	        track.add(makeEvent(176,1,127,0,i));
	        track.add(makeEvent(128,1,r,100,i+2));
	    }
	    player.setTempoInBPM(220);
	    player.setSequence(CD);
	    frame.setVisible(true);
	    player.start();
	} catch (Exception e) { System.out.println(e.getMessage()); }
    }
    public class Panel extends JPanel implements ControllerEventListener {
	private boolean soundChanged=false;
	public void paintComponent(Graphics g) {
	    if (soundChanged) {
		Graphics2D G=(Graphics2D)g;
		int red=(int)(Math.random()*255);
		int green=(int)(Math.random()*255);
		int blue=(int)(Math.random()*255);
		G.setColor(new Color(red,green,blue));
		int height=(int)((Math.random()*120)+10);
		int width=(int)((Math.random()*120)+10);
		int x=(int)((Math.random()*40)+10);
		int y=(int)((Math.random()*40)+10);
		G.fillRect(x,y,width,height);
		soundChanged=false;
	    }
	}
	public void controlChange(ShortMessage event) {
	    soundChanged=true;
	    repaint();
	}
    }
}
