/*
Steven Hunt
Assignment 2: Casino

Description: Create a slot machine program that prompts the user to enter an 
integer between 1 and 100 as a bet, simulates a slot machine, and displays the 
whether the user won or lost, and displays the amount won
*/

import java.util.*;
import java.lang.Math;

public class Assign2 {

    // Main method: 
    public static void main(String[] args) {
                
        int toBet = 1;
        int multiplier;
        int winnings;
        TripleString pullString = new TripleString();
      
        //Display welcome message
        System.out.print("\nWelcome to the Hunger Games Slot Machine!\n");

        //Continue plays while user input does not equal 0 and number of plays does not exceed MAX_PULL
        while (pullString.continuePlay()) {
            
            toBet = getBet();
            if(toBet == 0) break;
            pullString = pull();
            multiplier = getPayMultiplier(pullString);
            winnings = multiplier*toBet;
            display(pullString, winnings);
            pullString.saveWinnings(winnings);   
       } 
             
        //Displays individual winnings, total winnings, and thank message
        System.out.print("\nYour individual winnings are: \n" + pullString.displayWinnings());
        System.out.print("\nYour total winnings is: " + pullString.totalWinnings);
        System.out.print("\nThank you for playing.\n\n");
    }

    // Asks user to enter the bet amount and returns it
    public static int getBet() {
        
        //Scanner will allow for user input
        Scanner enterBet = new Scanner(System.in);
        //set input to int 
        int bet;
        // A while loop will keep the program running when true
        while (true)  {
            
            //Output welcome message
            System.out.print("Place a bet from 1 to 100 or enter 0 to quit: ");

            //nextInt scans the next token of the input as an int. 
            bet = enterBet.nextInt();

            //If statement returns bet range from 1 to 100
            if (bet <= 100 && bet >= 0) {
                return bet;
            }
        }
    }

    // Generates a pull string and returns it
    public static TripleString pull() {

        // Use reel to set TripleString default constructor to initialize string members 
        TripleString reel = new TripleString();

        // Output result message
        System.out.print("Thank you, and may the odds be ever in your favor!\n");
        System.out.print("***YOUR PULL IS***\n");
        
        // Set mutators that intake private helper method randString()
        reel.setFirst(randString());
        reel.setSecond(randString());
        reel.setThird(randString());

        return reel;
    }
   
    // Randomly generates value for slot machine based on defined probabilities
    static String randString() {
        
        String randStr;

        // generate a random integer in the range from 0 to 999
        Random randomGenerator = new Random(); 
        int randInt = randomGenerator.nextInt(1000);    

        // assign string value to random number according to probabilities: 
        // 50% "BAR", 25% "cherries", 12.5% "space", 12.5% "7"
        if (randInt < 500)  {
            randStr = "BAR";
        }
      
        else if (randInt >= 500 && randInt < 750) {
            randStr = "cherries";
        }
      
        else if (randInt >= 750 && randInt < 875) {
            randStr = "(space)";
        }
      
        else {
            randStr = "7";
        }

        return randStr;  
    }

    // Takes a TripleString object and generate a multiplier based on that object
    public static int getPayMultiplier(TripleString pullString) {

        // Sort the first 3rd of the TripleString
        switch (pullString.getFirst()) {
      
            // If cherries is first
            case "cherries":
            
                // If the second 3rd of the TripleString does not equal cherries
                if ("cherries".equals(pullString.getSecond())) {
                    return 5;
                }
             
                else if ("cherries".equals(pullString.getSecond())) {
                
                    if ("cherries".equals(pullString.getThird())) {
                        return 15;
                    }
                    
                    else if ("cherries".equals(pullString.getThird())) {
                        return 30;
                    }
             
                }  break;
             
            // If there are three BARs in a row
            case "BAR":
             
                if ("BAR".equals(pullString.getSecond())) {
                    if ("BAR".equals(pullString.getThird())) {
                        return 50;
                    }
                }  break;
             
            // If there are three 7s in a row
            case "7":
           
            if ("7".equals(pullString.getSecond())) {
                if ("7".equals(pullString.getThird())) {
                    return 100;
                }
            }  break;
        }
        // If the TripleString does not match, then return 0
        return 0;
    }

