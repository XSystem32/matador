package Service;

import Bean.Player;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;

public class GameBoard {

    private GUI gui;
    private GUI_Player[] guiPlayers;
    private Player[] players;

    public GameBoard() {
        gui = new GUI(customFields());
        setupPlayers();
        movePlayer();
    }

    public GUI_Field[] customFields() {
        GUI_Field[] fieldsDesign = new GUI_Field[20];

        fieldsDesign[0] = new GUI_Start("Start", "", "Hver gang du passerer start, modtager du 2 kr.", Color.GREEN.darker(), Color.BLACK);
        fieldsDesign[1] = new GUI_Street("Burgerbaren", "1M", "Få en burger på parkens bedste burgerbar. ('Bedste' nomineret af egen resturant.)", "1", Color.ORANGE, Color.BLACK);
        fieldsDesign[2] = new GUI_Street("Pizzariaet", "1M", "Hvis munden nu ikke er stor nok til den burger.", "1", Color.ORANGE, Color.BLACK);
        fieldsDesign[3] = new GUI_Street("Slikbutikken", "1M", "Til den lille slikmund.", "1", Color.BLUE.brighter(), Color.BLACK);
        fieldsDesign[4] = new GUI_Street("Iskiosken", "1M", "Hjælper når solen er fremme.", "1", Color.BLUE.brighter(), Color.BLACK);
        fieldsDesign[5] = new GUI_Jail();
        fieldsDesign[5].setSubText("");
        fieldsDesign[6] = new GUI_Street("Museet", "2M", "Både sjovt og lærerigt.", "2", Color.PINK, Color.BLACK);
        fieldsDesign[7] = new GUI_Street("Biblioteket", "2M", "Lærerigt, men ikke så sjovt som museet.", "2", Color.PINK, Color.BLACK);
        fieldsDesign[8] = new GUI_Street("Skaterparken", "3M", "Husk nu hjelmen.", "2", Color.YELLOW.brighter(), Color.BLACK);
        fieldsDesign[9] = new GUI_Street("Svømmingpoolen", "3M", "Ikke løbe ved poolen!.", "2", Color.YELLOW.brighter(), Color.BLACK);
        fieldsDesign[10] = new GUI_Refuge();
        fieldsDesign[10].setTitle("Gratis parkering");
        fieldsDesign[10].setSubText("");
        fieldsDesign[11] = new GUI_Street("Spillehallen", "4M", "Forældrenes undskyldning for deres ludomani.", "3", Color.RED, Color.BLACK);
        fieldsDesign[12] = new GUI_Street("Biografen", "4M", "Brug tid sammen med familien, uden at snakke med dem.", "3", Color.RED, Color.BLACK);
        fieldsDesign[13] = new GUI_Street("Legetøjsbutikken", "4M", "Hvem siger man ikke kan købe kærlighed.", "3", Color.YELLOW, Color.BLACK);
        fieldsDesign[14] = new GUI_Street("Dyrehandlen", "4M", "Hvis familien nu ikke er stor nok.", "3", Color.YELLOW, Color.BLACK);
        fieldsDesign[15] = new GUI_Shipping();
        fieldsDesign[15].setTitle("Gå i fængsel");
        fieldsDesign[15].setSubText("");
        fieldsDesign[16] = new GUI_Street("Bowlinghallen", "5M", "Når far skal vise sig.", "4", Color.GREEN, Color.BLACK);
        fieldsDesign[17] = new GUI_Street("Zoo", "5M", "Det eneste sted dine forældre tillader dyr.", "4", Color.GREEN, Color.BLACK);
        fieldsDesign[18] = new GUI_Street("Vandlandet", "5M", "Samme som poolen, bare dyrer.", "5", Color.BLUE.darker(), Color.WHITE);
        fieldsDesign[19] = new GUI_Street("Strandpromenaden", "5M", "Sand. Alle. Steder.", "5", Color.BLUE.darker(), Color.WHITE);

        return fieldsDesign;
    }


