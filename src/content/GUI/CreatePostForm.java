package content.GUI;

import javax.swing.*;
import java.awt.*;

public class CreatePostForm extends JPanel {
    public CreatePostForm(Runnable onClose) {
        setLayout(new GridLayout(0, 2, 10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setPreferredSize(new Dimension(400, 300)); // Размер формы

        // Пример полей
        add(new JLabel("Название поста:"));
        add(new JTextField());

        add(new JLabel("Тип медиа:"));
        add(new JComboBox<>(new String[]{"Текст", "Фото", "Видео"}));

        // Кнопка закрытия
        JButton closeBtn = new JButton("Отмена");
        closeBtn.addActionListener(e -> onClose.run()); // Вызываем метод закрытия

        // Пустая ячейка для выравнивания кнопки вправо
        add(new JLabel(""));
        add(closeBtn);
    }
}