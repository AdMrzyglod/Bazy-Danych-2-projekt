package com.example.project.Providers;

import com.example.project.Logic.*;
import com.example.project.RandomCode;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Provider {
    private static final EntityManagerFactory emf;
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            emf= Persistence.
                    createEntityManagerFactory("myDatabaseConfig");

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static EntityManager getManager(){
        return emf.createEntityManager();
    }
    public void main(final String[] args) throws Exception {
        final EntityManager manager = getManager();
        try {
            EntityTransaction etx = manager.getTransaction();
            etx.begin();

            /*
            for (CategoryName categoryName : CategoryName.values()){
                manager.persist(new Category(categoryName.getDisplayName(),"other"));
            }

            manager.persist(new Game("cod", getCategory(CategoryName.ADVENTURE.getDisplayName()),"cod.png",(float) 200.80,(float)1528.7, true));
            manager.persist(new Game("cod1", getCategory(CategoryName.ARCADE.getDisplayName()),"cod.png",(float) 22.80,(float)1258.7,false));
            manager.persist(new Game("cod2", getCategory(CategoryName.SIMULATION.getDisplayName()),"cod.png",(float) 300.80,(float)1548.7,true));
            manager.persist(new Game("cod3", getCategory(CategoryName.SPORTS.getDisplayName()),"cod.png",(float) 120.80,(float)1158.7,false));
            manager.persist(new Game("cod4", getCategory(CategoryName.STRATEGY.getDisplayName()),"cod.png",(float) 90.80,(float)1528.7,true));
            manager.persist(new Game("cod5", getCategory(CategoryName.ROLE_PLAY.getDisplayName()),"cod.png",(float) 87.80,(float)1158.7,false));

             */

           /* List<Game> games = getGames();

            for(Game game: games){

                for(int i=0;i<(int)Math.floor(Math.random() * (15 - 3 + 1) + 3);i++) {
                    addGameCode(game, RandomCode.getString());
                }
            }

            */

           /* CompanyUser user= (CompanyUser) getUsersByName("GameDev");
            Game game = getGameByName("cod3");

            Tournament tournament = new Tournament("NEW_OPEN",getTimeFromString("2023-06-20 11:00:00"),getTimeFromString("2023-06-20 14:00:00"),"Other",200,user,game);

            manager.persist(tournament);

            */


            etx.commit();


        } finally {
            manager.close();
        }
    }


    public Timestamp getTimeFromString(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dateFormat.parse(dateString);
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    public PlatformUser getUsersByName(String username){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT u from PlatformUser u where u.username= :username");
        query.setParameter("username",username);
        List<PlatformUser> platformUsers = query.getResultList();

        PlatformUser user=platformUsers.size()==0 ? null : platformUsers.get(0);



        return user;
    }

    public List<Game> getUserGames(String username){
        PlatformUser platformUser = getUsersByName(username);

        List<Game> games = new ArrayList<>();
        for(GameCode gameCode:platformUser.getGameCodes()){
            games.add(gameCode.getGame());
        }

        return games;
    }

    public boolean userExists(String username){
        return getUsersByName(username)!=null;
    }

    public boolean loginAuthorization(String username,String password){
        PlatformUser platformUser = getUsersByName(username);
        if(platformUser!=null){
            return password.equals(platformUser.getPassword());
        }
        return false;
    }

    public void addIndividualUser(IndividualUser individualUser){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            manager.persist(individualUser);

            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
        }
    }

    public void addCompanyUser(CompanyUser companyUser){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            manager.persist(companyUser);

            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
        }
    }

    public Category getCategory(String name){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT c from Category c where c.categoryName= :name");
        query.setParameter("name",name);
        List<Category> categories = query.getResultList();

        Category category = categories==null ? null : categories.get(0);


        return category;
    }

    public Game getGameByName(String name){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT g from Game g where g.gameName=:game");
        query.setParameter("game",name);
        List<Game> games = new ArrayList<>(query.getResultList());


        return games.size()>0? games.get(0):null;
    }


    public List<Game> getGames(){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT g from Game g");
        List<Game> games = new ArrayList<>(query.getResultList());


        return games;
    }

    public List<GameCode> getFreeCodes(Game game){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT cod from GameCode cod where cod.orderDetails=NULL and cod.game=:game",GameCode.class);
        query.setParameter("game",game);
        List<GameCode> gameCodes = new ArrayList<>(query.getResultList());


        return gameCodes;
    }

    public void addGameCode(Game game,String code){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            GameCode gameCode = new GameCode(code,game);

            manager.persist(gameCode);

            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
        }
    }

    public boolean userCanBuy(String user,float money){
        PlatformUser platformUser = getUsersByName(user);
        return platformUser.getMoney()-money>0 ? true : false;
    }

    public void purchase(PlatformUser user,HashMap<Game, Integer> gamesDetails,float totalSum){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            PlatformUser platformUser= getUsersByName(user.getUsername());

            PlatformOrder platformOrder = new PlatformOrder(platformUser);

            for(Map.Entry<Game, Integer> entry : gamesDetails.entrySet()){
                List<GameCode> gameCodes = getFreeCodes(entry.getKey());
                if(gameCodes.size()-entry.getValue()<0){
                    throw new IllegalArgumentException("Too few game codes!");
                }
                Game game = entry.getKey();
                int quantity= entry.getValue();
                OrderDetails details = new OrderDetails(game.getPrice(),platformOrder,game);
                manager.persist(details);
                details.addCode(new ArrayList<GameCode>(gameCodes.subList(0,quantity)));

                for(GameCode code: gameCodes.subList(0,quantity)){
                    manager.merge(code);
                }
            }


            platformUser.updateMoney(-totalSum);


            manager.persist(platformOrder);

            manager.merge(platformUser);

            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
        }

    }

    public boolean userOwnGame(String username,Game game){
        List<Game> games = getUserGames(username);
        return games.contains(game);
    }

    public GameCode getGameCode(String code){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT cod from GameCode cod where cod.accessCode=:code",GameCode.class);
        query.setParameter("code",code);
        List<GameCode> gameCodes = new ArrayList<>(query.getResultList());



        return gameCodes.size()>0 ? gameCodes.get(0) : null;
    }

    public boolean isCodeCanBeUsed(String code,String username){

        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT cod from GameCode cod where cod.orderDetails!=NULL and cod.accessCode=:code and cod.user=NULL",GameCode.class);
        query.setParameter("code",code);
        List<GameCode> gameCodes = new ArrayList<>(query.getResultList());


        if(gameCodes.size()==0){
            return false;
        }
        GameCode gameCode = gameCodes.get(0);
        return !userOwnGame(username,gameCode.getGame());
    }

    public void addCodeToUser(String username,String code){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            PlatformUser user = getUsersByName(username);
            GameCode gameCode = getGameCode(code);

            gameCode.setUser(user);


            manager.merge(gameCode);
            manager.merge(user);

            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
        }
    }

    public PlatformOrder getOrder(int OrderID){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT order from PlatformOrder order where order.Order_ID=:order",PlatformOrder.class);
        query.setParameter("order",OrderID);
        List<PlatformOrder> platformOrders = new ArrayList<>(query.getResultList());

        return platformOrders.size()>0 ? platformOrders.get(0) : null;
    }

    public List<GameCode> getCodesFromDetails(int orderDetailID){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT code from GameCode code where code.orderDetails.OrderDetails_ID=:detailsID",GameCode.class);
        query.setParameter("detailsID",orderDetailID);
        List<GameCode> gameCodeList = new ArrayList<>(query.getResultList());

        return gameCodeList;
    }


    public void addTournament(Tournament tournament){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            manager.persist(tournament);

            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
        }
    }

    public Tournament getTournamentByName(String name){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT t from Tournament t where t.name= :name");
        query.setParameter("name",name);
        List<Tournament> tournaments = query.getResultList();

        Tournament tournament=tournaments.size()==0 ? null : tournaments.get(0);

        return tournament;
    }

    public void addUserToTournament(String username,String tournamentName,String code){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            PlatformUser platformUser = getUsersByName(username);
            Tournament tournament = getTournamentByName(tournamentName);

            UserTournament userTournament = new UserTournament(tournament,platformUser,code);
            manager.persist(userTournament);

            platformUser.addUserTournament(userTournament);
            tournament.addUserTournament(userTournament);

            manager.merge(platformUser);
            manager.merge(tournament);

            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
        }
    }

    public List<Tournament> getAvailableTournaments(String username){
        final EntityManager manager = getManager();

        PlatformUser user = getUsersByName(username);

        TypedQuery<Tournament> query = manager.createQuery(
                "SELECT t FROM Tournament t WHERE t.startTournament>current_timestamp and  t NOT IN (SELECT ut.tournament FROM UserTournament ut  WHERE ut.user.User_ID = :user)", Tournament.class);
        query.setParameter("user", user.getUser_ID());
        List<Tournament> tournaments = query.getResultList();

        return tournaments;
    }

    public List<Tournament> getUserTournaments(String username){
        final EntityManager manager = getManager();

        PlatformUser user = getUsersByName(username);

        TypedQuery<Tournament> query = manager.createQuery(
                "SELECT t FROM Tournament t WHERE t IN (SELECT ut.tournament FROM UserTournament ut  WHERE ut.user.User_ID = :user)", Tournament.class);
        query.setParameter("user", user.getUser_ID());
        List<Tournament> tournaments = query.getResultList();

        return tournaments;
    }

    public String getTournamentCode(int userID,int tournamentID){
        final EntityManager manager = getManager();

        TypedQuery<UserTournament> query = manager.createQuery(
                "SELECT ut FROM UserTournament ut where ut.user.User_ID=:user and ut.tournament.Tournament_ID=:tournament", UserTournament.class);
        query.setParameter("user",userID);
        query.setParameter("tournament",tournamentID);
        List<UserTournament> list = query.getResultList();

        String code= list.size()>0 ? list.get(0).getAccessCode() : "";
        manager.close();

        return code;
    }

    public boolean getIsCompanyUser(int userID){
        final EntityManager manager = getManager();

        Query query = manager.createQuery(
                "SELECT c FROM CompanyUser c where c.User_ID=:user", CompanyUser.class);
        query.setParameter("user", userID);
        List<CompanyUser> companyUsers = query.getResultList();
        boolean value= companyUsers.size()>0;

        return value;
    }

    public List<Tournament> getTournamentsCreatedByUser(int companyID){
        final EntityManager manager = getManager();

        TypedQuery<Tournament> query = manager.createQuery(
                "SELECT t FROM Tournament t WHERE t.companyUser.User_ID=:id", Tournament.class);
        query.setParameter("id", companyID);
        List<Tournament> tournaments = query.getResultList();

        return tournaments;
    }

    public List<PlatformUser> getTournamentsAllUsers(int tournamentID){
        final EntityManager manager = getManager();

        TypedQuery<PlatformUser> query = manager.createQuery(
                "SELECT user FROM PlatformUser user where user IN (SELECT ut.user FROM UserTournament ut  WHERE ut.tournament.Tournament_ID=:id)", PlatformUser.class);
        query.setParameter("id", tournamentID);
        List<PlatformUser> users = query.getResultList();

        return users;
    }


    public void addPayment(String username,Payment payment){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            PlatformUser platformUser = getUsersByName(username);

            payment.setPlatformUser(platformUser);
            manager.persist(payment);


            platformUser.addPaymentToUser(payment);
            manager.merge(platformUser);


            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
        }
    }

    public Payment getPayment(int paymentID){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT p FROM Payment p where p.payment_ID=:id",Payment.class);
        query.setParameter("id", paymentID);
        List<Payment> payments = query.getResultList();

        return payments.size()>0 ? payments.get(0) : null;
    }

}
