/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Database.DAO;
import Objects.InStockSlab;
import Objects.SoldSlab;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Computebot
 */
public class SampleData {

    private final static ArrayList<String> allAvailableWoodCodes = DAO.getAllAvailableWoodCodes();
    private final static ArrayList<String> mainLocationsList = new ArrayList<>(Arrays.asList("Big Shed","Big Field","LittleShed"));
    private final static ArrayList<String> subAreaLocationsList = new ArrayList<>(Arrays.asList("Inside","Front Side","Front","Customer Bins","Back Side","Back"));
    private final static ArrayList<String> customerList = new ArrayList<>(Arrays.asList("Groompa","George Harrison","Franny Poot","Big Clem","Lil\' Peanut","Gordy Tu-Shues"));
    private final static ArrayList<Integer> rowList = new ArrayList<>();
    private final static ArrayList<Integer> columnList = new ArrayList<>();
    private final static ArrayList<Integer> thicknessList = new ArrayList<>();
    private final static ArrayList<Integer> widthList = new ArrayList<>();
    private final static ArrayList<Integer> lengthList = new ArrayList<>();    
    static {
        for(int i = 1; i <= 5; i++){
            rowList.add(i);
        }
        for(int i = 5; i >= 1; i--){
            columnList.add(i);
        }
        for(int i = 1; i <= 12; i++){
            thicknessList.add(i);
        }
        for(int i = 8; i <= 30; i++){
            widthList.add(i);
        }
        for(int i = 8; i <= 38; i++){
            lengthList.add(i++);
        }        
    }
    
    public static ArrayList<InStockSlab> createInStockSlabs(int numInStockSlabs){
        Long idCounter = DAO.getNextId();
        ArrayList<InStockSlab> createdSlabs = new ArrayList<>();
        for(int i=1;i<=numInStockSlabs;i++){
            createdSlabs.add(new InStockSlab(idCounter++,allAvailableWoodCodes.get(i%allAvailableWoodCodes.size()),
                LocalDate.now().minusWeeks(i*2),thicknessList.get(i%thicknessList.size()),
                widthList.get(i%widthList.size()),lengthList.get(i%lengthList.size()),true,true,true,true,true,false,
                false,false,false,false,mainLocationsList.get(i%mainLocationsList.size()),subAreaLocationsList.get(i%subAreaLocationsList.size()),
                rowList.get(i%rowList.size()),columnList.get(i%columnList.size()),null,null));
        
        }
        return createdSlabs;
    }
    
    public static void createAndInsertNewInStockSlabs(int numOfSlabsToCreate){
        int slabsAdded = 0;
        for(InStockSlab slab:createInStockSlabs(numOfSlabsToCreate)){
           slabsAdded = slabsAdded + DAO.insertNewInStockSlab(slab);
           System.out.println(slab.toString());
        }
        System.out.println("SlabsAdded: "+slabsAdded);
    }
    

    public static ArrayList<SoldSlab> createSoldSlabs(int numSoldSlabs){
        Long idCounter = DAO.getNextId();
        ArrayList<SoldSlab> createdSlabs = new ArrayList<>();
        for(int i=1;i<=numSoldSlabs;i++){
            String woodCode = allAvailableWoodCodes.get(i%allAvailableWoodCodes.size());
            double width = widthList.get(i%widthList.size());
            createdSlabs.add(new SoldSlab(
                idCounter++,
                woodCode,
                LocalDate.now().minusWeeks(i*2),
                (double)thicknessList.get(i%thicknessList.size()),
                (double)width,
                (double)lengthList.get(i%lengthList.size()),
                DAO.getSpeciesFeaturePrice(false,woodCode,"AmbrosiaPrc"),
                DAO.getSpeciesFeaturePrice(false,woodCode,"Axe SinkerPrc"),
                DAO.getSpeciesFeaturePrice(false,woodCode,"Birds EyePrc"),
                DAO.getSpeciesFeaturePrice(false,woodCode,"BurledPrc"),
                DAO.getSpeciesFeaturePrice(false,woodCode,"CurlyPrc"),
                DAO.getSpeciesFeaturePrice(true,woodCode,"DressedPrc"),
                DAO.getSpeciesFeaturePrice(true,woodCode,"KilnedPrc"),
                DAO.getSpeciesFeaturePrice(true,woodCode,"PeckyPrc"),
                DAO.getSpeciesFeaturePrice(true,woodCode,"SinkerPrc"),
                DAO.getSpeciesFeaturePrice(true,woodCode,"SpaltedPrc"),
                DAO.getSpeciesWidthPrice(woodCode, width),    
                mainLocationsList.get(i%mainLocationsList.size()),
                subAreaLocationsList.get(i%subAreaLocationsList.size()),
                rowList.get(i%rowList.size()),
                columnList.get(i%columnList.size()),
                LocalDate.now().minusWeeks(i),
                customerList.get(i%customerList.size())));        
        }
        return createdSlabs;
    }
    
    public static void createAndInsertNewSoldSlabs(int numOfSlabsToCreate){
        int slabsAdded = 0;
        for(SoldSlab slab:createSoldSlabs(numOfSlabsToCreate)){
           slabsAdded = slabsAdded + DAO.insertNewSoldSlab(slab);
           System.out.println(slab.toString());
        }
        System.out.println("SlabsAdded: "+slabsAdded);
    }
    
}
