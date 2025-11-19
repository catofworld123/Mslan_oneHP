package net.fabricmc.halfaheart;

import java.io.*;

public class AttemptCounterBase {
    private int ATTEMPT_NUMBER = loadNumber();
    public boolean AddAttemptUponNewWorldCreation =   loadBooleanA();
    public boolean AddAttemptUponDeath =  loadBooleanB();
    public boolean overlayon = loadBooleanC();

    public void setoverlayConfig(boolean b){
        overlayon = b;
        saveBooleanC(overlayon);
    }
    public boolean getoverlayConfig(){
        return loadBooleanC();
    }


    public void AddAttempt() {
        ATTEMPT_NUMBER = ATTEMPT_NUMBER + 1;
        saveNumber(ATTEMPT_NUMBER);
    }

    public void SetAttemptOverride(int number){
        ATTEMPT_NUMBER = number;
        saveNumber(ATTEMPT_NUMBER);
    }

    public void ResetAttemptCounter(){
        ATTEMPT_NUMBER = 0;
        saveNumber(ATTEMPT_NUMBER);
    }


    public int getAttemptNumber(){
        return loadNumber();
    }

    private static final String FILE_NAME = "saved_number.txt";
    private static final String FILE_NAME_BOOLEAN = "Config_generated.txt";
    private static final String FILE_NAME_BOOLEAN_C = "Config_generated_overlay.txt";

    public static void saveNumber(int number) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(String.valueOf(number));
        } catch (IOException e) {
            System.err.println("Error saving number: " + e.getMessage());
        }
    }

    public static int loadNumber() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading number or file not found: " + e.getMessage());
        }
        return 0;
    }

    public static void saveBooleans(boolean a, boolean b) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME_BOOLEAN))) {
            writer.write(String.valueOf(a));
            writer.newLine();
            writer.write(String.valueOf(b));
        } catch (IOException e) {
            System.err.println("Error saving boolean: " + e.getMessage());
        }

    }
    public static boolean loadBooleanA(){
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME_BOOLEAN))) {
            String line = reader.readLine();
            if (line != null) {
                return Boolean.parseBoolean(line);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading boolean or file not found: " + e.getMessage());
        }
        return false;

    }
    public static boolean loadBooleanB(){
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME_BOOLEAN))) {

            String line = reader.readLine();
            if (line != null) {
                String line2 = reader.readLine();
                return Boolean.parseBoolean(line2);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading boolean or file not found: " + e.getMessage());
        }
        return false;

    }
    public static void saveBooleanC(boolean c) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME_BOOLEAN_C))) {
            writer.write(String.valueOf(c));
        } catch (IOException e) {
            System.err.println("Error saving boolean: " + e.getMessage());
        }

    }
    public static boolean loadBooleanC(){
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME_BOOLEAN_C))) {
            String line = reader.readLine();
                return Boolean.parseBoolean(line);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading boolean or file not found: " + e.getMessage());
        }
        return false;

    }
}
