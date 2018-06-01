package farkleGame;

import java.util.ArrayList;
import java.util.Collection;
import java.io.*;

// Class to write display the final score in a text file. Can also have a method to make the display of score be pretty !! . !!

public class ScoreBoard {
	
	Collection<Player> players = new ArrayList<>();
	
	public void updatePlayer(Player player) {
		if (!players.contains(player)) {
			players.add(player);
		}
	}

	
	// Write to file method:
	
	public void writeToFile(String filename) {
		PrintWriter scoreWriter = null;
		for (Player player : players) {
			System.out.println(player.getPlayerName() + " has " + player.getPlayerScore() + " points.");
		}
		
		boolean fileNameUnique = false;
		while (!fileNameUnique) {
			File scoreFile = new File(filename);
			
			if (scoreFile.exists() == false) {
				try {
					scoreWriter = new PrintWriter(filename);
					System.out.println("Just made a document");
					scoreWriter.print("A list of points for the players: ");
					for (Player player : players) {
						scoreWriter.print( " " + player.getPlayerName());
					}
					System.out.println("Wrote to the document :: just a bit introduction");
					scoreWriter.println();
					for (Player player : players) {
						scoreWriter.println(player.getPlayerName() + " has " + player.getPlayerScore() + " points.");
					}
					System.out.println("Wrote the players data");
					fileNameUnique = true;
				}
				catch (IOException e) {
					e.printStackTrace();
					System.err.println("Something happend in writeToFile");
				}
				finally {
					if (scoreWriter != null) {
						scoreWriter.close();
					}
				}
			}
			
			// If the filename is not unique, add the number 1 at the end of the name.
			else if (scoreFile.exists() && !scoreFile.isDirectory()) {
				filename += "1";
			}
		}
	}
	public static void main(String[] args) {
		File scoreFile = new File("/home/oddie/Documents/tester.txt");
		System.out.println("scoreFile.exists(): " + scoreFile.exists());
		System.out.println("!scoreFile.exists(): " + !scoreFile.exists());
		System.out.println("scoreFile.exists() == false: " + (scoreFile.exists() == false));
	}
}	
