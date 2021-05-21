package com.grant;

import com.grant.exception.ToolException;
import com.grant.proxy.TimerProxyFactory;
import com.grant.util.CustomFileChooser;
import com.grant.util.Functions;
import com.grant.util.UtilRenamer;
import com.grant.util.UtilRenamerImpl;
import com.grant.view.Help;
import com.grant.view.ListOfChanges;
import com.grant.view.View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.grant.OutputWindow.consoleStringBuilder;

public class Controller {
    private final UtilRenamerImpl utilRenamerImpl;
    private final View view;
    private final HelperOutView helperOutView;

    // Constructor
    public Controller(View view, UtilRenamerImpl utilRenamerImpl) {
        this.utilRenamerImpl = utilRenamerImpl;
        this.view = view;
        helperOutView = new HelperOutView(view, utilRenamerImpl);
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
            new CustomFileChooser(utilRenamerImpl, view, helperOutView);
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

//                System.out.println(view.getTabbedPane().getSelectedIndex());
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
        view.getOpenDirMenuItem().addActionListener(e -> {                                 // Выбор директории
            new CustomFileChooser(utilRenamerImpl, view, helperOutView);
            helperOutView.logInConsole();
        });
        view.getButtonOpenDir().addActionListener(e -> {
            if (!utilRenamerImpl.getPath().toString().isEmpty() && utilRenamerImpl.getPath() != null) {
                File file = new File(utilRenamerImpl.getPath().toString());
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
        view.getHelpMenuItem().addActionListener(e -> new Help(view));                              // Помощь
        view.getListOfChangesMenuItem().addActionListener(e -> new ListOfChanges(view));            // Список изменений
        view.getExitMenuItem().addActionListener(e -> System.exit(0));                      // Выход из программы
    }

    public void processing() throws ToolException { // Запуск пакетного переименования
        utilRenamerImpl.setMaxDepth((Integer) view.getDepthSpinner().getValue());
        if (checkingInvalidCharReplace()) {
            generateText();
            UtilRenamer timerProxyUtilRenamer = (UtilRenamer) new TimerProxyFactory(utilRenamerImpl).createProxy();
            timerProxyUtilRenamer.startTask(Functions.REPLACE);
        }
    }

    public void processingAdd() throws ToolException {
        if (checkingInvalidCharAdd()) {
            generateTextAdd();
            utilRenamerImpl.startTask(Functions.ADD);
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

    public void generateText() {                        // передаёт модели значения параметров введеных пользователем
        utilRenamerImpl.setTextSearch(view.getTextFieldSearch().getText());
        utilRenamerImpl.setTextReplace(view.getTextFieldReplace().getText());
    }

    public void generateTextAdd() {                     // передаёт модели значения параметров введеных пользователем
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

    public void cancelSelection() {  // Отмена выбора директории
        if (!utilRenamerImpl.getPath().toString().isEmpty()) {
            utilRenamerImpl.cancelSelection();
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
