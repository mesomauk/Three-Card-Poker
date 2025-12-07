// ThreeCardLogic:
// Handles all logic for evaluating and comparing three card hands.
// Main methods evaluate what kind of hand the user has, compares the
// dealer and player's hands, and retrieves the pair plus payout that
// the user would receive depending on their hand.

import java.util.ArrayList;
import java.util.Collections;

public class ThreeCardLogic {

    // Purpose: Evaluate what particular kind of hand the user has and return its ranking (1 for lowest, 6 for highest).
    public int evaluateHand(ArrayList<Card> cards) {
        if (cards.size() != 3) {
            return -1;
        }

        // straight flush has ranking of 6 (highest)
        if (isStraightFlush(cards)) {
            return 6;
        }

        // three of a kind has ranking of 5
        if (isThreeOfAKind(cards)) {
            return 5;
        }

        // straight has ranking of 4
        if (isStraight(cards)) {
            return 4;
        }

        // flush has ranking of 3
        if (isFlush(cards)) {
            return 3;
        }

        // pair has ranking of 2
        if (isPair(cards)) {
            return 2;
        }

        return 1; // high card has ranking of 1 (lowest)
    }

    // Purpose: Compares rankings of the dealer and player's hands.
    // If dealer win, returns 1. If player wins, returns 2. If there is a tie, return 0.
    public int compareHand(ArrayList<Card> dealer, ArrayList<Card> player) {
        if ((dealer.size() != 3) || (player.size() != 3)) {
            return -1; // return -1 if hands are not three cards
        }

        // compare and evaluate rankings directly
        int dealerHand = evaluateHand(dealer);
        int playerHand = evaluateHand(player);
        if (dealerHand >  playerHand) {
            return 1;
        } else if (dealerHand < playerHand) {
            return 2;
        }

        // if both hands are high card hands store ranks in ArrayList
        ArrayList<Integer> dealerRanks = new ArrayList<>();
        ArrayList<Integer> playerRanks = new ArrayList<>();
        for (Card card : dealer) {
            dealerRanks.add(card.getRank());
        }
        for (Card card : player) {
            playerRanks.add(card.getRank());
        }
        Collections.sort(dealerRanks);
        Collections.sort(playerRanks);

        // if both hands are high card hands determine who has highest ranked card
        for (int i = 2; i >= 0; i--) {
            if (dealerRanks.get(i) > playerRanks.get(i)) {
                return 1;
            }
            if (dealerRanks.get(i) < playerRanks.get(i)) {
                return 2;
            }
        }

        return 0; // return 0 if dealer and player have same exact hand
    }

    // Purpose: Retrieves the corresponding payout multipliers from earning each particular kind of hand.
    public int getPairPlusPayout(ArrayList<Card> cards) {
        if (cards.size() != 3) {
            return -1;
        }

        // straight flush has 40 to 1 payout
        if (isStraightFlush(cards)) {
            return 40;
        }

        // three of a kind has 30 to 1 payout
        if (isThreeOfAKind(cards)) {
            return 30;
        }

        // straight has 6 to 1 payout
        if (isStraight(cards)) {
            return 6;
        }

        // flush has 3 to 1 payout
        if (isFlush(cards)) {
            return 3;
        }

        // pair has 1 to 1 payout
        if (isPair(cards)) {
            return 1;
        }

        return 0;
    }

    // Purpose: Determine if hand contains three cards with ranks in consecutive order and of the same suit.
    public boolean isStraightFlush(ArrayList<Card> cards) {
        return isStraight(cards) && isFlush(cards);
    }

    // Purpose: Determine if hand contains three cards of the same suit.
    public boolean isThreeOfAKind(ArrayList<Card> cards) {
        if  (cards.size() != 3) {
            return false;
        }

        int firstRank = cards.get(0).getRank();
        int secondRank = cards.get(1).getRank();
        int thirdRank = cards.get(2).getRank();

        return (firstRank == secondRank) && (secondRank == thirdRank);
    }

    // Purpose: Determine if hand contains three cards with ranks in consecutive order.
    public boolean isStraight(ArrayList<Card> cards) {
        if  (cards.size() != 3) {
            return false;
        }

        // store ranks in ArrayList to check for consecutive order
        ArrayList<Integer> ranks = new ArrayList<>();
        ranks.add(cards.get(0).getRank());
        ranks.add(cards.get(1).getRank());
        ranks.add(cards.get(2).getRank());
        Collections.sort(ranks);

        int firstRank = ranks.get(0);
        int secondRank = ranks.get(1);
        int thirdRank = ranks.get(2);

        // checks if ranks are correctly in consecutive order
        if ((firstRank + 1 == secondRank) && (secondRank + 1 == thirdRank)) {
            return true;
        }

        // edge case of Ace, 2, and 3 counts as a straight
        if ((firstRank == 2) && (secondRank == 3) && (thirdRank == 14)) {
            return true;
        }

        return false;
    }

