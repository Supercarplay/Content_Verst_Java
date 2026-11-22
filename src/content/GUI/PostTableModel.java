package content.GUI;

import service.Content.ContentElement;
import service.Content.MediaPost;
import service.ContentManager;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PostTableModel extends AbstractTableModel {
    private final ContentManager manager;
    private List<ContentElement> rows;

    private final String[] columns = {
            "ID БД", "Дата и время", "Название",
            "Тип", "Согласован", "Опубликован"
    };

    public PostTableModel(ContentManager manager) {
        this.manager = manager;
        refresh();
    }

    public void refresh() {
        rows = manager.getAll();
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
            case 0: return post.getIdDB();
            case 1: return post.getDate() + " " + post.getTime();
            case 2: return post.getName();
            case 3: return post instanceof MediaPost ? "Медиа" : "Текст";
            case 4: return post.isApproved() ? "Да" : "Нет";
            case 5: return post.isPublished() ? "Да" : "Нет";
            default: return "";
        }
    }
}