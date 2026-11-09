package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMain {
    /// Данные для входа на сервер.
    /// УДАЛИТЬ ИЗ ОТЧЁТА!!!
    private static final String serverURL= "";
    //private static final String serverURL= "jdbc:sqlserver://IP:1433;databaseName=NAME;encrypt=false;trustServerCertificate=true";
    private static final String loginDB="";
    private static final String passwordDB="!";

    ///ИД юзера под котором проходит авторизация

    private static final String UsersId="";

    public Connection getConnecting() {
        Connection db = null;
        try {
            db = DriverManager.getConnection(serverURL, loginDB, passwordDB);
        }catch (SQLException e){
            throw new RuntimeException("\t\t\t\t\tОшибка подключения!!!\n"+e);
        }
        return db;
    }

    public String getUsersId(){
        return UsersId;
    }
}