    // Purpose: Determine if hand contains three cards of the same suit.
    public boolean isFlush(ArrayList<Card> cards) {
        if (cards.size() != 3) {
            return false;
        }

        char firstSuit = cards.get(0).getSuit();
        char secondSuit = cards.get(1).getSuit();
        char thirdSuit = cards.get(2).getSuit();

        return (firstSuit == secondSuit) && (secondSuit == thirdSuit);
    }

    // Purpose: Determine if hand contains two cards of the same rank.
    public boolean isPair(ArrayList<Card> cards) {
        if  (cards.size() != 3) {
            return false;
        }

        int firstRank = cards.get(0).getRank();
        int secondRank = cards.get(1).getRank();
        int thirdRank = cards.get(2).getRank();

        return (firstRank == secondRank) || (firstRank == thirdRank) || (secondRank == thirdRank);
    }

    // Purpose: Return hand type as a string.
    public String getHandName(ArrayList<Card> hand, boolean individual) {
        int rank = evaluateHand(hand);

        switch (rank) {
            case 6: return "straight flush";
            case 5: return "three of a kind";
            case 4: return "straight";
            case 3: return "flush";
            case 2: return "pair";
            case 1:
            default:
                if (individual) {
                    return "high card (" + rankToSymbol(getHighestCard(hand)) + ")";
                }
                else {
                    return "(" + rankToSymbol(getHighestCard(hand)) + ")";
                }
        }
    }

    // Purpose: Getter for highest card in hand.
    public int getHighestCard(ArrayList<Card> hand) {
        return getSortedRanks(hand).get(0);
    }

    // Purpose: Convert a rank to a string symbol.
    public String rankToSymbol(int rank) {
        switch (rank) {
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            case 14:
                return "A";
            default:
                return String.valueOf(rank);
        }
    }

    // Purpose: Return three cards in order of highest t
    public ArrayList<Integer> getSortedRanks(ArrayList<Card> hand) {
        ArrayList<Integer> ranks = new ArrayList<>();
        for (Card c : hand) ranks.add(c.getRank());
        ranks.sort(Collections.reverseOrder());
        return ranks;
    }

    // Purpose: Return a string of outcome of play action between dealer and user to show winner and hand types.
    public String getOutcomeDescription(ArrayList<Card> dealer, ArrayList<Card> player) {
        int dealerRank = evaluateHand(dealer);
        int playerRank = evaluateHand(player);

        // Retrieve strings of hand type for dealer and player
        String dealerName;
        String playerName;

        // If hand types differ, return win/lose message
        if (playerRank != dealerRank) {
            dealerName = getHandName(dealer, true);
            playerName = getHandName(player, true);
            return "Player's " + playerName +
                    (playerRank > dealerRank ? " beats " : " loses to ") +
                    "Dealer's " + dealerName + ".";
        }

        // Card-by-card tiebreak if same hand type
        ArrayList<Integer> p = getSortedRanks(player);
        ArrayList<Integer> d = getSortedRanks(dealer);
        String tieString = "\nHighest card tiebreak required.\n";

        // Compare each card in highest order to find a winner
        for (int i = 0; i < 3; i++) {
            if (!p.get(i).equals(d.get(i))) {
                playerName = getHandName(player, false);

                if (i == 0) {
                    return tieString + "Player's ("
                        + rankToSymbol(p.get(i)) + ") "
                        + (p.get(i) > d.get(i) ? "beats" : "loses to") +
                        " Dealer's (" + rankToSymbol(d.get(i)) + ").";
                }
                else if (i == 1) {
                    return tieString + "Same highest cards " + playerName +
                            ".\nComparing second highest cards... \nPlayer's ("
                            + rankToSymbol(p.get(i)) + ") "
                            + (p.get(i) > d.get(i) ? "beats" : "loses to") +
                            " Dealer's (" + rankToSymbol(d.get(i)) + ").";
                }
                else {
                    return tieString + "Same highest and second highest cards " + playerName +
                            ".\nComparing third highest cards… \nPlayer's ("
                            + rankToSymbol(p.get(i)) + ") "
                            + (p.get(i) > d.get(i) ? "beats" : "loses to") +
                            " Dealer's (" + rankToSymbol(d.get(i)) + ").";
                }
            }
        }

        return "Player and Dealer have identical hands.";
    }

}


