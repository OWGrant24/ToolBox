package com.grant;

import com.grant.model.Model;
import com.grant.view.View;

import static com.grant.OutputWindow.consoleStringBuilder;

public class HelperOutView { // Логирует свои действия в консоль
    private final View view;
    private final Model model;

    public HelperOutView(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public void logInConsole() {
        view.getTextAreaConsole().setText(consoleStringBuilder.toString());
    }

    public void outputTextFieldPath() {
        view.getTextFieldPath().setText(model.getPath().toString());
    }

}
