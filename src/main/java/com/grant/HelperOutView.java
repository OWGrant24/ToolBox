package com.grant;

public class HelperOutView { // Логирует свои действия в консоль
    private final View view;
    private final Model model;

    public HelperOutView(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public void logInConsole() {
        view.getTextAreaConsole().setText(model.getConsoleStringBuilder().toString());
    }

    public void outputTextFieldPath() {
        view.getTextFieldPath().setText(model.getPath().toString());
    }

}
