/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import Database.DAO;

/**
 *
 * @author Computebot
 */
public class Species {
    private String speciesWoodCode;
    private String speciesName;
    private double price8InTo11In;
    private double price12InTo20In;
    private double price21InTo23In;
    private double price24InTo30In;
    private double ambrosiaPrice;
    private double axeSinkerPrice;
    private double birdsEyePrice;
    private double burledPrice;
    private double curlyPrice;
    private double dressedPrice;
    private double kilnedPrice;
    private double peckyPrice;
    private double sinkerPrice;
    private double spaltedPrice;

    public Species(String speciesWoodCode, String speciesName, double price8InTo11In, double price12InTo20In, double price21InTo23In, double price24InTo31In, double ambrosiaPrice, double axeSinkerPrice, double birdsEyePrice, double burledPrice, double curlyPrice, double DressedPrice, double KilnedPrice, double peckyPrice, double sinkerPrice, double spaltedPrice) {
        this.speciesWoodCode = speciesWoodCode;
        this.speciesName = speciesName;
        this.price8InTo11In = price8InTo11In;
        this.price12InTo20In = price12InTo20In;
        this.price21InTo23In = price21InTo23In;
        this.price24InTo30In = price24InTo31In;
        this.ambrosiaPrice = ambrosiaPrice;
        this.axeSinkerPrice = axeSinkerPrice;
        this.birdsEyePrice = birdsEyePrice;
        this.burledPrice = burledPrice;
        this.curlyPrice = curlyPrice;
        this.dressedPrice = DressedPrice;
        this.kilnedPrice = KilnedPrice;
        this.peckyPrice = peckyPrice;
        this.sinkerPrice = sinkerPrice;
        this.spaltedPrice = spaltedPrice;
    }

    public String getSpeciesWoodCode() {
        return speciesWoodCode;
    }

