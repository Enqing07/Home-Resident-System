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

public class AdminPageController implements Initializable {

    @FXML
    private TextField displayAdminID;

    @FXML
    private TextField displayAdminName;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane mainscreen;

    @FXML
    private AnchorPane residentListPage;

    @FXML
    private Button residentList_btn;

    @FXML
    private TableView<ResidentData> resident_TableView;

    @FXML
    private TableColumn<ResidentData, String> rlCol_residentcontact;

    @FXML
    private TableColumn<ResidentData, String> rlCol_residentid;

    @FXML
    private TableColumn<ResidentData, String> rlCol_residentname;

    @FXML
    private TableColumn<ResidentData, String> rlCol_towerfloorunit;

    @FXML
    private Button rl_home_btn;

    @FXML
    private Button rl_side_btn;

    @FXML
    private Button rl_side_btn1;

    @FXML
    private Button rl_signout_btn;

    @FXML
    private Button signout_btn;

    @FXML
    private AnchorPane visitorListPage;

    @FXML
    private Button visitorList_btn;

    @FXML
    private TableView<VisitorData> visitor_TableView;

    @FXML
    private TableColumn<VisitorData, String> vlCol_contact;

    @FXML
    private TableColumn<VisitorData, String> vlCol_residentid;

    @FXML
    private TableColumn<VisitorData, String> vlCol_visitorid;

    @FXML
    private TableColumn<VisitorData, String> vlCol_visitorname;

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
        displayAdminName.setText("Hi, " + Data.adminName);
        displayAdminID.setText(" (AdminID: " + Data.adminID + ")");
    }

    public void switchForm(ActionEvent event) {
        // main screen naviagte
        if (event.getSource() == residentList_btn) {
            mainscreen.setVisible(false);
            residentListPage.setVisible(true);
            visitorListPage.setVisible(false);
        } else if (event.getSource() == visitorList_btn) {
            mainscreen.setVisible(false);
            residentListPage.setVisible(false);
            visitorListPage.setVisible(true);
        }

        // return to main screen
        if (event.getSource() == home_btn || event.getSource() == rl_home_btn || event.getSource() == vl_home_btn) {
            mainscreen.setVisible(true);
            residentListPage.setVisible(false);
            visitorListPage.setVisible(false);
        }

        // side button
        if (event.getSource() == rl_side_btn || event.getSource() == rl_side_btn1) {
            mainscreen.setVisible(false);
            residentListPage.setVisible(true);
            visitorListPage.setVisible(false);
        } else if (event.getSource() == vl_side_btn || event.getSource() == vl_side_btn1) {
            mainscreen.setVisible(false);
            residentListPage.setVisible(false);
            visitorListPage.setVisible(true);
        }

        // sign out
        if (event.getSource() == signout_btn || event.getSource() == rl_signout_btn
                || event.getSource() == vl_signout_btn) {

            Stage stage = (Stage) signout_btn.getScene().getWindow();
            stage.close();
        }
    }

    // view resident list
    public ObservableList<ResidentData> residentGetData() {
        ObservableList<ResidentData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM resident";
        connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResidentData data;
            while (resultSet.next()) {
                data = new ResidentData(resultSet.getInt("resident_id"),
                        resultSet.getString("resident_name"),
                        resultSet.getString("resident_contactno"),
                        resultSet.getString("tower_floor_unit"));

                listData.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<ResidentData> residentListData;

    public void residentShowData() {
        residentListData = residentGetData();

        rlCol_residentid.setCellValueFactory(new PropertyValueFactory<>("residentId"));
        rlCol_residentname.setCellValueFactory(new PropertyValueFactory<>("residentName"));
        rlCol_residentcontact.setCellValueFactory(new PropertyValueFactory<>("residentContactNo"));
        rlCol_towerfloorunit.setCellValueFactory(new PropertyValueFactory<>("tower_floor_unit"));

        resident_TableView.setItems(residentListData);
    }





    // view visitor list
    public ObservableList<VisitorData> visitorGetData() {
        ObservableList<VisitorData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM visitor";
        connection = connectDB();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            VisitorData data;
            while (resultSet.next()) {
                data = new VisitorData(resultSet.getInt("visitor_id"),
                        resultSet.getInt("resident_id"),
                        resultSet.getString("visitor_name"),
                        resultSet.getString("visitor_contactno"));

                listData.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<VisitorData> visitorListData;

    public void visitorShowData() {
        visitorListData = visitorGetData();

        vlCol_visitorid.setCellValueFactory(new PropertyValueFactory<>("visitorId"));
        vlCol_residentid.setCellValueFactory(new PropertyValueFactory<>("residentId"));
        vlCol_visitorname.setCellValueFactory(new PropertyValueFactory<>("visitorName"));
        vlCol_contact.setCellValueFactory(new PropertyValueFactory<>("visitorContact"));

        visitor_TableView.setItems(visitorListData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayInfo();
        residentShowData();
        visitorShowData();
    }

}
