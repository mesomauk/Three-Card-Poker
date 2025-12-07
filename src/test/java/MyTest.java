import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class ThreeCardPokerTests {

    @Test
    void testCardConstructorAndGetters() {
        Card c = new Card(1, 'C');
        Card d = new Card(2, 'D');
        Card h = new Card(3, 'H');
        Card s = new Card(4, 'S');

        Card j = new Card(11, 'C');
        Card q = new Card(12, 'D');
        Card k = new Card(13, 'H');
        Card a = new Card(14, 'S');

        assertEquals(1, c.getRank(), "getRank() returned incorrect value");
        assertEquals(2, d.getRank(), "getRank() returned incorrect value");
        assertEquals(3, h.getRank(), "getRank() returned incorrect value");
        assertEquals(4, s.getRank(), "getRank() returned incorrect value");
        assertEquals(11, j.getRank(), "getRank() returned incorrect value");
        assertEquals(12, q.getRank(), "getRank() returned incorrect value");
        assertEquals(13, k.getRank(), "getRank() returned incorrect value");
        assertEquals(14, a.getRank(), "getRank() returned incorrect value");

        assertEquals('C', c.getSuit(), "getSuit() returned incorrect value");
        assertEquals('D', d.getSuit(), "getSuit() returned incorrect value");
        assertEquals('H', h.getSuit(), "getSuit() returned incorrect value");
        assertEquals('S', s.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', j.getSuit(), "getSuit() returned incorrect value");
        assertEquals('D', q.getSuit(), "getSuit() returned incorrect value");
        assertEquals('H', k.getSuit(), "getSuit() returned incorrect value");
        assertEquals('S', a.getSuit(), "getSuit() returned incorrect value");
    }

    @Test
    void testCardToString() {
        Card c = new Card(1, 'C');
        Card d = new Card(2, 'D');
        Card h = new Card(3, 'H');
        Card s = new Card(4, 'S');

        Card j = new Card(11, 'C');
        Card q = new Card(12, 'D');
        Card k = new Card(13, 'H');
        Card a = new Card(14, 'S');

        assertEquals("1_of_Clubs", c.toString(), "toString() returned incorrect value");
        assertEquals("2_of_Diamonds", d.toString(), "toString() returned incorrect value");
        assertEquals("3_of_Hearts", h.toString(), "toString() returned incorrect value");
        assertEquals("4_of_Spades", s.toString(), "toString() returned incorrect value");

        assertEquals("Jack_of_Clubs", j.toString(), "toString() returned incorrect value");
        assertEquals("Queen_of_Diamonds", q.toString(), "toString() returned incorrect value");
        assertEquals("King_of_Hearts", k.toString(), "toString() returned incorrect value");
        assertEquals("Ace_of_Spades", a.toString(), "toString() returned incorrect value");
    }

    @Test
    void testDeckBuildDeck() {
        ThreeCardDeck deck = new ThreeCardDeck();
        deck.buildDeck();
        assertEquals(52, deck.getDeckSize(), "Deck should contain 52 cards after buildDeck()");
    }

    @Test
    void testDeckDealCards() {
        ThreeCardDeck deck = new ThreeCardDeck();
        deck.buildDeck();

        Card card1 = deck.dealCard();
        Card card2 = deck.dealCard();
        Card card3 = deck.dealCard();
        Card card4 = deck.dealCard();
        Card card5 = deck.dealCard();
        Card card6 = deck.dealCard();
        Card card7 = deck.dealCard();
        Card card8 = deck.dealCard();
        Card card9 = deck.dealCard();
        Card card10 = deck.dealCard();
        Card card11 = deck.dealCard();
        Card card12 = deck.dealCard();
        Card card13 = deck.dealCard();

        assertEquals(2, card1.getRank(), "getRank() returned incorrect value");
        assertEquals(3, card2.getRank(), "getRank() returned incorrect value");
        assertEquals(4, card3.getRank(), "getRank() returned incorrect value");
        assertEquals(5, card4.getRank(), "getRank() returned incorrect value");
        assertEquals(6, card5.getRank(), "getRank() returned incorrect value");
        assertEquals(7, card6.getRank(), "getRank() returned incorrect value");
        assertEquals(8, card7.getRank(), "getRank() returned incorrect value");
        assertEquals(9, card8.getRank(), "getRank() returned incorrect value");
        assertEquals(10, card9.getRank(), "getRank() returned incorrect value");
        assertEquals(11, card10.getRank(), "getRank() returned incorrect value");
        assertEquals(12, card11.getRank(), "getRank() returned incorrect value");
        assertEquals(13, card12.getRank(), "getRank() returned incorrect value");
        assertEquals(14, card13.getRank(), "getRank() returned incorrect value");

        assertEquals('C', card1.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card2.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card3.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card4.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card5.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card6.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card7.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card8.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card9.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card10.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card11.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card12.getSuit(), "getSuit() returned incorrect value");
        assertEquals('C', card13.getSuit(), "getSuit() returned incorrect value");
    }

    @Test
    void testDeckBuildDeckAndDealCards() {
        ThreeCardDeck deck = new ThreeCardDeck();
        deck.buildDeck();
        assertEquals(52, deck.getDeckSize(), "Deck should contain 52 cards after call to buildDeck()");

        ArrayList<String> cards = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            Card c = deck.dealCard();
            cards.add(c.toString());
            assertEquals(52 - i - 1, deck.getDeckSize(), "Deck incorrectly decrements after call to dealCard(");
        }
        assertEquals(0, deck.getDeckSize(), "Deck should contain 0 cards after 52 calls to dealCard()");

        for (int suit = 0; suit < 4; suit++) {
            for (int rank = 2; rank < 15; rank++) {
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
                    case 0:
                        suitString = "Clubs";
                        break;
                    case 1:
                        suitString = "Diamonds";
                        break;
                    case 2:
                        suitString = "Hearts";
                        break;
                    case 3:
                        suitString = "Spades";
                        break;
                }

                String expected = rankString + "_of_" + suitString;
                assertTrue(cards.contains(expected), "Missing card: " + expected);
            }
        }
    }

    @Test
    void testLogicIsPair() {
        ThreeCardLogic logic = new ThreeCardLogic();

        ArrayList<Card> three1 = new ArrayList<>();
        three1.add(new Card(1, 'C'));
        three1.add(new Card(1, 'D'));
        three1.add(new Card(2, 'H'));
        assertTrue(logic.isPair(three1), "Three cards contain a pair");

        ArrayList<Card> three2 = new ArrayList<>();
        three2.add(new Card(6, 'S'));
        three2.add(new Card(3, 'H'));
        three2.add(new Card(6, 'D'));
        assertTrue(logic.isPair(three2), "Three cards contain a pair");

        ArrayList<Card> three3 = new ArrayList<>();
        three3.add(new Card(5, 'C'));
        three3.add(new Card(7, 'S'));
        three3.add(new Card(9, 'C'));
        assertFalse(logic.isPair(three3), "Three cards do not contain a pair");

        ArrayList<Card> three4 = new ArrayList<>();
        three4.add(new Card(2, 'D'));
        three4.add(new Card(4, 'D'));
        three4.add(new Card(6, 'H'));
        assertFalse(logic.isPair(three4), "Three cards do not contain a pair");
    }

    @Test
    void testLogicIsFlush() {
        ThreeCardLogic logic = new ThreeCardLogic();

        ArrayList<Card> three1 = new ArrayList<>();
        three1.add(new Card(1, 'H'));
        three1.add(new Card(3, 'H'));
        three1.add(new Card(5, 'H'));
        assertTrue(logic.isFlush(three1), "Three cards contain a flush");

        ArrayList<Card> three2 = new ArrayList<>();
        three2.add(new Card(2, 'S'));
        three2.add(new Card(4, 'S'));
        three2.add(new Card(6, 'S'));
        assertTrue(logic.isFlush(three2), "Three cards contain a flush");

        ArrayList<Card> three3 = new ArrayList<>();
        three3.add(new Card(5, 'C'));
        three3.add(new Card(7, 'S'));
        three3.add(new Card(9, 'C'));
        assertFalse(logic.isFlush(three3), "Three cards do not contain a flush");

        ArrayList<Card> three4 = new ArrayList<>();
        three4.add(new Card(2, 'D'));
        three4.add(new Card(4, 'D'));
        three4.add(new Card(6, 'H'));
        assertFalse(logic.isFlush(three4), "Three cards do not contain a flush");
    }

    @Test
    void testLogicIsStraight() {
        ThreeCardLogic logic = new ThreeCardLogic();

        ArrayList<Card> three1 = new ArrayList<>();
        three1.add(new Card(1, 'C'));
        three1.add(new Card(2, 'D'));
        three1.add(new Card(3, 'H'));
        assertTrue(logic.isStraight(three1), "Three cards contain a straight");

        ArrayList<Card> three2 = new ArrayList<>();
        three2.add(new Card(12, 'S'));
        three2.add(new Card(11, 'H'));
        three2.add(new Card(13, 'D'));
        assertTrue(logic.isStraight(three2), "Three cards contain a straight");

        ArrayList<Card> three3 = new ArrayList<>();
        three3.add(new Card(5, 'C'));
        three3.add(new Card(7, 'S'));
        three3.add(new Card(9, 'C'));
        assertFalse(logic.isStraight(three3), "Three cards do not contain a straight");

        ArrayList<Card> three4 = new ArrayList<>();
        three4.add(new Card(2, 'D'));
        three4.add(new Card(4, 'D'));
        three4.add(new Card(6, 'H'));
        assertFalse(logic.isStraight(three4), "Three cards do not contain a straight");
    }

    @Test
    void testLogicIsThreeOfAKind() {
        ThreeCardLogic logic = new ThreeCardLogic();

        ArrayList<Card> three1 = new ArrayList<>();
        three1.add(new Card(12, 'C'));
        three1.add(new Card(12, 'D'));
        three1.add(new Card(12, 'H'));
        assertTrue(logic.isThreeOfAKind(three1), "Three cards contain a three of a kind");

        ArrayList<Card> three2 = new ArrayList<>();
        three2.add(new Card(13, 'S'));
        three2.add(new Card(13, 'H'));
        three2.add(new Card(13, 'D'));
        assertTrue(logic.isThreeOfAKind(three2), "Three cards contain a three of a kind");

        ArrayList<Card> three3 = new ArrayList<>();
        three3.add(new Card(5, 'C'));
        three3.add(new Card(7, 'S'));
        three3.add(new Card(9, 'C'));
        assertFalse(logic.isThreeOfAKind(three3), "Three cards do not contain a three of a kind");

        ArrayList<Card> three4 = new ArrayList<>();
        three4.add(new Card(2, 'D'));
        three4.add(new Card(4, 'D'));
        three4.add(new Card(6, 'H'));
        assertFalse(logic.isThreeOfAKind(three4), "Three cards do not contain a three of a kind");
    }

    @Test
    void testLogicIsStraightFlush() {
        ThreeCardLogic logic = new ThreeCardLogic();

        ArrayList<Card> three1 = new ArrayList<>();
        three1.add(new Card(1, 'C'));
        three1.add(new Card(2, 'C'));
        three1.add(new Card(3, 'C'));
        assertTrue(logic.isStraightFlush(three1), "Three cards contain a straight flush");

        ArrayList<Card> three2 = new ArrayList<>();
        three2.add(new Card(12, 'S'));
        three2.add(new Card(11, 'S'));
        three2.add(new Card(13, 'S'));
        assertTrue(logic.isStraightFlush(three2), "Three cards contain a straight flush");

        ArrayList<Card> three3 = new ArrayList<>();
        three3.add(new Card(5, 'C'));
        three3.add(new Card(6, 'S'));
        three3.add(new Card(7, 'C'));
        assertFalse(logic.isStraightFlush(three3), "Three cards do not contain a straight flush");

        ArrayList<Card> three4 = new ArrayList<>();
        three4.add(new Card(2, 'D'));
        three4.add(new Card(4, 'D'));
        three4.add(new Card(6, 'D'));
        assertFalse(logic.isStraightFlush(three4), "Three cards do not contain a straight flush");
    }

    @Test
    void testLogicEvaluateHand() {
        ThreeCardLogic logic = new ThreeCardLogic();

        ArrayList<Card> three1 = new ArrayList<>();
        three1.add(new Card(1, 'C'));
        three1.add(new Card(1, 'D'));
        three1.add(new Card(2, 'H'));
        assertTrue(logic.isPair(three1), "Three cards contain a pair");
        assertEquals(2, logic.evaluateHand(three1), "Three cards containing pair returns incorrect payout");

        ArrayList<Card> three2 = new ArrayList<>();
        three2.add(new Card(1, 'H'));
        three2.add(new Card(3, 'H'));
        three2.add(new Card(5, 'H'));
        assertTrue(logic.isFlush(three2), "Three cards contain a flush");
        assertEquals(3, logic.evaluateHand(three2), "Three cards containing flush returns incorrect payout");

        ArrayList<Card> three3 = new ArrayList<>();
        three3.add(new Card(1, 'C'));
        three3.add(new Card(2, 'D'));
        three3.add(new Card(3, 'H'));
        assertTrue(logic.isStraight(three3), "Three cards contain a straight");
        assertEquals(4, logic.evaluateHand(three3), "Three cards containing straight returns incorrect payout");

        ArrayList<Card> three4 = new ArrayList<>();
        three4.add(new Card(12, 'C'));
        three4.add(new Card(12, 'D'));
        three4.add(new Card(12, 'H'));
        assertTrue(logic.isThreeOfAKind(three4), "Three cards contain a three of a kind");
        assertEquals(5, logic.evaluateHand(three4), "Three cards containing three of a kind returns incorrect payout");

        ArrayList<Card> three5 = new ArrayList<>();
        three5.add(new Card(1, 'C'));
        three5.add(new Card(2, 'C'));
        three5.add(new Card(3, 'C'));
        assertTrue(logic.isStraightFlush(three5), "Three cards contain a straight flush");
        assertEquals(6, logic.evaluateHand(three5), "Three cards containing straight flush returns incorrect payout");

        ArrayList<Card> three6 = new ArrayList<>();
        three6.add(new Card(1, 'C'));
        three6.add(new Card(3, 'D'));
        three6.add(new Card(5, 'H'));
        assertEquals(1, logic.evaluateHand(three6), "Three cards containing high card only returns incorrect payout");
    }

    @Test
    void testLogicGetPairPlusPayout() {
        ThreeCardLogic logic = new ThreeCardLogic();

        ArrayList<Card> three1 = new ArrayList<>();
        three1.add(new Card(1, 'C'));
        three1.add(new Card(1, 'D'));
        three1.add(new Card(2, 'H'));
        assertTrue(logic.isPair(three1), "Three cards contain a pair");
        assertEquals(1, logic.getPairPlusPayout(three1), "Three cards containing pair returns incorrect payout");

        ArrayList<Card> three2 = new ArrayList<>();
        three2.add(new Card(1, 'H'));
        three2.add(new Card(3, 'H'));
        three2.add(new Card(5, 'H'));
        assertTrue(logic.isFlush(three2), "Three cards contain a flush");
        assertEquals(3, logic.getPairPlusPayout(three2), "Three cards containing flush returns incorrect payout");

        ArrayList<Card> three3 = new ArrayList<>();
        three3.add(new Card(1, 'C'));
        three3.add(new Card(2, 'D'));
        three3.add(new Card(3, 'H'));
        assertTrue(logic.isStraight(three3), "Three cards contain a straight");
        assertEquals(6, logic.getPairPlusPayout(three3), "Three cards containing straight returns incorrect payout");

        ArrayList<Card> three4 = new ArrayList<>();
        three4.add(new Card(12, 'C'));
        three4.add(new Card(12, 'D'));
        three4.add(new Card(12, 'H'));
        assertTrue(logic.isThreeOfAKind(three4), "Three cards contain a three of a kind");
        assertEquals(30, logic.getPairPlusPayout(three4), "Three cards containing three of a kind returns incorrect payout");

        ArrayList<Card> three5 = new ArrayList<>();
        three5.add(new Card(1, 'C'));
        three5.add(new Card(2, 'C'));
        three5.add(new Card(3, 'C'));
        assertTrue(logic.isStraightFlush(three5), "Three cards contain a straight flush");
        assertEquals(40, logic.getPairPlusPayout(three5), "Three cards containing straight flush returns incorrect payout");

        ArrayList<Card> three6 = new ArrayList<>();
        three6.add(new Card(1, 'C'));
        three6.add(new Card(3, 'D'));
        three6.add(new Card(5, 'H'));
        assertEquals(0, logic.getPairPlusPayout(three6), "Three cards containing high card only returns incorrect payout");
    }

    @Test
    void testLogicCompareHandDealerWins() {
        ThreeCardLogic logic = new ThreeCardLogic();

        ArrayList<Card> player1 = new ArrayList<>();
        player1.add(new Card(2, 'C'));
        player1.add(new Card(3, 'D'));
        player1.add(new Card(5, 'H'));
        ArrayList<Card> dealer1 = new ArrayList<>();
        dealer1.add(new Card(1, 'C'));
        dealer1.add(new Card(2, 'D'));
        dealer1.add(new Card(3, 'H'));
        assertEquals(1, logic.compareHand(dealer1, player1), "compareHand() returns incorrect winner");

        ArrayList<Card> player2 = new ArrayList<>();
        player2.add(new Card(1, 'C'));
        player2.add(new Card(1, 'D'));
        player2.add(new Card(2, 'H'));
        ArrayList<Card> dealer2 = new ArrayList<>();
        dealer2.add(new Card(12, 'C'));
        dealer2.add(new Card(12, 'D'));
        dealer2.add(new Card(12, 'H'));
        assertEquals(1, logic.compareHand(dealer2, player2), "compareHand() returns incorrect winner");

        ArrayList<Card> player3 = new ArrayList<>();
        player3.add(new Card(1, 'H'));
        player3.add(new Card(3, 'H'));
        player3.add(new Card(5, 'H'));
        ArrayList<Card> dealer3 = new ArrayList<>();
        dealer3.add(new Card(1, 'C'));
        dealer3.add(new Card(2, 'C'));
        dealer3.add(new Card(3, 'C'));
        assertEquals(1, logic.compareHand(dealer3, player3), "compareHand() returns incorrect winner");
    }

    @Test
    void testLogicCompareHandPlayerWins() {
        ThreeCardLogic logic = new ThreeCardLogic();

        ArrayList<Card> player1 = new ArrayList<>();
        player1.add(new Card(1, 'C'));
        player1.add(new Card(2, 'D'));
        player1.add(new Card(3, 'H'));
        ArrayList<Card> dealer1 = new ArrayList<>();
        dealer1.add(new Card(2, 'C'));
        dealer1.add(new Card(3, 'D'));
        dealer1.add(new Card(5, 'H'));
        assertEquals(2, logic.compareHand(dealer1, player1), "compareHand() returns incorrect winner");

        ArrayList<Card> player2 = new ArrayList<>();
        player2.add(new Card(12, 'C'));
        player2.add(new Card(12, 'D'));
        player2.add(new Card(12, 'H'));
        ArrayList<Card> dealer2 = new ArrayList<>();
        dealer2.add(new Card(1, 'C'));
        dealer2.add(new Card(1, 'D'));
        dealer2.add(new Card(2, 'H'));
        assertEquals(2, logic.compareHand(dealer2, player2), "compareHand() returns incorrect winner");

        ArrayList<Card> player3 = new ArrayList<>();
        player3.add(new Card(1, 'C'));
        player3.add(new Card(2, 'C'));
        player3.add(new Card(3, 'C'));
        ArrayList<Card> dealer3 = new ArrayList<>();
        dealer3.add(new Card(1, 'H'));
        dealer3.add(new Card(3, 'H'));
        dealer3.add(new Card(5, 'H'));
        assertEquals(2, logic.compareHand(dealer3, player3), "compareHand() returns incorrect winner");
    }

    @Test
    void testLogicCompareHandTiesAndHighCards() {
        ThreeCardLogic logic = new ThreeCardLogic();

        ArrayList<Card> player1 = new ArrayList<>();
        player1.add(new Card(1, 'C'));
        player1.add(new Card(3, 'D'));
        player1.add(new Card(5, 'H'));
        ArrayList<Card> dealer1 = new ArrayList<>();
        dealer1.add(new Card(7, 'C'));
        dealer1.add(new Card(9, 'D'));
        dealer1.add(new Card(11, 'H'));
        assertEquals(1, logic.compareHand(dealer1, player1), "compareHand() returns incorrect winner");

        ArrayList<Card> player2 = new ArrayList<>();
        player2.add(new Card(12, 'C'));
        player2.add(new Card(2, 'D'));
        player2.add(new Card(1, 'H'));
        ArrayList<Card> dealer2 = new ArrayList<>();
        dealer2.add(new Card(10, 'C'));
        dealer2.add(new Card(8, 'D'));
        dealer2.add(new Card(4, 'H'));
        assertEquals(2, logic.compareHand(dealer2, player2), "compareHand() returns incorrect winner");

        ArrayList<Card> player3 = new ArrayList<>();
        player3.add(new Card(1, 'C'));
        player3.add(new Card(2, 'C'));
        player3.add(new Card(3, 'C'));
        ArrayList<Card> dealer3 = new ArrayList<>();
        dealer3.add(new Card(1, 'H'));
        dealer3.add(new Card(2, 'H'));
        dealer3.add(new Card(3, 'H'));
        assertEquals(0, logic.compareHand(dealer3, player3), "compareHand() returns incorrect winner (result is a tie)");

        ArrayList<Card> player4 = new ArrayList<>();
        player4.add(new Card(1, 'C'));
        player4.add(new Card(2, 'C'));
        player4.add(new Card(3, 'C'));
        player4.add(new Card(4, 'C'));
        ArrayList<Card> dealer4 = new ArrayList<>();
        dealer4.add(new Card(1, 'H'));
        dealer4.add(new Card(2, 'H'));
        dealer4.add(new Card(3, 'H'));
        dealer4.add(new Card(4, 'H'));
        assertEquals(-1, logic.compareHand(dealer4, player4), "compareHand() returns incorrect winner (hands are not three cards)");
    }

    @Test
    void testRoundEvaluateRoundDealerNotQualified() {
        ThreeCardRound round = new ThreeCardRound();

        ArrayList<Card> pHand = new ArrayList<>();
        pHand.add(new Card(7, 'H'));
        pHand.add(new Card(7, 'S'));
        pHand.add(new Card(3, 'D'));

        ArrayList<Card> dHand = new ArrayList<>();
        dHand.add(new Card(4, 'C'));
        dHand.add(new Card(6, 'D'));
        dHand.add(new Card(9, 'H'));

        round.setHands(pHand, dHand);

        int result = round.evaluateRound(10, 5, 10, false);
        assertEquals(5, result, "Incorrect result after dealer not qualified");
    }

    @Test
    void testRoundEvaluateRoundFold() {
        ThreeCardRound round = new ThreeCardRound();

        ArrayList<Card> p = new ArrayList<>();
        p.add(new Card(5, 'C'));
        p.add(new Card(8, 'D'));
        p.add(new Card(9, 'H'));

        ArrayList<Card> d = new ArrayList<>();
        d.add(new Card(10, 'C'));
        d.add(new Card(11, 'D'));
        d.add(new Card(2, 'H'));

        round.setHands(p, d);

        int result = round.evaluateRound(10, 5, 10, true);
        assertEquals(-15, result, "Incorrect result after folding");
    }

    @Test
    void testRoundEvaluateRoundPlayerWins() {
        ThreeCardRound round = new ThreeCardRound();

        ArrayList<Card> p = new ArrayList<>();
        p.add(new Card(5, 'H'));
        p.add(new Card(6, 'C'));
        p.add(new Card(7, 'D'));

        ArrayList<Card> d = new ArrayList<>();
        d.add(new Card(12, 'S'));
        d.add(new Card(9, 'H'));
        d.add(new Card(4, 'C'));

        round.setHands(p, d);

        int result = round.evaluateRound(10, 0, 10, false);
        assertEquals(40, result, "Incorrect result after player win");
        assertEquals(20, round.getAnteResult(), "Ante payout incorrect");
        assertEquals(20, round.getPlayResult(), "Play payout incorrect");
    }

    @Test
    void testRoundEvaluateRoundDealerWins() {
        ThreeCardRound round = new ThreeCardRound();

        ArrayList<Card> p = new ArrayList<>();
        p.add(new Card(2, 'C'));
        p.add(new Card(5, 'D'));
        p.add(new Card(9, 'H'));

        ArrayList<Card> d = new ArrayList<>();
        d.add(new Card(12, 'C'));
        d.add(new Card(12, 'D'));
        d.add(new Card(3, 'H'));

        round.setHands(p, d);

        int result = round.evaluateRound(10, 0, 10, false);
        assertEquals(-20, result, "Incorrect result after dealer win");
        assertEquals(-10, round.getAnteResult(), "Ante loss incorrect");
        assertEquals(-10, round.getPlayResult(), "Play loss incorrect");
    }

    @Test
    void testRoundEvaluateRoundPairPlusLoss() {
        ThreeCardRound round = new ThreeCardRound();

        ArrayList<Card> p = new ArrayList<>();
        p.add(new Card(3, 'C'));
        p.add(new Card(6, 'D'));
        p.add(new Card(9, 'S'));

        ArrayList<Card> d = new ArrayList<>();
        d.add(new Card(10, 'H'));
        d.add(new Card(11, 'C'));
        d.add(new Card(12, 'S'));

        round.setHands(p, d);

        int result = round.evaluateRound(10, 5, 10, false);
        assertEquals(-25, result, "Incorrect result after pair plus loss");
        assertEquals(-10, round.getAnteResult(), "Ante loss incorrect");
        assertEquals(-5, round.getPairPlusResult(), "Pair plus loss incorrect");
        assertEquals(-10, round.getPlayResult(), "Play loss incorrect");
    }

    @Test
    void testRoundEvaluateRoundDealerQualifies() {
        ThreeCardRound round = new ThreeCardRound();

        ArrayList<Card> p = new ArrayList<>();
        p.add(new Card(3, 'C'));
        p.add(new Card(7, 'D'));
        p.add(new Card(9, 'S'));

        ArrayList<Card> d = new ArrayList<>();
        d.add(new Card(12, 'H'));
        d.add(new Card(5, 'C'));
        d.add(new Card(2, 'D'));

        round.setHands(p, d);

        int result = round.evaluateRound(10, 0, 10, false);
        assertEquals(-20, result, "Dealer qualifies and wins");
        assertEquals(-10, round.getAnteResult(), "Incorrect ante result");
        assertEquals(-10, round.getPlayResult(), "Incorrect play result");
    }

    @Test
    void testRoundEvaluateRoundStraightFlush() {
        ThreeCardRound round = new ThreeCardRound();

        ArrayList<Card> p = new ArrayList<>();
        p.add(new Card(5, 'H'));
        p.add(new Card(6, 'H'));
        p.add(new Card(7, 'H'));

        ArrayList<Card> d = new ArrayList<>();
        d.add(new Card(12, 'C'));
        d.add(new Card(9, 'D'));
        d.add(new Card(4, 'S'));

        round.setHands(p, d);

        int result = round.evaluateRound(10, 5, 10, false);
        assertEquals(240, result, "Incorrect result after straight flush");
        assertEquals(20, round.getAnteResult(), "Ante result incorrect");
        assertEquals(200, round.getPairPlusResult(), "Pair plus result incorrect");
        assertEquals(20, round.getPlayResult(), "Play result incorrect");
    }

    @Test
    void testRoundEvaluateRoundFlushVsStraight() {
        ThreeCardRound round = new ThreeCardRound();

        ArrayList<Card> dealer = new ArrayList<>();
        ArrayList<Card> player = new ArrayList<>();

        dealer.add(new Card(4, 'H'));
        dealer.add(new Card(9, 'H'));
        dealer.add(new Card(12, 'H'));

        player.add(new Card(7, 'C'));
        player.add(new Card(8, 'D'));
        player.add(new Card(9, 'S'));

        round.setHands(player, dealer);

        int total = round.evaluateRound(10, 0, 10, false);

        assertEquals(40, total, "Incorrect total winnings");
        assertEquals(20, round.getAnteResult(), "Ante result incorrect");
        assertEquals(20, round.getPlayResult(), "Play result incorrect");
    }

    @Test
    void testRoundEvaluateRoundRandomTest() {
        ThreeCardRound round = new ThreeCardRound();

        ArrayList<Card> dealer = new ArrayList<>();
        ArrayList<Card> player = new ArrayList<>();

        dealer.add(new Card(12, 'C'));
        dealer.add(new Card(13, 'D'));
        dealer.add(new Card(14, 'H'));

        player.add(new Card(3, 'S'));
        player.add(new Card(6, 'S'));
        player.add(new Card(9, 'S'));

        round.setHands(player, dealer);

        int total = round.evaluateRound(10, 0, 10, false);
        assertEquals(-20, total, "Incorrect result after straight flush");
        assertEquals(-10, round.getAnteResult(), "Ante result incorrect");
        assertEquals(-10, round.getPlayResult(), "Play result incorrect");
    }

    @Test
    void testRoundEvaluateRoundStraightVsPair() {
        ThreeCardRound round = new ThreeCardRound();

        ArrayList<Card> dealer = new ArrayList<>();
        ArrayList<Card> player = new ArrayList<>();

        dealer.add(new Card(5, 'D'));
        dealer.add(new Card(5, 'H'));
        dealer.add(new Card(13, 'S'));

        player.add(new Card(7, 'C'));
        player.add(new Card(8, 'D'));
        player.add(new Card(9, 'S'));

        round.setHands(player, dealer);

        int total = round.evaluateRound(15, 0, 15, false);
        assertEquals(60, total, "Incorrect total winnings");
        assertEquals(30, round.getAnteResult(), "Ante result incorrect");
        assertEquals(30, round.getPlayResult(), "Play result incorrect");
    }

    @Test
    void testRoundEvaluateRoundPlayerThreeOfAKindFlush() {
        ThreeCardRound round = new ThreeCardRound();

        ArrayList<Card> dealer = new ArrayList<>();
        ArrayList<Card> player = new ArrayList<>();

        dealer.add(new Card(2, 'H'));
        dealer.add(new Card(7, 'H'));
        dealer.add(new Card(14, 'H'));

        player.add(new Card(9, 'C'));
        player.add(new Card(9, 'D'));
        player.add(new Card(9, 'S'));

        round.setHands(player, dealer);

        int total = round.evaluateRound(20, 10, 20, false);
        assertEquals(380, total, "Incorrect result after three of a kind");
        assertEquals(40, round.getAnteResult(), "Ante result incorrect");
        assertEquals(40, round.getPlayResult(), "Play result incorrect");
        assertEquals(300, round.getPairPlusResult(), "Pair plus result incorrect");
    }

}