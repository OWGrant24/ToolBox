package com.grant.view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

@Getter
@Setter
public class View extends JFrame {
    private JPanel mainPanel;
    private JPanel jpanel;
    private JTextArea textArea;
    private JButton buttonChoiceFolder;
    private JButton consoleClearButton;
    private JTextField textFieldSearch;
    private JButton processingButton;
    private JButton cancelChoiceButton;
    private JMenuItem exitMenuItem;
    private JMenuItem openDirMenuItem;
    private JTextField textFieldPath;
    private JMenuItem aboutProgMenuItem;
    private JTextField textFieldReplace;
    private JMenuItem helpMenuItem;
    private JMenuItem listOfChangesMenuItem;
    private JTabbedPane tabbedPane;
    private JPanel Replace;
    private JPanel Add;
    private JTextField textFieldAdd;
    private JButton buttonOpenDir;
    private JSpinner spinner1;
    private JMenu referenceMenu;
    private JMenu fileMenu;
    private JMenuBar jMenuBar;
    private JPanel Settings;
    private JSpinner depthSpinner;
    private JRadioButton radioButtonExtension;
    private JMenu toolsMenu;
    private JMenuItem md5calcMenu;
    private JMenuItem listFilesAndDirMenu;

    // Constructor
    public View(String title) {
        $$$setupUI$$$();
        setTitle(title);
    }

