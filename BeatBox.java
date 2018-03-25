/* BeatBox.java */

import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;

public class BeatBox implements MetaEventListener {
    JPanel mainPanel;
    ArrayList checkboxList;
    int bpm=120;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame theFrame;
    String[] instrumentNames={"Bass Drum","Closed Hi-Hat","Open Hi-Hat",
			      "Acoustic Snare","Crash Cymbal","Hand Clap",
			      "High Tom","Hi Bongo","Maracas","Whistle",
			      "Low Conga","Cowbell","Vibraslap","Low-mid Tom",
			      "High Agogo","Open Hi Conga"};
    int[] instruments={35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
    final int SIZE=instrumentNames.length;
    
    public static void main(String[] args) {
	new BeatBox().buildGUI();
    }
    public void buildGUI() {
	theFrame=new JFrame("Cyber BeatBox");
	BorderLayout layout=new BorderLayout();
	JPanel background=new JPanel(layout);
	background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	
	checkboxList=new ArrayList();
	Box buttonBox=new Box(BoxLayout.Y_AXIS);
	
	JButton start=new JButton("Start");
	start.addActionListener(new StartListener());
	buttonBox.add(start);
	
	JButton stop=new JButton("Stop");
	stop.addActionListener(new StopListener());
	buttonBox.add(stop);
	
	JButton upTempo=new JButton("Tempo Up");
        upTempo.addActionListener(new UpTempoListener());
	buttonBox.add(upTempo);
	
	JButton downTempo=new JButton("Tempo Down");
        downTempo.addActionListener(new DownTempoListener());
	buttonBox.add(downTempo);
	
	Box nameBox=new Box(BoxLayout.Y_AXIS);
	for (int i=0; i<SIZE; i++) {
	    nameBox.add(new Label(instrumentNames[i]));
	}
	
	background.add(BorderLayout.WEST,nameBox);
	background.add(BorderLayout.EAST,buttonBox);
	theFrame.getContentPane().add(background);
	
	GridLayout grid=new GridLayout(SIZE,SIZE);
	grid.setVgap(1);
	grid.setHgap(2);
	mainPanel=new JPanel(grid);
	background.add(BorderLayout.CENTER,mainPanel);
	
	final int GRIDSIZE=SIZE*SIZE;
	JCheckBox c;
	for (int i=0; i<GRIDSIZE; i++) {
	    c=new JCheckBox();
	    c.setSelected(false);
	    checkboxList.add(c);
	    mainPanel.add(c);
	}
	
	setUpMIDI();
	theFrame.setBounds(50,50,300,300);
	theFrame.pack();
	theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	theFrame.setVisible(true);
    }
    public void setUpMIDI() {
        try {
	    sequencer=MidiSystem.getSequencer();
	    sequencer.open();
	    sequencer.addMetaEventListener(this);
	    sequence=new Sequence(Sequence.PPQ,4);
	    track=sequence.createTrack();
	    sequencer.setTempoInBPM(bpm);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	    System.exit(0);
	}
    }
    public void buildTrackAndStart() {
	int[] trackList=null;
	sequence.deleteTrack(track);
	track=sequence.createTrack();
	int key;
	JCheckBox jc;
	for (int i=0; i<SIZE; i++) {
	    trackList=new int[SIZE];
	    key=instruments[i];
	    for (int j=0; j<SIZE; j++) {
		jc=(JCheckBox)checkboxList.get(j+(16*i));
		if (jc.isSelected()) {
		    trackList[j]=key;
		} else {
		    trackList[j]=0;
		}
	    }
	    makeTracks(trackList);
	}
	track.add(makeEvent(192,9,1,0,15));
	try {
	    sequencer.setSequence(sequence);
	    sequencer.start();
	    sequencer.setTempoInBPM(bpm);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
    public class StartListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    buildTrackAndStart();
	}
    }
    public class StopListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    sequencer.stop();
	}
    }
    public class UpTempoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    bpm+=3;
	}
    }
    public class DownTempoListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    bpm-=3;
	}
    }
    public void makeTracks(int[] trackList) {
	int key;
	for (int i=0; i<SIZE; i++) {
	    key=trackList[i];
	    if (key!=0) {
		track.add(makeEvent(144,9,key,100,i));
		track.add(makeEvent(128,9,key,100,i+1));
	    }
	}
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
    public void meta(MetaMessage msg) {
	if (msg.getType()==47) {
	    sequencer.start();
	    sequencer.setTempoInBPM(bpm);
	}
    }
}
