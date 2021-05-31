package com.grant;

import com.grant.view.View;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        View view = new View("ToolBox v0.7");
        SwingUtilities.invokeLater(view::init);
        Controller controller = new Controller(view);
        controller.initController();
    }
}