    public void init() {
        setSize(650, 500);
        initSystemLookAndFeel();
        setContentPane(getMainPanel());
        // Приложение завершит свою работу при закрытии GUI интерфейса
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Отображение по центру
        setResizable(false);
        // Устанавливаем иконку
        Image image;
        try {
            image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon.png"))).getImage();
            setIconImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pack();
        setVisible(true); // Делаем фрейм видимым

    }

    public void initSystemLookAndFeel() {
        try {
            String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
            // for Windows: com.sun.java.swing.plaf.windows.WindowsLookAndFeel
            // for Windows Classic: com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel
            //System.out.println(systemLookAndFeelClassName); // Текущий LookAndFeel;
            UIManager.setLookAndFeel(systemLookAndFeelClassName); // устанавливаем LookAndFeel
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Can't use the specified look and feel on this platform.");
        } catch (Exception e) {
            System.err.println("Couldn't get specified look and feel, for some reason.");
        }
    }

    public void showMessageErrorAdd() {
        JOptionPane.showMessageDialog(
                this,
                "В строке \"Добавить\" имеются недопустимые символы",
                "Ошибка", JOptionPane.ERROR_MESSAGE
        );
    }

    public void showMessageErrorReplace() {
        JOptionPane.showMessageDialog(
                this,
                "В строке \"Заменить\" имеются недопустимые символы",
                "Ошибка", JOptionPane.ERROR_MESSAGE
        );
    }

    public void showMessageErrorSearch() {
        JOptionPane.showMessageDialog(
                this,
                "Cтрока \"Найти\" пустая",
                "Ошибка", JOptionPane.ERROR_MESSAGE
        );
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        mainPanel = new JPanel();
        jpanel = new JPanel();
        spinner1 = new JSpinner(new SpinnerNumberModel(0, 0, 32767, 1));
        depthSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 32767, 1));
        // Меню:
        openDirMenuItem = new JMenuItem(); // Выбор директории
        exitMenuItem = new JMenuItem(); // Выход
        helpMenuItem = new JMenuItem(); // Помощь
        listOfChangesMenuItem = new JMenuItem(); // Список изменений
        aboutProgMenuItem = new JMenuItem(); // О программе
        setIcon();
    }

    private void setIcon() {
        try {
            openDirMenuItem.setIcon(
                    new ImageIcon(
                            new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/files-folders-folder-2.png")))
                                    .getImage().getScaledInstance(15, 13, Image.SCALE_SMOOTH))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            exitMenuItem.setIcon(
                    new ImageIcon(
                            new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/interface-logout.png")))
                                    .getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        mainPanel.setLayout(new BorderLayout(0, 0));
        jpanel.setLayout(new GridBagLayout());
        mainPanel.add(jpanel, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        label1.setText("Вывод информации");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 6, 0, 0);
        jpanel.add(label1, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setMinimumSize(new Dimension(400, 200));
        scrollPane1.setPreferredSize(new Dimension(400, 200));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 6, 0, 6);
        jpanel.add(scrollPane1, gbc);
        textArea = new JTextArea();
        textArea.setAutoscrolls(true);
        textArea.setBackground(new Color(-1314561));
        textArea.setEditable(false);
        scrollPane1.setViewportView(textArea);
        textFieldPath = new JTextField();
        textFieldPath.setEditable(false);
        textFieldPath.setPreferredSize(new Dimension(450, 30));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 0);
        jpanel.add(textFieldPath, gbc);
        buttonChoiceFolder = new JButton();
        buttonChoiceFolder.setIconTextGap(4);
        buttonChoiceFolder.setText("Выбор директории");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);
        jpanel.add(buttonChoiceFolder, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Текущая директория");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 6, 0, 0);
        jpanel.add(label2, gbc);
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(3);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 6, 6, 0);
        jpanel.add(tabbedPane, gbc);
        Replace = new JPanel();
        Replace.setLayout(new GridBagLayout());
        tabbedPane.addTab("Замена", Replace);
        textFieldReplace = new JTextField();
        textFieldReplace.setMinimumSize(new Dimension(49, 25));
        textFieldReplace.setPreferredSize(new Dimension(430, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 6, 0, 6);
        Replace.add(textFieldReplace, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Заменить ...");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 6, 0, 0);
        Replace.add(label3, gbc);
        textFieldSearch = new JTextField();
        textFieldSearch.setMinimumSize(new Dimension(49, 25));
        textFieldSearch.setPreferredSize(new Dimension(430, 25));
        textFieldSearch.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 6, 0, 6);
        Replace.add(textFieldSearch, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Найти ...");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 6, 0, 0);
        Replace.add(label4, gbc);
        Add = new JPanel();
        Add.setLayout(new GridBagLayout());
        Add.setEnabled(true);
        tabbedPane.addTab("Добавление", Add);
        textFieldAdd = new JTextField();
        textFieldAdd.setMinimumSize(new Dimension(49, 25));
        textFieldAdd.setPreferredSize(new Dimension(430, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);
        Add.add(textFieldAdd, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Добавление текста в заданную позицию в названии файлов и директорий");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 6, 0, 6);
        Add.add(label5, gbc);
        spinner1.setPreferredSize(new Dimension(55, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);
        Add.add(spinner1, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("Место введения заданного текста (По умолчанию = 0)");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 6, 6, 6);
        Add.add(label6, gbc);
        Settings = new JPanel();
        Settings.setLayout(new GridBagLayout());
        tabbedPane.addTab("Настройки", Settings);
        depthSpinner.setOpaque(false);
        depthSpinner.setPreferredSize(new Dimension(55, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);
        Settings.add(depthSpinner, gbc);
        final JLabel label7 = new JLabel();
        label7.setText("Глубина вложенности (По умолчанию = 1)");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 6, 6, 6);
        Settings.add(label7, gbc);
        radioButtonExtension = new JRadioButton();
        radioButtonExtension.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(6, 6, 6, 6);
        Settings.add(radioButtonExtension, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("Учитывая расширение файла");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 6, 6, 6);
        Settings.add(label8, gbc);
        processingButton = new JButton();
        processingButton.setText("Переименовать");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);
        jpanel.add(processingButton, gbc);
        buttonOpenDir = new JButton();
        buttonOpenDir.setText("Открыть директорию");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);
        jpanel.add(buttonOpenDir, gbc);
        cancelChoiceButton = new JButton();
        cancelChoiceButton.setText("Отменить выбор");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);
        jpanel.add(cancelChoiceButton, gbc);
        consoleClearButton = new JButton();
        consoleClearButton.setText("Очистить консоль");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);
        jpanel.add(consoleClearButton, gbc);
        jMenuBar = new JMenuBar();
        jMenuBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        mainPanel.add(jMenuBar, BorderLayout.NORTH);
        fileMenu = new JMenu();
        fileMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        fileMenu.setText("Файл");
        jMenuBar.add(fileMenu);
        openDirMenuItem.setText("Выбор директории");
        fileMenu.add(openDirMenuItem);
        exitMenuItem.setText("Выход");
        fileMenu.add(exitMenuItem);
        toolsMenu = new JMenu();
        toolsMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        toolsMenu.setEnabled(true);
        toolsMenu.setText("Инструменты");
        jMenuBar.add(toolsMenu);
        md5calcMenu = new JMenuItem();
        md5calcMenu.setLayout(new BorderLayout(0, 0));
        md5calcMenu.setText("Расчёт MD5");
        md5calcMenu.setVisible(false);
        toolsMenu.add(md5calcMenu);
        listFilesAndDirMenu = new JMenuItem();
        listFilesAndDirMenu.setLayout(new BorderLayout(0, 0));
        listFilesAndDirMenu.setText("Список файлов");
        toolsMenu.add(listFilesAndDirMenu);
        referenceMenu = new JMenu();
        referenceMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        referenceMenu.setText("Справка");
        jMenuBar.add(referenceMenu);
        helpMenuItem.setText("Помощь");
        referenceMenu.add(helpMenuItem);
        listOfChangesMenuItem.setText("Список изменений");
        referenceMenu.add(listOfChangesMenuItem);
        aboutProgMenuItem.setText("О программе");
        referenceMenu.add(aboutProgMenuItem);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}