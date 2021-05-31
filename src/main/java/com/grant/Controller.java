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
    private final RenameUtilImpl utilRenamerImpl;
    private final View view;
    private final HelperOutView helperOutView;


    // Constructor
    public Controller(View view) {
        this.view = view;
        this.pathAddToListUtil = new PathAddToListUtil();
        this.utilRenamerImpl = new RenameUtilImpl(pathAddToListUtil);
        helperOutView = new HelperOutView(view, pathAddToListUtil);
        helperOutView.logInTextArea();
    }

    // Methods
    public void initController() {
        view.getConsoleClearButton().addActionListener(e -> clearConsole());                // Очистка
        view.getCancelChoiceButton().addActionListener(e -> {                               // Отмена выбора
            cancelSelection();
            helperOutView.outputTextFieldPath();
            helperOutView.logInTextArea();
        });
        view.getButtonChoiceFolder().addActionListener(e -> {                               // Выбор директории
            new CustomFileChooser(pathAddToListUtil, view, helperOutView);
            helperOutView.logInTextArea();
        });

        view.getProcessingButton().addActionListener(e -> {                                 // Процесс переименования
            if (view.getTabbedPane().getSelectedIndex() == 0) {
                try {
                    processingReplace();
                } catch (ToolException toolException) {
                    toolException.printStackTrace();
                } finally {
                    helperOutView.logInTextArea();
                }
            } else if (view.getTabbedPane().getSelectedIndex() == 1) {
                try {
                    processingAdd();
                } catch (ToolException toolException) {
                    toolException.printStackTrace();
                } finally {
                    helperOutView.logInTextArea();
                }

            }
        });
        view.getOpenDirMenuItem().addActionListener(e -> {                                          // Выбор директории
            new CustomFileChooser(pathAddToListUtil, view, helperOutView);
            helperOutView.logInTextArea();
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
                helperOutView.logInTextArea();
            }
        });
        view.getAboutProgMenuItem().addActionListener(e -> aboutApp());                            // О программе
        view.getHelpMenuItem().addActionListener(e -> new Help(view));                             // Помощь
        view.getListOfChangesMenuItem().addActionListener(e -> new ListOfChanges(view));           // Список изменений
        view.getExitMenuItem().addActionListener(e -> System.exit(0));                      // Выход из программы

        // Инструменты (новый раздел)
        view.getListFilesAndDirMenu().addActionListener(e -> {
            pathAddToListUtil.setMaxDepth((Integer) view.getDepthSpinner().getValue());
            pathAddToListUtil.printFiles();
            helperOutView.logInTextArea();
        });
    }

    public void processingReplace() throws ToolException {             // Запуск пакетного переименования
        if (checkingError(view.getTextFieldReplace().getText(), Functions.REPLACE)) {
            generateText();
            RenameUtil timerProxyRenameUtil = (RenameUtil) new TimerProxyFactory(utilRenamerImpl).createProxy();
            timerProxyRenameUtil.startRename(Functions.REPLACE);
        }
    }

    public void processingAdd() throws ToolException {
        if (checkingError(view.getTextFieldAdd().getText(), Functions.ADD)) {
            generateTextAdd();
            RenameUtil timerProxyRenameUtil = (RenameUtil) new TimerProxyFactory(utilRenamerImpl).createProxy();
            timerProxyRenameUtil.startRename(Functions.ADD);
        }
    }

    // {'/','\\',':','*','?','"','>','<','|'}
    private boolean checkingError(String textField, Functions func) throws ToolException {   // Проверка на ошибки
        if (pathAddToListUtil.getPath() == null || pathAddToListUtil.getPath().toString().isBlank()) {
            throw new ToolException("Директория не выбрана\n");
        }
        switch (func) {
            case REPLACE:
                if (view.getTextFieldSearch().getText().isBlank()) {
                    view.showMessageErrorSearch();
                    throw new ToolException("Строка \"Найти\" пустая \n");
                } else if (view.getTextFieldSearch().getText().equals(view.getTextFieldReplace().getText())) {
                    throw new ToolException("В строках \"Найти\" и \"Заменить\" одинаковые значения\n");
                }
                break;
            case ADD:
                if (view.getTextFieldAdd().getText().isEmpty()) {
                    throw new ToolException("Строка \"Добавить\" пустая \n");
                }
                break;
        }
        if (textField.matches("[/\\\\:*?\">,<|]+")) {
            switch (func) {
                case ADD:
                    view.showMessageErrorAdd();
                    throw new ToolException("Недопустимые сиволы");
                case REPLACE:
                    view.showMessageErrorReplace();
                    throw new ToolException("Недопустимые сиволы");
            }
        }
        return true;
    }

    // передаёт модели значения параметров введенных пользователем
    public void generateText() {
        pathAddToListUtil.setMaxDepth((Integer) view.getDepthSpinner().getValue());
        utilRenamerImpl.setTextSearch(view.getTextFieldSearch().getText());
        utilRenamerImpl.setTextReplace(view.getTextFieldReplace().getText());
        utilRenamerImpl.setUseExtension(view.getRadioButtonExtension().isSelected());
    }

    // передаёт модели значения параметров введенных пользователем
    public void generateTextAdd() {
        pathAddToListUtil.setMaxDepth((Integer) view.getDepthSpinner().getValue());
        int numberInsert = (Integer) view.getSpinner1().getValue();
        utilRenamerImpl.setNumberInsert(numberInsert);
        if (numberInsert == 0 && view.getTextFieldAdd().getText().startsWith(" ")) {
            utilRenamerImpl.setTextAdd(view.getTextFieldAdd().getText().replaceFirst("\\s+", ""));
        } else {
            utilRenamerImpl.setTextAdd(view.getTextFieldAdd().getText());
        }
    }

    public void clearConsole() {
        view.getTextArea().setText("");
        consoleStringBuilder.setLength(0);
    }

    public void cancelSelection() {                     // Отмена выбора директории
        if (!pathAddToListUtil.getPath().toString().isEmpty()) {
            pathAddToListUtil.cancelSelection();
        }
    }

    public void aboutApp() {
        JOptionPane.showMessageDialog(view,
                "ToolBox\n"
                        + "Разработчик Александр Шабельский\n"
                        + "Начата разработка 12.01.21\n",
                "О программе", JOptionPane.INFORMATION_MESSAGE);
    }
}
