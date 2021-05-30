package com.grant;

import com.grant.util.PathAddToListUtil;
import com.grant.view.View;

import static com.grant.OutputWindow.consoleStringBuilder;

public class HelperOutView { // Логирует свои действия в консоль
    private final View view;
    private final PathAddToListUtil pathAddToListUtil;

    public HelperOutView(View view, PathAddToListUtil pathAddToListUtil) {
        this.view = view;
        this.pathAddToListUtil = pathAddToListUtil;
    }

    public void logInConsole() {
        view.getTextAreaConsole().setText(consoleStringBuilder.toString());
    }

    public void outputTextFieldPath() {
        view.getTextFieldPath().setText(pathAddToListUtil.getPath().toString());
    }

}
