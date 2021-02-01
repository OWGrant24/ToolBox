package com.grant;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Controller {
    private final Model model;
    private final View view;
    private final HelperOutView helperOutView;

    // Constructor
    public Controller(View view, Model model) {
        this.model = model;
        this.view = view;
        helperOutView = new HelperOutView(view, model);
        helperOutView.logInConsole();
    }

    // Methods

    public void initController() {
        view.getConsoleClearButton().addActionListener(e -> clearConsole());                // Очистка
        view.getCancelChoiceButton().addActionListener(e -> {                               // Отмена выбора
            cancelSelection();
            helperOutView.outputTextFieldPath();
            helperOutView.logInConsole();
        });
        view.getButtonChoiceFolder().addActionListener(e -> {                               // Выбор директории
            new CustomFileChooser(model, view, helperOutView);
            helperOutView.logInConsole();
        });

        view.getProcessingButton().addActionListener(e -> {                                 // Процесс переименования
            if (view.getTabbedPane().getSelectedIndex() == 0) {
                processing();
                helperOutView.logInConsole();
//                System.out.println(view.getTabbedPane().getSelectedIndex());
            } else if (view.getTabbedPane().getSelectedIndex() == 1) {
                processingAdd();
                helperOutView.logInConsole();
            }
        });
        view.getJMenuItemOpenDir().addActionListener(e -> {                                 // Выбор директории
            new CustomFileChooser(model, view, helperOutView);
            helperOutView.logInConsole();
        });
        view.getButtonOpenDir().addActionListener(e -> {
            if (!model.getPath().toString().isEmpty() && model.getPath() != null) {
                File file = new File(model.getPath().toString());
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            } else {
                model.getConsoleStringBuilder().append("Директория не выбрана\n");
                helperOutView.logInConsole();
            }
        });
        view.getAboutProg().addActionListener(e -> aboutProg());                            // О программе
        view.getHelp().addActionListener(e -> new Help(view));                              // Помощь
        view.getListOfChanges().addActionListener(e -> new ListOfChanges(view));            // Список изменений
        view.getJMenuItemExit().addActionListener(e -> System.exit(0));              // Выход из программы
    }

    public void processing() { // Запуск пакетного переименования
        if (checkingInvalidCharReplace()) {
            generateText();
            model.startTaskRename();
        }
    }

    public void processingAdd() {
        if (checkingInvalidCharAdd()) {
            generateTextAdd();
            model.startTaskAdd();
        }
    }

    private boolean checkingInvalidCharAdd() { // {'/','\\',':','*','?','"','>','<','|'}
        if (view.getTextFieldAdd().getText().matches("[/\\\\:*?\">,<|]+")) {
            view.showMessageErrorAdd();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkingInvalidCharReplace() {
        if (view.getTextFieldReplace().getText().matches("[/\\\\:*?\">,<|]+")) {
            view.showMessageErrorReplace();
            return false;
        } else {
            return true;
        }
    }

    public void generateText() {   // передаёт модели значения параметров введеных пользователем
        model.setTextSearch(view.getTextFieldSearch().getText());
        model.setTextReplace(view.getTextFieldReplace().getText());
    }

    public void generateTextAdd() {   // передаёт модели значения параметров введеных пользователем
        if (view.getTextFieldAdd().getText().startsWith(" ")) {
            model.setTextAdd(view.getTextFieldAdd().getText().replaceFirst("\\s+", ""));
        } else {
            model.setTextAdd(view.getTextFieldAdd().getText());
        }
    }

    public void clearConsole() {
        view.getTextAreaConsole().setText("");
        model.getConsoleStringBuilder().setLength(0);
    }

    public void cancelSelection() {  // Отмена выбора директории
        if (!model.getPath().toString().isEmpty()) {
            model.cancelSelection();
        }
    }

    public void aboutProg() {
        JOptionPane.showMessageDialog(view, "Программа для пакетного переименования файлов\n" +
                "Разработчик Александр Шабельский\n" +
                "Начата разработка 12.01.21\n", "О программе", JOptionPane.INFORMATION_MESSAGE);
    }
}
