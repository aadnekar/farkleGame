package farkleGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DiceRoller {
	// Should be a number of dice to be thrown at a turn. from 1 to 6 dice.
	private Collection<Dice> diceList = new ArrayList<>();
	
	public DiceRoller(int numberOfDice) {
		for(int i=0; i<numberOfDice; i++) {
			diceList.add(new Dice());
		}
	}
	
	// returns a collection of the a diceRoll (length equal to the number of dice rolled.
	public Collection<Integer> diceScore() {
		Collection<Integer> result = new ArrayList<>();
		diceList.stream()
			.forEach(p -> result.add(p.roll()));
		return result;
	}
	
	/*
	 	Dice Combinations | Score
	 		Each 1	| 100
	 		Each 5	| 50
	 		Three 1s| 1000
	 		Three 2s| 200
	 		Three 3s| 300
	 		Three 4s| 400
	 		Three 5s| 500
	 		Three 6s| 600
	 */
	
	// The ScoreBoard should calculate the actual score to get: since the player decides how they want to play.
	// Return the a list of occurrences of each dice.
	public  List<Integer> countTheDice(Collection<Integer> scoreList) {
		int oneCounter=0,twoCounter=0,threeCounter=0,fourCounter=0,fiveCounter=0,sixCounter=0; //Counters for each DiceScore
//		for (int dice : scoreList) {
//			if (dice == 1) {oneCounter++;}
//			else if (dice==2) {twoCounter++;}
//			else if (dice==3) {threeCounter++;}
//			else if (dice==4) {fourCounter++;}
//			else if (dice==5) {fiveCounter++;}
//			else if (dice==6) {sixCounter++;}
//		}
		for (int dice : scoreList) {
			switch (dice) {
			case 1:
				oneCounter++;
				break;
			case 2:
				twoCounter++;
				break;
			case 3:
				threeCounter++;
				break;
			case 4:
				fourCounter++;
				break;
			case 5:
				fiveCounter++;
				break;
			case 6:
				sixCounter++;
				break;
			default:
				System.err.println("No valid Case for switch method --> countTheDice Method...");
			}
		}
		
		List<Integer> result = new ArrayList<>();
		result.addAll(Arrays.asList(oneCounter, twoCounter, threeCounter, fourCounter, fiveCounter, sixCounter));
		return result;
		
	}
	
	public static void main(String[] args) {
		DiceRoller test = new DiceRoller(6);
		DiceRoller test2 = new DiceRoller(3);
		
		Collection<Integer> first = test.diceScore();
		List<Integer> scoreList = test.countTheDice(first);
		System.out.println(first);
		System.out.println(scoreList);
		Collection<Integer> second = test2.diceScore();
		List<Integer> scoreList2 = test2.countTheDice(second);
		System.out.println(second);
		System.out.println(scoreList2);
		
		
	}
}
