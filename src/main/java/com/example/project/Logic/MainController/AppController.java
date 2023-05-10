package com.example.project.Logic.MainController;

import com.example.project.Controllers.*;
import com.example.project.Logic.*;
import com.example.project.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppController extends Application {

    public final String pathToImages="src/main/resources/com/example/project/data/";
    private User user;
    private Cart cart;
    public List<Game> games= new ArrayList<Game>();
    public List<User> users= new ArrayList<User>();

    private LoginController loginController;
    private HomeController homeController;
    private CartPageController cartPageController;
    private LibraryController libraryController;
    private RegisterController registerController;

    public Stage stage;


    @Override
    public void start(Stage stage) throws IOException {
        this.cart=new Cart();
        games.add(new Game("cod", Category.ADVENTURE,"cod.png",(float) 200.80,(float)1528.7, Option.YES));
        games.add(new Game("cod1", Category.ARCADE,"cod.png",(float) 22.80,(float)1258.7,Option.NO));
        games.add(new Game("cod2", Category.SIMULATION,"cod.png",(float) 300.80,(float)1548.7,Option.YES));
        games.add(new Game("cod3", Category.SPORTS,"cod.png",(float) 120.80,(float)1158.7,Option.NO));
        games.add(new Game("cod4", Category.STRATEGY,"cod.png",(float) 90.80,(float)1528.7,Option.YES));
        games.add(new Game("cod5", Category.ROLE_PLAY,"cod.png",(float) 87.80,(float)1158.7,Option.NO));
        this.users.add(new User("A","M","Nickname","password","example@example.com"));
        loginLoad(stage);
    }

    public void loginLoad(Stage stage) throws IOException {
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

    public void homeLoad(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader =  new FXMLLoader(Main.class.getResource("home-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1320,800);
        homeController=fxmlLoader.getController();
        homeController.setApp(this);
        homeController.setStage(stage);
        Label userlabel=(Label) scene.lookup("#userName");
        userlabel.setText(user.getUsername());
        stage.setScene(scene);
        stage.setTitle("GameNet");
        homeController.addToGameList(this.games);
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
        userlabel.setText(user.getUsername());
        stage.setScene(scene);
        stage.setTitle("GameNet");
        libraryController.addToGameList(this.user.getUserGames());
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
        userlabel.setText(user.getUsername());
        stage.setScene(scene);
        stage.setTitle("GameNet");
        cartPageController.addToGameList(getCart().getGamesCart());
        cartPageController.setTotalPrice(this.cart.getTotalPrice()+"");
        stage.show();
    }


    public void setUser(User user){
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public Cart getCart(){
        return this.cart;
    }

    public void addGame(Game game){
        this.games.add(game);
    }

    public void removeGame(Game game){
        this.games.remove(game);
    }

    public CartPageController getCartPageController() {
        return cartPageController;
    }

    public LibraryController getLibraryController() {
        return libraryController;
    }

    public List<User> getUsers(){
        return this.users;
    }
    public void setUsers(User user){
        this.users.add(user);
    }

    public boolean checkUser(String username,String password){
        for(User user1: users){
            if(user1.getUsername().equals(username) && user1.getPassword().equals(password)){
                this.setUser(user1);
                return true;
            }
        }
        return false;
    }

    public boolean userInDataBase(String username){
        for(User user1: users){
            if(user1.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

}
