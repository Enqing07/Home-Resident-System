
public class VisitData {
    private int visitId;
    private int visitorId;
    private int residentId;
    private String arrivalDate;
    private String arrivalTime;
    private String purpose;

    public VisitData(int visitId, int visitorId, int resident_Id, String arrivalDate, String arrivalTime, String purpose) {
        this.visitId = visitId;
        this.visitorId = visitorId;
        this.residentId = resident_Id;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.purpose = purpose;
    }

    public int getVisitId() {
        return visitId;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public int getResidentId() {
        return residentId;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getPurpose() {
        return purpose;
    }
}
