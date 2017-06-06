package it.polimi.ingsw.gc31.view.cli;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.PlayerColor;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.cards.CardColor;
import it.polimi.ingsw.gc31.model.resources.NoResourceMatch;

import java.util.UUID;

public class AsciiProva {

    public static void main(String[] args) throws NoResourceMatch {

        Card c1 = new Card(CardColor.GREEN, "Possedimento", 13, 2, null);
        Card c2 = new Card(CardColor.GREEN, "Ambassador", 72, 3, null);
        Card c3 = new Card(CardColor.YELLOW, "Cathedral", 48, 3, null);
        Card c4 = new Card(CardColor.PURPLE, "Sostegno al Papa", 96, 3, null);

        Player p1 = new Player(UUID.randomUUID(),"Matru", PlayerColor.BLUE);

        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow(c1.toString(), c2.toString(), c3.toString(), c4.toString(), "STUFF STUFF STUFF STUFF STUFF STUFF STUFF STUFF STUFF STUFF STUFF STUFF ");
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_FixedWidth().add(20).add(20).add(20).add(20).add(60));
        System.out.println(at.render());

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