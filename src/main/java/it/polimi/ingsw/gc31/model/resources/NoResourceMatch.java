package it.polimi.ingsw.gc31.model.resources;


public class NoResourceMatch extends Exception {
    public NoResourceMatch(){
        super("\n Non è stata trovata nessuna corrispondenza \n con il valore immesso e i tipi di risorsa disponibili ");
    }
}
