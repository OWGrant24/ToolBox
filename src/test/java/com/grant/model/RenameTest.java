package com.grant.model;

import com.grant.exception.ToolException;
import com.grant.util.Functions;
import com.grant.util.UtilRenamer;
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
    protected UtilRenamer utilRenamer;

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
        utilRenamer = new UtilRenamer();
        utilRenamer.setMaxDepth(100);
        utilRenamer.setTextSearch("666");
        utilRenamer.setTextReplace("555");
        utilRenamer.setPath(pathTempDir);
    }

    @Test
    void startTaskRename() throws ToolException {
        utilRenamer.startTask(Functions.REPLACE);
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