package farkleGame;

import java.util.ArrayList;
import java.util.List;

public class MainGame implements ScoreListener {
	// Should keep track of: number of players and each players score.

	private int roundCounter = 1;	// roundCounter should be static because it keeps track of rounds... doesn't really matter?
	private Player player;
	
	private List<Player> players, tempWinners;
	private List<Player> winners = new ArrayList<>(); // add to Stack when a player reach 10.000 points.
	
	ScoreBoard scoreBoard = new ScoreBoard();
	
	// constructor should initialize a game: needs number of players etc...
	
	public MainGame(int numberOfPlayers) {
		players = new ArrayList<>();
		for(int i=0;i<numberOfPlayers;i++) {
			player = new Player();
			players.add(player);
		}
	}
	
	// index starts at player 1 and goes up
	public void setPlayerName(int index, String name) {
		players.get(index-1).setPlayerName(name);
	}
	
	
	public boolean playerFinished(Player player) {
		if (player.getPlayerScore() >= 200) {
			System.out.println(player.getPlayerName() + "Finished the game with: " + player.getPlayerScore() + " points!");
			return true;
		}
		return false;
	}
	
	public String displayRoll(List<Integer> resultList) {
		String result = "The number of	|	ONES	|	TWOS	|	THREES	|	FOURS	|	FIVES	|	SIXES	|\n";
		result+="		|";
		for(int i : resultList) {
			result += "	" + i + "	|";
		}
		result += "\n";
		return result;
	}
	
	
	
	public void playGame() {
		
		System.out.print("Players today are | ");
		for (Player player : players) { System.out.print(player.getPlayerName() + " | "); }
		System.out.println();
		
		// Logic to keep the game rolling:
		while (players.size()>1) { // Game continues until there is only one left.
			tempWinners = new ArrayList<>();
			
//		for (int i = 0 ; i < 2 ; i++) { // Tester to check for a less amount of rounds.
			
			System.out.println(" || NEW ROUND || STARTING ROUND: " + roundCounter);
			
			if (roundCounter > 1) {
				System.out.println("Players still playing are | ");
				for (Player player : players) { System.out.print(player.getPlayerName() + " | "); }
				System.out.println();
			}
			
			// Each player rolls their dice before next round.
			for (Player player : players) {
				player.setRoundFinished(roundCounter);
				player.setFireDice(true); // Ensure that fireDice is true at the beginning of each round.
				int calculateScoreThisRound = 0; // reset scoreCount this round.
				
				// To keep one the same player to roll if he has "fireDice".
				while (player.getFireDice()) {	// while true this player gets another shot.
					System.out.println("It's " + player.getPlayerName() + "s turn.");
					int numberOfDice = 6; // number of dice to throw this round.
					List<Integer> diceToBeScored = new ArrayList<>();
					
					// while a player still has dice to roll.
					while (numberOfDice>0) {
						List<Integer> resultOfRoll = player.rollDices(numberOfDice); 	// Rolls the number of dice.
						System.out.println(displayRoll(resultOfRoll));								// Prints out the result of the roll.
						List<Integer> diceToKeep = player.playerResponse(resultOfRoll);	// Returns diceToKeep (chosen by the player).  
						numberOfDice -= diceToKeep.size();								// Subtracts number of diceToKeep from numberOfDice (to throw).
						diceToKeep.stream().forEach(p -> diceToBeScored.add(p));		// adds all the diceToKeep to a list which will calculate the score after all dice to keep is picked. list = DiceToBeScored.
						
						// Rule says that if there is a score of zero on any roll you lose all points accumulated this round.
						if ((player.scoreCalculator(diceToKeep) == 0)) {
							calculateScoreThisRound = 0;
							System.out.println("Sorry you did not score any points this roll --> all points accumulated this round will be terminated.");
							for (int i=0; i<diceToBeScored.size();i++) {
								diceToBeScored.set(i, 0);
							}
							player.setFireDice(false); // to make sure next person goes next.
							break;
						}
						// To check if the number of dice hits zero this time.
						if (numberOfDice == 0) {
							break;
						}
					}
					calculateScoreThisRound += player.scoreCalculator(diceToBeScored); 	// calculate this rounds score.
					player.addPlayerScore(calculateScoreThisRound);						// add the score to PlayerScore.
					System.out.println(player.getPlayerName() + " scored: " + calculateScoreThisRound + ". And has now got a total of: " + player.getPlayerScore() + " points.");		// Print out rounds score and totalScore.
					scoreBoard.updatePlayer(player);
			
					System.out.println("--------------------------------------------------------------------------------------------------");
				}
				if (playerFinished(player)) {
					tempWinners.add(player);
					player.setRoundFinished(roundCounter);
				}
			}
			
			
			if (tempWinners.size() > 1) {
				tempWinners.sort((p1,p2) -> p2.getPlayerScore() - p1.getPlayerScore());
			}
			
			tempWinners.stream().forEach(p -> winners.add(p));
			winners.stream().forEach(p->players.remove(p));
			roundCounter++;
		}
	
		System.out.println("Congratulation to the winner of this game " + winners.get(0).getPlayerName() + "!");
		System.out.println("The results are:|			PLAYER			|			TOTAL SCORE			|	FINISHED ON ROUND	|");
		for (int i=0;i<winners.size();i++) {
			System.out.println("              "+(i+1)+" |			"+winners.get(i).getPlayerName()+"			|			"+winners.get(i).getPlayerScore()+"				|		"+winners.get(i).getRoundFinished()+"		|" );
		}
		if (players.size() > 0) {
		System.out.println("              "+(winners.size()+1)+" |			"+players.get(0).getPlayerName()+"			|			"+players.get(0).getPlayerScore()+"				|		"+players.get(0).getRoundFinished()+"		|" ); // Last player.
		}
		scoreBoard.writeToFile("/home/oddie/Documents/testerFarkle.txt");
		
	}

	public static void main(String[] args) {
		MainGame testGame = new MainGame(4);
		testGame.setPlayerName(1, "Miguel");
		testGame.setPlayerName(2, "Ådne");
		testGame.setPlayerName(3, "Karl");
		testGame.setPlayerName(4, "Oskar");
		
		testGame.playGame();
	}

	@Override
	public void scoreChanged(Player player) {
		scoreBoard.updatePlayer(player);
	}

}


























