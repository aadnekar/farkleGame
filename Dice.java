package farkleGame;

import java.util.Random;

public class Dice {
	// Just an ordinary Dice ... should throw 1 to 6.
	
	//default constructor.
	
	// Rolls the Dice with a random number from 1 to 6.
	public int roll() {
		Random diceRoll = new Random();
		return diceRoll.nextInt(6) + 1;
	}
	
	public static void main(String[] args) {
		Dice test = new Dice();
		System.out.println(test.roll());
	}
	
}
