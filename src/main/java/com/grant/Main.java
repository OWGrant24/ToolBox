package com.grant;

import com.grant.util.UtilRenamerImpl;
import com.grant.view.View;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        UtilRenamerImpl utilRenamerImpl = new UtilRenamerImpl();
        View view = new View("Пакетное переименование файлов v0.6");
        SwingUtilities.invokeLater(view::init);
        Controller controller = new Controller(view, utilRenamerImpl);
        controller.initController();
    }
}
