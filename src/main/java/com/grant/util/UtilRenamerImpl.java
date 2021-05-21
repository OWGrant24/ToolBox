package com.grant.util;

import com.grant.exception.ToolException;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import static com.grant.Color.ANSI_GREEN;
import static com.grant.Color.ANSI_RESET;
import static com.grant.OutputWindow.consoleStringBuilder;

@Getter
@Setter
public class UtilRenamerImpl implements UtilRenamer { //12/01/21
    private Path path;
    private final List<Path> paths;
    private String textSearch;
    private String textReplace;
    private String textAdd;
    private long countFiles;
    private long countProcessedFiles;
    private long countDir;
    private long countProcessedDir;
    private int numberInsert;
    private int maxDepth;

    // Constructor
    public UtilRenamerImpl() {
        path = Path.of("");                        // путь до папки , в которой будет проходить переименование
        paths = new ArrayList<>();                      // Список файлов и директорий
    }

    // Methods
    public void cancelSelection() {
        path = Path.of("");
        consoleStringBuilder.append("Отмена выбора произведена\n");
    }

    @Override
    public void resetTask() {
        paths.clear();
        countFiles = 0;
        countProcessedFiles = 0;
        countDir = 0;
        countProcessedDir = 0;
    }

    @Override
    public void startTask(Functions func) throws ToolException {
        checkingError(func);
        resetTask();
        if (addInListFiles()) {
            rename(func);
        }
        if (countProcessedDir + countProcessedFiles > 0) {
            infoProcess();
        } else {
            consoleStringBuilder.append("Нет файлов/директорий подходящих под критерии\n");
        }

    }

    private void checkingError(Functions func) throws ToolException { // Проверка на ошибки
        if (path == null || path.toString().isBlank()) {

            throw new ToolException("Директория не выбрана\n");
        }
        switch (func) {
            case REPLACE:
                if (textSearch.isBlank()) {
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

    public boolean addInListFiles() throws ToolException {
        try {
            Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), maxDepth, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    paths.add(dir.toAbsolutePath());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    paths.add(file.toAbsolutePath());
                    return FileVisitResult.CONTINUE;
                }
            });
            paths.removeIf(path1 -> path1.toString().equals(path.toString()));
            if (paths.isEmpty()) {
                System.out.println(ANSI_GREEN + "Список файлов/директорий пуст" + ANSI_RESET);
                return false;
            }
            // Записываем кол-во директорий / файлов в списке
            countFiles = paths.stream().filter(path -> path.toFile().isFile()).count();
            countDir = paths.stream().filter(path -> path.toFile().isDirectory()).count();
        } catch (Exception e) {
            throw new ToolException(e);
        } finally {
            // Это проверка
            System.out.println(ANSI_GREEN + "Список файлов :" + ANSI_RESET);
            paths.stream().filter(path -> path.toFile().isFile()).forEach(System.out::println);
            System.out.println(ANSI_GREEN + "Список директорий :" + ANSI_RESET);
            paths.stream().filter(path -> path.toFile().isDirectory()).forEach(System.out::println);
        }
        return true;
    }

    public void rename(Functions func) {
        switch (func) {
            case REPLACE:
                renameObj(paths);
                break;
            case ADD:
                renameObjAdd(paths);
                break;
        }
    }

    private void renameObj(Collection<Path> paths) {
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

    private void renameObjAdd(Collection<Path> paths) {
        for (Path value : paths) {
            File file = new File(value.toString());
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
        consoleStringBuilder
                .append("Переименование успешно выполнено!\n");
        consoleStringBuilder
                .append("Переименовано ")
                .append(countProcessedDir)
                .append("/")
                .append(countDir)
                .append(" директорий\n");
        consoleStringBuilder.append("Переименовано ")
                .append(countProcessedFiles)
                .append("/")
                .append(countFiles)
                .append(" файлов\n");
        //consoleStringBuilder.append("Общий размер директории - ").append(sizeDir / 1000).append(" килобайт\n");
    }
}