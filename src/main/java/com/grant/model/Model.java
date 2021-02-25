package com.grant.model;

import com.grant.util.WorkingHours;
import com.grant.exception.ToolException;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static com.grant.OutputWindow.consoleStringBuilder;

public class Model { //12/01/21
    private Path path;
    private final List<Path> listFiles;
    private final List<Path> listDir;
    private final WorkingHours workingHours;
    private String textSearch;
    private String textReplace;
    private String textAdd;
    private int countFiles = 0;
    private int countProcessedFiles = 0;
    private int countDir = 0;
    private int countProcessedDir = 0;
    private int numberInsert;
    //private long sizeDir = 0;

    // Constructor
    public Model() {
        path = Path.of(""); // путь до папки , в которой будет производить переименование
        workingHours = new WorkingHours(); // Время процесса переименования
        listFiles = new ArrayList<>(); // Список файлов, подходящий под заданные критерии
        listDir = new ArrayList<>(); // Список директорий, подходящих под заданные критерии
        // "Добро пожаловать в программу пакетного переименования файлов и папок\n"
    }

    // Getters and Setters

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setTextAdd(String textAdd) {
        this.textAdd = textAdd;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public void setTextReplace(String textReplace) {
        this.textReplace = textReplace;
    }

    public void setNumberInsert(int numberInsert) {
        this.numberInsert = numberInsert;
    }

    // Methods
    public void cancelSelection() {
        path = Path.of("");
        //path = null;
        consoleStringBuilder.append("Отмена выбора произведена\n");
    }

    public void resetTask() {
        listDir.clear();
        listFiles.clear();
        countFiles = 0;
        countProcessedFiles = 0;
        countDir = 0;
        countProcessedDir = 0;
    }

    public void startTask(Functions func) {
        checkingError(func);
        resetTask();
        workingHours.startTime();
        if (addInListFiles()) {
            rename(func);
        }
        infoProcess();
        workingHours.stopTime();
        workingHours.durationTime();
    }

    private void checkingError(Functions func) { // Проверка на ошибки
        if (path == null || path.toString().isEmpty()) {
            throw new ToolException("Директория не выбрана\n");
        }
        switch (func) {
            case REPLACE:
                if (textSearch.isEmpty()) {
                    throw new ToolException("Строка \"Найти\" пустая \n");
                } else if (textSearch.equals(textReplace)) {
                    throw new ToolException("В строках \"Найти\" и \"Заменить\" одинаковые значения\n");
                }
                break;
            case ADD:
                if (textAdd.isEmpty()) {
                    throw new ToolException("Строка \"Добавить\" пустая \n");
                }
                break;
        }
    }

    public boolean addInListFiles() {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    if (!dir.toAbsolutePath().toString().equals(path.toAbsolutePath().toString())) {
                        countDir++;
                        listDir.add(dir.toAbsolutePath());
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    countFiles++;
                    listFiles.add(file.toAbsolutePath());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            throw new ToolException(e);
        } finally {
            // Это проверка
            System.out.println("Список файлов");
            listFiles.forEach(System.out::println);
            System.out.println("Список директорий");
            listDir.forEach(System.out::println);
        }
        return countDir + countFiles > 0;
    }

    public void rename(Functions func) {
        switch (func) {
            case REPLACE:
                renameObj(listFiles, countProcessedFiles);
                renameObj(listDir, countProcessedDir);
                break;
            case ADD:
                renameObjAdd(listFiles, countProcessedFiles);
                renameObjAdd(listDir, countProcessedDir);
                break;
        }
    }

    private void renameObj(List<Path> list, int amount) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().contains(textSearch)) {
                File file = new File(list.get(i).toString());
                String newFileName = replaceText(list.get(i));
                File fileResult = new File(list.get(i).getParent() + "/" + newFileName);
                if (file.renameTo(fileResult)) {
                    amount++;
                }
            }
        }
    }

    private void renameObjAdd(List<Path> list, int amount) {
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i).toString());
            String newFileName = insertText(list.get(i));
            File fileResult;
            if (newFileName.startsWith(" ")) {
                fileResult = new File(list.get(i).getParent()
                        + "/" + newFileName.replaceFirst("\\s+", ""));
            } else {
                fileResult = new File(list.get(i).getParent() + "/" + newFileName);
            }
            if (file.renameTo(fileResult)) {
                amount++;
            }
        }
    }

    private String replaceText(Path pathFile) {  // Замена
        return pathFile.getFileName().toString().replace(textSearch, textReplace);
    }

    private String insertText(Path pathFile) { // Добавление текста в заданную позицию
        int lengthNameFile = pathFile.getFileName().toString().length();
        if (numberInsert >= lengthNameFile) {
            return new StringBuilder(pathFile.getFileName().toString()).insert(lengthNameFile, textAdd).toString();
        }
        return new StringBuilder(pathFile.getFileName().toString()).insert(numberInsert, textAdd).toString();
    }

    public void infoProcess() {
        consoleStringBuilder.append("Переименование успешно выполнено!\n");
        consoleStringBuilder.append("Переименовано ").append(countProcessedDir).append("/").append(countDir).append(" директорий\n");
        consoleStringBuilder.append("Переименовано ").append(countProcessedFiles).append("/").append(countFiles).append(" файлов\n");
        //consoleStringBuilder.append("Общий размер директории - ").append(sizeDir / 1000).append(" килобайт\n");
    }
}