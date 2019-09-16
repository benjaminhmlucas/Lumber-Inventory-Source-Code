/*
 * This class allows the filter to get the measurment values from the strings in the filter ComboBoxes.
 * It only pulls the first two numbers out of a string with the string in this format: N(NNNN)___N(NNN)
 * Numbers can be variable length but must be separated by 3 spaces.  The inner method finishes when
 * it reaches it reaches the end of the second number.
 */
package Utilities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Computebot
 */
public class MeasurementsParser {   
    
    public static HashMap<Integer,Integer> getHashListFromMeasurmentsList(ArrayList<String> inputList){
        HashMap<Integer,Integer> measurementsList = new HashMap<>();
        ArrayList<Integer> lowHigh;
        for(String dimensions:inputList){
            lowHigh = getFirstTwoNumbersFromMixedString(dimensions);
            measurementsList.put(lowHigh.get(0),lowHigh.get(1));
            System.out.println("Low Number: "+lowHigh.get(0)+" High Number: "+lowHigh.get(1));
        }
        return measurementsList;    
    }
    
    public static ArrayList<Integer> getFirstTwoNumbersFromMixedString(String dimensions){
        ArrayList<Integer> lowHighRange = new ArrayList<>();
        int low=0;
        int high=0;
        int positionStopped = 0;
        StringBuilder numberToParse = new StringBuilder();
        String s = dimensions.replaceAll("[^\\d.]", " ");
        for(int i = 0;i<s.length();i++){                
            if(Character.isDigit(s.charAt(i))){
                numberToParse.append(s.charAt(i));
            }else{
                low = Integer.parseInt(numberToParse.toString());
                positionStopped = i+2;
                break;
            }
        }
        numberToParse = new StringBuilder();
        for(int i = positionStopped;i<s.length();i++){                
            if(Character.isDigit(s.charAt(i))){
                numberToParse.append(s.charAt(i));
            }else{
                high = Integer.parseInt(numberToParse.toString());
                break;
            }
        }
        lowHighRange.add(low);
        lowHighRange.add(high);
        return lowHighRange;
    }
}
