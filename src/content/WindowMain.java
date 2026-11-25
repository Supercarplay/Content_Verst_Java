package content;

import content.GUI.*;
import db.DatabaseMain;
import db.ServiceDB;
import service.*;
import service.Content.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WindowMain extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel mainPanel;
    private JPanel topPanel;

    private MainTable mainTable;
    private ArchiveTable archiveTable;

    private OverlayPanel createPostOverlay;

    public WindowMain(ContentManager manager, DatabaseMain db) {
        setTitle("Content Verst");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 500);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton archiveBtn = new JButton("Архив");
        archiveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (archiveBtn.getText().equals("Архив")) {
                    archiveBtn.setText("Выйти из архива");
                    cardLayout.show(contentPanel, "archive");
                } else {
                    archiveBtn.setText("Архив");
                    cardLayout.show(contentPanel, "main");
                }
                archiveTable.refresh();
            }
        });

        JButton settingsBtn = new JButton("Настройки");
        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton addPostBtn = new JButton("Добавить пост");
        addPostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPostOverlay.setVisible(true);
                createPostOverlay.revalidate();
                createPostOverlay.repaint();
            }


        });

        JButton updateBtn = new JButton("UPD");
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.clear();
                new ServiceDB().loadAll(manager, db);
                mainTable.refresh();
                archiveTable.refresh();
            }
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.add(updateBtn);
        rightPanel.add(settingsBtn);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.add(addPostBtn);

        topPanel.add(archiveBtn, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        mainTable = new MainTable(manager);
        archiveTable = new ArchiveTable(manager);

        contentPanel.add(mainTable, "main");
        contentPanel.add(archiveTable, "archive");

        CreatePostForm form = new CreatePostForm(() -> {
            createPostOverlay.setVisible(false);
            mainTable.refresh();
        });

        createPostOverlay = new OverlayPanel(form);
        this.setGlassPane(createPostOverlay);
        createPostOverlay.setVisible(false);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        cardLayout.show(contentPanel, "main");

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (mainTable != null || archiveTable!=null) {
                    mainTable.refresh();
                    archiveTable.refresh();
                }
            }
        });
    }
}