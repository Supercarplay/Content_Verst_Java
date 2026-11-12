package db;

import service.Content.*;
import service.*;

import java.sql.*;

public class ServiceDB {
    /// Добавление контент элемента в базу данных
    public void add(ContentElement element, DatabaseMain db){
        String sqlAdd = "INSERT INTO TablePost (Users_ID, Date_post, name_post, About_post, Text_post, Scencens_post," +
                                "ViewMedia_post, Published, Files, Approved)" +
                                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = db.getConnecting()){
            PreparedStatement preparedStatementAdd = connection.prepareStatement(sqlAdd, Statement.RETURN_GENERATED_KEYS);

            // Установка значений параметров
            preparedStatementAdd.setInt(1, Integer.parseInt(db.getUsersId()));
            preparedStatementAdd.setString(2, element.getDate()+" "+element.getTime());
            preparedStatementAdd.setString(3, element.getName());
            preparedStatementAdd.setString(4, element.getAbout());
            preparedStatementAdd.setString(5, element.getText());
            if(element instanceof MediaPost){
                preparedStatementAdd.setString(6, ((MediaPost) element).getScript());
                preparedStatementAdd.setString(7,((MediaPost) element).getViewMedia());
                preparedStatementAdd.setString(9, ((MediaPost) element).getURL());
            }else {
                preparedStatementAdd.setString(6, null);
                preparedStatementAdd.setString(7, null);
                preparedStatementAdd.setString(9, null);
            }
            preparedStatementAdd.setBoolean(8, element.isPublished());
            preparedStatementAdd.setBoolean(10, element.isApproved());

            int status=preparedStatementAdd.executeUpdate();
            if(status>0){
                IO.println("Пост добавлен в базу данных!");
                try (ResultSet generatedKeys = preparedStatementAdd.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        element.setIdDB(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            IO.println("Ошибка при сохранении в БД: " + e.getMessage());
        }
    }
    /// Обновление элемента базы данных на актуальные данные
    public void update(ContentElement element){
        DatabaseMain db = new DatabaseMain();
        String sqlConnection = "UPDATE TablePost SET Users_ID = ?, Date_post = ?, name_post = ?, About_post = ?, Text_post = ?, " +
                                "Scencens_post = ?, ViewMedia_post = ?, Published = ?, Files = ?, Approved = ? " +
                                "WHERE ID = ?";
        try(Connection connection = db.getConnecting()){
            PreparedStatement preparedStatement=connection.prepareStatement(sqlConnection);

            // Установка значений параметров
            preparedStatement.setInt(1, Integer.parseInt(db.getUsersId()));
            preparedStatement.setString(2, element.getDate()+" "+element.getTime());
            preparedStatement.setString(3, element.getName());
            preparedStatement.setString(4, element.getAbout());
            preparedStatement.setString(5, element.getText());
            if(element instanceof MediaPost){
                preparedStatement.setString(6, ((MediaPost) element).getScript());
                preparedStatement.setString(7,((MediaPost) element).getViewMedia());
                preparedStatement.setString(9, ((MediaPost) element).getURL());
            }else {
                preparedStatement.setString(6, null);
                preparedStatement.setString(7, null);
                preparedStatement.setString(9, null);
            }
            preparedStatement.setBoolean(8, element.isPublished());
            preparedStatement.setBoolean(10, element.isApproved());
            preparedStatement.setInt(11, element.getIdDB());

            int status=preparedStatement.executeUpdate();
            if(status>0){
                IO.println("Пост изменён!");
            }else {
                IO.println("Запись не найдена в БД!");
            }
        } catch (SQLException e) {
        IO.println("Ошибка при сохранении в БД: " + e.getMessage());
        }
    }
    /// Удаление элемента из базы данных по ID из БД, содержащийся в ContentElement каждого поста
    public void delete(ContentElement element){
        DatabaseMain db = new DatabaseMain();
        String sqlConnection ="DELETE FROM TablePost WHERE ID=? and Users_ID=?";
        try(Connection connection = db.getConnecting()){
            PreparedStatement preparedStatement=connection.prepareStatement(sqlConnection);

            preparedStatement.setInt(1, element.getIdDB());
            preparedStatement.setInt(2, Integer.parseInt(db.getUsersId()));

            int status=preparedStatement.executeUpdate();
            if(status>0){
                IO.println("Пост удалён из базы данных!");
            }else {
                IO.println("Запись не найдена в БД или у вас нет прав на её удаление!");
            }
        } catch (SQLException e) {
            IO.println("Ошибка при удалении из БД: " + e.getMessage());
        }
    }
    /// Выгрузка всех постов из базы данных
    public void loadAll(ContentManager contentPlan, DatabaseMain db){
        String sqlConnection ="SELECT ID, Users_ID, Date_post, name_post, About_post, Text_post, Scencens_post, " +
                                "ViewMedia_post, Published, Files, Approved FROM TablePost WHERE Users_ID = ? ORDER BY Date_post ASC";

        try(Connection connection= db.getConnecting()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlConnection);
            preparedStatement.setInt(1, Integer.parseInt(db.getUsersId()));

            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("name_post");
                String about = resultSet.getString("About_post");
                String text = resultSet.getString("Text_post");
                if (text != null) {
                    text = text.replace("\r\n", "\n").replace("\r", "\n");
                }
                String script = resultSet.getString("Scencens_post");
                String viewMedia = resultSet.getString("ViewMedia_post");
                String files = resultSet.getString("Files");
                boolean published = resultSet.getBoolean("Published");
                boolean approved = resultSet.getBoolean("Approved");

                // Преобразование даты
                String dateTime = resultSet.getString("Date_post");
                String[] parts=dateTime.split("\\s+");
                String date=parts[0];
                String[] dateParts=date.split("-");
                date=dateParts[2]+'.'+dateParts[1]+'.'+dateParts[0];

                // Преобразование времени
                String time=parts[1].substring(0,5);

                ContentElement element;
                if (viewMedia==null || viewMedia.equals("Ничего") || viewMedia.trim().isEmpty()){
                    element=new TextPost();
                    element.setIdDB(id);
                    element.loadFromDB(date, time, name,about,text,approved,published);
                }else {
                    element = new MediaPost();
                    element.setIdDB(id);
                    ((MediaPost)element).loadFromDB(date, time, name,about,text,script,viewMedia,files,approved,published);
                }
                contentPlan.add(element);
            }
        }catch (SQLException e){
            IO.println("Добавить посты в приложение не получилось! "+e.getMessage());
        }
    }
}