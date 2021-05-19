package com.grant;

import com.grant.util.UtilRenamer;
import com.grant.view.View;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        UtilRenamer utilRenamer = new UtilRenamer();
        View view = new View("Пакетное переименование файлов v0.6");
        SwingUtilities.invokeLater(view::init);
        Controller controller = new Controller(view, utilRenamer);
        controller.initController();
    }
}
