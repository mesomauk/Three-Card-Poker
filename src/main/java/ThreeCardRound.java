import java.util.ArrayList;

public class ThreeCardRound {

    private ThreeCardDeck deck;
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;
    private int roundWinnings;
    private int totalWinnings;
    private int anteResult;
    private int pairPlusResult;
    private int playResult;

    // Constructor for a complete round of Three Card Poker.
    public ThreeCardRound(ThreeCardDeck deck) {
        this.deck = deck;
    }

    public ThreeCardRound() {}

    public void setHands(ArrayList<Card> player, ArrayList<Card> dealer) {
        this.playerHand = player;
        this.dealerHand = dealer;
    }

    // Purpose: Performs a complete round of Three Card Poker. Calculates pair plus bet earnings if user opted for it,
    // deducts wager from round winnings if user opted to fold, checks if dealer qualified for the hand comparison,
    // and determines winner and winnings of round if so.
    public int evaluateRound(int anteBet, int pairPlusBet, int playBet, boolean fold) {
        ThreeCardLogic logic = new ThreeCardLogic();

        anteResult = 0;
        pairPlusResult = 0;
        playResult = 0;

        if (pairPlusBet > 0) {
            int multiplier = logic.getPairPlusPayout(playerHand);
            if (multiplier > 0) {
                pairPlusResult = pairPlusBet * multiplier;
            }
            else {
                pairPlusResult = -pairPlusBet;
            }
        }

        if (fold) {
            roundWinnings = roundWinnings - anteBet - pairPlusBet;
            return roundWinnings;
        }

        if (!dealerQualifies(dealerHand)) {
            roundWinnings = anteResult + pairPlusResult + playResult;
            return roundWinnings;
        }

        int result = logic.compareHand(dealerHand, playerHand);

        if (result == 2) { // player wins
            anteResult = anteBet * 2;
            playResult = playBet * 2;
        }
        else if (result == 1) { // dealer wins
            anteResult = -anteBet;
            playResult = -playBet;
        }

        roundWinnings = anteResult + pairPlusResult + playResult;
        return roundWinnings;
    }

    // Purpose: Getter for current round winnings.
    public int getRoundWinnings() {
        return roundWinnings;
    }

    // Purpose: Getter for overall total winnings.
    public int getTotalWinnings() {
        return totalWinnings;
    }

    // Purpose: Deals 3 cards each.
    public void dealCards() {
        dealerHand = new ArrayList<>();
        playerHand = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            dealerHand.add(deck.dealCard());
            playerHand.add(deck.dealCard());
        }
    }

    // Purpose: Getter for player's hand.
    public ArrayList<Card> getPlayerCards() {
        return playerHand;
    }

    // Purpose: Getter for dealer's hand.
    public ArrayList<Card> getDealerCards() {
        return dealerHand;
    }

    // Purpose: Determines if dealer qualifies (has at least a queen).
    public boolean dealerQualifies(ArrayList<Card> hand) {
        for (Card c : hand) {
            if (c.getRank() > 11) {
                return true;
            }
        }
        return false;
    }

    // Purpose: Getter for ante bet result.
    public int getAnteResult() {
        return anteResult;

    }

    // Purpose: Getter for pair plus bet result.
    public int getPairPlusResult() {
        return pairPlusResult;

    }

    // Purpose: Getter for play bet result.
    public int getPlayResult() {
        return playResult;
    }
}
