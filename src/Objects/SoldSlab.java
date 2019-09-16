/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import Database.DAO;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 *
 * @author Computebot
 */
public class SoldSlab extends Wood{
    
    private double thicknessInInches;
    private double widthInInches;
    private double lengthInFeet;
    private double totalBoardFeet;
    private double kilnedCharge;
    private double curlyCharge;
    private double burledCharge;
    private double spaltedCharge;
    private double birdsEyeCharge;
    private double peckyCharge;
    private double ambrosiaCharge;
    private double sinkerCharge;
    private double axeSinkerCharge;
    private double dressedCharge;
    private double featuresPrice;
    private double widthPrice;
    private double totalPricePerBoardFoot;
    private double totalSlabPrice;
    private String locationMain;
    private String locationSubArea;
    private int locationRow;
    private int locationColumn;
    private LocalDate dateSold;
    private String soldToCustomer;
    //extrapolated variable data-------------------------
    private String isDry;
    private String featureString;
    private String area;
    private String rowColumn;
    
    public SoldSlab(Long slabId, String woodCode, LocalDate milledDate,double thicknessInInches, double widthInInches, double lengthInFeet, double kilnedCharge, double curlyCharge, double burledCharge, double spaltedCharge, double birdsEyeCharge, double peckyCharge, double ambrosiaCharge, double sinkerCharge, double axeSinkerCharge, double dressedCharge, double widthPrice, String locationMain, String locationSubArea, int locationRow, int locationColumn, LocalDate dateSold, String soldToCustomer) {
        super(slabId, woodCode, milledDate);
        this.thicknessInInches = thicknessInInches;
        this.widthInInches = widthInInches;
        this.lengthInFeet = lengthInFeet;
        this.totalBoardFeet = (thicknessInInches*widthInInches*lengthInFeet)/12;
        this.kilnedCharge = kilnedCharge;
        this.curlyCharge = curlyCharge;
        this.burledCharge = burledCharge;
        this.spaltedCharge = spaltedCharge;
        this.birdsEyeCharge = birdsEyeCharge;
        this.peckyCharge = peckyCharge;
        this.ambrosiaCharge = ambrosiaCharge;
        this.sinkerCharge = sinkerCharge;
        this.axeSinkerCharge = axeSinkerCharge;
        this.dressedCharge = dressedCharge;
        this.featuresPrice = kilnedCharge+curlyCharge+burledCharge+spaltedCharge+birdsEyeCharge+peckyCharge+ambrosiaCharge+sinkerCharge+axeSinkerCharge+dressedCharge;
        this.widthPrice = widthPrice;
        this.totalPricePerBoardFoot = featuresPrice+widthPrice;
        this.totalSlabPrice = totalPricePerBoardFoot*totalBoardFeet;
        this.locationMain = locationMain;
        this.locationSubArea = locationSubArea;
        this.locationRow = locationRow;
        this.locationColumn = locationColumn;
        this.dateSold = dateSold;
        this.soldToCustomer = soldToCustomer;
    }

    public Long getSlabId() {
        return super.getWoodId();
    }
    
    public String getSlabIdFormatted() {
        return super.getWoodIdFormatted();
    } 
    
    public void setSlabId(Long slabId) {
        super.setWoodId(slabId);
    }

    public double getThicknessInInches() {
        return thicknessInInches;
    }

    public String getThicknessInInchesFormatted() {
        return DAO.doubleFormat.format(this.thicknessInInches);
    }

    public void setThicknessInInches(double thicknessInInches) {
        this.thicknessInInches = thicknessInInches;
    }
    
    public double getWidthInInches() {
        return widthInInches;
    }
    
    public String getWidthInInchesFormatted() {
        return DAO.doubleFormat.format(widthInInches);
    }
    
    public void setWidthInInches(double widthInInches) {
        this.widthInInches = widthInInches;
    }

