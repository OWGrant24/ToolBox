package com.grant.util;

import com.grant.exception.ToolException;

public interface UtilRenamer {

    void resetTask();

    void startTask(Functions func) throws ToolException;

}
