package com.grant;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
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