    public double getLengthInFeet() {
        return lengthInFeet;
    }

    public String getLengthInFeetFormatted() {
        return DAO.doubleFormat.format(lengthInFeet);
    }

    public void setLengthInFeet(double lengthInInches) {
        this.lengthInFeet = lengthInInches;
    }

    public double getTotalBoardFeet() {
        this.totalBoardFeet = (thicknessInInches*widthInInches*lengthInFeet)/12;
        return totalBoardFeet;
    }
    
    public String getTotalBoardFeetFormatted(){
        return DAO.doubleFormat.format(getTotalBoardFeet());
    }
    
    public void setTotalBoardFeet(double totalBoardFeet) {
        this.totalBoardFeet = (thicknessInInches*widthInInches*lengthInFeet)/12;
    }

    public double getKilnedCharge() {
        return kilnedCharge;
    }

    public String getKilnedChargeFormatted() {
        return DAO.currencyFormat.format(kilnedCharge);
    }

    public void setKilnedCharge(double kilnedCharge) {
        this.kilnedCharge = kilnedCharge;
    }

    public double getCurlyCharge() {
        return curlyCharge;
    }

    public String getCurlyChargeFormatted() {
        return DAO.currencyFormat.format(curlyCharge);
    }

    public void setCurlyCharge(double curlyCharge) {
        this.curlyCharge = curlyCharge;
    }

    public double getBurledCharge() {
        return burledCharge;
    }

    public String getBurledChargeFormatted() {
        return DAO.currencyFormat.format(burledCharge);
    }

    public void setBurledCharge(double burledCharge) {
        this.burledCharge = burledCharge;
    }

    public double getSpaltedCharge() {
        return spaltedCharge;
    }

    public String getSpaltedChargeFormatted() {
        return DAO.currencyFormat.format(spaltedCharge);
    }

    public void setSpaltedCharge(double spaltedCharge) {
        this.spaltedCharge = spaltedCharge;
    }

    public double getBirdsEyeCharge() {
        return birdsEyeCharge;
    }

    public String getBirdsEyeChargeFormatted() {
        return DAO.currencyFormat.format(birdsEyeCharge);
    }

    public void setBirdsEyeCharge(double birdsEyeCharge) {
        this.birdsEyeCharge = birdsEyeCharge;
    }

    public double getPeckyCharge() {
        return peckyCharge;
    }

    public String getPeckyChargeFormatted() {
        return DAO.currencyFormat.format(peckyCharge);
    }

    public void setPeckyCharge(double peckyCharge) {
        this.peckyCharge = peckyCharge;
    }

    public double getAmbrosiaCharge() {
        return ambrosiaCharge;
    }
    
    public String getAmbrosiaChargeFormatted() {
        return DAO.currencyFormat.format(ambrosiaCharge);
    }

    public void setAmbrosiaCharge(double ambrosiaCharge) {
        this.ambrosiaCharge = ambrosiaCharge;
    }

    public double getSinkerCharge() {
        return sinkerCharge;
    }

    public String getSinkerChargeFormatted(){
        return DAO.currencyFormat.format(sinkerCharge);
    }

    public void setSinkerCharge(double sinkerCharge) {
        this.sinkerCharge = sinkerCharge;
    }

    public double getAxeSinkerCharge() {
        return axeSinkerCharge;
    }

    public String getAxeSinkerChargeFormatted() {
        return DAO.currencyFormat.format(axeSinkerCharge);
    }

    public void setAxeSinkerCharge(double axeSinkerCharge) {
        this.axeSinkerCharge = axeSinkerCharge;
    }

    public double getDressedCharge() {
        return dressedCharge;
    }

    public String getDressedChargeFormatted() {
        return DAO.currencyFormat.format(dressedCharge);
    }

    public void setDressedCharge(double dressedCharge) {
        this.dressedCharge = dressedCharge;
    }

