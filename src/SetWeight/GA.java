package SetWeight;

import Main.Window;
import AI.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class GA {
    private static int numberOfObjects = 50;
    private static int numberOfReProduce = 30;
    private static int generationLimit = 1;
    private static final int randomWeightFrom = 1;
    private static final int randomWeightTo = 100000;
    static private class GAObject {
        Weight weight;
        long score;
    };
    public static void main(String[] args) {
        Vector<GAObject> GAobjects = new Vector<>();
        
        // Generation 1
        System.out.println("Generation: 1");
        produceObject(GAobjects, numberOfObjects);
        Collections.sort(GAobjects, new scoreComparator());
        for(int i = 1; i <= numberOfReProduce; i++) GAobjects.remove(numberOfObjects - i);

        // Generation 2+
        for(int generation = 2; generation <= generationLimit; generation++) {
            System.out.println("Generation: " + generation);
            produceObject(GAobjects, numberOfReProduce);
            Collections.sort(GAobjects, new scoreComparator());
            for(int i = 0; i < numberOfReProduce; i++) GAobjects.remove(numberOfObjects - i);
        }
        try { record(GAobjects.get(0)); } catch (Exception e) { e.getStackTrace(); }
    }
    static private void produceObject(Vector<GAObject> GAobjects, int numberOfProduce) {
        for(int i = 1; i <= numberOfProduce; i++) {
            System.out.println("StartGame(index: " + i + ")");
            GAObject object = new GAObject();
            object.weight = SetWeight();
            AI.AI_Tetris ai = new AI.AI_Tetris(object.weight);
            new Window(ai);
            ai.PPS = 1000;
            ai.gameStart();
            
            while(true) {
                if(ai.gameState == GameState.GAME_OVER) {
                    object.score = ai.score;
                    if(ai.numOfUsedBlocks < 3000) ai.score /= 2;
                    break;
                }
            }
            GAobjects.add(object);
            System.out.println("GameEnd(index: " + i + "), score: " + object.score);
        }
    }

    static private void record(GAObject object) throws IOException {
        BufferedOutputStream output = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream("D:/Eclipse/Java/Output.txt", false));
            String str1 = "heightWeight: \n" + object.weight.heightWeight;
            String str2 = "doMakeHoleWeight: \n" + object.weight.doMakeHoleWeight;
            output.write(str1.getBytes()); // Byte형으로만 넣을 수 있음
            output.write(str2.getBytes()); // Byte형으로만 넣을 수 있음
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            output.close();
        }
    }

    static private Weight SetWeight() {
        Weight weight = new Weight();
        weight.heightWeight = randomInt(randomWeightFrom, randomWeightTo);
        weight.doMakeHoleWeight = 3000;
        return weight;
    }
    static private int randomInt(int from, int to) {
        int random = (int)(Math.random()) * (to - from) + from;
        return random;
    }
    static class scoreComparator implements Comparator<GAObject> {
        public int compare(GAObject object1, GAObject object2) {
            if (object1.score > object2.score) {
                return 1;
            } 
            else if(object1.score == object2.score) return 0;
            else return -1;
        }
    }
}
