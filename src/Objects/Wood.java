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
public class Wood {
    private Long woodId;
    private String woodCode;
    private LocalDate milledDate;

    public Wood(Long woodId, String woodCode, LocalDate milledDate) {
        this.woodId = woodId;
        this.woodCode = woodCode;
        this.milledDate = milledDate;
    }

    public Long getWoodId() {
        return woodId;
    }
    
    public String getWoodIdFormatted() {
        int idLength = String.valueOf(Math.abs(woodId)).length();
        int numZeroesToAdd = 10 - idLength;
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<numZeroesToAdd;i++){
            sb.append("0");
        }
        sb.append(woodId);
        return sb.toString();
    }

    public void setWoodId(Long woodId) {
        this.woodId = woodId;
    }

    public String getWoodCode() {
        return woodCode;
    }

    public void setWoodCode(String woodCode) {
        this.woodCode = woodCode;
    }

    public LocalDate getMilledDate() {
        return milledDate;
    }
    
    public String getMilledDateFormatted() {
        return DAO.readableDateFormat.format(milledDate.atTime(LocalTime.of(1,1)));
    }

    public void setMilledDate(LocalDate milledDate) {
        this.milledDate = milledDate;
    }
    
    
}
