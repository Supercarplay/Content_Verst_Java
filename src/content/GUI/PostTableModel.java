package content.GUI;

import db.ServiceDB;
import service.Content.ContentElement;
import service.Content.MediaPost;
import service.ContentManager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PostTableModel extends AbstractTableModel {
    private final ContentManager manager;
    private List<ContentElement> rows;

    private final String[] columns = {
            "Дата и время", "Название", "Описание", "Текст", "Сценарий","Вид медиа", "", ""
    };

    public PostTableModel(ContentManager manager, int status) {
        this.manager = manager;
        refresh(status);
    }

    public void refresh(int status) {
        if(status==0){
            rows=manager.getNoPublished();
        }else if(status==1){
            rows=manager.getPublished();
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        ContentElement post = rows.get(row);

        switch (column) {
            case 0: return post.getDate() + " " + post.getTime();
            case 1: return post.getName();
            case 2: return post.getAbout();
            case 3: return post.getText();
            case 4: {
                if (post instanceof MediaPost) {
                    return ((MediaPost) post).getScript();
                }
                return "";
            }
            case 5: return post instanceof MediaPost ? "Медиа" : "Текст";
            case 6: return null;
            case 7: return null;
            default: return null;
        }
    }

    public ContentElement getRowAt(Integer row) {
        return rows.get(row);
    }
}