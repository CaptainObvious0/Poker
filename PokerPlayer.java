
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random; 
import java.util.List;

public class PokerPlayer {
    
    // TODO: Create some methods with parameters so that there's no as much repeated code
    // Hopefully clean up the earlier methods made
    // Duplicates can occur when the player deck is made
    
    // Constructor fields
    String player;
    ArrayList<String> cards;
    
    // ArrayList of all cards
    static ArrayList<String> playableCards = new ArrayList<String>();
    
    // Constructor for new poker player
    public PokerPlayer(String player) {
        this.player = player;
        this.cards = makeDeck();
    }
    
    // Set the players deck
    public void setDeck(ArrayList<String> cards) {
        this.cards = cards;
    } 
    
    // Get the players deck;
    public ArrayList<String> getDeck() {
        return this.cards;
    }
 
     public static void main(String []args){
         
         // Creates the deck of cards
         initDeck();
         
         // Makes a new poker player named Michael, name has no use right now
         PokerPlayer player = new PokerPlayer("Michael");
         
        System.out.println(player.getDeckType());
        System.out.println(player.getDeck());
     }
     
     // Using all the methods created below, check in order to see what type of deck the player has
     public String getDeckType() {
         if (checkRoyalFlush()) {
             return "You have a royal flush!";
         } else if (checkStratFlush()) {
             return "You have a straight flush!";
         } else if (checkFourOfKind()) {
             return "You have four of a kind!";
         } else if (checkFullHouse()) {
             return "You have a full house!";
         } else if (checkFlush()) {
             return "You have a flush!";
         } else if (checkStraight()) {
             return "You have a straight!";
         } else if (checkThreeOfKind()) {
             return "You have three of a kind!";
         } else if (checkTwoPair()) {
             return "You have two pairs!";
         } else if (checkPair()) {
             return "You have a pair!";
         } else {
             return "Looks like you're out of luck! Your highest card is " + checkHighCard();
         }
     }
     
     // Loop to create the deck
     // D = Diamonds
     // H = Hearts
     // C = Clubs
     // S = Spades
     // Note: 1 actually means two and 14 means ace
     public static void initDeck() {
         for (int i = 0; i < 4; i++) {
             for (int z = 1; z < 14; z++) {
                 if (i == 0) {
                     playableCards.add("H" + z);
                 } else if (i == 1) {
                     playableCards.add("D" + z);
                 } else if (i == 2) {
                     playableCards.add("C" + z);
                 } else if (i == 3) {
                     playableCards.add("S" + z);
                 }
             }
         }
     }
     
     // Create the deck for the player randomly
     public ArrayList<String> makeDeck() {
         ArrayList<String> card = new ArrayList<String>();
         Random random = new Random();
         for (int i = 0; i < 5; i++) {
             String currentCard = playableCards.get(random.nextInt(51) + 1);
             if (card.contains(currentCard)) {
                 i--;
             } else {
                 card.add(playableCards.get(random.nextInt(51) + 1));
             }
         }
         return card;
     }
     
     public boolean checkRoyalFlush() {
         ArrayList<String> cards = getDeck();
         // Check if the card values go from 10 to ace
         ArrayList<String> repeats = new ArrayList<String>();
         for (String card : cards) {
             // Check if the card value is double digit
             if (card.length() != 3) return false;
             // Check to make sure nothing is repeating
             if (repeats.contains(card)) return false;
             if (card.substring(1,3).equals("10") || card.substring(1,3).equals("11") || card.substring(1,3).equals("12") || card.substring(1,3).equals("13")) {
                 repeats.add(card);
             } else {
                 return false;
             }
         }
         return true;
     }
     
     public boolean checkStratFlush() {
         ArrayList<String> cards = getDeck();
         String firstSuit = "";
         int lowestCard = -1;
         String[] cardValue = new String[5];
         int counter = 0;
         // Gets the lowest card value
         for (String card : cards) {
             int value;
             if (card.length() == 3) {
                 value = Integer.parseInt(card.substring(1,3));
             } else {
                 value = Integer.parseInt(card.substring(1,2));
             }
             
             cardValue[counter] = value + "";
             counter++;
             if (value < lowestCard || lowestCard == -1) {
                 lowestCard = value;
             }
         }
         // Checks if the cards are consecutive
         int increase = 1;
         List<String> str = Arrays.asList(cardValue);
         for (String val : str) {
           if (str.contains(lowestCard + increase + "")) {
               increase++;
               continue;
           }  else if (increase != 5) {
               return false;
           }
         }
         // Checks if all the cards are in the same suit
         for (String card : cards) {
             if (firstSuit.equals("")) firstSuit = String.valueOf(card.charAt(0));
             if (!String.valueOf(card.charAt(0)).equals(firstSuit)) return false;
             
         }
         return true;
     }
     
     public boolean checkFourOfKind() {
         ArrayList<String> cards = getDeck();
         int[] checkCards = new int[5];
         // Creates an int array of all the card values
         for (int i = 0; i < cards.size(); i++) {
             String val;
             if (cards.get(i).length() == 2) {
                 val = cards.get(i).substring(1,2);
             } else {
                 val = cards.get(i).substring(1,3);
             }
             checkCards[i] = Integer.parseInt(val);
         }
         
         // Counts the amount of same card values
         int count = 0;
         for (int val : checkCards) {
             for (int i = 0; i < 5; i++) {
                 if (val == checkCards[i]) {
                     count++;
                 }
             }
             if (count >= 4) return true;
             count = 0;
         }
         
         return false;
     }
     
