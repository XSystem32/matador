package Service;

public class Dice {

    public int diceValue() {
        int min = 1;
        int max = 6;
        int range = max - min + 1;
        return (int)((Math.random() * range) + min);
    }

}
