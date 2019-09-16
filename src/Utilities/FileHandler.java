/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Objects.InStockSlab;
import Objects.SoldSlab;
import Objects.Species;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Computebot
 */
public class FileHandler {
    
    public static boolean setFilePath(Stage primaryStage,TextField pathFieldToSet){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Database Files", "*.accdb"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);            
            try {
                pathFieldToSet.setText(selectedFile.getCanonicalPath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"There was an issue with the file you chose!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);  
                return false;
            } catch(NullPointerException ex){
                pathFieldToSet.setText("");
            }  
        return true;
    }
    public static String readDatabaseConfig(){    
        Path exportPath = Paths.get("SlabTrackerResources/");
        if(!Files.exists(exportPath, NOFOLLOW_LINKS)){
            try {
                Files.createDirectory(exportPath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"I can't create that directory: SlabTrackerResources!\n"+ex.getMessage(),"Corruption!",JOptionPane.ERROR_MESSAGE); 
             }
        }        
        String dbConfigFileName = "SlabTrackerResources/dbConfig.txt";        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dbConfigFileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                        list.add(line);
                }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"I can't find your config file.  I will create a new one when you connect.\n"+e.getMessage(),"Corruption!",JOptionPane.ERROR_MESSAGE); 
        }
        if(list.isEmpty()){
            return "";
        }
        return list.get(0);
    }
    
    public static void overwriteDatabaseConfig(String newLocation){    
        Path exportPath = Paths.get("SlabTrackerResources/");
        if(!Files.exists(exportPath, NOFOLLOW_LINKS)){
            try {
                Files.createDirectory(exportPath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"I can't create that directory: SlabTrackerResources!\n"+ex.getMessage(),"Corruption!",JOptionPane.ERROR_MESSAGE); 
             }
        }        
        String dbConfigFileName = "SlabTrackerResources/dbConfig.txt";
        try (BufferedWriter outStream= new BufferedWriter(new FileWriter(dbConfigFileName, false))) {
            outStream.write(newLocation);
            outStream.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"I can't find your config file!\n"+e.getMessage(),"Corruption!",JOptionPane.ERROR_MESSAGE); 
        }
        
    }
    
    public static boolean checkFilePath(String filePath){
        try (FileWriter testFile = new FileWriter(filePath, true)) {
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"I can't find that file:\n"+e.getMessage(),"Uh oh!",JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }
    
    public static void printTableToNotePad(TableView tableView,String fileNameType){
        
        String exportFileName = "SlabTrackerResources/Exported-"+fileNameType+"-Data.txt";
        Path exportPath = Paths.get("SlabTrackerResources/");
        if(!Files.exists(exportPath, NOFOLLOW_LINKS)){
            try {
                Files.createDirectory(exportPath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"I can't create that directory: SlabTrackerResources!\n"+ex.getMessage(),"Corruption!",JOptionPane.ERROR_MESSAGE); 
            }
        }
        StringBuilder data = new StringBuilder();
        for(Object row:(tableView.getItems())){
            switch(fileNameType){
                case "In-Stock":
                    InStockSlab inStockSlab = (InStockSlab)row;
                    data.append(inStockSlab.toExportString());
                    break;
                case "Sold":
                    SoldSlab soldSlab = (SoldSlab)row;
                    data.append(soldSlab.toExportString());
                    break;
                case "Species":
                    Species species = (Species)row;
                    data.append(species.toExportString());
                    break;
            }
        }
        String slabExportData = data.toString();
        if(slabExportData.isEmpty()){slabExportData="This table holds no slabs!";}  
        try {
        Files.deleteIfExists(Paths.get(exportFileName));
        Files.createFile(Paths.get(exportFileName));

        } catch (IOException ex) {/*Do Nothing, file is created*/}
        
        try {
            Files.write(Paths.get(exportFileName), slabExportData.getBytes(),CREATE);
        } catch (IOException ex) {
            System.out.println("I am having trouble creating and writing to your file:\n"+ex.getMessage());
        }

        ProcessBuilder pb = new ProcessBuilder("Notepad.exe", exportFileName);
        try{
            pb.start();
        }catch(IOException ex){
            System.out.println("I am having trouble opening the file in notepad:\n"+ex.getMessage());
        }    
    
    }
    
    public static void printTextAreaToNotePad(String reportData,String fileNameType){
        String exportFileName = "SlabTrackerResources/Exported-"+fileNameType+"-Data.txt";
        Path exportPath = Paths.get("SlabTrackerResources/");
        if(!Files.exists(exportPath, NOFOLLOW_LINKS)){
            try {
                Files.createDirectory(exportPath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"I can't create that directory: SlabTrackerResources!\n"+ex.getMessage(),"Corruption!",JOptionPane.ERROR_MESSAGE); 
             }
        }
        reportData = reportData.replace(">",">"+System.getProperty("line.separator"));
        reportData = reportData.replace("---------------------------------------------------------------------------------",""+System.getProperty("line.separator"));
        try {
            Files.write(Paths.get(exportFileName), reportData.getBytes());
        } catch (IOException ex) {
            System.out.println("I am having trouble creating and writing to your file:\n"+ex.getMessage());
        }

        ProcessBuilder pb = new ProcessBuilder("Notepad.exe", exportFileName);
        try{
            pb.start();
        }catch(IOException ex){
            System.out.println("I am having trouble opening the file in notepad:\n"+ex.getMessage());
        }    
    
    }
}
