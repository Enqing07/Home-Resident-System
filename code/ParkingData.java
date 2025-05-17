public class ParkingData {
    private int parkingId;
    private int visitId;
    private int visitorId;
    private String carPlate;
    private String stayDuration;
    private String estimateCheckIn;
    private String checkOutBefore;

    public ParkingData(int parkingId, int visitId, int visitorId, String carPlate, String stayDuration, String estimateCheckIn, String checkOurBefore) {
        this.parkingId = parkingId;
        this.visitId = visitId;
        this.visitorId = visitorId;
        this.carPlate = carPlate;
        this.stayDuration = stayDuration;
        this.estimateCheckIn = estimateCheckIn;
        this.checkOutBefore = checkOurBefore;
    }

    public int getParkingId() {
        return parkingId;
    }

    public int getVisitId() {
        return visitId;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public String getStayDuration() {
        return stayDuration;
    }

    public String getEstimateCheckIn() {
        return estimateCheckIn;
    }

    public String getCheckOutBefore() {
        return checkOutBefore;
    }
}
