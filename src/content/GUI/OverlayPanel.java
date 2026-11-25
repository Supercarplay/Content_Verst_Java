package content.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class OverlayPanel extends JPanel {
    public OverlayPanel(JPanel contentForm) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        add(contentForm);
        addMouseListener(new MouseAdapter() {});
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}