package com.grant.util;

import com.grant.HelperOutView;
import com.grant.view.View;

import javax.swing.*;
import java.io.File;

import static com.grant.OutputWindow.consoleStringBuilder;

public class CustomFileChooser extends JFileChooser {
    private final UtilRenamer utilRenamer;
    private final View view;
    private final HelperOutView helperOutView;

    public CustomFileChooser(UtilRenamer utilRenamer, View view, HelperOutView helperOutView) {
        this.utilRenamer = utilRenamer;
        this.view = view;
        this.helperOutView = helperOutView;
        initFileChooser();
    }

    private void initFileChooser() {
        createDialog(view);
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (utilRenamer.getPath().toString() != null & !utilRenamer.getPath().toString().isEmpty()) {
            setCurrentDirectory(utilRenamer.getPath().toFile());
        }
        setAcceptAllFileFilterUsed(false);
        setUpdateUI(this);
        int ret = showDialog(view, "Выбор директории");

        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = getSelectedFile();
            utilRenamer.setPath(file.toPath());
            System.out.println(utilRenamer.getPath().toString());
            consoleStringBuilder
                    .append("Директория: ")
                    .append(utilRenamer.getPath().toString())
                    .append(" успешно выбрана\n");
        } else {
            consoleStringBuilder
                    .append("Выбор директории отменен\n");
        }
        if (!utilRenamer.getPath().toString().isEmpty() || utilRenamer.getPath().toString() != null) {
            helperOutView.outputTextFieldPath();
        }
    }

    public void setUpdateUI(JFileChooser choose) {
        UIManager.put("FileChooser.acceptAllFileFilterText", "Все файлы");
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Отмена");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Таблица");
        UIManager.put("FileChooser.fileNameHeaderText", "Имя");
        UIManager.put("FileChooser.fileSizeHeaderText", "Размер");
        UIManager.put("FileChooser.fileTypeHeaderText", "Тип");
        UIManager.put("FileChooser.fileDateHeaderText", "Изменен");
        UIManager.put("FileChooser.fileAttrHeaderText", "Атрибуты");
        UIManager.put("FileChooser.folderNameLabelText", "Путь директории");
        UIManager.put("FileChooser.fileNameLabelText", "Имя файла");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Тип файла");
        UIManager.put("FileChooser.lookInLabelText", "Директория");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Список");
        UIManager.put("FileChooser.newFolderAccessibleName", "Создание новой папки");
        UIManager.put("FileChooser.openButtonText", "Открыть");
        UIManager.put("FileChooser.openButtonToolTipText", "Открыть");
        UIManager.put("FileChooser.saveButtonText", "Сохранить");
        UIManager.put("FileChooser.saveButtonToolTipText", "Сохранить");
        UIManager.put("FileChooser.saveInLabelText", "Сохранить в директории");
        UIManager.put("FileChooser.upFolderToolTipText", "На один уровень вверх");

        choose.updateUI();
    }
}
