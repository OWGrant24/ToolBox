package com.grant.util;

import com.grant.exception.ToolException;

public interface RenameUtil {
    void resetRename();

    void startRename(Functions func) throws ToolException;

}
