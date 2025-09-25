package net.fabricmc.example;

import java.io.*;

public class GoalManager {
    private boolean showoverlay = loadBoolean();
    private String goalString = "Goal: ";
    private String goalAddedString = loadGoal();

    private static final String FILE_NAME_BOOLEAN = "Config_generated_overlay_goal.txt";
    private static final String FILE_NAME_GOAL = "Config_generated_overlay_goal_text.txt";

    public void setoverlayConfig(boolean b){
        showoverlay = b;
        saveBoolean(showoverlay);
    }

    public boolean getoverlayConfig(){
        return loadBoolean();
    }



    public static boolean loadBoolean(){
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

    public static void saveBoolean(boolean a) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME_BOOLEAN))) {
            writer.write(String.valueOf(a));
        } catch (IOException e) {
            System.err.println("Error saving boolean: " + e.getMessage());
        }
    }
    public static void saveGoal(String goalString){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME_GOAL))) {
            writer.write(String.valueOf(goalString));
        } catch (IOException e) {
            System.err.println("Error saving goal string: " + e.getMessage());
        }
    }
    public static String loadGoal(){
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME_GOAL))) {
            String line = reader.readLine();
            if (line != null) {
                return ( line);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading goal string or file not found: " + e.getMessage());
        }
        return "";

    }
    public String GetGoal(){
        return loadGoal();
    }
    public void SetGoal(String text){
        this.goalAddedString = text;
        saveGoal(text);
    }

}
