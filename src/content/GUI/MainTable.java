package content.GUI;

import db.ServiceDB;
import service.*;
import service.Content.ContentElement;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class MainTable extends JPanel {
    private ContentManager manager;
    private PostTableModel tableModel;
    private JTable table;
    private ServiceDB serviceDB=new ServiceDB();
    public MainTable(ContentManager manager){
        this.manager=manager;
        tableModel=new PostTableModel(this.manager, 0);
        table=new JTable(tableModel);
        configureTable();

        JScrollPane scroll = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);

        updateRowHeights();
    }

    public void refresh() {
        tableModel.refresh(0);
        updateRowHeights();
    }

    private void configureTable() {
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        int[] min  = {50,  50,  150,  150,  150,  50,   50,  50};
        int[] pref = {100,  100,  220,  300,  260,  50,   50,  50};
        int[] max  = {100,  200,  250,  900,  700,  50,   50,  50};

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < pref.length; i++) {
            TableColumn col = columnModel.getColumn(i);
            col.setMinWidth(min[i]);
            col.setPreferredWidth(pref[i]);
            col.setMaxWidth(max[i]);
        }

        TextAreaRenderer wrapRenderer = new TextAreaRenderer();
        columnModel.getColumn(2).setCellRenderer(wrapRenderer);
        columnModel.getColumn(3).setCellRenderer(wrapRenderer);
        columnModel.getColumn(4).setCellRenderer(wrapRenderer);

        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("✎"));
        table.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("X"));

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row < 0 || col < 0) return;

                if (col == 6) {                       // Редактировать
                    ContentElement post = tableModel.getRowAt(row);
                    // открыть форму редактирования
                } else if (col == 7) {                // Удалить
                    int answer = JOptionPane.showConfirmDialog(MainTable.this,
                            "Удалить пост?", "Подтверждение", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        manager.remove(String.valueOf(row), serviceDB);
                        refresh();
                    }
                }
            }
        });
    }

    private void updateRowHeights() {
        for (int row = 0; row < table.getRowCount(); row++) {
            int maxHeight = 24;
            for (int col = 0; col < table.getColumnCount(); col++) {
                int colWidth = table.getColumnModel().getColumn(col).getWidth();
                if (colWidth <= 0) continue;
                Component comp = table.prepareRenderer(table.getCellRenderer(row, col), row, col);
                comp.setSize(colWidth, 1);
                maxHeight = Math.max(maxHeight, comp.getPreferredSize().height+6);
            }
            if (table.getRowHeight(row) != maxHeight) {
                table.setRowHeight(row, Math.max(24,maxHeight));
            }

        }
    }

    private static class TextAreaRenderer extends JTextArea implements TableCellRenderer {
        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            setFont(table.getFont());

            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }
            return this;
        }
    }


}
