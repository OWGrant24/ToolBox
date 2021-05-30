package com.grant;

import com.grant.exception.ToolException;
import com.grant.proxy.TimerProxyFactory;
import com.grant.util.*;
import com.grant.view.Help;
import com.grant.view.ListOfChanges;
import com.grant.view.View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.grant.OutputWindow.consoleStringBuilder;

public class Controller {
    private final PathAddToListUtil pathAddToListUtil;
    private final UtilRenamerImpl utilRenamerImpl;
    private final View view;
    private final HelperOutView helperOutView;
    private final String textFieldAdd;
    private final String textFieldReplace;


    // Constructor
    public Controller(View view) {
        this.view = view;
        this.pathAddToListUtil = new PathAddToListUtil();
        this.utilRenamerImpl = new UtilRenamerImpl(pathAddToListUtil);
        textFieldAdd = view.getTextFieldAdd().getText();
        textFieldReplace = view.getTextFieldReplace().getText();
        helperOutView = new HelperOutView(view, pathAddToListUtil);
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
            new CustomFileChooser(pathAddToListUtil, view, helperOutView);
            helperOutView.logInConsole();
        });

        view.getProcessingButton().addActionListener(e -> {                                 // Процесс переименования
            if (view.getTabbedPane().getSelectedIndex() == 0) {
                try {
                    processing();
                } catch (ToolException toolException) {
                    toolException.printStackTrace();
                } finally {
                    helperOutView.logInConsole();
                }
            } else if (view.getTabbedPane().getSelectedIndex() == 1) {
                try {
                    processingAdd();
                } catch (ToolException toolException) {
                    toolException.printStackTrace();
                } finally {
                    helperOutView.logInConsole();
                }

            }
        });
        view.getOpenDirMenuItem().addActionListener(e -> {                                          // Выбор директории
            new CustomFileChooser(pathAddToListUtil, view, helperOutView);
            helperOutView.logInConsole();
        });
        view.getButtonOpenDir().addActionListener(e -> {
            if (!pathAddToListUtil.getPath().toString().isEmpty() && pathAddToListUtil.getPath() != null) {
                File file = new File(pathAddToListUtil.getPath().toString());
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            } else {
                consoleStringBuilder.append("Директория не выбрана\n");
                helperOutView.logInConsole();
            }
        });
        view.getAboutProgMenuItem().addActionListener(e -> aboutApp());                            // О программе
        view.getHelpMenuItem().addActionListener(e -> new Help(view));                             // Помощь
        view.getListOfChangesMenuItem().addActionListener(e -> new ListOfChanges(view));           // Список изменений
        view.getExitMenuItem().addActionListener(e -> System.exit(0));                      // Выход из программы
    }

    public void processing() throws ToolException {             // Запуск пакетного переименования
        pathAddToListUtil.setMaxDepth((Integer) view.getDepthSpinner().getValue());
        if (checkingInvalidChar(textFieldReplace, Functions.REPLACE) && checkingError(Functions.REPLACE)) {
            generateText();
            UtilRenamer timerProxyUtilRenamer = (UtilRenamer) new TimerProxyFactory(utilRenamerImpl).createProxy();
            timerProxyUtilRenamer.startTask(Functions.REPLACE);
        }
    }

    public void processingAdd() throws ToolException {
        if (checkingInvalidChar(textFieldAdd, Functions.ADD) && checkingError(Functions.ADD)) {
            generateTextAdd();
            UtilRenamer timerProxyUtilRenamer = (UtilRenamer) new TimerProxyFactory(utilRenamerImpl).createProxy();
            timerProxyUtilRenamer.startTask(Functions.ADD);
        }
    }

    private boolean checkingInvalidChar(String textField, Functions func) { // {'/','\\',':','*','?','"','>','<','|'}
        if (textField.matches("[/\\\\:*?\">,<|]+")) {
            switch (func) {
                case ADD:
                    view.showMessageErrorAdd();
                case REPLACE:
                    view.showMessageErrorReplace();
            }
            return false;
        } else {
            return true;
        }
    }

    private boolean checkingError(Functions func) throws ToolException { // Проверка на ошибки
        if (pathAddToListUtil.getPath() == null || pathAddToListUtil.getPath().toString().isBlank()) {
            throw new ToolException("Директория не выбрана\n");
        }
        switch (func) {
            case REPLACE:
                if (view.getTextFieldSearch().getText().isBlank()) {
                    throw new ToolException("Строка \"Найти\" пустая \n");
                } else if (view.getTextFieldSearch().getText().equals(view.getTextFieldReplace().getText())) {
                    throw new ToolException("В строках \"Найти\" и \"Заменить\" одинаковые значения\n");
                }
                return true;
            case ADD:
                if (view.getTextFieldAdd().getText().isEmpty()) {
                    throw new ToolException("Строка \"Добавить\" пустая \n");
                }
                return true;
        }
        return false;
    }

    // передаёт модели значения параметров введенных пользователем
    public void generateText() {
        utilRenamerImpl.setTextSearch(view.getTextFieldSearch().getText());
        utilRenamerImpl.setTextReplace(view.getTextFieldReplace().getText());
    }

    // передаёт модели значения параметров введенных пользователем
    public void generateTextAdd() {
        int numberInsert = (Integer) view.getSpinner1().getValue();
        utilRenamerImpl.setNumberInsert(numberInsert);
        if (numberInsert == 0 && view.getTextFieldAdd().getText().startsWith(" ")) {
            utilRenamerImpl.setTextAdd(view.getTextFieldAdd().getText().replaceFirst("\\s+", ""));
        } else {
            utilRenamerImpl.setTextAdd(view.getTextFieldAdd().getText());
        }
    }

    public void clearConsole() {
        view.getTextAreaConsole().setText("");
        consoleStringBuilder.setLength(0);
    }

    public void cancelSelection() {                     // Отмена выбора директории
        if (!pathAddToListUtil.getPath().toString().isEmpty()) {
            pathAddToListUtil.cancelSelection();
        }
    }

    public void aboutApp() {
        JOptionPane.showMessageDialog(view,
                "Программа для пакетного переименования файлов\n"
                        + "Разработчик Александр Шабельский\n"
                        + "Начата разработка 12.01.21\n",
                "О программе", JOptionPane.INFORMATION_MESSAGE);
    }
}