    public double getTotalFeaturePrice() {
        this.featuresPrice = kilnedCharge+curlyCharge+burledCharge+spaltedCharge+birdsEyeCharge+peckyCharge+ambrosiaCharge+sinkerCharge+axeSinkerCharge+dressedCharge;
        return featuresPrice;
    }
    
    public String getTotalFeaturePriceFormatted(){
        return DAO.currencyFormat.format(getTotalFeaturePrice());
    }

    public void setFeaturesPrice(double featuresPrice) {
        this.featuresPrice = kilnedCharge+curlyCharge+burledCharge+spaltedCharge+birdsEyeCharge+peckyCharge+ambrosiaCharge+sinkerCharge+axeSinkerCharge+dressedCharge;
    }

    public double getWidthCost() {
        return widthPrice;
    }
    
    public String getWidthCostFormatted(){
     return DAO.currencyFormat.format(getWidthCost());
    }

    public void setWidthPrice(double widthPrice) {
        this.widthPrice = widthPrice;
    }

    public double getTotalPricePerBoardFoot() {
        this.totalPricePerBoardFoot = featuresPrice+widthPrice;
        return totalPricePerBoardFoot;
    }
    
    public String getTotalPricePerBoardFootFormatted(){
        return DAO.currencyFormat.format(getTotalPricePerBoardFoot());
    }

    public void setTotalPricePerBoardFoot(double totalPricePerBoardFoot) {
        this.totalPricePerBoardFoot = featuresPrice+widthPrice;
    }

    public double getTotalSlabPrice() {
        this.totalSlabPrice = totalPricePerBoardFoot*totalBoardFeet;
        return totalSlabPrice;
    }
    
    public String getTotalSlabPriceFormatted(){
        return DAO.currencyFormat.format(getTotalSlabPrice());
    }

    public void setTotalSlabPrice(double totalSlabPrice) {
        this.totalSlabPrice = totalPricePerBoardFoot*totalBoardFeet;
    }

    public String getLocationMain() {
        return locationMain;
    }

    public void setLocationMain(String locationMain) {
        this.locationMain = locationMain;
    }

    public String getLocationSubArea() {
        return locationSubArea;
    }

    public void setLocationSubArea(String locationSubArea) {
        this.locationSubArea = locationSubArea;
    }

    public int getLocationRow() {
        return locationRow;
    }

    public void setLocationRow(int locationRow) {
        this.locationRow = locationRow;
    }

    public int getLocationColumn() {
        return locationColumn;
    }

    public void setLocationColumn(int locationColumn) {
        this.locationColumn = locationColumn;
    }

    public LocalDate getDateSold() {
        return dateSold;
    }
    
    public String getDateSoldFormatted() {
        return DAO.readableDateFormat.format(dateSold.atTime(LocalTime.of(1,1)));
    }
    
    public void setDateSold(LocalDate dateSold) {
        this.dateSold = dateSold;
    }

    public String getSoldToCustomer() {
        return soldToCustomer;
    }

    public void setSoldToCustomer(String soldToCustomer) {
        this.soldToCustomer = soldToCustomer;
    }

    //Extropolated data functions------------------------------------------>
    public String getIsDry(){
        isDry = "No";        
        if(sinkerCharge>0.0){
            if(this.getMilledDate().equals(LocalDate.now().minusYears(1))||this.getMilledDate().isBefore(LocalDate.now().minusYears(1))){
                isDry = "Yes";
            }
        } else {
            int numYearsToDry = (int) Math.round(thicknessInInches);
                if(this.getMilledDate().equals(LocalDate.now().minusYears(numYearsToDry))||this.getMilledDate().isBefore(LocalDate.now().minusYears(numYearsToDry))){
                    isDry = "Yes";
                }
        }
        return isDry;
    }
    
