package farkleGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
	
	private String playerName;
	private int playerScore;
	private int roundFinished;
	private DiceRoller dices;
	private boolean fireDice = true;
	Scanner reader;
	
	
	
	public Player() {
		playerName = null;
		playerScore = 0;
		roundFinished = -1; // to make sure it's not equal if it has not been set...
	}
	public Player(String name) {
		playerName = name;
		playerScore = 0;
		roundFinished = -1;
	}

	public int getRoundFinished() {
		return roundFinished;
	}
	
	public void setRoundFinished(int round) {
		roundFinished = round;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String name) {
		playerName = name;
	}
	
	public void addPlayerScore(int score) {
		playerScore += score;
	}
	
	public int getPlayerScore() {
		return playerScore;
	}
	
	public boolean getFireDice() {
		return fireDice;
	}
	
	public void setFireDice(boolean input) {
		fireDice = input;
	}
	
	public List<Integer> rollDices(int numberOfDice) {
		dices = new DiceRoller(numberOfDice);
		return dices.countTheDice(dices.diceScore());
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
	
	/**
	 * Calculate the accumulated score during last set of rolls.
	 * @param listOfDiceToKeep
	 */
	public int scoreCalculator(List<Integer> listOfDiceToKeep) {
		int ones=0, twos=0, threes=0, fours=0, fives=0, sixs=0;
		for (Integer score : listOfDiceToKeep) {
			if (score == 1) {ones++;}
			else if (score == 2) {twos++;}
			else if (score == 3) {threes++;}
			else if (score == 4) {fours++;}
			else if (score == 5) {fives++;}
			else if (score == 6) {sixs++;}
			else if (score == 0) {continue;}
			else { throw new IllegalStateException("Looks like there was an illegal element in a scoreCalculator argument (Player Class)"); }
		}
		int tempPlayerScore = 0;
		while (twos>0 || threes > 0 || fours > 0 || sixs > 0) {
			if (twos >=3) {
				tempPlayerScore += 200;
				twos -= 3;
			}
			else if (threes >= 3) {
				tempPlayerScore += 300;
				threes -= 3;
			}
			else if (fours >= 3) {
				tempPlayerScore += 400;
				fours -= 3;
			}
			else if (sixs >= 3) {
				tempPlayerScore += 600;
				sixs -= 3;
			}
			else {
				this.setFireDice(false);
				break;
			}
		}
		while (ones>0 || fives > 0) {
			if (ones >= 3) {
				tempPlayerScore += 1000;
				ones -= 3;
			}
			else if (ones >= 1) {
				tempPlayerScore += 100;
				ones -= 1;
			}
			else if (fives >= 3) {
				tempPlayerScore += 500;
				fives -= 3;
			}
			else if (fives >= 1) {
				tempPlayerScore += 50;
				fives -= 1;
			}
		}
		return tempPlayerScore;
	}
	
	
	public List<Integer> playerResponse(List<Integer> actualDiceRolled) {
		reader = new Scanner(System.in); // Reading form System.in
		List<Integer> listOfDiceToKeep = new ArrayList<>();
		System.out.println("Enter the dice you'd like to keep separeted by a whitespace, f.eks '2 2 2' will keep three 2s.");
		System.out.println("To end the selection end with a 0.");
		
		int dice = 10;
		while (dice > 0) {
			if (listOfDiceToKeep.size()==6) {
				System.out.println("You've stored all dices");
				break;
			}
			
			reader.useDelimiter("[^0-9]+");
			dice = reader.nextInt(); // reader.nextInt() takes the next token and reads it as an integer.
				if (dice < 1 || dice > 6) {
					System.out.println("You wish to exit");
					System.out.println();
					System.out.println();
					break;
				}
				// Remember the index has to match with the dice, thus .get(dice-1).
				if (actualDiceRolled.get(dice-1) > 0) {
					listOfDiceToKeep.add(dice);
					actualDiceRolled.set(dice-1, actualDiceRolled.get(dice-1)-1);
					System.out.println("You've stored a: " + dice + ".");
				}
				else {
					System.out.println("Doesn't look like you actually got that value, try again.");
				}
		}
	
		System.out.println("And the list of dice you've stored: " + listOfDiceToKeep+".");
		System.out.println("----------------------------------------------------------");
		
//		if (reader != null) {  // Kan ikke åpnes flere ganger?
//			System.out.println("test");
//			reader.close();
//		}
		return listOfDiceToKeep;		// Only need this list, because I can take the number of dice rolled minus this list to know how many dice to roll next.
	}
}


