    /*
    Pre: continuePlay is true.  
    Post: Displays the output of pull (either win or lose). If won, then winnin amount is displayed as well. 
    */
    public static void display (TripleString thePull, int winnings) {
        
        System.out.print (thePull.getFirst() + " " + thePull.getSecond() + " " + thePull.getThird() + "\n");

        if (winnings == 0)
            System.out.println ("You lose. Try again.\n");
        else
            System.out.println ("Congratulations you won: $" + winnings + "\n");
    }
}

class TripleString {
 
    // Class instances and methods: 
    private String string1, string2, string3;
    public static final int MAX_LEN = 20;
    public static final int MAX_PULLS = 40;
    public static int pullWinnings[] = new int[MAX_PULLS];
    public static int round = 0;
    public int totalWinnings = 0;

    // Default Constructor
    public TripleString() {

        string1 = string2 = string3 = "";
    }

    // Mutators
    public boolean setFirst(String firstSymbol) {

        if (validString(firstSymbol)) {
            string1 = firstSymbol;
            return true;
        }
        else 
            return false;
    }

    public boolean setSecond(String secondSymbol) {

        if (validString(secondSymbol)) {
            string2 = secondSymbol;
            return true;
        }
        else 
            return false;
    }

   public boolean setThird(String thirdSymbol) {
      
    if (validString(thirdSymbol)) {
        string3 = thirdSymbol;
        return true;
    }
    else 
        return false;
    }

    // Accessors
    public String getFirst() {
        
        return string1;
    }

    public String getSecond() {
        
        return string2;
    }

    public String getThird() {

        return string3;
    }

   // Method to combine all strings
    public String toString() {

        return getFirst() + getSecond() + getThird();
    }
   
    // Method to determine whether the rounds is less than the maximum pulls allowed
    public boolean continuePlay() {
    
        if (round == MAX_PULLS) {
            return false;
        }
        else
            return true;
    }
   
    // Determines if the string inputted by the user is valid
    private boolean validString(String str) {
 
        if (str.length() <= MAX_LEN) {
            return true;
        }
        else
            return false;
   }

   // Winning Methods: 
   // Stores winnings into the pullWinnings array
    public boolean saveWinnings(int winnings) {
        
        if (round < MAX_PULLS) {
            pullWinnings[round] = winnings;
            ++round;
            return true;
        }
        else
            return false;
    }

    // Creates a string contain every individual winning
    public String displayWinnings() {

        String allWinnings = "";

        for (int i = 0; i < round; i++) {
            allWinnings += Integer.toString(pullWinnings[i]) + " ";
            totalWinnings += pullWinnings[i];
        }

        return allWinnings;
    }

} 

/***********************************OUTPUT******************************************
Welcome to the Hunger Games Slot Machine!
Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
(space) BAR BAR
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR BAR cherries
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
(space) cherries BAR
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
cherries cherries BAR
You won: 250

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR BAR BAR
You won: 2500

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR 7 BAR
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
7 BAR (space)
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
cherries BAR BAR
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
7 cherries cherries
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
(space) cherries 7
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: -5
Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR (space) BAR
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR BAR BAR
You won: 2500

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
(space) BAR BAR
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR (space) cherries
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
7 BAR cherries
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR 7 BAR
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR BAR 7
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR BAR BAR
You won: 2500

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
7 BAR cherries
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR cherries cherries
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
(space) BAR BAR
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
cherries 7 BAR
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR cherries BAR
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
cherries BAR BAR
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR cherries cherries
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
cherries BAR 7
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
BAR BAR BAR
You won: 2500

Place a bet from 1 to 100 or enter 0 to quit: 50
Thank you, and may the odds be ever in your favor!
***YOUR PULL IS***
7 BAR (space)
You lose. Try again.

Place a bet from 1 to 100 or enter 0 to quit: 0

Your individual winnings are: 
0 0 0 250 2500 0 0 0 0 0 0 2500 0 0 0 0 0 2500 0 0 0 0 0 0 0 0 2500 0 
Your total winnings is: 10250
Thank you for playing.
*************************************************************************************/

