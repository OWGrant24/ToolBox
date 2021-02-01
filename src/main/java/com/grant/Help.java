package com.grant;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class Help extends JFrame {
    private final View view;
    private JPanel panelHelp;
    private JTextArea TextArea;

    public JPanel getPanelHelp() {
        return panelHelp;
    }

    public Help(View view) {
        this.view = view;
        createHelp();
    }

    public void createHelp() {
        setTitle("Помощь");
        setSize(550, 280);
        setContentPane(getPanelHelp());
        setLocationRelativeTo(view);
        setVisible(true);
        try {
            setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setResizable(false);
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
        panelHelp = new JPanel();
        panelHelp.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setPreferredSize(new Dimension(550, 250));
        panelHelp.add(panel1, BorderLayout.CENTER);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setMinimumSize(new Dimension(500, 200));
        scrollPane1.setPreferredSize(new Dimension(500, 200));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel1.add(scrollPane1, gbc);
        TextArea = new JTextArea();
        TextArea.setBackground(new Color(-1644826));
        TextArea.setEditable(false);
        Font TextAreaFont = this.$$$getFont$$$(null, -1, 12, TextArea.getFont());
        if (TextAreaFont != null) TextArea.setFont(TextAreaFont);
        TextArea.setText("Программа предназначена для пакетного переименования файлов и папок\nЧтобы воспользоваться данной программой необходимо:\n- выбрать папку в которой будем производить переименование, \nкнопкой \"Выбор директории\"\n- если путь выбран правильно, в окне \"Вывод информации\" \nполучите подтверждение.\n- в графе \"Найти\" ввести текст, который будем заменять.\n- в графе \"Заменить\" текст на который будем заменять.\n- нажимаем \"Переименовать\" и ждём успешное завершение.");
        scrollPane1.setViewportView(TextArea);
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
        return panelHelp;
    }

}
