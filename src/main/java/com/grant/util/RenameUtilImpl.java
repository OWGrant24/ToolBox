package com.grant.util;

import com.grant.exception.ToolException;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.nio.file.*;
import java.util.Collection;

import static com.grant.OutputWindow.consoleStringBuilder;

@Getter
@Setter
public class RenameUtilImpl implements RenameUtil { //12/01/21
    private PathAddToListUtil pathAddToListUtil;
    private String textSearch;
    private String textReplace;
    private String textAdd;
    private long countProcessedFiles;
    private long countProcessedDir;
    private int numberInsert;
    private boolean useExtension;

    // Constructor
    public RenameUtilImpl(PathAddToListUtil pathAddToListUtil) {
        this.pathAddToListUtil = pathAddToListUtil;
    }

    @Override
    public void resetRename() {
        pathAddToListUtil.clearList();
        countProcessedFiles = 0;
        countProcessedDir = 0;
    }

    @Override
    public void startRename(Functions func) throws ToolException {
        resetRename();
        if (pathAddToListUtil.addInListFiles()) {
            rename(func);
        }
        if (countProcessedDir + countProcessedFiles > 0) {
            infoProcess();
        } else {
            consoleStringBuilder.append("Нет файлов/директорий подходящих под критерии\n");
        }
    }

    public void rename(Functions func) {
        switch (func) {
            case REPLACE:
                renamePathReplaceText(pathAddToListUtil.getPaths());
                break;
            case ADD:
                renamePathAddText(pathAddToListUtil.getPaths());
                break;
        }
    }

    private void renamePathReplaceText(Collection<Path> paths) {
        for (Path value : paths) {
            if (value.getFileName().toString().contains(textSearch)) {
                File file = value.toFile();
                String newFileName = replaceText(value);
                File fileResult = new File(value.getParent() + "/" + newFileName);
                if (file.renameTo(fileResult)) {
                    if (fileResult.isDirectory()) {
                        countProcessedDir++;
                    } else {
                        countProcessedFiles++;
                    }
                }
            }
        }
    }

    private void renamePathAddText(Collection<Path> paths) {
        for (Path value : paths) {
            File file = value.toFile();
            String newFileName = insertText(value);
            File fileResult;
            if (newFileName.startsWith(" ")) {
                fileResult = new File(value.getParent()
                        + "/" + newFileName.replaceFirst("\\s+", ""));
            } else {
                fileResult = new File(value.getParent() + "/" + newFileName);
            }
            if (file.renameTo(fileResult)) {
                if (fileResult.isDirectory()) {
                    countProcessedDir++;
                } else {
                    countProcessedFiles++;
                }
            }
        }
    }

    private String replaceText(Path pathFile) {                             // Замена текста
        if (useExtension || pathFile.toFile().isDirectory()) {
            return pathFile.getFileName().toString().replace(textSearch, textReplace);
        } else {
            String fileExtension = pathFile.getFileName().toString().substring(
                    pathFile.getFileName().toString().lastIndexOf(".") + 1
            );
            String fileNameNotExtension = pathFile.getFileName().toString().substring(
                    0, pathFile.getFileName().toString().lastIndexOf(".") + 1
            );
            String newFileNameNotExtension = fileNameNotExtension.replace(textSearch, textReplace);
            return newFileNameNotExtension.concat(fileExtension);
        }
    }

    private String insertText(Path pathFile) {                              // Добавление текста в заданную позицию
        int lengthNameFile = pathFile.getFileName().toString().length();
        if (numberInsert >= lengthNameFile) {
            return new StringBuilder(pathFile.getFileName().toString()).insert(lengthNameFile, textAdd).toString();
        }
        return new StringBuilder(pathFile.getFileName().toString()).insert(numberInsert, textAdd).toString();
    }

    public void infoProcess() {
        consoleStringBuilder
                .append("Переименование успешно выполнено!\n");
        consoleStringBuilder
                .append("Переименовано ")
                .append(countProcessedDir)
                .append("/")
                .append(pathAddToListUtil.getCountDir())
                .append(" директорий\n");
        consoleStringBuilder.append("Переименовано ")
                .append(countProcessedFiles)
                .append("/")
                .append(pathAddToListUtil.getCountFiles())
                .append(" файлов\n");
        //consoleStringBuilder.append("Общий размер директории - ").append(sizeDir / 1000).append(" килобайт\n");
    }
}