package it.polimi.ingsw.gc31.view.cli;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public class AsciiProva {

    public static void main(String[] args) {

        AsciiTable at1 = new AsciiTable();
        at1.addRule();
        at1.addRow(null, null, null, "TOWER CARDS");
        at1.addRule();
        at1.addRow("++++CARD++++", "++++CARD++++", "++++CARD++++", "++++CARD++++");
        at1.addRule();
        at1.addRow("++++CARD++++", "++++CARD++++", "++++CARD++++", "++++CARD++++");
        at1.addRule();
        at1.addRow("++++CARD++++", "++++CARD++++", "++++CARD++++", "++++CARD++++");
        at1.addRule();
        at1.addRow("++++CARD++++", "++++CARD++++", "++++CARD++++", "++++CARD++++");
        at1.addRule();
        //at1.setPadding(1);
        at1.setTextAlignment(TextAlignment.CENTER);
        String rend1 = at1.render(80);

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow(rend1, rend1);
        at.addRule();
        System.out.println(at.render(163));
    }
}
