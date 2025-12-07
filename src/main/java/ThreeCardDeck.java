// ThreeCardDeck:
// Contains all logic and methods for handling the 52-card deck to be used in the game.
// Includes methods for building the 52-card deck, dealing cards to the user, and
// shuffling the deck.

import java.util.ArrayList;
import java.util.Collections;

public class ThreeCardDeck {
    private ArrayList<Card> cards;

    ThreeCardDeck() {
        cards = new ArrayList<>();
    }

    // Purpose: Build the deck of 52 cards, including four suits (Clubs, Diamonds, Hearts, and Spades), and
    // thirteen ranks (2-10, Jack, Queen, King, and Ace).
    public void buildDeck() {
        cards.clear();
        ArrayList<Character> suits = new ArrayList<>();
        suits.add('C');
        suits.add('D');
        suits.add('H');
        suits.add('S');

        // initialize entire deck of 52 cards
        for (char suit : suits) {
            for (int rank = 2; rank < 15; rank++) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    // Purpose: Deal a single card to the user and remove it from the deck of 52 cards.
    public Card dealCard() {
        if (cards.isEmpty()) {
            buildDeck();
        }
        return cards.remove(0);
    }

    // Purpose: Shuffle the deck of 52 cards.
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    // Purpose: Getter for deck size.
    public int getDeckSize() {
        return cards.size();
    }
}