     public boolean checkFullHouse() {
         ArrayList<String> cards = getDeck();
         int[] checkCards = new int[5];
         // Creates an int array of the card values
         for (int i = 0; i < cards.size(); i++) {
             String val;
             if (cards.get(i).length() == 2) {
                 val = cards.get(i).substring(1,2);
             } else {
                 val = cards.get(i).substring(1,3);
             }
             checkCards[i] = Integer.parseInt(val);
         }
         // Sort the array so that it can be easier to check
         Arrays.sort(checkCards);
         int prevVal = checkCards[0] - 1;
         int lastVal = 0;
         int count = 0;
         // Checks for 2/3 cards of the same value
         for (int val : checkCards) {
             if (val == prevVal) {
                 count++;
                 if (count >= 3) {
                     lastVal = val;
                     break;
                 }
             } else {
                 prevVal = val;
             }
         }
         count--;
         // Checks for 2/3 (different) cards of the same value
         if (lastVal != 0 && count >= 3 | count >= 2) {
             prevVal = checkCards[0];
             count = 0;
             for (int val : checkCards) {
                 if (val == lastVal) continue;
                 if (val == prevVal) {
                     count++;
                     prevVal = val;
                 }
             }
             if (count >= 2) {
                 return true;
             }
         }
         return false;
     }
     
     public boolean checkFlush() {
         ArrayList<String> cards = getDeck();
         String[] faceCard = new String[5];
         // Creates a string array of the suits
         for (int i = 0; i < 5; i++) {
             faceCard[i] = cards.get(i).substring(0,1);
         }
         // Checks if all cards are in the same suit
         String lastFace = faceCard[0];
         int count = 0;
         for (String face : faceCard) {
             if (face.equals(lastFace)) {
                 count++;
             } else {
                 return false;
             }
         }
         return true;
     }
     
     public boolean checkStraight() {
         ArrayList<String> cards = getDeck();
         String firstSuit = "";
         int lowestCard = -1;
         String[] cardValue = new String[5];
         int counter = 0;
         // Creates a string array with all the card values and also gets the lowest card value
         // Using a string array so that it will be easier to use .contains later
         // TODO: change this to int array and order the array so no need for .contains
         for (String card : cards) {
             int value;
             if (card.length() == 3) {
                 value = Integer.parseInt(card.substring(1,3));
             } else {
                 value = Integer.parseInt(card.substring(1,2));
             }
             
             cardValue[counter] = value + "";
             counter++;
             if (value < lowestCard || lowestCard == -1) {
                 lowestCard = value;
             }
         }
         // Checks if the cards are consecutive
         int increase = 1;
         List<String> str = Arrays.asList(cardValue);
         for (String val : str) {
           if (str.contains(lowestCard + increase + "")) {
               increase++;
               continue;
           }  else if (increase != 5) {
               return false;
           }
         }
         return true;
     }
     
     public boolean checkThreeOfKind() {
         ArrayList<String> cards = getDeck();
         int[] checkCards = new int[5];
         // Creates an int array with all the card values
         for (int i = 0; i < cards.size(); i++) {
             String val;
             if (cards.get(i).length() == 2) {
                 val = cards.get(i).substring(1,2);
             } else {
                 val = cards.get(i).substring(1,3);
             }
             checkCards[i] = Integer.parseInt(val);
         }
         // Sorts the array and then checks for three of the same cards
         Arrays.sort(checkCards);
         int prevVal = checkCards[0];
         int lastVal = 0;
         int count = -1;
         for (int val : checkCards) {
             for (int i = 0; i < 5; i++) {
                 if (checkCards[i] == val) {
                     count++;
                 }
             }
             if (count >= 3) {
                 return true;
             } else {
                 count = 0;
             }
         }
         
         return false;
     }
     
     public boolean checkTwoPair() {
         ArrayList<String> cards = getDeck();
         int[] checkCards = new int[5];
         // Creates an int array of all the card values
         for (int i = 0; i < cards.size(); i++) {
             String val;
             if (cards.get(i).length() == 2) {
                 val = cards.get(i).substring(1,2);
             } else {
                 val = cards.get(i).substring(1,3);
             }
             checkCards[i] = Integer.parseInt(val);
         }
         
         // Checks if there's two different pairs (of the same value) of cards
         int count = 0;
         int pairs = 0;
         int lastVal = 0;
         for (int val : checkCards) {
             for (int i = 0; i < 5; i++) {
                 if (val == lastVal) continue;
                 if (val == checkCards[i]) {
                     count++;
                 }
             }
             if (count >= 2) {
                 pairs++;
                 lastVal = val;
             }
             count = 0;
         }
         if (pairs >= 2) {
             return true;
         }
         return false;
     }
     
     public boolean checkPair() {
         ArrayList<String> cards = getDeck();
         int[] checkCards = new int[5];
         // Creates an int array of all the card values
         for (int i = 0; i < cards.size(); i++) {
             String val;
             if (cards.get(i).length() == 2) {
                 val = cards.get(i).substring(1,2);
             } else {
                 val = cards.get(i).substring(1,3);
             }
             checkCards[i] = Integer.parseInt(val);
         }
         
         // Checks for one pair within the int array
         int count = 0;
         for (int val : checkCards) {
             for (int i = 0; i < 5; i++) {
                 if (val == checkCards[i]) {
                     count++;
                 }
             }
             if (count >= 2) {
                 return true;
             }
             count = 0;
         }
         return false;
     }
     
     public int checkHighCard() {
         ArrayList<String> cards = getDeck();
         int highValue = 0;
         // Parses the string to an int and then loops through to see what the highest card is
         for (String card : cards) {
             int value;
             if (card.length() == 3) {
                 value = Integer.parseInt(card.substring(1,3));
             } else {
                 value = Integer.parseInt(card.substring(1,2));
             }
             
             if (value > highValue) {
                 highValue = value;
             }
         }
         return highValue;
     }
}
