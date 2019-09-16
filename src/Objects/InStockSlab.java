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
public class InStockSlab extends Wood{
    private double thicknessInInches;
    private double widthInInches;
    private double lengthInFeet;
    private boolean isKilned;
    private boolean isCurly;
    private boolean isBurled;
    private boolean isSpalted;
    private boolean isBirdsEye;
    private boolean isPecky;
    private boolean isAmbrosia;
    private boolean isSinker;
    private boolean isAxeSinker;
    private boolean isDressed;
    private String locationMain;
    private String locationSubArea;
    private Integer locationRow;
    private Integer locationColumn;
    private LocalDate dateSold;//this variable allows a user to unsell an item for repricing and save the dateSold variable from first sale
    private String soldToCustomer;//this variable allows a user to unsell an item for repricing and save the soldToCustomer variable from first sale
    
    //internal use only for extrapolated functions, keeps from making multiple trips to the database;
    private static String isDry;
    private static double totalBoardFeet;
    private static String featureString;    
    private static double totalFeaturePrice;
    private static double widthCost;
    private static double totalPricePerBoardFoot;
    private static double totalSlabPrice;
    private static String area;
    private static String rowColumn;

    
    public InStockSlab(Long slabId, String woodCode, LocalDate milledDate, double thicknessInInches, double widthInInches, double lengthInFeet, boolean isKilned, boolean isCurly, boolean isBurled, boolean isSpalted, boolean isBirdsEye, boolean isPecky, boolean isAmbrosia, boolean isSinker, boolean isAxeSinker, boolean isDressed, String locationMain, String locationSubArea, int locationRow, int locationColumn, LocalDate dateSold, String soldToCustomer) {
        super(slabId,woodCode,milledDate);
        this.thicknessInInches = thicknessInInches;
        this.widthInInches = widthInInches;
        this.lengthInFeet = lengthInFeet;
        this.isKilned = isKilned;
        this.isCurly = isCurly;
        this.isBurled = isBurled;
        this.isSpalted = isSpalted;
        this.isBirdsEye = isBirdsEye;
        this.isPecky = isPecky;
        this.isAmbrosia = isAmbrosia;
        this.isSinker = isSinker;
        this.isAxeSinker = isAxeSinker;
        this.isDressed = isDressed;
        this.locationMain = locationMain;
        this.locationSubArea = locationSubArea;
        this.locationRow = locationRow;
        this.locationColumn = locationColumn;
        this.dateSold = dateSold;
        this.soldToCustomer = soldToCustomer;
    }
//Variable Functions------------------------------------------->
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
        return DAO.doubleFormat.format(thicknessInInches);
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

    public boolean isKilned() {
        return isKilned;
    }

    public void setIsKilned(boolean isKilned) {
        this.isKilned = isKilned;
    }

    public boolean isCurly() {
        return isCurly;
    }

    public void setIsCurly(boolean isCurly) {
        this.isCurly = isCurly;
    }

    public boolean isBurled() {
        return isBurled;
    }

    public void setIsBurled(boolean isBurled) {
        this.isBurled = isBurled;
    }

    public boolean isSpalted() {
        return isSpalted;
    }

    public void setIsSpalted(boolean isSpalted) {
        this.isSpalted = isSpalted;
    }

    public boolean isBirdsEye() {
        return isBirdsEye;
    }

    public void setIsBirdsEye(boolean isBirdsEye) {
        this.isBirdsEye = isBirdsEye;
    }

    public boolean isPecky() {
        return isPecky;
    }

    public void setIsPecky(boolean isPecky) {
        this.isPecky = isPecky;
    }

    public boolean isAmbrosia() {
        return isAmbrosia;
    }

    public void setIsAmbrosia(boolean isAmbrosia) {
        this.isAmbrosia = isAmbrosia;
    }

    public boolean isSinker() {
        return isSinker;
    }

    public void setIsSinker(boolean isSinker) {
        this.isSinker = isSinker;
    }

    public boolean isAxeSinker() {
        return isAxeSinker;
    }

    public void setIsAxeSinker(boolean isAxeSinker) {
        this.isAxeSinker = isAxeSinker;
    }

