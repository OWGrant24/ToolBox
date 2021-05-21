package com.grant;

import com.grant.util.UtilRenamerImpl;
import com.grant.view.View;

import static com.grant.OutputWindow.consoleStringBuilder;

public class HelperOutView { // Логирует свои действия в консоль
    private final View view;
    private final UtilRenamerImpl utilRenamerImpl;

    public HelperOutView(View view, UtilRenamerImpl utilRenamerImpl) {
        this.view = view;
        this.utilRenamerImpl = utilRenamerImpl;
    }

    public void logInConsole() {
        view.getTextAreaConsole().setText(consoleStringBuilder.toString());
    }

    public void outputTextFieldPath() {
        view.getTextFieldPath().setText(utilRenamerImpl.getPath().toString());
    }

}
