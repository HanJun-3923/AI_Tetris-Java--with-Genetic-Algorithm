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
    private static int generationLimit = 5;
    private static final int randomWeightFrom = 1;
    private static final int randomWeightTo = 100000;
    static private class GAObject {
        Weight weight;
        double score;
    };
    public static void main(String[] args) {
        Vector<GAObject> GAobjects = new Vector<>();
        
        // Generation 1
        System.out.println("System: Generation: 1");

        produceObject(GAobjects, numberOfObjects);
        System.out.println("System: Complete Creating Objects");

        Collections.sort(GAobjects, new scoreComparator());
        System.out.println("System: Complete Sorting Objects");

        for(int i = 1; i <= numberOfReProduce; i++) GAobjects.remove(numberOfObjects - i);
        System.out.println("System: Complete Removing Objects");

        for(int i = 1; i <= GAobjects.size(); i++) {
            System.out.println(i + ". " + GAobjects.get(i - 1).score);
        }
        
        // Generation 2+
        for(int generation = 2; generation <= generationLimit; generation++) {
            try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println("Generation: " + generation);
            produceObject(GAobjects, numberOfReProduce);
            System.out.println("System: Complete Creating Objects");

            Collections.sort(GAobjects, new scoreComparator());
            System.out.println("System: Complete Sorting Objects");

            for(int i = 1; i <= numberOfReProduce; i++) GAobjects.remove(numberOfObjects - i);
            System.out.println("System: Complete Removing Objects");

            for(int i = 1; i <= GAobjects.size(); i++) {
                System.out.println(i + ". " + GAobjects.get(i - 1).score);
            }

        }
        try { 
            System.out.println("System: Start Saving");
            saveWeight(GAobjects.get(0)); 
        } catch (Exception e) { e.printStackTrace(); }
    }


    static private void produceObject(Vector<GAObject> GAobjects, int numberOfProduce) {
        for(int i = 1; i <= numberOfProduce; i++) {
            GAObject object = new GAObject();
            object.weight = SetWeight();
            AI.AI_Tetris ai = new AI.AI_Tetris(object.weight);
            ai.PPS = 900;
            new Window(ai);
            System.out.println("StartGame(index: " + i + ")");
            ai.gameStart();
            
            while(true) {
                if(ai.gameState == GameState.GAME_OVER) {
                    object.score = ai.score * (ai.numOfUsedBlocks / 1000.0);
                    break;
                }
                else if(ai.numOfUsedBlocks > 3000) {
                    ai.gameState = GameState.GAME_OVER;
                    object.score = ai.score * (ai.numOfUsedBlocks / 1000.0);
                    break;
                }
                else {
                    try { 
                        Thread.sleep(5); 
                    } catch (InterruptedException e) { 
                        e.printStackTrace(); 
                    }
                    continue;
                }
            }
            GAobjects.add(object);
            System.out.println("GameEnd(index: " + i + "), Info: " + (int)object.score + ", " + object.weight.heightWeight + ", " + object.weight.doMakeHoleWeight);
        }
    }

    static private void saveWeight(GAObject object) throws IOException {
        BufferedOutputStream output = null;
        try {
            final String filePath = "src/PlayData/PlayData.txt";
            output = new BufferedOutputStream(new FileOutputStream(filePath, false));
            String[] str = new String[5];
            
            int i = 0;
            str[i++] = "heightWeight: " + (int)object.weight.heightWeight + "\n";
            str[i++] = "doMakeHoleWeight: " + (int)object.weight.doMakeHoleWeight + "\n";
            str[i++] = "lineClearWeight[1]: " + (int)object.weight.lineClearWeight[0] + "\n";
            str[i++] = "lineClearWeight[2]: " + (int)object.weight.lineClearWeight[1] + "\n";
            str[i++] = "lineClearWeight[3]: " + (int)object.weight.lineClearWeight[2] + "\n";

            for(i = 0; ;i++) {
                try {
                    output.write(str[i++].getBytes());
                } catch (Exception e) {
                    break;
                }
            }
            System.out.println("System: Complete Saving");
        } catch (Exception e) {
            System.out.println("System: Failed to save");
            e.getCause();
            e.printStackTrace();
        } finally {
            output.close();
        }
    }

    static private Weight SetWeight() {
        Weight weight = new Weight();
        weight.heightWeight = randomInt(randomWeightFrom, randomWeightTo);
        weight.doMakeHoleWeight = randomInt(randomWeightFrom, randomWeightTo);;
        for(int i = 0; i < 3; i++) 
            weight.lineClearWeight[i] = randomInt(randomWeightFrom, randomWeightTo);;
        return weight;
    }
    static private int randomInt(int from, int to) {
        int random = (int)((Math.random()) * (to - from) + from);
        return random;
    }
    static class scoreComparator implements Comparator<GAObject> {
        public int compare(GAObject object1, GAObject object2) {
            if (object1.score > object2.score) {
                return -1;
            } 
            else if(object1.score == object2.score) return 0;
            else return 1;
        }
    }
}
