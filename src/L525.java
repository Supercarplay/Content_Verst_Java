import content.*;
import content.WindowMain;
import db.*;
import service.ContentManager;
import service.Menu;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class L525 {
    void main() {
        DatabaseMain db = new DatabaseMain();
        ContentManager manager = new ContentManager();

        new ServiceDB().loadAll(manager, db);

        new WindowMain(manager, db).setVisible(true);

//        Connection connection=null;
//        try {
//            connection = db.getConnecting();
//            IO.println("Подключение к базе данных произошло!\n\n\n\n\n\n\n\n");
//            Menu start=new Menu();
//            start.run(db);
//            connection.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}