    public boolean isDressed() {
        return isDressed;
    }

    public void setIsDressed(boolean isDressed) {
        this.isDressed = isDressed;
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
        if(isSinker){
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
    public double getTotalBoardFeet(){
        totalBoardFeet = (thicknessInInches*widthInInches*lengthInFeet)/12;
        return totalBoardFeet;
    }
    
    public String getTotalBoardFeetFormatted(){
        return DAO.doubleFormat.format(getTotalBoardFeet());
    }
    public String getFeatureString(){
        StringBuilder sb = new StringBuilder();
        if(isSinker){
            sb.append("-Sn");
        }
        if(isAxeSinker){
            sb.append("-Ax");
        }
        if(isAmbrosia){
            sb.append("-Am");
        }
        if(isBirdsEye){
            sb.append("-Be");
        }
        if(isBurled){
            sb.append("-Bu");
        }
        if(isCurly){
            sb.append("-C");
        }
        if(isPecky){
            sb.append("-P");
        }
        if(isSpalted){
            sb.append("-Sp");
        }
        if(isKilned){
            sb.append("-K");
        }
        if(isDressed){
            sb.append("-D");
        }
        sb.append("-");
        featureString =sb.toString();
        return featureString;
    }
    public double getTotalFeaturePrice(){
        double totalCalculatedFeaturePrice = 0.0;
        totalCalculatedFeaturePrice = totalCalculatedFeaturePrice + DAO.getSpeciesFeaturePrice(isKilned,this.getWoodCode(),"KilnedPrc");
        totalCalculatedFeaturePrice = totalCalculatedFeaturePrice + DAO.getSpeciesFeaturePrice(isCurly,this.getWoodCode(),"CurlyPrc");
        totalCalculatedFeaturePrice = totalCalculatedFeaturePrice + DAO.getSpeciesFeaturePrice(isBurled,this.getWoodCode(),"BurledPrc");
        totalCalculatedFeaturePrice = totalCalculatedFeaturePrice + DAO.getSpeciesFeaturePrice(isSpalted,this.getWoodCode(),"SpaltedPrc");
        totalCalculatedFeaturePrice = totalCalculatedFeaturePrice + DAO.getSpeciesFeaturePrice(isBirdsEye,this.getWoodCode(),"Birds EyePrc");
        totalCalculatedFeaturePrice = totalCalculatedFeaturePrice + DAO.getSpeciesFeaturePrice(isPecky,this.getWoodCode(),"PeckyPrc");
        totalCalculatedFeaturePrice = totalCalculatedFeaturePrice + DAO.getSpeciesFeaturePrice(isAmbrosia,this.getWoodCode(),"AmbrosiaPrc");
        totalCalculatedFeaturePrice = totalCalculatedFeaturePrice + DAO.getSpeciesFeaturePrice(isSinker,this.getWoodCode(),"SinkerPrc");
        totalCalculatedFeaturePrice = totalCalculatedFeaturePrice + DAO.getSpeciesFeaturePrice(isAxeSinker,this.getWoodCode(),"Axe SinkerPrc");
        totalCalculatedFeaturePrice = totalCalculatedFeaturePrice + DAO.getSpeciesFeaturePrice(isDressed,this.getWoodCode(),"DressedPrc");
        totalFeaturePrice = totalCalculatedFeaturePrice;
        return totalFeaturePrice;
    }
    
    public String getTotalFeaturePriceFormatted(){
        return DAO.currencyFormat.format(getTotalFeaturePrice());
    }
    
    public double getWidthCost(){
        widthCost = DAO.getSpeciesWidthPrice(this.getWoodCode(),widthInInches);
        return widthCost;
    }
    
    public String getWidthCostFormatted(){
     return DAO.currencyFormat.format(getWidthCost());
    }
    
    public double getTotalPricePerBoardFoot(){
        totalPricePerBoardFoot = widthCost + totalFeaturePrice;
        return totalPricePerBoardFoot;
    }
    
    public String getTotalPricePerBoardFootFormatted(){
        return DAO.currencyFormat.format(getTotalPricePerBoardFoot());
    }
    
    public double getTotalSlabPrice(){
        totalSlabPrice = totalPricePerBoardFoot*totalBoardFeet;
        return totalSlabPrice;
    }
    
    public String getTotalSlabPriceFormatted(){
        return DAO.currencyFormat.format(getTotalSlabPrice());
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

    public SoldSlab convertInStockSlabToSoldSlab(){
        return new SoldSlab(
            this.getSlabId(),
            this.getWoodCode(),
            this.getMilledDate(),
            this.getThicknessInInches(),
            this.getWidthInInches(),
            this.getLengthInFeet(),
            DAO.getSpeciesFeaturePrice(this.isKilned(), this.getWoodCode(),"KilnedPrc"),
            DAO.getSpeciesFeaturePrice(this.isCurly(), this.getWoodCode(),"CurlyPrc"),
            DAO.getSpeciesFeaturePrice(this.isBurled(), this.getWoodCode(),"BurledPrc"),
            DAO.getSpeciesFeaturePrice(this.isSpalted(), this.getWoodCode(),"SpaltedPrc"),
            DAO.getSpeciesFeaturePrice(this.isBirdsEye(), this.getWoodCode(),"Birds EyePrc"),
            DAO.getSpeciesFeaturePrice(this.isPecky(), this.getWoodCode(),"PeckyPrc"),
            DAO.getSpeciesFeaturePrice(this.isAmbrosia(), this.getWoodCode(),"AmbrosiaPrc"),
            DAO.getSpeciesFeaturePrice(this.isSinker(), this.getWoodCode(),"SinkerPrc"),
            DAO.getSpeciesFeaturePrice(this.isAxeSinker(), this.getWoodCode(),"Axe SinkerPrc"),
            DAO.getSpeciesFeaturePrice(this.isDressed(), this.getWoodCode(),"DressedPrc"),
            DAO.getSpeciesWidthPrice(this.getWoodCode(), this.getWidthInInches()),
            this.getLocationMain(),
            this.getLocationSubArea(),
            this.getLocationRow(),
            this.getLocationColumn(),
            this.getDateSold(),
            this.getSoldToCustomer()
        );
    }
    public void getDerivedAttributes(){
        getIsDry();
        getTotalBoardFeet();
        getTotalFeaturePrice();
        getWidthCost();
        getTotalPricePerBoardFoot();
        getTotalSlabPrice();
    }
    
    public String toExportString(){
        getDerivedAttributes();
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------------------------------------------------------------------------------------------------------------------------------").append(System.getProperty("line.separator"));
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
        if(isSinker){
           sb.append("-sn");
        }
        if(isAxeSinker){
           sb.append("-ax");
        }
        if(isAmbrosia){
           sb.append("-am");
        }
        if(isBirdsEye){
           sb.append("-be");
        }
        if(isBurled){
           sb.append("-bu");
        }
        if(isCurly){
           sb.append("-c");
        }
        if(isPecky){
           sb.append("-p");
        }
        if(isSpalted){
           sb.append("-sp");
        }
        if(isKilned){
           sb.append("-k");
        }
        if(isDressed){
           sb.append("-d");
        }
        sb.append("|Location: ");
        sb.append(this.getArea());
        sb.append("|").append(this.getRowColumn()).append(System.getProperty("line.separator"));
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
        sb.append("Length(ft): ").append(lengthInFeet).append("\n");
        sb.append("Features:\n");
        if(isSinker){
           sb.append("Sinker\n");
        }
        if(isAxeSinker){
           sb.append("AxeSinker\n");
        }
        if(isAmbrosia){
           sb.append("Ambrosia\n");
        }
        if(isBirdsEye){
           sb.append("Birds Eye\n");
        }
        if(isBurled){
           sb.append("Burled\n");
        }
        if(isCurly){
           sb.append("Curly\n");
        }
        if(isPecky){
           sb.append("Pecky\n");
        }
        if(isSpalted){
           sb.append("Spalted\n");
        }
        if(isKilned){
           sb.append("Kilned\n");
        }
        if(isDressed){
           sb.append("Dressed\n");
        }
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
