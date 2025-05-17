public class VisitorData {
    private int visitorId;
    private int residentId;
    private String visitorName;
    private String visitorContact;

    public VisitorData(int visitorId, int resident_Id, String visitorName, String visitorContact) {
        this.visitorId = visitorId;
        this.residentId = resident_Id;
        this.visitorName = visitorName;
        this.visitorContact = visitorContact;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public int getResidentId() {
        return residentId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public String getVisitorContact() {
        return visitorContact;
    }

}