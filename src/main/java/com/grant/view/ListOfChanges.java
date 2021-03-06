package com.grant.view;

import lombok.Getter;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

@Getter
public class ListOfChanges extends JFrame {
    private final View view;
    private JPanel panel;
    private JTextArea textChanges;
    private JPanel internalPanel;

    public ListOfChanges(View view) {
        this.view = view;
        $$$setupUI$$$();
        createListOfChangesGUI();
    }

    public void createListOfChangesGUI() {
        setTitle("Список изменений");
        setSize(550, 330);
        setContentPane(getPanel());
        setLocationRelativeTo(view);
        setResizable(false);
        try {
            setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        pack();
        setVisible(true);
    }

    private void createUIComponents() {
        panel = new JPanel();
        internalPanel = new JPanel();
        // TODO: place custom component creation code here
        textChanges = new JTextArea(
                "12.01.2021 Начата разработка данного приложения\n" +
                "19.01.2021 Скомпилирована первая Alpha версия приложения 0.1\n" +
                "24.01.2021 Скомпилирована Toolbox 0.2\n" +
                "           Исправление ошибок, полный рефакторинг \n" +
                "29.01.2021 Скомпилирована ToolBox 0.3c\n" +
                "           Новые функции (Добавить), исправление ошибок\n" +
                "01.02.2021 Скомпилирована ToolBox 0.4\n" +
                "           Подправлен подсчёт кол-ва переименованных файлов\n" +
                "07.02.2021 Скомпилирована ToolBox 0.5\n" +
                "           Добавлена возможность вставки текста в \n" +
                "           определенную позицию\n" +
                "20.05.2021 Скомпилирована ToolBox 0.6\n" +
                "           Добавлена глубина прохода в настройках \n" +
                "           Рефакторинг, Исправление ошибок\n" +
                "31.05.2021 Скомпилирована ToolBox 0.7\n" +
                "           Добавлена возможность исключить расширение\n" +
                "           Рефакторинг, Исправление ошибок\n");

    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel.setLayout(new BorderLayout(0, 0));
        internalPanel.setLayout(new GridBagLayout());
        internalPanel.setPreferredSize(new Dimension(500, 270));
        panel.add(internalPanel, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        label1.setText("Список изменений");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        internalPanel.add(label1, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(450, 230));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        internalPanel.add(scrollPane1, gbc);
        textChanges.setBackground(new Color(-1644826));
        textChanges.setEditable(false);
        Font textChangesFont = this.$$$getFont$$$(null, -1, 12, textChanges.getFont());
        if (textChangesFont != null) textChanges.setFont(textChangesFont);
        textChanges.setVisible(true);
        scrollPane1.setViewportView(textChanges);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