    public GUI_Car vehicleChoice(int playerId) {
        String vehicle = gui.getUserSelection("Vælg din transport", "Bil", "Traktor", "UFO");
        Color color = getVehicleColor(playerId);
        if (vehicle.equals("Bil")) {
            return new GUI_Car(color, color, GUI_Car.Type.CAR, GUI_Car.Pattern.ZEBRA);
        } else if (vehicle.equals("Traktor")) {
            return new GUI_Car(color, color, GUI_Car.Type.TRACTOR, GUI_Car.Pattern.FILL);
        } else if (vehicle.equals("UFO")) {
            return new GUI_Car(color, color, GUI_Car.Type.UFO, GUI_Car.Pattern.FILL);
        }
        return new GUI_Car(color, color, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL);
    }

    public Color getVehicleColor(int playerId) {
        switch (playerId) {
            case 1:
                return Color.RED;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.YELLOW;
            default:
                return Color.BLUE;
        }
    }

    public void setupPlayers() {
        int number;
        String playerName;
        number=  Integer.parseInt(requestNumberOfPlayers());
        int balance=24 - 2 * number;

        players = new Player[number];

        for (int i = 0; i < number; i++) {

            playerName = messageToPlayer("Spiller " + (i + 1) + ", indtast dit navn");
            if (playerName.equals("")) {
                playerName = "Spiller" + (i + 1);
            }

            players[i] = new Player(i, playerName, balance);
            addUIPlayer(players[i], number);
        }
    }

    public String requestNumberOfPlayers() {
        return  gui.getUserSelection("Hvor mange spillere skal der være?", "2", "3","4");
    }

    public String messageToPlayer(String message) {
        return gui.getUserString(message);
    }

    public void addUIPlayer(Player player, int amountOfPlayers) {
        if (guiPlayers == null) {
            guiPlayers = new GUI_Player[amountOfPlayers];
        }
        guiPlayers[player.getPlayerId()] = new GUI_Player(player.getName(), player.getBalance().getAmount(), vehicleChoice(player.getPlayerId()));
        gui.addPlayer(guiPlayers[player.getPlayerId()]);
    }

    public void rollDice() {
        Dice dice = new Dice();
        gui.getUserButtonPressed("", "Kast terningerne");
        gui.setDice(dice.diceValue(),dice.diceValue());
    }

    public void movePlayer() {
        Dice dice = new Dice();
        GUI_Field[] fields = gui.getFields();

        gui.getUserButtonPressed("Kast terningerne", "Kast");

        int a = dice.diceValue();
        int b = dice.diceValue();

        int c = a + b;

        gui.setDice(a, b);

        for (GUI_Player guiPlayer : guiPlayers) {

            guiPlayer.getCar().setPosition(fields[0]);

            switch (c) {
                case 2:
                    guiPlayer.setBalance(guiPlayer.getBalance() + 10);
                    guiPlayer.getCar().setPosition(fields[1]);
                    break;
                case 3:
                    guiPlayer.setBalance(guiPlayer.getBalance() + 15);
                    guiPlayer.getCar().setPosition(fields[3]);
                    break;
                case 4:
                    guiPlayer.setBalance(guiPlayer.getBalance() - 20);
                    guiPlayer.getCar().setPosition(fields[5]);
                    break;
                case 5:
                    guiPlayer.setBalance(guiPlayer.getBalance() + 5);
                    guiPlayer.getCar().setPosition(fields[7]);
                    break;
                case 6:
                    guiPlayer.setBalance(guiPlayer.getBalance() + 50);
                    guiPlayer.getCar().setPosition(fields[10]);
                    break;
                case 7:
                    guiPlayer.setBalance(guiPlayer.getBalance() + 25);
                    guiPlayer.getCar().setPosition(fields[15]);
                    break;
                case 8:
                    guiPlayer.setBalance(guiPlayer.getBalance() + 12);
                    guiPlayer.getCar().setPosition(fields[11]);
                    break;
                case 9:
                    guiPlayer.setBalance(guiPlayer.getBalance() + 18);
                    guiPlayer.getCar().setPosition(fields[13]);
                    break;
                case 10:
                    guiPlayer.setBalance(guiPlayer.getBalance() + 9);
                    guiPlayer.getCar().setPosition(fields[19]);
                    break;
                case 11:
                    guiPlayer.setBalance(guiPlayer.getBalance() + 33);
                    guiPlayer.getCar().setPosition(fields[6]);
                    break;
                case 12:
                    guiPlayer.setBalance(guiPlayer.getBalance() + 44);
                    guiPlayer.getCar().setPosition(fields[8]);
                    break;
            }
        }

    }

}
