package com.grant.model;

import com.grant.exception.ToolException;
import com.grant.util.PathAddToListUtil;
import com.grant.util.Functions;
import com.grant.util.RenameUtilImpl;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class RenameTest {
    protected Path pathTempDir;
    protected RenameUtilImpl utilRenamerImpl;
    protected PathAddToListUtil pathAddToListUtil;

    private void createFile(Path path) {
        try {
            for (int i = 0; i < 5; i++) {
                Path pathFile = Path.of(path.toString() + "/666File" + i + ".txt");
                Files.createFile(pathFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Path> createDir(Path path) {
        List<Path> list = new ArrayList<>();
        try {
            for (int i = 0; i < 5; i++) {
                Path pathFile = Path.of(path.toString() + "/666Dir" + i);
                Files.createDirectory(pathFile);
                list.add(pathFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<String> addFiles(Path path) {
        List<String> paths = new ArrayList<>();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    paths.add(dir.getFileName().toString());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    paths.add(file.getFileName().toString());
                    return FileVisitResult.CONTINUE;
                }
            });
            paths.removeIf(path1 -> path1.equals(path.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }

    @BeforeEach
    void setUp() {
        try {
            pathTempDir = Files.createTempDirectory("TestToolBox"); // Создание тестовой директории
            System.out.println(pathTempDir.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        createFile(pathTempDir);
        List<Path> listPath = createDir(pathTempDir);
        listPath.forEach(this::createFile);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        utilRenamerImpl = new RenameUtilImpl(pathAddToListUtil);
        pathAddToListUtil.setMaxDepth(100);
        utilRenamerImpl.setTextSearch("666");
        utilRenamerImpl.setTextReplace("555");
        pathAddToListUtil.setPath(pathTempDir);
        utilRenamerImpl.setUseExtension(true);
    }

    @Test
    void startTaskRename() throws ToolException {
        List<String> pathsOrig = addFiles(pathTempDir);
        utilRenamerImpl.startRename(Functions.REPLACE);
        List<String> pathsDone = addFiles(pathTempDir).stream()
                .map(s -> s.replace("555", "666"))
                .collect(Collectors.toList());
        Assertions.assertLinesMatch(pathsOrig, pathsDone);
    }

    @AfterEach
    void afterEach() {
        // Подчищаем хвосты в папке Temp
        try {
            FileUtils.deleteDirectory(pathTempDir.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}