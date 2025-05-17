import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SGPageController implements Initializable{
    @FXML
    private TextField displaySGID;

    @FXML
    private TextField displaySGName;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane mainscreen;

    @FXML
    private AnchorPane parkingListPage;

    @FXML
    private Button parkingList_btn;

    @FXML
    private TableView<ParkingData> parking_TableView;

    @FXML
    private TableColumn<ParkingData, String> plCol_carplate;

    @FXML
    private TableColumn<ParkingData, String> plCol_checkoutbefore;

    @FXML
    private TableColumn<ParkingData, String> plCol_estimatecheckin;

    @FXML
    private TableColumn<ParkingData, String> plCol_parkingid;

    @FXML
    private TableColumn<ParkingData, String> plCol_stayduration;

    @FXML
    private TableColumn<ParkingData, String> plCol_visitid;

    @FXML
    private TableColumn<ParkingData, String> plCol_visitorid;

    @FXML
    private Button pl_home_btn;

    @FXML
    private Button pl_side_btn;

    @FXML
    private Button pl_side_btn1;

    @FXML
    private Button pl_signout_btn;

    @FXML
    private Button signout_btn;

    @FXML
    private AnchorPane visitListPage;

    @FXML
    private Button visitList_btn;

    @FXML
    private TableView<VisitData> visit_TableView;

    @FXML
    private TableColumn<VisitData, String> vlCol_arrivaldate;

    @FXML
    private TableColumn<VisitData, String> vlCol_arrivaltime;

    @FXML
    private TableColumn<VisitData, String> vlCol_purpose;

    @FXML
    private TableColumn<VisitData, String> vlCol_residentid;

    @FXML
    private TableColumn<VisitData, String> vlCol_visitid;

    @FXML
    private TableColumn<VisitData, String> vlCol_visitorid;

    @FXML
    private Button vl_home_btn;

    @FXML
    private Button vl_side_btn;

    @FXML
    private Button vl_side_btn1;

    @FXML
    private Button vl_signout_btn;


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

    public void displayInfo(){
        displaySGName.setText("Hi, " + Data.sgName);
        displaySGID.setText(" (Security Guard ID: " + Data.sgID + ")");
    }

    public void switchForm(ActionEvent event) {
        // main screen naviagte
        if (event.getSource() == visitList_btn) {
            mainscreen.setVisible(false);
            visitListPage.setVisible(true);
            parkingListPage.setVisible(false);
        } else if (event.getSource() == parkingList_btn) {
            mainscreen.setVisible(false);
            visitListPage.setVisible(false);
            parkingListPage.setVisible(true);
        }

        // return to main screen
        if (event.getSource() == home_btn || event.getSource() == vl_home_btn || event.getSource() == pl_home_btn) {
            mainscreen.setVisible(true);
            visitListPage.setVisible(false);
            parkingListPage.setVisible(false);
        }

        // side button
        if (event.getSource() == vl_side_btn || event.getSource() == vl_side_btn1) {
            mainscreen.setVisible(false);
            visitListPage.setVisible(true);
            parkingListPage.setVisible(false);
        } else if (event.getSource() == pl_side_btn || event.getSource() == pl_side_btn1) {
            mainscreen.setVisible(false);
            visitListPage.setVisible(false);
            parkingListPage.setVisible(true);
        }

        // sign out
        if (event.getSource() == signout_btn || event.getSource() == vl_signout_btn
                || event.getSource() == pl_signout_btn) {

            Stage stage = (Stage) signout_btn.getScene().getWindow();
            stage.close();
        }
    }

   // view visit list
    public ObservableList<VisitData> visitGetData() {
        ObservableList<VisitData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM visit";
        connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            VisitData data;
            while (resultSet.next()) {
                data = new VisitData(resultSet.getInt("visit_id"),
                        resultSet.getInt("visitor_id"),
                        resultSet.getInt("resident_id"),
                        resultSet.getString("arrival_date"),
                        resultSet.getString("arrival_time"),
                        resultSet.getString("purpose"));

                listData.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<VisitData> visitListData;

    public void visitShowData() {
        visitListData = visitGetData();

        vlCol_visitid.setCellValueFactory(new PropertyValueFactory<>("visitId"));
        vlCol_visitorid.setCellValueFactory(new PropertyValueFactory<>("visitorId"));
        vlCol_residentid.setCellValueFactory(new PropertyValueFactory<>("residentId"));
        vlCol_arrivaldate.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        vlCol_arrivaltime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        vlCol_purpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));

        visit_TableView.setItems(visitListData);
    }




    // view parking list
    public ObservableList<ParkingData> parkingGetData() {
        ObservableList<ParkingData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM parking";
        connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ParkingData data;
            while (resultSet.next()) {
                data = new ParkingData(resultSet.getInt("parking_id"),
                resultSet.getInt("visit_id"),
                        resultSet.getInt("visitor_id"),
                        resultSet.getString("car_plate"),
                        resultSet.getString("stay_duration"),
                        resultSet.getString("estimate_check_in"),
                        resultSet.getString("check_out_before"));

                listData.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<ParkingData> parkingListData;

    public void parkingShowData() {
        parkingListData = parkingGetData();

        plCol_parkingid.setCellValueFactory(new PropertyValueFactory<>("parkingId"));
        plCol_visitid.setCellValueFactory(new PropertyValueFactory<>("visitId"));
        plCol_visitorid.setCellValueFactory(new PropertyValueFactory<>("visitorId"));
        plCol_carplate.setCellValueFactory(new PropertyValueFactory<>("carPlate"));
        plCol_stayduration.setCellValueFactory(new PropertyValueFactory<>("stayDuration"));
        plCol_estimatecheckin.setCellValueFactory(new PropertyValueFactory<>("estimateCheckIn"));
        plCol_checkoutbefore.setCellValueFactory(new PropertyValueFactory<>("checkOutBefore"));

        parking_TableView.setItems(parkingListData);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayInfo();
        visitShowData();
        parkingShowData();
    }

}
