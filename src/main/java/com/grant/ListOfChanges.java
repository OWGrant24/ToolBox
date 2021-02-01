package com.grant;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class ListOfChanges extends JFrame {
    private final View view;
    private JPanel panel;
    private JTextArea textChanges;

    public JPanel getPanel() {
        return panel;
    }

    public JTextArea getTextChanges() {
        return textChanges;
    }

    public ListOfChanges(View view) {
        this.view = view;
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
        setVisible(true);
        pack();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setPreferredSize(new Dimension(500, 270));
        panel.add(panel1, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        label1.setText("Список изменений");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel1.add(label1, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(450, 230));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel1.add(scrollPane1, gbc);
        textChanges = new JTextArea();
        textChanges.setBackground(new Color(-1644826));
        textChanges.setEditable(false);
        Font textChangesFont = this.$$$getFont$$$(null, -1, 12, textChanges.getFont());
        if (textChangesFont != null) textChanges.setFont(textChangesFont);
        textChanges.setText("12.01.2021 Начата разработка данного приложения\n19.01.2021 Скомпилирована первая Alpha версия приложения 0.1 \n24.01.2021 Скомпилирована Toolbox 0.2\n           Исправление ошибок, полный рефакторинг \n29.01.2021 Скомпилирована ToolBox 0.3c\n           Новые функции (Добавить), исправление ошибок\n01.02.2021 Скомпилирована ToolBox 0.4\n           Подправлен подсчёт кол-ва переименованных файлов");
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
