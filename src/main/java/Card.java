// Card:
// Represents a single playing card used in Three Card Poker.
// Each card has a rank from 2-14, where 11 = Jack, 12 = Queen, 13 = King, and 14 = Ace.
// Each card has a suit, where C = Clubs, D = Diamonds, H = Hearts, and S = Spades.

import java.io.Serializable;

public class Card implements Serializable {
    private int rank;
    private char suit;
    private String path;

    // Constructor for card.
    public Card(int rank, char suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // Getter for card's rank.
    public int getRank() {
        return rank;
    }

    // Getter for card's suit.
    public char getSuit() {
        return suit;
    }

    // Getter for card's path.
    public String getPath() {
        return path;
    }

    public String toImagePath() {
        String rankString = "";
        String suitString = "";

        // Assigns numbers 11-14 to Jack, Queen, King, and Ace.
        switch (rank) {
            case 11:
                rankString = "jack";
                break;
            case 12:
                rankString = "queen";
                break;
            case 13:
                rankString = "king";
                break;
            case 14:
                rankString = "ace";
                break;
            default:
                rankString = String.valueOf(rank);
        }

        // Assigns C, D, H, and S to their respective suit names.
        switch (suit) {
            case 'C':
                suitString = "clubs";
                break;
            case 'D':
                suitString = "diamonds";
                break;
            case 'H':
                suitString = "hearts";
                break;
            case 'S':
                suitString = "spades";
                break;
        }

        return "/Image" + rankString + "_of_" + suitString + ".png";
    }

    // Purpose: Converts card's rank and suit into a string.
    @Override
    public String toString() {
        String rankString = "";
        String suitString = "";

        // Assigns numbers 11-14 to Jack, Queen, King, and Ace.
        switch (rank) {
            case 11:
                rankString = "Jack";
                break;
            case 12:
                rankString = "Queen";
                break;
            case 13:
                rankString = "King";
                break;
            case 14:
                rankString = "Ace";
                break;
            default:
                rankString = String.valueOf(rank);
        }

        // Assigns C, D, H, and S to their respective suit names.
        switch (suit) {
            case 'C':
                suitString = "Clubs";
                break;
            case 'D':
                suitString = "Diamonds";
                break;
            case 'H':
                suitString = "Hearts";
                break;
            case 'S':
                suitString = "Spades";
                break;
        }

        return rankString + "_of_" + suitString;
    }
}