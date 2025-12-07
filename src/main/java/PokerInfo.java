// PokerInfo:
// Send information back and forth between the server and clients.
// Contains data members of player and dealer's hands, the player's
// ante, pair plus, and play bets, if the user decides to fold or
// play for the current round, current round winnings, and overall
// total winnings.

import java.io.Serializable;
import java.util.ArrayList;

public class PokerInfo implements Serializable {
    private ArrayList<Card> playerHand; // stores hand of 3 cards for player
    private ArrayList<Card> dealerHand; // stores hand of 3 card for dealer
    private int anteBet; // stores player's ante bet
    private int pairPlusBet; // stores player's pair plus bet
    private int playBet; // stores player's play bet
    private boolean fold; // stores if user chose to fold
    private boolean play; // stores if user chose to play
    private int roundWinnings; // stores player's current round winnings
    private int totalWinnings; // stores player's overall total winnings
    private String command; // command server and client uses (deal, play, fold)
    private String outcome; // outcome of player vs. dealer's hands
    private String playerHandString; // format player's hand type into string
    private String dealerHandString; // format dealer's hand type into string
    private boolean dealerQualified; // if dealer has at least a queen in their hand
    private int anteResult; // win/loss from ante
    private int pairPlusResult; // win/loss from pair plus
    private int playResult; // win/loss from play bet

    public PokerInfo() {
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        this.command = null;
    }

    public PokerInfo(String command) {
        this.command = command;
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(ArrayList<Card> hand) {
        this.playerHand = hand;
    }

    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(ArrayList<Card> hand) {
        this.dealerHand = hand;
    }

    public int getAnteBet() {
        return anteBet;
    }

    public void setAnteBet(int anteBet) {
        this.anteBet = anteBet;
    }

    public int getPairPlusBet() {
        return pairPlusBet;
    }

    public void setPairPlusBet(int pairPlusBet) {
        this.pairPlusBet = pairPlusBet;
    }

    public int getPlayBet() {
        return playBet;
    }

    public void setPlayBet(int playBet) {
        this.playBet = playBet;
    }

    public boolean isFold() {
        return fold;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public int getRoundWinnings() {
        return roundWinnings;
    }

    public void setRoundWinnings(int roundWinnings) {
        this.roundWinnings = roundWinnings;
    }

    public int getTotalWinnings() {
        return totalWinnings;
    }

    public void setTotalWinnings(int totalWinnings) {
        this.totalWinnings = totalWinnings;
    }

    public String toString() {
        return "playerHand=" + playerHand +
                ", dealerHand=" + dealerHand +
                ", \nanteBet=" + anteBet +
                ", pairPlusBet=" + pairPlusBet +
                ", playBet=" + playBet +
                ", fold=" + fold +
                ", play=" + play +
                ", roundWinnings=" + roundWinnings +
                ", totalWinnings=" + totalWinnings +
                ", \ncommand='" + command + '\'' +
                ", outcome='" + outcome + '\'' +
                ", playerHandString='" + playerHandString + '\'' +
                ", dealerHandString='" + dealerHandString + '\'' +
                ", dealerQualified=" + dealerQualified +
                ", \nanteResult=" + anteResult +
                ", pairPlusResult=" + pairPlusResult +
                ", playResult=" + playResult +
                '}';
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getPlayerHandString() {
        return playerHandString;
    }

    public void setPlayerHandString(String playerHandString) {
        this.playerHandString = playerHandString;
    }

    public String getDealerHandString() {
        return dealerHandString;
    }

    public void setDealerHandString(String dealerHandString) {
        this.dealerHandString = dealerHandString;
    }

    public boolean getDealerQualified() {
        return dealerQualified;
    }

    public void setDealerQualified(boolean dealerQualified) {
        this.dealerQualified = dealerQualified;
    }

    public int getAnteResult() {
        return anteResult;
    }

    public void setAnteResult(int r) {
        anteResult = r;
    }

    public int getPairPlusResult() {
        return pairPlusResult;
    }

    public void setPairPlusResult(int r) {
        pairPlusResult = r;
    }

    public int getPlayResult() {
        return playResult;
    }

    public void setPlayResult(int r) {
        playResult = r;
    }
}