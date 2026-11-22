package content;

import content.GUI.AcrhiveTable;
import content.GUI.CreatePost;
import content.GUI.MainTable;
import content.GUI.SettingsMenu;
import service.ContentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowMain extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel mainPanel;
    private JPanel topPanel;
    private MainTable mainTable;
    private AcrhiveTable archiveTable;
    private CreatePost createPost;
    private SettingsMenu settingsMenu;
    public WindowMain(ContentManager manager) {
        setTitle("Content Verst");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 500);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Контент-план");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton ArchiveBtn = new JButton("Архив");
        ArchiveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ArchiveBtn.getText()=="Архив"){
                    ArchiveBtn.setText("Основной");
                }else{
                    ArchiveBtn.setText("Архив");
                }
            }
        });

        JButton SettingsBtn = new JButton("Настройки");
        ArchiveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        topPanel.add(ArchiveBtn, BorderLayout.WEST);

        topPanel.add(title, BorderLayout.CENTER);
        topPanel.add(SettingsBtn, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        setContentPane(mainPanel);
    }
}
