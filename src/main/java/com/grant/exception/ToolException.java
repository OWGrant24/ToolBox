package com.grant.exception;

import static com.grant.OutputWindow.consoleStringBuilder;

public class ToolException extends RuntimeException {

    public ToolException(String message) {
        super(message);
        consoleStringBuilder.append(message);
    }

    public ToolException(Exception e) {
        super(e);
        consoleStringBuilder.append(e.getMessage());
    }

}
