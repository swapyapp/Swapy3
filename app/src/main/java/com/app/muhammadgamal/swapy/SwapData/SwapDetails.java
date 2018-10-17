package com.app.muhammadgamal.swapy.SwapData;

public class SwapDetails {

    private String swapperImageUrl;
    private String swapperTl;
    private String swapperShiftTime;
    private String swapperShiftDay;
    private String swapperPreferredShift;
    private String swapperPhone;
    private String swapShiftDate;
    private String swapperTeamLeader;
    private String swapperID;

    public SwapDetails() {
    }

    public SwapDetails(String swapperImageUrl, String swapperTl, String swapperShiftTime, String swapperShiftDay,
                       String swapperPreferredShift, String swapperPhone, String swapShiftDate) {
        this.swapperImageUrl = swapperImageUrl;
        this.swapperTl = swapperTl;
        this.swapperShiftTime = swapperShiftTime;
        this.swapperShiftDay = swapperShiftDay;
        this.swapperPreferredShift = swapperPreferredShift;
        this.swapperPhone = swapperPhone;
        this.swapShiftDate = swapShiftDate;
    }

    public SwapDetails(String swapperID, String swapperShiftDay, String swapShiftDate, String swapperShiftTime, String swapperTeamLeader, String swapperPreferredShift) {
        this.swapperShiftTime = swapperShiftTime;
        this.swapperShiftDay = swapperShiftDay;
        this.swapperPreferredShift = swapperPreferredShift;
        this.swapShiftDate = swapShiftDate;
        this.swapperTeamLeader = swapperTeamLeader;
        this.swapperID = swapperID;
    }

    public String getSwapperID() {
        return swapperID;
    }

    public void setSwapperID(String swapperID) {
        this.swapperID = swapperID;
    }

    public String getSwapperImageUrl() {
        return swapperImageUrl;
    }

    public void setSwapperImageUrl(String swapperImageUrl) {
        this.swapperImageUrl = swapperImageUrl;
    }

    public String getSwapperTl() {
        return swapperTl;
    }

    public void setSwapperTl(String swapperTl) {
        this.swapperTl = swapperTl;
    }

    public String getSwapperShiftTime() {
        return swapperShiftTime;
    }

    public void setSwapperShiftTime(String swapperShiftTime) {
        this.swapperShiftTime = swapperShiftTime;
    }

    public String getSwapperShiftDay() {
        return swapperShiftDay;
    }

    public void setSwapperShiftDay(String swapperShiftDay) {
        this.swapperShiftDay = swapperShiftDay;
    }

    public String getSwapperPreferredShift() {
        return swapperPreferredShift;
    }

    public void setSwapperPreferredShift(String swapperPreferredShift) {
        this.swapperPreferredShift = swapperPreferredShift;
    }

    public String getSwapperPhone() {
        return swapperPhone;
    }

    public void setSwapperPhone(String swapperPhone) {
        this.swapperPhone = swapperPhone;
    }

    public String getSwapShiftDate() {
        return swapShiftDate;
    }

    public void setSwapShiftDate(String swapShiftDate) {
        this.swapShiftDate = swapShiftDate;
    }

    public String getSwapperTeamLeader() {
        return swapperTeamLeader;
    }

    public void setSwapperTeamLeader(String swapperTeamLeader) {
        this.swapperTeamLeader = swapperTeamLeader;
    }
}
