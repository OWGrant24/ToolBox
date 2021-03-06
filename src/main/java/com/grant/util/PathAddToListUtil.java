package com.grant.util;

import com.grant.exception.ToolException;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.grant.Color.ANSI_GREEN;
import static com.grant.Color.ANSI_RESET;
import static com.grant.OutputWindow.consoleStringBuilder;

@Getter
@Setter
public class PathAddToListUtil {
    private Path path;
    private final List<Path> paths;
    private long countFiles;
    private long countDir;
    private int maxDepth;

    public PathAddToListUtil() {
        path = Path.of("");                        // путь до папки , в которой будет проходить переименование
        paths = new ArrayList<>();                      // Список файлов и директорий
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

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
            paths.removeIf(path1 -> path1.toString().equals(path.toString()));
            if (paths.isEmpty()) {
                System.out.println(paths);
                System.out.println(ANSI_GREEN + "Список файлов/директорий пуст" + ANSI_RESET);
                return false;
            }
            countingTheNumberOfDirectoriesAndFiles();
        } catch (Exception e) {
            throw new ToolException(e);
        }
        return true;
    }

    public void cancelSelection() {
        path = Path.of("");
        consoleStringBuilder.append("Отмена выбора произведена\n");
    }

    public void clearList() {
        paths.clear();
        countDir = 0;
        countFiles = 0;
    }

    public void printFiles() {
        clearList();
        try {
            addInListFiles();
        } catch (ToolException e) {
            e.printStackTrace();
        }
        countingTheNumberOfDirectoriesAndFiles();
        if (countFiles > 0) {
            consoleStringBuilder.append("Список файлов\n");
            paths.stream()
                    .filter(path -> path.toFile().isFile())
                    .forEach(p -> consoleStringBuilder.append(p).append("\n")
                    );
        }
        if (countDir > 0) {
            consoleStringBuilder.append("Список директорий\n");
            paths.stream()
                    .filter(path -> path.toFile().isDirectory())
                    .forEach(p -> consoleStringBuilder.append(p).append("\n"));
        }
    }

    // Записываем кол-во директорий / файлов в списке
    private void countingTheNumberOfDirectoriesAndFiles() {
        countFiles = paths.stream().filter(path -> path.toFile().isFile()).count();
        countDir = paths.stream().filter(path -> path.toFile().isDirectory()).count();
    }
}
