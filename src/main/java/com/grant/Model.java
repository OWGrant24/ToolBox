package com.grant;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Model { //12/01/21
    private Path path;
    private final StringBuilder consoleStringBuilder;
    private final List<Path> list;
    private final WorkingHours workingHours;
    private String textSearch;
    private String textReplace;
    private String textAdd;
    private int countFiles = 0;
    private int countProcessedFiles = 0;
    private int countDir = 0;
    private int countProcessedDir = 0;

    //private long sizeDir = 0;

    // Constructor
    public Model() {
        path = Path.of(""); // путь до папки , в которой будет производить переименование
        workingHours = new WorkingHours(this); // Время процесса переименования
        list = new ArrayList<>(); // Список файлов, подходяций под заданные критерии
        consoleStringBuilder = new StringBuilder(); // Лог работы
        // "Добро пожаловать в программу пакетного переименования файлов и папок\n"
    }

    // Getters and Setters

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public StringBuilder getConsoleStringBuilder() {
        return consoleStringBuilder;
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

    // Methods
    public void cancelSelection() {
        path = Path.of("");
        consoleStringBuilder.append("Отмена выбора произведена\n");
    }

    public void clearCountFiles() {
        countFiles = 0;
        countProcessedFiles = 0;
        countDir = 0;
        countProcessedDir = 0;
    }

    public void processing() {
        try {
            if (checkingErrorReplace()) {
                workingHours.startTime();
                if (addInListFiles()) {
                    rename();
                    infoProcess();
                    workingHours.stopTime();
                    workingHours.durationTime();
                } else {
                    consoleStringBuilder.append("Файлов/директорий с соответсвующими параметрами не было найдено\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearCountFiles(); // Очистка переменных от данных о кол-ве директорий и файлов
        }
    }

    public void processingAdd() {
        try {
            if (checkingErrorAdd()) {
                workingHours.startTime();
                if (addInListFilesForAdd()) {
                    renameAdd();
                    infoProcess();
                    workingHours.stopTime();
                    workingHours.durationTime();
                } else {
                    consoleStringBuilder.append("Файлов/директорий по пути не найдено\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clearCountFiles(); // Очистка переменных от данных о кол-ве директорий и файлов
        }
    }

    public boolean checkingErrorReplace() { // Проверка на ошибки
        if (path == null || path.toString().isEmpty()) {
            consoleStringBuilder.append("Директория не выбрана\n");
            return false;
        } else if (textSearch.isEmpty()) {
            consoleStringBuilder.append("Строка \"Найти\" пустая \n");
            return false;
        } else if (textSearch.equals(textReplace)) {
            consoleStringBuilder.append("В строках \"Найти\" и \"Заменить\" одинаковые значения\n");
            return false;
        }
        return true;
    }

    public boolean checkingErrorAdd() { // Проверка на ошибки в режиме добавления
        if (path == null || path.toString().isEmpty()) {
            consoleStringBuilder.append("Директория не выбрана\n");
            return false;

        } else if (textAdd.isEmpty()) {
            consoleStringBuilder.append("Строка \"Добавить\" пустая \n");
            return false;
        }
        return true;
    }

    public boolean addInListFiles() { // Добавление в список файлов, которые содержат заданный текст в названии файла
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    if (!dir.toAbsolutePath().toString().equals(path.toAbsolutePath().toString())) {
                        countDir++;
                        if (dir.getFileName().toString().contains(textSearch)) {
                            list.add(dir.toAbsolutePath());
                            countProcessedDir++;
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    countFiles++;
                    //sizeDir += attrs.size();
                    if (file.getFileName().toString().contains(textSearch)) {
                        list.add(file.toAbsolutePath());
                        countProcessedFiles++;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Это проверка
            System.out.println(list);
        }
        return countProcessedDir + countProcessedFiles > 0;
    }

    private boolean addInListFilesForAdd() {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    if (!dir.toAbsolutePath().toString().equals(path.toAbsolutePath().toString())) {
                        countDir++;
                        list.add(dir.toAbsolutePath());
                        countProcessedDir++;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    countFiles++;
                    //sizeDir += attrs.size();
                    list.add(file.toAbsolutePath());
                    countProcessedFiles++;
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Это проверка
            System.out.println(list);
        }
        return countProcessedDir + countProcessedFiles > 0;
    }

    public void rename() {
        for (int i = 0; i < list.size(); i++) {
            File file = list.get(i).toFile();
            String newFileName = replaceText(list.get(i));
            File fileResult = new File(list.get(i).getParent() + "/" + newFileName);
            file.renameTo(fileResult);
        }
        list.clear();
    }

    private void renameAdd() {
        for (int i = 0; i < list.size(); i++) {
            File file = list.get(i).toFile();
            String newFileName = insertText(list.get(i));
            File fileResult;
            if (newFileName.startsWith(" ")) {
                fileResult = new File(list.get(i).getParent() + "/" + newFileName.replaceFirst("\\s+", ""));
            } else {
                fileResult = new File(list.get(i).getParent() + "/" + newFileName);
            }
            file.renameTo(fileResult);
        }
        list.clear();
    }

    public String replaceText(Path pathFile) {  // Замена
        return pathFile.getFileName().toString().replace(textSearch, textReplace);
    }

    private String insertText(Path pathFile) {
        return new StringBuilder(pathFile.getFileName().toString()).insert(0, textAdd).toString();
    }

    public void infoProcess() {
        consoleStringBuilder.append("Переименование успешно выполнено!\n");
        consoleStringBuilder.append("Переименовано ").append(countProcessedDir).append("/").append(countDir).append(" директорий\n");
        consoleStringBuilder.append("Переименовано ").append(countProcessedFiles).append("/").append(countFiles).append(" файлов\n");
        //consoleStringBuilder.append("Общий размер директории - ").append(sizeDir / 1000).append(" килобайт\n");
    }
}