import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SGSignInController implements Initializable {

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_sgID;

    @FXML
    private TextField login_showpassword;

    @FXML
    private ComboBox<?> login_user;

    @FXML
    private CheckBox showPassword;

    @FXML
    private AnchorPane signin_page;

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

    public void Login(ActionEvent event) {
        alertMessage alert = new alertMessage();

        // check if have empty field
        if (login_sgID.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("All fields are neccessary to be filled.");
        } else {
            String selectData = "SELECT * FROM securityguard WHERE sg_id = ?";
            connection = connectDB();
            try {
                preparedStatement = connection.prepareStatement(selectData);
                preparedStatement.setInt(1, Integer.parseInt(login_sgID.getText()));
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) { // move the cursor to the next row of the result, return booleon
                    alert.errorMessage("Warning: security guard with ID " + login_sgID.getText() + " does not exist.");
                } else {
                    int retrivedID = resultSet.getInt("sg_id");
                    String retrivedName = resultSet.getNString("sg_name");
                    String retrivedPassword = resultSet.getNString("sg_password");
                    if (retrivedPassword.equals(login_password.getText())) {
                        Data.sgID = Integer.parseInt(login_sgID.getText());
                        Data.sgName = retrivedName;

                        alert.successMessage("Login successful!\nWelcome," + retrivedName);
                        Parent root = FXMLLoader.load(getClass().getResource("SGPage.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Home sg System");
                        stage.setScene(new Scene(root));
                        stage.show();

                        signInClearFields();
                    } else {
                        alert.errorMessage("Warning: security guard with ID " + retrivedID + " incorrect password.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void signInClearFields() {
        login_sgID.setText("");
        login_password.setText("");
        login_showpassword.setText("");
        showPassword.setSelected(false);
        login_showpassword.setVisible(false);
        login_password.setVisible(true);
        login_user.getSelectionModel().clearSelection();
    }

    public void showPassword() {
        if (showPassword.isSelected()) {
            login_showpassword.setText(login_password.getText());
            login_showpassword.setVisible(true);
            login_password.setVisible(false);
        } else {
            login_password.setText(login_showpassword.getText());
            login_showpassword.setVisible(false);
            login_password.setVisible(true);
        }
    }

    public void userList() {
        List<String> list = new ArrayList<>();

        for (String data : User.user) {
            list.add(data);
        }
        ObservableList listData = FXCollections.observableList(list);
        login_user.setItems(listData);
    }

    public void switchPage() {

        if (login_user.getSelectionModel().getSelectedItem() == "Resident") {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Rsignin.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Login");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (login_user.getSelectionModel().getSelectedItem() == "Admin") {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Asignin.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Login");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (login_user.getSelectionModel().getSelectedItem() == "Security Guard") {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("SGsignin.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Login");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        login_user.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userList();
    }
}