    public void setSpeciesWoodCode(String speciesWoodCode) {
        this.speciesWoodCode = speciesWoodCode;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public double getPrice8InTo11In() {
        return price8InTo11In;
    }

    public String getPrice8InTo11InCurrencyFormatted() {
        return DAO.currencyFormat.format(price8InTo11In);
    }
    
    public String getPrice8InTo11InNumberFormatted() {
        return DAO.doubleFormatDecimal.format(price8InTo11In);
    }

    public void setPrice8InTo11In(double price8InTo11In) {
        this.price8InTo11In = price8InTo11In;
    }

    public double getPrice12InTo20In() {
        return price12InTo20In;
    }

    public String getPrice12InTo20InCurrencyFormatted() {
        return DAO.currencyFormat.format(price12InTo20In);
    }
    
    public String getPrice12InTo20InNumberFormatted() {
        return DAO.doubleFormatDecimal.format(price12InTo20In);
    }

    public void setPrice12InTo20In(double price12InTo20In) {
        this.price12InTo20In = price12InTo20In;
    }

    public double getPrice21InTo23In() {
        return price21InTo23In;
    }

    public String getPrice21InTo23InCurrencyFormatted() {
        return DAO.currencyFormat.format(price21InTo23In);
    }
    
    public String getPrice21InTo23InNumberFormatted() {
        return DAO.doubleFormatDecimal.format(price21InTo23In);
    }

    public void setPrice21InTo23In(double price21InTo23In) {
        this.price21InTo23In = price21InTo23In;
    }

    public double getPrice24InTo30In() {
        return price24InTo30In;
    }

    public String getPrice24InTo30InCurrencyFormatted() {
        return DAO.currencyFormat.format(price24InTo30In);
    }
    
    public String getPrice24InTo30InNumberFormatted() {
        return DAO.doubleFormatDecimal.format(price24InTo30In);
    }

    public void setPrice24InTo30In(double price24InTo31In) {
        this.price24InTo30In = price24InTo31In;
    }

    public double getAmbrosiaPrice() {
        return ambrosiaPrice;
    }

    public String getAmbrosiaPriceCurrencyFormatted() {
        return DAO.currencyFormat.format(ambrosiaPrice);
    }

    public String getAmbrosiaPriceNumberFormatted() {
        return DAO.doubleFormatDecimal.format(ambrosiaPrice);
    }

    public void setAmbrosiaPrice(double ambrosiaPrice) {
        this.ambrosiaPrice = ambrosiaPrice;
    }

    public double getAxeSinkerPrice() {
        return axeSinkerPrice;
    }

    public String getAxeSinkerPriceCurrencyFormatted() {
        return DAO.currencyFormat.format(axeSinkerPrice);
    }

    public String getAxeSinkerPriceNumberFormatted() {
        return DAO.doubleFormatDecimal.format(axeSinkerPrice);
    }

    public void setAxeSinkerPrice(double axeSinkerPrice) {
        this.axeSinkerPrice = axeSinkerPrice;
    }

    public double getBirdsEyePrice() {
        return birdsEyePrice;
    }

    public String getBirdsEyePriceCurrencyFormatted() {
        return DAO.currencyFormat.format(birdsEyePrice);
    }

    public String getBirdsEyePriceNumberFormatted() {
        return DAO.doubleFormatDecimal.format(birdsEyePrice);
    }

    public void setBirdsEyePrice(double birdsEyePrice) {
        this.birdsEyePrice = birdsEyePrice;
    }

    public double getBurledPrice() {
        return burledPrice;
    }

    public String getBurledPriceCurrencyFormatted() {
        return DAO.currencyFormat.format(burledPrice);
    }

    public String getBurledPriceNumberFormatted() {
        return DAO.doubleFormatDecimal.format(burledPrice);
    }

    public void setBurledPrice(double burledPrice) {
        this.burledPrice = burledPrice;
    }

    public double getCurlyPrice() {
        return curlyPrice;
    }

    public String getCurlyPriceCurrencyFormatted() {
        return DAO.currencyFormat.format(curlyPrice);
    }

    public String getCurlyPriceNumberFormatted() {
        return DAO.doubleFormatDecimal.format(curlyPrice);
    }

    public void setCurlyPrice(double curlyPrice) {
        this.curlyPrice = curlyPrice;
    }

    public double getDressedPrice() {
        return dressedPrice;
    }

    public String getDressedPriceCurrencyFormatted() {
        return DAO.currencyFormat.format(dressedPrice);
    }

    public String getDressedPriceNumberFormatted() {
        return DAO.doubleFormatDecimal.format(dressedPrice);
    }

    public void setDressedPrice(double DressedPrice) {
        this.dressedPrice = DressedPrice;
    }

    public double getKilnedPrice() {
        return kilnedPrice;
    }

    public String getKilnedPriceCurrencyFormatted() {
        return DAO.currencyFormat.format(kilnedPrice);
    }

    public String getKilnedPriceNumberFormatted() {
        return DAO.doubleFormatDecimal.format(kilnedPrice);
    }

    public void setKilnedPrice(double KilnedPrice) {
        this.kilnedPrice = KilnedPrice;
    }

    public double getPeckyPrice() {
        return peckyPrice;
    }

    public String getPeckyPriceCurrencyFormatted() {
        return DAO.currencyFormat.format(peckyPrice);
    }

    public String getPeckyPriceNumberFormatted() {
        return DAO.doubleFormatDecimal.format(peckyPrice);
    }

    public void setPeckyPrice(double peckyPrice) {
        this.peckyPrice = peckyPrice;
    }

    public double getSinkerPrice() {
        return sinkerPrice;
    }

    public String getSinkerPriceCurrencyFormatted(){
        return DAO.currencyFormat.format(sinkerPrice);
    }

    public String getSinkerPriceNumberFormatted(){
        return DAO.doubleFormatDecimal.format(sinkerPrice);
    }

    public void setSinkerPrice(double sinkerPrice) {
        this.sinkerPrice = sinkerPrice;
    }

    public double getSpaltedPrice() {
        return spaltedPrice;
    }

    public String getSpaltedPriceCurrencyFormatted() {
        return DAO.currencyFormat.format(spaltedPrice);
    }

    public String getSpaltedPriceNumberFormatted() {
        return DAO.doubleFormatDecimal.format(spaltedPrice);
    }

    public void setSpaltedPrice(double spaltedPrice) {
        this.spaltedPrice = spaltedPrice;
    }
    
    
    public String toExportString(){
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------").append(System.getProperty("line.separator"));
        sb.append("WC: ").append(this.getSpeciesWoodCode());
        sb.append("|Name: ").append(this.getSpeciesName());
        sb.append("|8-12: ").append(this.getPrice8InTo11InCurrencyFormatted());
        sb.append("|12-20: ").append(this.getPrice12InTo20InCurrencyFormatted());
        sb.append("|21-23: ").append(this.getPrice21InTo23InCurrencyFormatted());
        sb.append("|24-30: ").append(this.getPrice24InTo30InCurrencyFormatted());
        sb.append("|Sinker: ").append(this.getSinkerPriceCurrencyFormatted());
        sb.append("|Axe: ").append(this.getAxeSinkerPriceCurrencyFormatted());
        sb.append("|Ambrosia: ").append(this.getAmbrosiaPriceCurrencyFormatted());
        sb.append("|Birds: ").append(this.getBirdsEyePriceCurrencyFormatted());
        sb.append("|Burled: ").append(this.getBurledPriceCurrencyFormatted());
        sb.append("|Curly: ").append(this.getCurlyPriceCurrencyFormatted());
        sb.append("|Pecky: ").append(this.getPeckyPriceCurrencyFormatted());
        sb.append("|Spalted: ").append(this.getSpaltedPriceCurrencyFormatted());
        sb.append("|Kilned: ").append(this.getKilnedPriceCurrencyFormatted());
        sb.append("|Dressed: ").append(this.getDressedPriceCurrencyFormatted()).append(System.getProperty("line.separator"));
        return sb.toString();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------\n");
        sb.append("Species Wood Code: ").append(speciesWoodCode).append("\n");
        sb.append("Species Name: ").append(speciesName).append("\n");
        sb.append("8in to 12in Price: ").append(price8InTo11In).append("\n");
        sb.append("12in to 20in Price: ").append(price12InTo20In).append("\n");
        sb.append("21in to 23in Price: ").append(price21InTo23In).append("\n");
        sb.append("24in to 30in Price: ").append(price24InTo30In).append("\n");
        sb.append("Ambrosia Charge: ").append(ambrosiaPrice).append("\n");
        sb.append("Axe Sinker Charge: ").append(axeSinkerPrice).append("\n");
        sb.append("Birds Eye Charge: ").append(birdsEyePrice).append("\n");
        sb.append("Burled Charge: ").append(burledPrice).append("\n");
        sb.append("Curly Charge: ").append(curlyPrice).append("\n");
        sb.append("Dressed Charge: ").append(dressedPrice).append("\n");
        sb.append("Kilned Charge: ").append(kilnedPrice).append("\n");
        sb.append("Pecky Charge: ").append(peckyPrice).append("\n");
        sb.append("Sinker Charge: ").append(sinkerPrice).append("\n");
        sb.append("Spalted Charge: ").append(spaltedPrice).append("\n");
        return sb.toString();
    }
}

