/*
 * Database input formats:
 *   UPDATE SlabEntry set [WoodCode]=\"ASHW\" WHERE [ID#]=0000000013//STRING
 *   UPDATE SlabEntry set [MilledDate]=#2017-10-01# WHERE [ID#]=0000000013 //DATE
 *   UPDATE SlabEntry set [Thick(in)]=4.5 WHERE [ID#]=0000000013//DOUBLE
 *   UPDATE SlabEntry set [Kilned]=false WHERE [ID#]=0000000013//YESNO
 *   UPDATE SlabEntry set [Row]=1 WHERE [ID#]=0000000013//INTEGER
 */
package Database;

import Objects.InStockSlab;
import Objects.SoldSlab;
import Objects.Species;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Computebot
 */
public class DAO {    
    
    public static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    public static NumberFormat doubleFormat = NumberFormat.getNumberInstance();
    public static DecimalFormat doubleFormatDecimal = new DecimalFormat("#.00");

    public static DateTimeFormatter readableDateFormat = DateTimeFormatter.ofPattern("MMM dd, YYYY");
    
    public static ArrayList<InStockSlab> getAllInStockSlabs(){
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT * FROM SlabEntry");
        ArrayList<InStockSlab> allSlabs = new ArrayList<>();
        try {
            while (rs.next()) {
                allSlabs.add(new InStockSlab(rs.getLong("ID#"),rs.getString("Wood Code"),
                        rs.getDate("Milled Date").toLocalDate(),rs.getDouble("Thick(in)"),
                        rs.getDouble("Width(in)"),rs.getDouble("Length(ft)"),rs.getBoolean("Kilned"),
                        rs.getBoolean("Curly"),rs.getBoolean("Burled"),rs.getBoolean("Spalted"),
                        rs.getBoolean("Birds Eye"),rs.getBoolean("Pecky"),rs.getBoolean("Ambrosia"),
                        rs.getBoolean("Sinker"),rs.getBoolean("Axe Sinker"),rs.getBoolean("Dressed"),
                        rs.getString("Main Area"),rs.getString("Sub Area"),rs.getInt("Row"),rs.getInt("Column"),
                        null,null));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble finding the in-stock slabs!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }        
        return allSlabs;
    }
   
    public static InStockSlab getInStockSlabById(int Id){
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT * FROM SlabEntry WHERE [ID#]="+Id);
        InStockSlab inStockSlab = null;
        try {
            while (rs.next()) {
                inStockSlab = new InStockSlab(rs.getLong("ID#"),rs.getString("Wood Code"),
                        rs.getDate("Milled Date").toLocalDate(),rs.getDouble("Thick(in)"),
                        rs.getDouble("Width(in)"),rs.getDouble("Length(ft)"),rs.getBoolean("Kilned"),
                        rs.getBoolean("Curly"),rs.getBoolean("Burled"),rs.getBoolean("Spalted"),
                        rs.getBoolean("Birds Eye"),rs.getBoolean("Pecky"),rs.getBoolean("Ambrosia"),
                        rs.getBoolean("Sinker"),rs.getBoolean("Axe Sinker"),rs.getBoolean("Dressed"),
                        rs.getString("Main Area"),rs.getString("Sub Area"),rs.getInt("Row"),rs.getInt("Column"),
                        null,null);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble finding that in-stock slabs!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }        
        return inStockSlab;
    }

    public static String getInStockSlabsReport(int filterOption){
        ArrayList<InStockSlab> allSlabs = getAllInStockSlabs();
        ArrayList<String> allWoodCodes = getAllAvailableWoodCodes();
        HashMap<String,Double> totalPriceByWoodType = new HashMap();
        HashMap<String,Double> totalBoardFeetByWoodType = new HashMap();
        StringBuilder report = new StringBuilder();
        Double totalBoardFeet = 0.0;
        Double totalprice = 0.0;

        for(String woodCode:allWoodCodes){
            totalPriceByWoodType.put(woodCode,0.0);
            totalBoardFeetByWoodType.put(woodCode,0.0);
        }
        
        for(InStockSlab slab:allSlabs){
            slab.getDerivedAttributes();
            String woodCode = slab.getWoodCode();
            Double singleSlabPrevTotalPrice = totalPriceByWoodType.get(woodCode);
            Double singleSlabTotalBoardFeet = totalBoardFeetByWoodType.get(woodCode);            
            Double slabTotalBdFt = slab.getTotalBoardFeet();
            Double slabPrice = slab.getTotalSlabPrice();
            switch(filterOption){
                case 1:
                    totalBoardFeet = totalBoardFeet + slabTotalBdFt;
                    totalprice = totalprice + slabPrice;
                    totalPriceByWoodType.put(woodCode,singleSlabPrevTotalPrice+slabPrice);
                    totalBoardFeetByWoodType.put(woodCode,singleSlabTotalBoardFeet+slabTotalBdFt);
                    break;
                case 2:
                    if(slab.getIsDry().equals("Yes")){
                        totalBoardFeet = totalBoardFeet + slabTotalBdFt;
                        totalprice = totalprice + slabPrice;
                        totalPriceByWoodType.put(woodCode,singleSlabPrevTotalPrice+slabPrice);
                        totalBoardFeetByWoodType.put(woodCode,singleSlabTotalBoardFeet+slabTotalBdFt);
                    }
                    break;
                case 3:
                    if(slab.getIsDry().equals("No")){
                        totalBoardFeet = totalBoardFeet + slabTotalBdFt;
                        totalprice = totalprice + slabPrice;
                        totalPriceByWoodType.put(woodCode,singleSlabPrevTotalPrice+slabPrice);
                        totalBoardFeetByWoodType.put(woodCode,singleSlabTotalBoardFeet+slabTotalBdFt);
                    }
                    break;
            
            }
        }
        switch(filterOption){
            case 1:
                report.append("In-Stock - All - Totals By Wood Code <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
            case 2:
                report.append("In-Stock - Dry - Totals By Wood Code <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
            case 3:
                report.append("In-Stock - Not Dry - Totals By Wood Code <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
        }
        for (HashMap.Entry<String, Double> entry : totalPriceByWoodType.entrySet()) {
            report.append("Wood Code:").append(entry.getKey()).append("--> Total Category BdFt: ").append(doubleFormatDecimal.format(totalBoardFeetByWoodType.get(entry.getKey()))).append("--> Total Category Price: $").append(doubleFormatDecimal.format(entry.getValue())).append(">").append(System.getProperty("line.separator"));
            report.append("---------------------------------------------------------------------------------------------------------------->").append(System.getProperty("line.separator"));
        }
        switch(filterOption){
            case 1:
                report.append("In-Stock - All - Totals <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
            case 2:
                report.append("In-Stock - Dry - Totals <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
            case 3:
                report.append("In-Stock - Not Dry - Totals <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
        }        
        report.append("Total Bdft: ").append(doubleFormatDecimal.format(totalBoardFeet)).append(", Total Price: $").append(doubleFormatDecimal.format(totalprice));
        return report.toString();
    }
   
    public static ArrayList<SoldSlab> getAllSoldSlabs(){
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT * FROM [Sold Slabs]");
        ArrayList<SoldSlab> allSlabs = new ArrayList<>();
        try {
            while (rs.next()) {
                allSlabs.add(new SoldSlab(rs.getLong("ID#"),rs.getString("Wood Code"),
                        rs.getDate("Milled Date").toLocalDate(),rs.getDouble("Thick(in)"),
                        rs.getDouble("Width(in)"),rs.getDouble("Length(ft)"),rs.getDouble("Kilned Price"),
                        rs.getDouble("Curly Price"),rs.getDouble("Burled Price"),rs.getDouble("Spalted Price"),
                        rs.getDouble("Birds Eye Price"),rs.getDouble("Pecky Price"),rs.getDouble("Ambrosia Price"),
                        rs.getDouble("Sinker Price"),rs.getDouble("Axe Sinker Price"),rs.getDouble("Dressed Price"),
                        rs.getDouble("Width Price"),rs.getString("Main Area"),rs.getString("Sub Area"),rs.getInt("Row"),
                        rs.getInt("Column"),rs.getDate("Date Sold").toLocalDate(),rs.getString("Customer Name")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble finding the sold slabs!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }        
        return allSlabs;
    }
    
    public static SoldSlab getSoldSlabById(int Id){
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT * FROM [Sold Slabs] WHERE [ID#] ="+Id);
        SoldSlab soldSlab = null;
        try {
            while (rs.next()) {
                soldSlab = new SoldSlab(rs.getLong("ID#"),rs.getString("Wood Code"),
                        rs.getDate("MilledDate").toLocalDate(),rs.getDouble("Thick(in)"),
                        rs.getDouble("Width(in)"),rs.getDouble("Length(ft)"),rs.getDouble("Kilned Price"),
                        rs.getDouble("Curly Price"),rs.getDouble("Burled Price"),rs.getDouble("Spalted Price"),
                        rs.getDouble("Birds Eye Price"),rs.getDouble("Pecky Price"),rs.getDouble("Ambrosia Price"),
                        rs.getDouble("Sinker Price"),rs.getDouble("Axe Sinker Price"),rs.getDouble("Dressed Price"),
                        rs.getDouble("Width Price"),rs.getString("Main Area"),rs.getString("Sub Area"),rs.getInt("Row"),
                        rs.getInt("Column"),rs.getDate("Date Sold").toLocalDate(),rs.getString("Customer Name"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble finding that sold slabs!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }        
        return soldSlab;
    }    

    public static ArrayList<SoldSlab> getSoldSlabsByDate(LocalDate getSlabsBeforeDate){
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT * FROM [Sold Slabs] WHERE [Date Sold] <#"+getSlabsBeforeDate+"#;");
        ArrayList<SoldSlab> exportList = new ArrayList<>();
        try {
            while (rs.next()) {
                exportList.add(new SoldSlab(rs.getLong("ID#"),rs.getString("Wood Code"),
                        rs.getDate("Milled Date").toLocalDate(),rs.getDouble("Thick(in)"),
                        rs.getDouble("Width(in)"),rs.getDouble("Length(ft)"),rs.getDouble("Kilned Price"),
                        rs.getDouble("Curly Price"),rs.getDouble("Burled Price"),rs.getDouble("Spalted Price"),
                        rs.getDouble("Birds Eye Price"),rs.getDouble("Pecky Price"),rs.getDouble("Ambrosia Price"),
                        rs.getDouble("Sinker Price"),rs.getDouble("Axe Sinker Price"),rs.getDouble("Dressed Price"),
                        rs.getDouble("Width Price"),rs.getString("Main Area"),rs.getString("Sub Area"),rs.getInt("Row"),
                        rs.getInt("Column"),rs.getDate("Date Sold").toLocalDate(),rs.getString("Customer Name")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble finding the sold slabs!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }        
        return exportList;    
    
    }
    
    public static String getSoldSlabsReport(int filterOption){
        ArrayList<SoldSlab> allSlabs = getAllSoldSlabs();
        ArrayList<String> allWoodCodes = getAllAvailableWoodCodes();
        HashMap<String,Double> totalPriceByWoodType = new HashMap();
        HashMap<String,Double> totalBoardFeetByWoodType = new HashMap();
        StringBuilder report = new StringBuilder();
        Double totalBoardFeet = 0.0;
        Double totalprice = 0.0;

        for(String woodCode:allWoodCodes){
            totalPriceByWoodType.put(woodCode,0.0);
            totalBoardFeetByWoodType.put(woodCode,0.0);
        }
        
        for(SoldSlab slab:allSlabs){
            String woodCode = slab.getWoodCode();
            Double singleSlabPrevTotalPrice = totalPriceByWoodType.get(woodCode);
            Double singleSlabTotalBoardFeet = totalBoardFeetByWoodType.get(woodCode);            
            Double slabTotalBdFt = slab.getTotalBoardFeet();
            Double slabPrice = slab.getTotalSlabPrice();
            switch(filterOption){
                case 1:
                    totalBoardFeet = totalBoardFeet + slabTotalBdFt;
                    totalprice = totalprice + slabPrice;
                    totalPriceByWoodType.put(woodCode,singleSlabPrevTotalPrice+slabPrice);
                    totalBoardFeetByWoodType.put(woodCode,singleSlabTotalBoardFeet+slabTotalBdFt);
                    break;
                case 2:
                    if(slab.getIsDry().equals("Yes")){
                        totalBoardFeet = totalBoardFeet + slabTotalBdFt;
                        totalprice = totalprice + slabPrice;
                        totalPriceByWoodType.put(woodCode,singleSlabPrevTotalPrice+slabPrice);
                        totalBoardFeetByWoodType.put(woodCode,singleSlabTotalBoardFeet+slabTotalBdFt);
                    }
                    break;
                case 3:
                    if(slab.getIsDry().equals("No")){
                        totalBoardFeet = totalBoardFeet + slabTotalBdFt;
                        totalprice = totalprice + slabPrice;
                        totalPriceByWoodType.put(woodCode,singleSlabPrevTotalPrice+slabPrice);
                        totalBoardFeetByWoodType.put(woodCode,singleSlabTotalBoardFeet+slabTotalBdFt);
                    }
                    break;
            
            }
        }
        switch(filterOption){
            case 1:
                report.append("Sold Slabs - All - Totals By Wood Code <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
            case 2:
                report.append("Sold Slabs - Dry - Totals By Wood Code <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
            case 3:
                report.append("Sold Slabs - Not Dry - Totals By Wood Code <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
        }
        for (HashMap.Entry<String, Double> entry : totalPriceByWoodType.entrySet()) {
            report.append("Wood Code:").append(entry.getKey()).append("--> Total Category BdFt: ").append(doubleFormatDecimal.format(totalBoardFeetByWoodType.get(entry.getKey()))).append("--> Total Category Price: $").append(doubleFormatDecimal.format(entry.getValue())).append(">").append(System.getProperty("line.separator"));
            report.append("---------------------------------------------------------------------------------------------------------------->").append(System.getProperty("line.separator"));
        }
        switch(filterOption){
            case 1:
                report.append("Sold Slabs - All - Totals <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
            case 2:
                report.append("Sold Slabs - Dry - Totals <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
            case 3:
                report.append("Sold Slabs - Not Dry - Totals <Time of Report: ").append(Timestamp.valueOf(LocalDateTime.now())).append(">").append(System.getProperty("line.separator"));
                break;
        }        
        report.append("Total Bdft: ").append(doubleFormatDecimal.format(totalBoardFeet)).append(", Total Price: $").append(doubleFormatDecimal.format(totalprice));
        return report.toString();
    }
    
    
    public static ArrayList<Species> getAllSpecies(){
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT * FROM AddOnPrices");
        ArrayList<Species> allSpecies = new ArrayList<>();
        try {
            while (rs.next()) {
                allSpecies.add(new Species(rs.getString("Wood Code"),
                        rs.getString("Species"),rs.getDouble("8in-11in"),rs.getDouble("12in-20in"),
                        rs.getDouble("21in-23in"),rs.getDouble("24in-30in"),rs.getDouble("AmbrosiaPrc"),
                        rs.getDouble("Axe SinkerPrc"),rs.getDouble("Birds EyePrc"),rs.getDouble("BurledPrc"),
                        rs.getDouble("CurlyPrc"),rs.getDouble("DressedPrc"),rs.getDouble("KilnedPrc"),
                        rs.getDouble("PeckyPrc"),rs.getDouble("SinkerPrc"),rs.getDouble("SpaltedPrc")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble finding the species!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }        
        return allSpecies;
    }
    
    public static Species getSpeciesByWoodCode(String woodCode){
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT * FROM AddOnPrices WHERE [Wood Code] ="+woodCode);
        Species species = null;
        try {
            while (rs.next()) {
                species = new Species(rs.getString("Wood Code"),
                    rs.getString("Species"),rs.getDouble("8in-11in"),rs.getDouble("12in-20in"),
                    rs.getDouble("21in-23in"),rs.getDouble("24in-30in"),rs.getDouble("AmbrosiaPrc"),
                    rs.getDouble("Axe SinkerPrc"),rs.getDouble("Birds EyePrc"),rs.getDouble("BurledPrc"),
                    rs.getDouble("CurlyPrc"),rs.getDouble("DressedPrc"),rs.getDouble("KilnedPrc"),
                    rs.getDouble("PeckyPrc"),rs.getDouble("SinkerPrc"),rs.getDouble("SpaltedPrc"));
                return species;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble finding that species!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }        
        return species;
    }     
    
    public static ArrayList<String> getAllAvailableWoodCodes(){
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT [Wood Code] FROM AddOnPrices");
        ArrayList<String> allWoodCodes = new ArrayList<>();
        try {
            while (rs.next()) {
                allWoodCodes.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble finding the wood codes!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }
        return allWoodCodes;
    }
    
    public static TreeSet<Long> getAllUsedIds(){
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT [ID#] FROM SlabEntry UNION SELECT [ID#] FROM [Sold Slabs]");
        TreeSet<Long> allUsedIds = new TreeSet<>();
        try {
            while (rs.next()) {
                allUsedIds.add(rs.getLong(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble finding the IDs!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }
        return allUsedIds;
    }
    
    public static Long getNextId(){
        Long nextId = 0L;
        if(getAllUsedIds().size()>0){
            nextId = getAllUsedIds().last();
        }         
        nextId++;
        return nextId;    
    }
    
    public static String getPasswordString(){
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT [Password] FROM Password WHERE [ID] = 1;");
        String pWord = "";
        try {
            while (rs.next()) {
                pWord = rs.getString("Password");
                break;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble finding that password!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }        
        return pWord;
    }

    /**
     * gets the width price of each species by width
     * @param speciesCode  Must be all capital with exactly 4 letters, must be in AddOnPrices table
     * @param width width of slab, must be between 8 and 30, anything else would be ridiculous!
     *  A four inch slab is just a board :)  
     * @return double value for width price for that species
     */
    public static double getSpeciesWidthPrice(String speciesCode,double width){
        double speciesWidthPrice=0.0;
        String widthPriceColumnName = "";
        if(width >= 8 && width <=30){
            if(width>=8&&width<12){
                widthPriceColumnName = "[8in-11in]";
            }
            if(width>=12&&width<21){
                widthPriceColumnName = "[12in-20in]";
            }
            if(width>=21&&width<24){
                widthPriceColumnName = "[21in-23in]";
            }
            if(width>=24&&width<=30){
                widthPriceColumnName = "[24in-30in]";
            }
        }else{
            System.out.println("Width must be between 8 and 30!");
            return -1;
        }
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT "+widthPriceColumnName+" FROM AddOnPrices WHERE [Wood Code]=\""+speciesCode+"\";");
        try {
            while (rs.next()) {
               speciesWidthPrice = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble getting that width price!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }        
        return speciesWidthPrice;
    }
    
    /**
     * gets the price price of each species by column name
     * @param isPresent indicates whether this particular slab has specified feature
     * @param speciesCode Must be all capitol with exactly 4 letters, must be in AddOnPrices table
     * @param featureColumnName name of column in AddOnPrices table that corresponds to the feature 
     * you want to price, ex: KilnedPrc. :)      * 
     * @return feature price for that feature, if it is present, for that species, otherwise a value of 0.0 is returned
     */
    public static double getSpeciesFeaturePrice(Boolean isPresent,String speciesCode,String featureColumnName){
        double featurePrice = 0.0;
        if(!isPresent){
            return featurePrice;
        }
        ResultSet rs = StatementMaker.makeSelectStatement("SELECT ["+featureColumnName+"] FROM AddOnPrices WHERE [Wood Code]=\""+speciesCode+"\";");
        try {
            while (rs.next()) {
               featurePrice = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble getting that feature price! Check your spelling!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }        
        return featurePrice;        
    }
/*
 * Database input formats:
 *   UPDATE SlabEntry set [WoodCode]=\"ASHW\" WHERE [ID#]=0000000013//STRING
 *   UPDATE SlabEntry set [MilledDate]=#2017-10-01# WHERE [ID#]=0000000013 //DATE
 *   UPDATE SlabEntry set [Thick(in)]=4.5 WHERE [ID#]=0000000013//DOUBLE
 *   UPDATE SlabEntry set [Kilned]=false WHERE [ID#]=0000000013//YESNO
 *   UPDATE SlabEntry set [Row]=1 WHERE [ID#]=0000000013//INTEGER
 */    
    public static int insertNewInStockSlab(InStockSlab inStockSlab){
        int rowsAdded = StatementMaker.makeUpdateDeleteOrInsertStatement(
                "INSERT INTO SlabEntry ([ID#],[Wood Code],[Purchased],[Milled Date],[Thick(in)],[Width(in)],[Length(ft)],"
                        + "[Kilned],[Curly],[Burled],[Spalted],[Birds Eye],[Pecky],[Ambrosia],[Sinker],[Axe Sinker],[Dressed],"
                        + "[Main Area],[Sub Area],[Row],[Column])"
                        + " VALUES ("
                        + inStockSlab.getSlabId()+","
                        + "\""+inStockSlab.getWoodCode()+"\","
                        +false+","
                        + "#"+inStockSlab.getMilledDate()+"#,"
                        +inStockSlab.getThicknessInInches()+ ","
                        +inStockSlab.getWidthInInches()+","
                        +inStockSlab.getLengthInFeet()+","
                        +inStockSlab.isKilned()+","
                        +inStockSlab.isCurly()+","
                        +inStockSlab.isBurled()+","
                        +inStockSlab.isSpalted()+","
                        +inStockSlab.isBirdsEye()+","
                        +inStockSlab.isPecky()+","
                        +inStockSlab.isAmbrosia()+","
                        +inStockSlab.isSinker()+","
                        +inStockSlab.isAxeSinker()+","
                        +inStockSlab.isDressed()+","
                        +"\""+inStockSlab.getLocationMain()+"\","
                        +"\""+inStockSlab.getLocationSubArea()+"\","
                        +inStockSlab.getLocationRow()+","
                        +inStockSlab.getLocationColumn()+")"); 
        try {
            DBConnector.getDBConnection().getConnection().commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble inserting, updating or deleting that In-Stock Slab!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }
        return rowsAdded;
    }
    
    public static int insertInStockSlabListWithIdCorrection(ArrayList<InStockSlab> passedInInStockSlabExportList){
        int totalRowsAdded = 0;        
        ArrayList<InStockSlab> slabsWithErrorsList = new ArrayList<>();
        ArrayList<InStockSlab> inStockSlabExportList = new ArrayList<>(passedInInStockSlabExportList);
        TreeSet<Long> usedIds = getAllUsedIds();
        HashMap<Long,Long> idChangeList = new HashMap();
        Long newIdCounter;
        if(usedIds.isEmpty()){
            newIdCounter = 1L;
        } else{
            newIdCounter  = usedIds.last()+1;
        }
        StringBuilder sb = new StringBuilder();
        int changeIdResponse;
        sb.append("Some of the In-Stock Slab Id's you are trying to import are already used!\n"
                + "You can either cancel the action and change these id's manually in either databse\n"
                + "or you can allow the program to change the imported id's to the ones listed.\n"
                + "please make a note of the ones listed so you can track the changes:\n");
        for(InStockSlab inStocSlab:inStockSlabExportList){
            if (newIdCounter<=inStocSlab.getSlabId()){
                newIdCounter=inStocSlab.getSlabId()+1;
            }
        }
        for(InStockSlab inStockSlab:inStockSlabExportList){
            for(Long usedId:usedIds){
                if (inStockSlab.getSlabId().equals(usedId)){
                    slabsWithErrorsList.add(inStockSlab);
                    idChangeList.put(inStockSlab.getSlabId(),newIdCounter);
                    sb.append("Duplicate IN-STOCK SLAB ID: ").append(inStockSlab.getSlabId()).append(" >> Changes to: ").append(newIdCounter).append("\n");
                    newIdCounter++;
                }
            }
        }
        
        if(!slabsWithErrorsList.isEmpty()){
            changeIdResponse = JOptionPane.showConfirmDialog(null,sb,"Duplicate IN-STOCK SLAB IDs!",JOptionPane.OK_CANCEL_OPTION);
            if(changeIdResponse==0){
                for(InStockSlab inStockSlab:slabsWithErrorsList){
                    inStockSlab.setSlabId(idChangeList.get(inStockSlab.getSlabId()));
                }
            }else{
                return totalRowsAdded;}
        }
        
        for(InStockSlab inStockSlab:inStockSlabExportList){
            int rowsAdded = insertNewInStockSlab(inStockSlab);
            totalRowsAdded = totalRowsAdded + rowsAdded;
        }

        try {
            DBConnector.getDBConnection().getConnection().commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble commiting that In-Stock Slab!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }
        return totalRowsAdded;
    }
    
    public static int insertNewSoldSlab(SoldSlab soldSlab){
        int rowsAdded = StatementMaker.makeUpdateDeleteOrInsertStatement(
                "INSERT INTO [Sold Slabs] "
            + "([ID#],[Wood Code],[Purchased],[Milled Date],[Dry?],[Thick(in)],[Width(in)],[Length(ft)],[TtlBdFt],\n"
            +"[Kilned Price],[Curly Price],[Burled Price],[Spalted Price],[Birds Eye Price],[Pecky Price],\n"
            +"[Ambrosia Price],[Sinker Price],[Axe Sinker Price],[Dressed Price],[Total Add On Price],[Width Price],\n"
            +"[Total Price Per Board Foot],[Total BdFt],[Total Slab Price],[Main Area],[Sub Area],[Row],[Column],\n"
            +"[Date Sold],[Customer Name]) "
            + "VALUES ("
            + ""+soldSlab.getSlabIdFormatted()+","
            + "\""+soldSlab.getWoodCode()+"\","
            + ""+true+","
            + "#"+soldSlab.getMilledDate()+"#,"
            + "\""+soldSlab.getIsDry()+"\","
            + ""+soldSlab.getThicknessInInches()+","
            + ""+soldSlab.getWidthInInches()+","
            + ""+soldSlab.getLengthInFeet()+","
            + ""+soldSlab.getTotalBoardFeet()+","
            + ""+soldSlab.getKilnedCharge()+","
            + ""+soldSlab.getCurlyCharge()+","
            + ""+soldSlab.getBurledCharge()+","
            + ""+soldSlab.getSpaltedCharge()+","
            + ""+soldSlab.getBirdsEyeCharge()+","
            + ""+soldSlab.getPeckyCharge()+","
            + ""+soldSlab.getAmbrosiaCharge()+","
            + ""+soldSlab.getSinkerCharge()+","
            + ""+soldSlab.getAxeSinkerCharge()+","
            + ""+soldSlab.getDressedCharge()+","
            + ""+soldSlab.getTotalFeaturePrice()+","
            + ""+soldSlab.getWidthCost()+","
            + ""+soldSlab.getTotalPricePerBoardFoot()+","
            + ""+soldSlab.getTotalBoardFeet()+","
            + ""+soldSlab.getTotalSlabPrice()+","                
            + "\""+soldSlab.getLocationMain()+"\","
            + "\""+soldSlab.getLocationSubArea()+"\","
            + ""+soldSlab.getLocationRow()+","
            + ""+soldSlab.getLocationColumn()+","
            + "#"+soldSlab.getDateSold()+"#,"
            + "\""+soldSlab.getSoldToCustomer()+"\")");        
        try {
            DBConnector.getDBConnection().getConnection().commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble inserting, updating or deleting that Sold Slab!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }
        return rowsAdded;
    }
    
    public static int insertSoldSlabListWithIdCorrection(ArrayList<SoldSlab> passedInSoldSlabExportList){
        int totalRowsAdded = 0;        
        ArrayList<SoldSlab> slabsWithErrorsList = new ArrayList<>();
        ArrayList<SoldSlab> soldSlabExportList = new ArrayList<>(passedInSoldSlabExportList);
        TreeSet<Long> usedIds = getAllUsedIds();
        HashMap<Long,Long> idChangeList = new HashMap();
        Long newIdCounter;
        if(usedIds.isEmpty()){
            newIdCounter = 1L;
        } else{
            newIdCounter  = usedIds.last()+1;
        }
        StringBuilder sb = new StringBuilder();
        int changeIdResponse;
        sb.append("You had some duplicate Sold Slab Id's between the two databases.\n"
                + "You can either cancel the action and change these id's manually in either database\n"
                + "or you can allow the program to change the id's to the ones listed.\n"
                + "please make a note of the ones listed so you can track the changes:\n");
        for(SoldSlab soldSlab:soldSlabExportList){
            if (newIdCounter<=soldSlab.getSlabId()){
                newIdCounter=soldSlab.getSlabId()+1;
            }
        }
        for(SoldSlab soldSlab:soldSlabExportList){
            for(Long usedId:usedIds){
                if (soldSlab.getSlabId().equals(usedId)){
                    slabsWithErrorsList.add(soldSlab);
                    idChangeList.put(soldSlab.getSlabId(),newIdCounter);
                    sb.append("Duplicate SOLD SLAB ID: ").append(soldSlab.getSlabId()).append(" >> Changes to: ").append(newIdCounter).append("\n");
                    newIdCounter++;
                }
            }
        }
        
        for(SoldSlab soldSlab:slabsWithErrorsList){
            soldSlabExportList.remove(soldSlab);
        }
        
        if(!slabsWithErrorsList.isEmpty()){
            changeIdResponse = JOptionPane.showConfirmDialog(null,sb,"Duplicate SOLD SLAB IDs!",JOptionPane.OK_CANCEL_OPTION);
            if(changeIdResponse==0){
                for(SoldSlab soldSlab:slabsWithErrorsList){
                    soldSlab.setSlabId(idChangeList.get(soldSlab.getSlabId()));
                    soldSlabExportList.add(soldSlab);
                }
            }else{
                return totalRowsAdded;}
        }
        
        for(SoldSlab soldSlab:soldSlabExportList){
            int rowsAdded = insertNewSoldSlab(soldSlab);
            totalRowsAdded = totalRowsAdded + rowsAdded;
        }

        try {
            DBConnector.getDBConnection().getConnection().commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble commiting that Sold Slab!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }
        return totalRowsAdded;
    }
    
    public static int insertNewSpecies(Species species){
        int rowsAdded = StatementMaker.makeUpdateDeleteOrInsertStatement(
                "INSERT INTO [AddOnPrices] "
            + "([Wood Code],[Species],[8in-11in],[12in-20in],[21in-23in],[24in-30in],[AmbrosiaPrc],[Axe SinkerPrc],[Birds EyePrc],\n"
            +"[BurledPrc],[CurlyPrc],[DressedPrc],[KilnedPrc],[PeckyPrc],[SinkerPrc],[SpaltedPrc]\n)"
            + "VALUES ("
            + "\""+species.getSpeciesWoodCode()+"\","
            + "\""+species.getSpeciesName()+"\","
            + ""+species.getPrice8InTo11In()+","
            + ""+species.getPrice12InTo20In()+","
            + ""+species.getPrice21InTo23In()+","
            + ""+species.getPrice24InTo30In()+","
            + ""+species.getAmbrosiaPrice()+","
            + ""+species.getAxeSinkerPrice()+","
            + ""+species.getBirdsEyePrice()+","
            + ""+species.getBurledPrice()+","
            + ""+species.getCurlyPrice()+","
            + ""+species.getDressedPrice()+","
            + ""+species.getKilnedPrice()+","
            + ""+species.getPeckyPrice()+","
            + ""+species.getSinkerPrice()+","
            + ""+species.getSpaltedPrice()+")");
        try {
            DBConnector.getDBConnection().getConnection().commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Something is amiss! I am having trouble inserting that Species!\n"+ex.getMessage(),"Denied!",JOptionPane.ERROR_MESSAGE);                   
        }
        return rowsAdded;
    }    
    
    public static int updateInStockSlab(InStockSlab updatedSlab,Long originalSlabId){
        int rowsUpdated = StatementMaker.makeUpdateDeleteOrInsertStatement(
        "UPDATE [SlabEntry] set "
            + "[ID#] = "+updatedSlab.getSlabIdFormatted()+","
            + "[Wood Code] = \""+updatedSlab.getWoodCode()+"\","
            + "[Purchased] = "+false+","
            + "[Milled Date] = #"+updatedSlab.getMilledDate()+"#,"
            + "[Thick(in)] = "+updatedSlab.getThicknessInInches()+","
            + "[Width(in)] = "+updatedSlab.getWidthInInches()+","
            + "[Length(ft)] = "+updatedSlab.getLengthInFeet()+","
            + "[Kilned] = "+updatedSlab.isKilned()+","
            + "[Curly] = "+updatedSlab.isCurly()+","
            + "[Burled] = "+updatedSlab.isBurled()+","
            + "[Spalted] = "+updatedSlab.isSpalted()+","
            + "[Birds Eye] = "+updatedSlab.isBirdsEye()+","
            + "[Pecky] = "+updatedSlab.isPecky()+","
            + "[Ambrosia] = "+updatedSlab.isAmbrosia()+","
            + "[Sinker] = "+updatedSlab.isSinker()+","
            + "[Axe Sinker] = "+updatedSlab.isAxeSinker()+","
            + "[Dressed] = "+updatedSlab.isDressed()+","
            + "[Main Area] = \""+updatedSlab.getLocationMain()+"\","
            + "[Sub Area] = \""+updatedSlab.getLocationSubArea()+"\","
            + "[Row] = "+updatedSlab.getLocationRow()+","
            + "[Column] = "+updatedSlab.getLocationColumn()+" "
            + "WHERE [ID#] = "+originalSlabId+";");    
        return rowsUpdated;
    }
    
    public static int updateSoldSlab(SoldSlab updatedSlab,Long originalSlabId){
        int rowsUpdated = StatementMaker.makeUpdateDeleteOrInsertStatement(
        "UPDATE [Sold Slabs] set "
            + "[ID#] = "+updatedSlab.getSlabIdFormatted()+","
            + "[Wood Code] = \""+updatedSlab.getWoodCode()+"\","
            + "[Purchased] = "+false+","
            + "[Milled Date] = #"+updatedSlab.getMilledDate()+"#,"
            + "[Dry?] = \""+updatedSlab.getIsDry()+"\","
            + "[Thick(in)] = "+updatedSlab.getThicknessInInches()+","
            + "[Width(in)] = "+updatedSlab.getWidthInInches()+","
            + "[Length(ft)] = "+updatedSlab.getLengthInFeet()+","
            + "[TtlBdFt] = "+updatedSlab.getTotalBoardFeet()+","
            + "[Kilned Price] = "+updatedSlab.getKilnedCharge()+","
            + "[Curly Price] = "+updatedSlab.getCurlyCharge()+","
            + "[Burled Price] = "+updatedSlab.getBurledCharge()+","
            + "[Spalted Price] = "+updatedSlab.getSpaltedCharge()+","
            + "[Birds Eye Price] = "+updatedSlab.getBirdsEyeCharge()+","
            + "[Pecky Price] = "+updatedSlab.getPeckyCharge()+","
            + "[Ambrosia Price] = "+updatedSlab.getAmbrosiaCharge()+","
            + "[Sinker Price] = "+updatedSlab.getSinkerCharge()+","
            + "[Axe Sinker Price] = "+updatedSlab.getAxeSinkerCharge()+","
            + "[Dressed Price] = "+updatedSlab.getDressedCharge()+","
            + "[Total Add On Price] = "+updatedSlab.getTotalFeaturePrice()+","
            + "[Width Price] = "+updatedSlab.getWidthCost()+","
            + "[Total Price Per Board Foot] = "+updatedSlab.getTotalPricePerBoardFoot()+","
            + "[Total BdFt] = "+updatedSlab.getTotalBoardFeet()+","
            + "[Total Slab Price] = "+updatedSlab.getTotalSlabPrice()+","                
            + "[Main Area] = \""+updatedSlab.getLocationMain()+"\","
            + "[Sub Area] = \""+updatedSlab.getLocationSubArea()+"\","
            + "[Row] = "+updatedSlab.getLocationRow()+","
            + "[Column] = "+updatedSlab.getLocationColumn()+","
            + "[Date Sold] = #"+updatedSlab.getDateSold()+"#,"
            + "[Customer Name] = \""+updatedSlab.getSoldToCustomer()+"\" "
            + "WHERE [ID#] = "+originalSlabId+";");    
        return rowsUpdated;
    }

    public static int updateSpecies(Species updatedspecies,String originalSpeciesCode){
        int rowsUpdated = StatementMaker.makeUpdateDeleteOrInsertStatement(                  
                "UPDATE [AddOnPrices] set "
            + "[Wood Code] = \""+updatedspecies.getSpeciesWoodCode()+"\","
            + "[Species] = \""+updatedspecies.getSpeciesName()+"\","
            + "[8in-11in] = "+updatedspecies.getPrice8InTo11In()+","
            + "[12in-20in] = "+updatedspecies.getPrice12InTo20In()+","
            + "[21in-23in] = "+updatedspecies.getPrice21InTo23In()+","
            + "[24in-30in] = "+updatedspecies.getPrice24InTo30In()+","
            + "[AmbrosiaPrc] = "+updatedspecies.getAmbrosiaPrice()+","
            + "[Axe SinkerPrc] = "+updatedspecies.getAxeSinkerPrice()+","
            + "[Birds EyePrc] = "+updatedspecies.getBirdsEyePrice()+","
            + "[BurledPrc] = "+updatedspecies.getBurledPrice()+","
            + "[CurlyPrc] = "+updatedspecies.getCurlyPrice()+","
            + "[DressedPrc] = "+updatedspecies.getDressedPrice()+","
            + "[KilnedPrc] = "+updatedspecies.getKilnedPrice()+","
            + "[PeckyPrc] = "+updatedspecies.getPeckyPrice()+","
            + "[SinkerPrc] = "+updatedspecies.getSinkerPrice()+","
            + "[SpaltedPrc] = "+updatedspecies.getSpaltedPrice()+" "
            + "WHERE [Wood Code] =\""+originalSpeciesCode+"\";");    
        return rowsUpdated;
    }
    
    public static int updatePassword(String newPassword){
        int rowsUpdated = StatementMaker.makeUpdateDeleteOrInsertStatement(                  
                "UPDATE [Password] set [Password] = \""+newPassword+"\" WHERE [ID] = 1;");
        return rowsUpdated;
    }
    
    public static int deleteInstockSlab(InStockSlab selectedSlab){
        int rowsDeleted = StatementMaker.makeUpdateDeleteOrInsertStatement("DELETE FROM [SlabEntry] WHERE [ID#]="+selectedSlab.getSlabIdFormatted()+";");
        return rowsDeleted;
    }
    
    public static int deleteSoldSlab(SoldSlab selectedSlab){
        int rowsDeleted = StatementMaker.makeUpdateDeleteOrInsertStatement("DELETE FROM [Sold Slabs] WHERE [ID#]="+selectedSlab.getSlabIdFormatted()+";");
        return rowsDeleted;
    }
    
    public static int deleteSpecies(Species selectedSpecies){
        int rowsDeleted = StatementMaker.makeUpdateDeleteOrInsertStatement("DELETE FROM [AddOnPrices] WHERE [Wood Code]=\""+selectedSpecies.getSpeciesWoodCode()+"\";");
        return rowsDeleted;
    }
}
