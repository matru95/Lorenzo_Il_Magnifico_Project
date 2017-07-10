package it.polimi.ingsw.gc31.model.effects;


import it.polimi.ingsw.gc31.enumerations.CardColor;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.messages.ServerMessage;
import it.polimi.ingsw.gc31.messages.ServerMessageEnum;
import it.polimi.ingsw.gc31.model.Player;
import it.polimi.ingsw.gc31.model.board.SpaceWrapper;
import it.polimi.ingsw.gc31.model.board.Tower;
import it.polimi.ingsw.gc31.model.board.TowerSpaceWrapper;
import it.polimi.ingsw.gc31.model.cards.Card;
import it.polimi.ingsw.gc31.model.effects.permanent.Malus;
import it.polimi.ingsw.gc31.model.resources.Resource;
import jdk.nashorn.internal.ir.ObjectNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeCardEffect extends Effect  {
    private CardColor freeCardColor;
    private int diceActionValue;
    private List<Resource> resources;
    private List<SpaceWrapper> positions;

    public FreeCardEffect(CardColor freeCardColor, int diceActionValue, List<Resource> resources){
        this.freeCardColor = freeCardColor;
        this.diceActionValue = diceActionValue;
        this.resources = resources;
        this.positions = new ArrayList<>();
    }

    private void setPositions(Player player) {
        Map<CardColor, Tower> towers = player.getBoard().getTowers();

        if(freeCardColor != null) {
            Tower tower = towers.get(this.freeCardColor);
            positions = tower.getTowerSpaces();
        } else {

            for(Map.Entry<CardColor, Tower> towerEntry: towers.entrySet()) {

                List<SpaceWrapper> towerSpaces = towerEntry.getValue().getTowerSpaces();

                for(SpaceWrapper towerSpace: towerSpaces) {
                    positions.add(towerSpace);
                }
            }
        }
    }

    @Override
    public ServerMessage exec(Player player) {
        Map<String, String> payload = new HashMap<>();
        List<Card> towerCards = new ArrayList<>();
        List<Card> towerCardsFiltered = new ArrayList<>();
        int playerServants = player.getRes().get(ResourceName.SERVANTS).getNumOf();
        int possibleValue = this.diceActionValue + playerServants;

        setPositions(player);

        for (SpaceWrapper spaceWrapper : positions) {
            int spaceDiceBond = spaceWrapper.getDiceBond();

            if (spaceDiceBond <= possibleValue) {

                if (((TowerSpaceWrapper) spaceWrapper).getCard() != null && ((TowerSpaceWrapper) spaceWrapper).getCard().getCardID() != 0) {
                    towerCards.add(((TowerSpaceWrapper) spaceWrapper).getCard());
                }
            }
        }

        towerCardsFiltered = filterCards(player, towerCards);

        for (Card card : towerCardsFiltered) {
            payload.put(card.getCardName(), String.valueOf(card.getCardID()));
        }

        return new ServerMessage(ServerMessageEnum.FREECARDREQUEST, payload);
    }

    private List<Card> filterCards(Player player, List<Card> unfilteredCards) {
        Map<ResourceName, Resource> tempPlayerRes = new HashMap<>();
        List<Card> filteredCards = new ArrayList<>();

        for(Map.Entry<ResourceName, Resource> singleResourceEntry: player.getRes().entrySet()) {
            tempPlayerRes.put(singleResourceEntry.getKey(), new Resource(singleResourceEntry.getKey(), singleResourceEntry.getValue().getNumOf()));
        }

        for(Resource resource: resources) {
            tempPlayerRes.get(resource.getResourceName()).addNumOf(resource.getNumOf());
        }

        for(Card card: unfilteredCards) {
            if(card.isAffordable(tempPlayerRes)) {
                filteredCards.add(card);
            }
        }

        return filteredCards;

    }

    @Override
    public ObjectNode toJson() {
        return null;
    }
}
