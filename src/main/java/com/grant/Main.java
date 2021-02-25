package com.grant;

import com.grant.model.Model;
import com.grant.view.View;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View("Пакетное переименование файлов v0.6");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                view.init();
            }
        });
        Controller controller = new Controller(view, model);
        controller.initController();

    }
}
