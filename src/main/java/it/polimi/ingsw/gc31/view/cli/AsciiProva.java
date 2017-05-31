package it.polimi.ingsw.gc31.view.cli;

import de.vandermeer.asciitable.AsciiTable;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;

public class AsciiProva {

    public static void main(String[] args) {

        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow(null, null, null, "TOWER CARDS");
        at.addRule();
        at.addRow("Pino", "Remo", "Mario", "Luca");
        at.addRule();
        at.addRow("Leo", "Gian", "Simo", "Boh");
        at.addRule();

        System.out.println(at.render(120));

    }
}

/*
        String leftAlignFormat = "| %-19s | %-4d |%n";
        System.out.format("+---------------------+------+%n");
        System.out.format("|      Card Name      | ID   |%n");
        System.out.format("+---------------------+------+%n");
        for (int i = 0; i < 5; i++) {
            System.out.format(leftAlignFormat, "some data: " + i, i * i);
        }
        System.out.format("+---------------------+------+%n");
        int[] values = {1 ,1 , 0};
        Card c = new Card(CardColor.GREEN, "Fabbro", values, null, null, null);
        System.out.println(c.toString());
*/