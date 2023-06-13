package com.example.project.Providers;

import com.example.project.Logic.*;
import com.example.project.Logic.DatabaseClasses.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Provider {
    private static final EntityManagerFactory emf;
    private static EntityManager em;
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            emf= Persistence.
                    createEntityManagerFactory("myDatabaseConfig");

            em=emf.createEntityManager();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public EntityManager getManager(){
        return em;
    }
    public void setEntityManager(){
        em= emf.createEntityManager();
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
        for(ActiveCode activeCode:platformUser.getActiveCodes()){
            games.add(activeCode.getGameCode().getGame());
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

    public Employee getEmployee(String username){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT e from Employee e where e.username= :username");
        query.setParameter("username",username);
        List<Employee> employees = query.getResultList();

        Employee user=employees.size()==0 ? null : employees.get(0);


        return user;
    }

    public boolean employeeLoginAuthorization(String username,String password){
        Employee employee = getEmployee(username);
        if(employee!=null){
            return password.equals(employee.getPassword());
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
            setEntityManager();
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
            setEntityManager();
        }
    }

    public Category getCategory(String name){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT c from Category c where c.categoryName= :name");
        query.setParameter("name",name);
        List<Category> categories = query.getResultList();

        Category category = categories.size()==0 ? null : categories.get(0);


        return category;
    }

    public List<Category> getAllCategories(){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT c from Category c");
        List<Category> categories = query.getResultList();



        return categories;
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

        Query query = manager.createQuery("SELECT cod from GameCode cod where cod NOT IN (SELECT od.gameCode FROM OrderDetails od) and cod.game=:game",GameCode.class);
        query.setParameter("game",game);
        List<GameCode> gameCodes = new ArrayList<>(query.getResultList());


        return gameCodes;
    }

    public void addGame(Game game){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            manager.persist(game);

            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
            setEntityManager();
        }
    }

    public void addCategory(Category category){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            manager.persist(category);

            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
            setEntityManager();
        }
    }

    public void addGameCode(GameCode gameCode){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            manager.persist(gameCode);

            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
            setEntityManager();
        }
    }

    public boolean userCanBuy(String user,BigDecimal money){
        PlatformUser platformUser = getUsersByName(user);
        return platformUser.canBuy(money);
    }

    public void purchase(String username,Cart cart,BigDecimal totalSum){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            HashMap<Game, Integer> gamesDetails = cart.getGamesCart();

            PlatformUser platformUser= getUsersByName(username);

            PlatformOrder platformOrder = new PlatformOrder(platformUser);

            manager.persist(platformOrder);


            for(Map.Entry<Game, Integer> entry : gamesDetails.entrySet()){

                List<GameCode> gameCodes = getFreeCodes(entry.getKey());

                if(gameCodes.size()-entry.getValue()<0){
                    throw new IllegalArgumentException("Too few game codes!");
                }

                Game game = entry.getKey();
                int quantity= entry.getValue();

                for(GameCode code: gameCodes.subList(0,quantity)){
                    OrderDetails orderDetails = new OrderDetails(game.getPrice(),platformOrder,code);
                    manager.persist(orderDetails);
                    manager.merge(code);
                }
            }

            platformUser.updateMoney(totalSum.negate());

            manager.merge(platformUser);

            etx.commit();

            cart.clearCart();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
            setEntityManager();
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

        Query query = manager.createQuery("SELECT cod from GameCode cod where cod IN (SELECT od.gameCode FROM OrderDetails od) and cod.accessCode=:code and cod not in (SELECT ac.gameCode FROM ActiveCode ac)",GameCode.class);
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

            ActiveCode activeCode = new ActiveCode(user,gameCode);

            manager.persist(activeCode);

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
            setEntityManager();
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
            setEntityManager();
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
            setEntityManager();
        }
    }

    public List<Tournament> getAvailableTournaments(int userID){

        final EntityManager manager = getManager();

        TypedQuery<Tournament> query = manager.createQuery(
                "SELECT t FROM Tournament t WHERE t.startTournament>current_timestamp and t.userTournaments.size<t.userLimit and  t NOT IN (SELECT ut.tournament FROM UserTournament ut  WHERE ut.user.User_ID = :user) and not t.companyUser.User_ID=:user", Tournament.class);
        query.setParameter("user", userID);
        List<Tournament> tournaments = query.getResultList();

        return tournaments;
    }

    public List<Tournament> getUserTournaments(int userID){

        final EntityManager manager = getManager();

        TypedQuery<Tournament> query = manager.createQuery(
                "SELECT t FROM Tournament t WHERE t IN (SELECT ut.tournament FROM UserTournament ut  WHERE ut.user.User_ID = :user)", Tournament.class);
        query.setParameter("user", userID);
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
            setEntityManager();
        }
    }

    public Payment getPayment(int paymentID){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT p FROM Payment p where p.payment_ID=:id",Payment.class);
        query.setParameter("id", paymentID);
        List<Payment> payments = query.getResultList();

        return payments.size()>0 ? payments.get(0) : null;
    }

    public List<Payment> getAllNotVerifiedPayment(){
        final EntityManager manager = getManager();

        Query query = manager.createQuery("SELECT p FROM Payment p where p.isVerified=false",Payment.class);
        List<Payment> payments = query.getResultList();

        return payments.size()>0 ? payments : null;
    }

    public void verifiedPayment(int paymentID){
        final EntityManager manager = getManager();
        EntityTransaction etx = manager.getTransaction();
        try {
            etx.begin();

            Query query = manager.createQuery("SELECT p FROM Payment p where p.payment_ID=:id",Payment.class);
            query.setParameter("id", paymentID);
            List<Payment> payments = query.getResultList();

            if(payments.size()==0 || payments.get(0).isVerified()==true){
                throw new Exception();
            }

            Payment payment = payments.get(0);

            payment.setVerified(true);

            manager.merge(payment);

            PlatformUser platformUser = getUsersByName(payment.getPlatformUser().getUsername());

            platformUser.updateMoney(payment.getAmount());

            manager.merge(platformUser);

            etx.commit();

        }catch (Exception exception){
            if(etx.isActive()){
                etx.rollback();
            }
        }
        finally {
            manager.close();
            setEntityManager();
        }
    }

    public String getCodeToTournament(int tournamentID, int userID){
        final EntityManager manager = getManager();

        TypedQuery<UserTournament> query = manager.createQuery(
                "SELECT t FROM UserTournament t WHERE t.tournament.Tournament_ID=:tournamentID and t.user.User_ID=:userID", UserTournament.class);
        query.setParameter("tournamentID", tournamentID);
        query.setParameter("userID",userID);
        List<UserTournament> tournaments = query.getResultList();

        return tournaments.size()==0 ? null : tournaments.get(0).getAccessCode();
    }

}
