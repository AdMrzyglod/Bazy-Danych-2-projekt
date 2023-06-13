package com.example.project.Logic.MainController;

import com.example.project.Controllers.*;
import com.example.project.Logic.*;
import com.example.project.Logic.DatabaseClasses.Employee;
import com.example.project.Logic.DatabaseClasses.PlatformUser;
import com.example.project.Main;
import com.example.project.Providers.Provider;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController extends Application {

    public final String pathToImages="src/main/resources/com/example/project/data/";

    public Provider provider= new Provider();
    private PlatformUser platformUser;
    private Employee employee;
    private Cart cart;
    private LoginController loginController;
    private HomeController homeController;
    private CartPageController cartPageController;
    private LibraryController libraryController;
    private RegisterController registerController;
    private RegisterCompanyController registerCompanyController;
    private PaymentController paymentController;
    private TournamentController tournamentController;

    private EmployeeController employeeController;

    public Stage stage;
    private boolean isCompany=false;

    @Override
    public void start(Stage stage) throws IOException {
        this.cart=new Cart();

        loginLoad(stage);
    }

    public void loginLoad(Stage stage) throws IOException {
        this.cart = new Cart();
        this.stage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        loginController=fxmlLoader.getController();
        loginController.setApp(this);
        loginController.setStage(stage);
        stage.setTitle("GameNet");
        stage.setScene(scene);
        stage.show();
    }
    public void registerLoad(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("register-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 651);
        registerController=fxmlLoader.getController();
        registerController.setApp(this);
        registerController.setStage(stage);
        stage.setTitle("GameNet");
        stage.setScene(scene);
        stage.show();
    }

    public void registerCompanyLoad(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("register-page-company.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 800);
        registerCompanyController=fxmlLoader.getController();
        registerCompanyController.setApp(this);
        registerCompanyController.setStage(stage);
        stage.setTitle("GameNet");
        stage.setScene(scene);
        stage.show();
    }

    public void homeLoad(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader =  new FXMLLoader(Main.class.getResource("home-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1320,800);
        homeController=fxmlLoader.getController();
        homeController.setApp(this);
        homeController.setStage(stage);
        Label userlabel=(Label) scene.lookup("#userName");
        userlabel.setText(platformUser.getUsername());
        stage.setScene(scene);
        stage.setTitle("GameNet");
        homeController.addToGameList(this.provider.getGames());
        homeController.setCategoryPanel();
        stage.show();
    }

    public void libraryLoad(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader =  new FXMLLoader(Main.class.getResource("library-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1320,800);
        libraryController=fxmlLoader.getController();
        libraryController.setApp(this);
        libraryController.setStage(stage);
        Label userlabel=(Label) scene.lookup("#userName");
        userlabel.setText(platformUser.getUsername());
        stage.setScene(scene);
        stage.setTitle("GameNet");
        libraryController.addToGameList(this.provider.getUserGames(this.platformUser.getUsername()));
        libraryController.setCategoryPanel();
        stage.show();
    }

    public void cartLoad(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader =  new FXMLLoader(Main.class.getResource("cart-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1320,800);
        cartPageController=fxmlLoader.getController();
        cartPageController.setApp(this);
        cartPageController.setStage(stage);
        Label userlabel=(Label) scene.lookup("#userName");
        userlabel.setText(platformUser.getUsername());
        stage.setScene(scene);
        stage.setTitle("GameNet");
        cartPageController.addToGameList(getCart().getGamesCart());
        cartPageController.setTotalPrice(this.cart.getTotalPrice()+"");
        stage.show();
    }

    public void paymentLoad(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader =  new FXMLLoader(Main.class.getResource("payment-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1320,800);
        paymentController=fxmlLoader.getController();
        paymentController.setApp(this);
        paymentController.setStage(stage);
        Label userlabel=(Label) scene.lookup("#userName");
        userlabel.setText(platformUser.getUsername());
        stage.setScene(scene);
        stage.setTitle("GameNet");
        paymentController.addAllOrders();
        paymentController.setTotalPrice(this.provider.getUsersByName(this.platformUser.getUsername()).getMoney()+"");
        stage.show();
    }

    public void tournamentLoad(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader =  new FXMLLoader(Main.class.getResource("tournament-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1320,800);
        tournamentController=fxmlLoader.getController();
        tournamentController.setApp(this);
        tournamentController.setStage(stage);
        Label userlabel=(Label) scene.lookup("#userName");
        userlabel.setText(platformUser.getUsername());
        stage.setScene(scene);
        stage.setTitle("GameNet");
        tournamentController.addTournaments(this.provider.getAvailableTournaments(this.getUser().getUser_ID()));
        if(!isCompany()){
            tournamentController.settingsForIndividual();
        }
        stage.show();
    }

    public void employeeLoad(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader =  new FXMLLoader(Main.class.getResource("employee-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1320,800);
        employeeController=fxmlLoader.getController();
        employeeController.setApp(this);
        employeeController.setStage(stage);
        Label userlabel=(Label) scene.lookup("#userName");
        userlabel.setText(getEmployee().getUsername());
        stage.setScene(scene);
        stage.setTitle("GameNet");
        employeeController.onCreate();
        stage.show();
    }

    public void setUser(PlatformUser platformUser){
        this.platformUser = platformUser;
    }

    public PlatformUser getUser() {
        return platformUser;
    }

    public Cart getCart(){
        return this.cart;
    }

    public CartPageController getCartPageController() {
        return cartPageController;
    }

    public LibraryController getLibraryController() {
        return libraryController;
    }

    public void setCompany(boolean value){
        this.isCompany=value;
    }

    public boolean isCompany() {
        return isCompany;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
