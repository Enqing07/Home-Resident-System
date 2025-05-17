import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ResidentPageController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private Button addVisitor_btn;

    @FXML
    private AnchorPane addvisitor_page;

    @FXML
    private Button av_home_btn;

    @FXML
    private Button av_return_btn;

    @FXML
    private TextField contactNo;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane mainscreen_page;

    @FXML
    private Button manageVisitor_btn;

    @FXML
    private AnchorPane managevisitor_page;

    @FXML
    private Button mv_home_btn;

    @FXML
    private Button mv_return_btn;

    @FXML
    private TextField parkingD_days;

    @FXML
    private TextField parkingD_hours;

    @FXML
    private TextField parkingD_mins;

    @FXML
    private Button parking_btn;

    @FXML
    private TextField parking_carplate;

    @FXML
    private TextField parking_date;

    @FXML
    private AnchorPane parking_page;

    @FXML
    private ComboBox<?> parking_selectVisitor;

    @FXML
    private Button parking_submit_btn;

    @FXML
    private TextField parking_time;

    @FXML
    private Button pr_home_btn;

    @FXML
    private Button pr_return_btn;

    @FXML
    private TextField r_residentid;

    @FXML
    private TextField r_residentname;

    @FXML
    private Button removeVisitor_btn;

    @FXML
    private Button remove_btn;

    @FXML
    private AnchorPane removevisitor_page;

    @FXML
    private Button rv_home_btn;

    @FXML
    private Button rv_return_btn;

    @FXML
    private AnchorPane schedulevisit_page;

    @FXML
    private ComboBox<?> selectVisitor_rv;

    @FXML
    private ComboBox<?> selectVisitor_sv;

    @FXML
    private Button signout_btn;

    @FXML
    private Button sv_home_btn;

    @FXML
    private Button sv_return_btn;

    @FXML
    private Button visit_btn;

    @FXML
    private TextField visit_date;

    @FXML
    private TextField visit_purpose;

    @FXML
    private Button visit_submit_btn;

    @FXML
    private TextField visit_time;

    @FXML
    private TextField visitorName;

    static final String DB_URL = "jdbc:mysql://localhost:3306/homeresidentsystem";
    static final String USER = "root";
    static final String PASS = "Admin123";

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public Connection connectDB() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void switchForm(ActionEvent event) {
        // main screen naviagte
        if (event.getSource() == manageVisitor_btn) {
            mainscreen_page.setVisible(false);
            managevisitor_page.setVisible(true);
            parking_page.setVisible(false);
        } else if (event.getSource() == parking_btn) {
            mainscreen_page.setVisible(false);
            managevisitor_page.setVisible(false);
            parking_page.setVisible(true);
        }

        // return to main screen
        if (event.getSource() == home_btn || event.getSource() == mv_home_btn ||
                event.getSource() == av_home_btn || event.getSource() == rv_home_btn ||
                event.getSource() == sv_home_btn || event.getSource() == pr_home_btn ||
                event.getSource() == pr_return_btn || event.getSource() == mv_return_btn) {
            mainscreen_page.setVisible(true);
            managevisitor_page.setVisible(false);
            parking_page.setVisible(false);
            addvisitor_page.setVisible(false);
            removevisitor_page.setVisible(false);
            schedulevisit_page.setVisible(false);
        }

        // manage visitor navigate
        if (event.getSource() == addVisitor_btn) {
            managevisitor_page.setVisible(false);
            addvisitor_page.setVisible(true);
            removevisitor_page.setVisible(false);
            schedulevisit_page.setVisible(false);
        } else if (event.getSource() == removeVisitor_btn) {
            managevisitor_page.setVisible(false);
            addvisitor_page.setVisible(false);
            removevisitor_page.setVisible(true);
            schedulevisit_page.setVisible(false);
        } else if (event.getSource() == visit_btn) {
            managevisitor_page.setVisible(false);
            addvisitor_page.setVisible(false);
            removevisitor_page.setVisible(false);
            schedulevisit_page.setVisible(true);
        }

        // return to manage visitor screen
        if (event.getSource() == av_return_btn || event.getSource() == rv_return_btn
                || event.getSource() == sv_return_btn) {
            managevisitor_page.setVisible(true);
            addvisitor_page.setVisible(false);
            removevisitor_page.setVisible(false);
            schedulevisit_page.setVisible(false);
        }

        // sign out
        if (event.getSource() == signout_btn) {

            Stage stage = (Stage) signout_btn.getScene().getWindow();
            stage.close();
        }
    }

    public void addVisitor() {
        alertMessage alert = new alertMessage();
        PreparedStatement psInsert = null;
        PreparedStatement psCheckVisitorExist = null;

        if (visitorName.getText().isEmpty() || contactNo.getText().isEmpty()) {
            alert.errorMessage("All fields are neccessary to be filled.");
        } else {
            connection = connectDB();
            try {
                psCheckVisitorExist = connection
                        .prepareStatement("SELECT * FROM visitor WHERE resident_id = ? AND visitor_contactno = ?");
                psCheckVisitorExist.setInt(1, Data.residentID);
                psCheckVisitorExist.setString(2, contactNo.getText());
                resultSet = psCheckVisitorExist.executeQuery();

                if (resultSet.isBeforeFirst()) {

                    alert.errorMessage("Cannot add Visitor " + visitorName.getText() +
                            "\nInfo: This phone number " + "(" + contactNo.getText() + ")"
                            + "\n\t is already in use by another visitor.");
                } else {
                    psInsert = connection.prepareStatement(
                            "INSERT INTO visitor(visitor_name, visitor_contactno, resident_id) VALUES(?,?,?)");
                    psInsert.setString(1, visitorName.getText());
                    psInsert.setString(2, contactNo.getText());
                    psInsert.setInt(3, Data.residentID);
                    psInsert.executeUpdate();

                    alert.successMessage("Visitor added successfully!");
                    addVisitorClearFields();
                    visitorList();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addVisitorClearFields() {
        visitorName.setText("");
        contactNo.setText("");
    }

    public void visitorList() {
        List<String> visitorList = new ArrayList<>();

        connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT visitor_name FROM visitor WHERE resident_id = ?");
            preparedStatement.setInt(1, Data.residentID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String visitorName = resultSet.getString("visitor_name");
                visitorList.add(visitorName);
            }

            ObservableList listData = FXCollections.observableArrayList(visitorList);
            selectVisitor_rv.setItems(listData);
            selectVisitor_sv.setItems(listData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeVisitor() {
        alertMessage alert = new alertMessage();

        if (selectVisitor_rv.getItems().isEmpty()) {
            alert.errorMessage("Please select a visitor to removed.");
        } else {
            connection = connectDB();
            try {
                String selectedVisitor = selectVisitor_rv.getSelectionModel().getSelectedItem().toString();
                PreparedStatement ps = connection.prepareStatement(
                        "DELETE FROM visitor WHERE visitor_name = ? AND resident_id = ?");
                ps.setString(1, selectedVisitor);
                ps.setInt(2, Data.residentID);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    alert.successMessage("Visitor removed successfully!");
                } else {
                    alert.errorMessage("Visitor not found for removal.");
                }
                removeVisitorClearFields();
                visitorList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeVisitorClearFields() {
        selectVisitor_sv.getSelectionModel().clearSelection();
    }

    public void scheduleVisit() {
        alertMessage alert = new alertMessage();

        if (selectVisitor_sv.getItems().isEmpty() || visit_date.getText().isEmpty() || visit_time.getText().isEmpty()
                || visit_purpose.getText().isEmpty()) {
            alert.errorMessage("All fields are neccessary to be filled.");
        } else {
            connection = connectDB();
            try {
                String selectedVisitor = selectVisitor_sv.getSelectionModel().getSelectedItem().toString();
                preparedStatement = connection
                        .prepareStatement("SELECT visitor_id FROM visitor WHERE visitor_name = ? AND resident_id = ?");
                preparedStatement.setString(1, selectedVisitor);
                preparedStatement.setInt(2, Data.residentID);
                resultSet = preparedStatement.executeQuery();

                int visitorId;
                if (resultSet.next()) {
                    visitorId = resultSet.getInt("visitor_id");
                } else {
                    alert.errorMessage("Visitor not found. Please select a valid visitor.");
                    return;
                }

                String dateStr = visit_date.getText();
                LocalDate localDate = LocalDate.parse(dateStr);

                String timeStr = visit_time.getText();
                timeStr += ":00";
                LocalTime localTime = LocalTime.parse(timeStr);

                String scheduleVisitQuery = "INSERT INTO visit (visitor_id, resident_id, arrival_date, arrival_time, purpose) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement psScheduleVisit = connection.prepareStatement(scheduleVisitQuery)) {
                    psScheduleVisit.setInt(1, visitorId);
                    psScheduleVisit.setInt(2, Data.residentID);
                    psScheduleVisit.setDate(3, java.sql.Date.valueOf(localDate));
                    psScheduleVisit.setTime(4, java.sql.Time.valueOf(localTime));
                    psScheduleVisit.setString(5, visit_purpose.getText());

                    int rowsAffected = psScheduleVisit.executeUpdate();

                    if (rowsAffected > 0) {
                        alert.successMessage("Visit scheduled successfully!");
                    } else {
                        alert.errorMessage("Failed to schedule visit. Please try again.");
                    }
                    visitList();
                    scheduleClearFields();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void scheduleClearFields() {
        selectVisitor_sv.getSelectionModel().clearSelection();
        visit_date.setText("");
        visit_time.setText("");
        visit_purpose.setText("");
    }

    public void visitList() {
        List<String> visitList = new ArrayList<>();
        connection = connectDB();

        try {

            preparedStatement = connection.prepareStatement(
                    "SELECT visitor_name, arrival_date, arrival_time FROM visit, visitor WHERE visitor.resident_id = ? AND visit.visitor_id = visitor.visitor_id");
            preparedStatement.setInt(1, Data.residentID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String visitorName = resultSet.getString("visitor_name");
                visitList.add(visitorName);
            }

            ObservableList listData = FXCollections.observableArrayList(visitList);
            parking_selectVisitor.setItems(listData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dateandtime() {
        parking_date.setEditable(false);
        parking_time.setEditable(false);
        connection = connectDB();

        try {
            String selectedVisitor = (String) parking_selectVisitor.getSelectionModel().getSelectedItem();
            String getVisitorIdQuery = "SELECT visit.visit_id, visit.visitor_id, visitor.visitor_name, visit.arrival_date, visit.arrival_time FROM visitor, visit WHERE visit.visitor_id = visitor.visitor_id AND visitor.visitor_name = ? AND visitor.resident_id = ?";
            preparedStatement = connection.prepareStatement(getVisitorIdQuery);
            preparedStatement.setString(1, selectedVisitor);
            preparedStatement.setInt(2, Data.residentID);
            resultSet = preparedStatement.executeQuery();

            Time arrTime;
            Date arrDate;
            if (resultSet.next()) {
                arrTime = resultSet.getTime("arrival_time");
                arrDate = resultSet.getDate("arrival_date");

                // Convert Time and Date to string representations
                String formattedTime = arrTime.toLocalTime().toString();
                String formattedDate = arrDate.toLocalDate().toString();

                parking_date.setText(formattedDate);
                parking_time.setText(formattedTime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void reserveParking() {
        alertMessage alert = new alertMessage();

        if (parking_selectVisitor.getItems().isEmpty() || parking_carplate.getText().isEmpty()) {
            alert.errorMessage("All fields are neccessary to be filled.");
        } else {

            try {
                connection = connectDB();
                String selectedVisitor = parking_selectVisitor.getSelectionModel().getSelectedItem().toString();
                String getVisitorIdQuery = "SELECT visit.visit_id, visit.visitor_id, visitor.visitor_name, visit.arrival_date, visit.arrival_time FROM visitor, visit WHERE visit.visitor_id = visitor.visitor_id AND visitor.visitor_name = ? AND visitor.resident_id = ?";
                preparedStatement = connection.prepareStatement(getVisitorIdQuery);
                preparedStatement.setString(1, selectedVisitor);
                preparedStatement.setInt(2, Data.residentID);
                resultSet = preparedStatement.executeQuery();

                int visitId;
                int visitorId;
                String visitor;
                Time arrTime;
                Date arrDate;
                if (resultSet.next()) {
                    visitId = resultSet.getInt("visit_id");
                    visitorId = resultSet.getInt("visitor_id");
                    visitor = resultSet.getString("visitor_name");
                    arrTime = resultSet.getTime("arrival_time");
                    arrDate = resultSet.getDate("arrival_date");
                } else {
                    alert.errorMessage("Scheduled visit not found. Please select a valid visitor.");
                    return;
                }

                int days = parkingD_days.getText().isEmpty() ? 0 : Integer.parseInt(parkingD_days.getText());
                int hours = parkingD_hours.getText().isEmpty() ? 0 : Integer.parseInt(parkingD_hours.getText());
                int minutes = parkingD_mins.getText().isEmpty() ? 0 : Integer.parseInt(parkingD_mins.getText());

                String stayDurationin = days + " days," + hours + " hrs," + minutes + " mins";

                Timestamp estimateCheckIn = Timestamp.valueOf(arrDate.toString() + " " + arrTime.toString());
                long millisecondsInDay = 24 * 60 * 60 * 1000;
                long totalStayMillis = (days * millisecondsInDay) + (hours * 60 * 60 * 1000) + (minutes * 60 * 1000);
                long checkoutTimeMillis = estimateCheckIn.getTime() + totalStayMillis;
                Timestamp checkOutBefore = new Timestamp(checkoutTimeMillis);

                String reserveParkingQuery = "INSERT INTO parking (visit_id, visitor_id, car_plate, stay_duration, estimate_check_in, check_out_before) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement psReserveParking = connection.prepareStatement(reserveParkingQuery)) {
                    psReserveParking.setInt(1, visitId);
                    psReserveParking.setInt(2, visitorId);
                    psReserveParking.setString(3, parking_carplate.getText());
                    psReserveParking.setString(4, stayDurationin);
                    psReserveParking.setTimestamp(5, estimateCheckIn);
                    psReserveParking.setTimestamp(6, checkOutBefore);

                    int rowsAffected = psReserveParking.executeUpdate();
                    if (rowsAffected > 0) {
                        alert.successMessage("Parking reserved successfully!");
                    } else {
                        alert.errorMessage("Failed to reserve parking. Please try again.");
                    }
                    visitList();
                    parkingClearFields();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void parkingClearFields() {
        parking_selectVisitor.getSelectionModel().clearSelection();
        visit_date.setText("");
        visit_time.setText("");
        parking_carplate.setText("");
        parkingD_days.setText("");
        parkingD_hours.setText("");
        parkingD_mins.setText("");
    }

    public void displayInfo() {
        r_residentname.setText("Welcome, " + Data.residentName + "!");
        r_residentid.setText(" (Resident ID: " + String.valueOf(Data.residentID) + ")");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visitorList();
        visitList();
        displayInfo();
    }

}
