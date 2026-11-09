import db.*;
import service.Menu;

import java.sql.Connection;
import java.sql.SQLException;

public class L423 {
    void main() {
        Connection connection=null;
        DatabaseMain db=new DatabaseMain();
        try {
            connection = db.getConnecting();
            IO.println("Подключение к базе данных произошло!\n\n\n\n\n\n\n\n");
            Menu start=new Menu();
            start.run(db);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}