    public String getFeatureString(){
        StringBuilder sb = new StringBuilder();
        if(sinkerCharge>0){
            sb.append("-Sn");
        }
        if(axeSinkerCharge>0){
            sb.append("-Ax");
        }
        if(ambrosiaCharge>0){
            sb.append("-Am");
        }
        if(birdsEyeCharge>0){
            sb.append("-Be");
        }
        if(burledCharge>0){
            sb.append("-Bu");
        }
        if(curlyCharge>0){
            sb.append("-C");
        }
        if(peckyCharge>0){
            sb.append("-P");
        }
        if(spaltedCharge>0){
            sb.append("-Sp");
        }
        if(kilnedCharge>0){
            sb.append("-K");
        }
        if(dressedCharge>0){
            sb.append("-D");
        }
        sb.append("-");
        featureString = sb.toString();
        return featureString;
    }
    
    public String getArea(){
    StringBuilder sb = new StringBuilder();
        sb.append(locationMain);
        if(!locationSubArea.isEmpty()||!locationSubArea.equals("")){
            sb.append("->").append(locationSubArea);
        }
        area = sb.toString();
        return area;
    }
    
    public String getRowColumn(){
        StringBuilder sb = new StringBuilder();
        sb.append("R:").append(locationRow);
        if(locationColumn>=1){
            sb.append(" C:").append(locationColumn);
        }
        rowColumn = sb.toString();
        return rowColumn;                
    }
    
    public InStockSlab convertSoldSlabToInStockSlab(){
       
        boolean isKilned=false; 
        boolean isCurly=false;  
        boolean isBurled=false;  
        boolean isSpalted=false;  
        boolean isBirdsEye=false;  
        boolean isPecky=false;  
        boolean isAmbrosia=false;  
        boolean isSinker=false;  
        boolean isAxeSinker=false;  
        boolean isDressed=false;  
        
        if(sinkerCharge>0){
           isSinker=true;
        }
        if(axeSinkerCharge>0){
           isAxeSinker=true;
        }
        if(ambrosiaCharge>0){
           isAmbrosia=true;
        }
        if(birdsEyeCharge>0){
           isBirdsEye=true;
        }
        if(burledCharge>0){
           isBurled=true;
        }
        if(curlyCharge>0){
           isCurly=true;
        }
        if(peckyCharge>0){
           isPecky=true;
        }
        if(spaltedCharge>0){
           isSpalted=true;
        }
        if(kilnedCharge>0){
           isKilned=true;
        }
        if(dressedCharge>0){
           isDressed=true;
        }
        return new InStockSlab(
            this.getWoodId(), 
            this.getWoodCode(), 
            this.getMilledDate(), 
            this.getThicknessInInches(), 
            this.getWidthInInches(), 
            this.getLengthInFeet(),
            isKilned, 
            isCurly, 
            isBurled, 
            isSpalted, 
            isBirdsEye, 
            isPecky, 
            isAmbrosia, 
            isSinker, 
            isAxeSinker, 
            isDressed, 
            this.getLocationMain(), 
            this.getLocationSubArea(), 
            this.getLocationRow(), 
            this.getLocationColumn(), 
            this.getDateSold(), 
            this.getSoldToCustomer()       
       ); 
    }
    
