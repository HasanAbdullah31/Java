/* DotComBust.java */

import java.util.*;

public class DotComBust {
    private GameHelper helper=new GameHelper();
    private ArrayList dotComsList=new ArrayList();
    private int numOfGuesses=0;

    private void setUpGame() {
	DotCom one=new DotCom();
	one.setName("Pets.com");
	DotCom two=new DotCom();
	two.setName("eToys.com");
	DotCom three=new DotCom();
	three.setName("Go2.com");
	dotComsList.add(one);
	dotComsList.add(two);
	dotComsList.add(three);
	System.out.println("Your goal is to sink three dot coms.");
	System.out.println("Pets.com, eToys.com, Go2.com");
	System.out.println("Try to sink them all in the fewest # of guesses");
        for (int i=0; i<dotComsList.size(); i++) {
	    ArrayList newLocation=helper.placeDotCom(3);
	    DotCom dotComToSet=(DotCom)dotComsList.get(i);
	    dotComToSet.setLocationCells(newLocation);
        }
    }
    private void startPlaying() {
	while (!dotComsList.isEmpty()) {
	    String userGuess=helper.getUserInput("Enter a guess");
	    checkUserGuess(userGuess);
	}
	finishGame();
    }
    private void checkUserGuess(String userGuess) {
	numOfGuesses++;
	String result="miss";
	for (int i=0; i<dotComsList.size(); i++) {
	    DotCom dotComToTest=(DotCom)dotComsList.get(i);
	    result=dotComToTest.checkYourself(userGuess);
	    if (result.equals("hit")) {
		break;
	    }
	    if (result.equals("kill")) {
		dotComsList.remove(i);
		break;
	    }
	}
	System.out.println(result);
    }
    private void finishGame() {
	System.out.println("All Dot Coms dead! Your stock is now worthless");
	if (numOfGuesses<=18) {
	    System.out.println("It only took you "+numOfGuesses+" guesses.");
	    System.out.println(" You got out before your options sank");
	} else {
	    System.out.println("Took long enough. "+numOfGuesses+" guesses.");
	    System.out.println("Fish are dancing with your options");
	}
    }
    public static void main(String[] args) {
        DotComBust game=new DotComBust();
	game.setUpGame();
	game.startPlaying();
    }
}
