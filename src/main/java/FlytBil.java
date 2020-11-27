import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import gui_main.GUI;

import java.util.ArrayList;

/**
 *  Simple test setup, to easily move cars around the Board
 */
public class FlytBil {

    private static final int NUM_PLAYERS = 4;

    public static void main(String[] args) {

        GUI gui = new GUI();

        // Liste af navne til dropdown menuen
        ArrayList<String> fieldNames = new ArrayList();
        for( int i=0; i<40; i++)
            fieldNames.add("Felt " + i);


        // Opretter spillere
        ArrayList<GUI_Player> players = new ArrayList();
        ArrayList<String> playerNames = new ArrayList();
        for( int i=0; i<NUM_PLAYERS; i++ ) {
            String playerName = "Player " + (i+1);
            playerNames.add(playerName);

            GUI_Player player = new GUI_Player(playerName);
            players.add(player);

            gui.addPlayer(player);
        }

        // Bliver ved med at spørge om input
        while(true) {

            // Indlæs hvilken spiller der skal rykkes
            String button = gui.getUserButtonPressed("Choose player to move",
                    playerNames.toArray(new String[0]));

            // Indlæs hvilket felt spilleren skal rykke til
            GUI_Player playerToMove = players.get( playerNames.indexOf(button) );
            String targetFieldName = gui.getUserSelection("Choose field to move to",
                    fieldNames.toArray(new String[0])
            );
            GUI_Field targetField = gui.getFields()[fieldNames.indexOf(targetFieldName)];

            // Rykker spillerens bil
            playerToMove.getCar().setPosition(targetField);
        }

    }

}