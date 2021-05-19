package com.grant;

import com.grant.util.UtilRenamer;
import com.grant.view.View;

import static com.grant.OutputWindow.consoleStringBuilder;

public class HelperOutView { // Логирует свои действия в консоль
    private final View view;
    private final UtilRenamer utilRenamer;

    public HelperOutView(View view, UtilRenamer utilRenamer) {
        this.view = view;
        this.utilRenamer = utilRenamer;
    }

    public void logInConsole() {
        view.getTextAreaConsole().setText(consoleStringBuilder.toString());
    }

    public void outputTextFieldPath() {
        view.getTextFieldPath().setText(utilRenamer.getPath().toString());
    }

}