    public String toExportString(){
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------").append(System.getProperty("line.separator"));
        sb.append("ID: ").append(this.getSlabId());
        sb.append("|WC: ").append(this.getWoodCode());
        sb.append("|Milled: ").append(this.getMilledDate().toString());
        sb.append("|Dry: ").append(this.getIsDry());
        sb.append("|Th-in: ").append(this.getThicknessInInchesFormatted());
        sb.append("|W-in: ").append(this.getWidthInInchesFormatted());
        sb.append("|L-ft: ").append(this.getLengthInFeetFormatted());
        sb.append("|TtlBdFt: ").append(this.getTotalBoardFeetFormatted());
        sb.append("|Ttl$: ").append(this.getTotalSlabPriceFormatted());
        sb.append("|Feat: ");
        if(sinkerCharge>0){
           sb.append("-sn");
        }
        if(axeSinkerCharge>0){
           sb.append("-ax");
        }
        if(ambrosiaCharge>0){
           sb.append("-am");
        }
        if(birdsEyeCharge>0){
           sb.append("-be");
        }
        if(burledCharge>0){
           sb.append("-bu");
        }
        if(curlyCharge>0){
           sb.append("-c");
        }
        if(peckyCharge>0){
           sb.append("-p");
        }
        if(spaltedCharge>0){
           sb.append("-sp");
        }
        if(kilnedCharge>0){
           sb.append("-k");
        }
        if(dressedCharge>0){
           sb.append("-d");
        }
        sb.append("|Location: ");
        sb.append(this.getArea());
        sb.append("|").append(this.getRowColumn());
        try{
            sb.append("|Date Sold: ").append(this.getDateSoldFormatted());
            sb.append("|Customer: ").append(this.getSoldToCustomer()).append(System.getProperty("line.separator"));
        }catch(NullPointerException ex){
        /*Do Nothing because slab has not been sold*/
        }
        return sb.toString();
    } 
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------\n");
        sb.append("Id: ").append(this.getSlabId()).append("\n");
        sb.append("Wood Code: ").append(this.getWoodCode()).append("\n");
        sb.append("Milled Date: ").append(this.getMilledDate().toString()).append("\n");
        sb.append("Thickness(in): ").append(thicknessInInches).append("\n");
        sb.append("Width(in): ").append(widthInInches).append("\n");
        sb.append("Length(in): ").append(lengthInFeet).append("\n");
        sb.append("Total Board Feet: ").append(totalBoardFeet).append("\n");
        sb.append("Feature Charges:\n");
        if(sinkerCharge>0){
           sb.append("Sinker Charge: ").append(sinkerCharge).append("\n");
        }
        if(axeSinkerCharge>0){
           sb.append("Axe Sinker Charge: ").append(axeSinkerCharge).append("\n");
        }
        if(ambrosiaCharge>0){
           sb.append("Ambrosia Charge: ").append(ambrosiaCharge).append("\n");
        }
        if(birdsEyeCharge>0){
           sb.append("Birds Eye Charge: ").append(birdsEyeCharge).append("\n");
        }
        if(burledCharge>0){
           sb.append("Burled Charge: ").append(burledCharge).append("\n");
        }
        if(curlyCharge>0){
           sb.append("Curly Charge: ").append(curlyCharge).append("\n");
        }
        if(peckyCharge>0){
           sb.append("Pecky Charge: ").append(peckyCharge).append("\n");
        }
        if(spaltedCharge>0){
           sb.append("Spalted Charge: ").append(spaltedCharge).append("\n");
        }
        if(kilnedCharge>0){
           sb.append("Kilned Charge: ").append(kilnedCharge).append("\n");
        }
        if(dressedCharge>0){
           sb.append("Dressed Charge: ").append(dressedCharge).append("\n");
        }
        sb.append("Total Features Charge Per Board Foot: ").append(featuresPrice).append("\n");
        sb.append("Width Charge Per Board Foot: ").append(widthPrice).append("\n");
        sb.append("Total Price Per Board Foot: ").append(totalPricePerBoardFoot).append("\n");
        sb.append("Total Slab Cost: ").append(totalSlabPrice).append("\n");
        sb.append("Location:\n");
        sb.append(locationMain).append("\n");
        sb.append(locationSubArea).append("\n");
        sb.append("Row: ").append(locationRow).append("\n");
        sb.append("Column: ").append(locationColumn).append("\n");
        try{
            sb.append("Date Sold: ").append(dateSold).append("\n");
            sb.append("Sold To Customer: ").append(soldToCustomer).append("\n");
        }catch(NullPointerException ex){
        /*Do Nothing because slab has not been sold*/
        }
        return sb.toString();
    }    
}
