public class ResidentData {
    private  int residentId;
    private String residentName;
    private String residentContactNo;
    private String tower_floor_unit;

    public ResidentData(int resident_Id, String residentName, String residentContactNo, String tower_floor_unit){
        this.residentId = resident_Id;
        this.residentName = residentName;
        this.residentContactNo = residentContactNo;
        this.tower_floor_unit = tower_floor_unit;
    }

    public int getResidentId() {
        return residentId;
    }

    public String getResidentName() {
        return residentName;
    }

    public String getResidentContactNo() {
        return residentContactNo;
    }

    public String getTower_floor_unit() {
        return tower_floor_unit;
    }
}
