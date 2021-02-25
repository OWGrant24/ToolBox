package com.grant.model;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class RenameTest {
    protected Path pathTempDir;
    protected Model model;

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
        model = new Model();
        model.setTextSearch("666");
        model.setTextReplace("555");
        model.setPath(pathTempDir);
    }

    @Test
    void startTaskRename() {
        model.startTask(Functions.REPLACE);
    }

    @AfterEach
    void afterEach() {
//        try {
//            FileUtils.deleteDirectory(pathTempDir.toFile());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}