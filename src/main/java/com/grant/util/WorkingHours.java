package com.grant.util;

import static com.grant.OutputWindow.consoleStringBuilder;

public class WorkingHours { // Класс будет отвечать за расчёт время работы
    private long startTime;
    private long endTime;

    public void startTime() {
        startTime = System.nanoTime();
    }

    public void stopTime() {
        endTime = System.nanoTime();
    }

    public void durationTime() {
        long duration = (endTime - startTime);
        if (duration < 1_000_000_000) {
            consoleStringBuilder.append("Процесс переименования занял: ").append(duration / 1_000_000).append(" мс.\n");
        } else {
            consoleStringBuilder.append("Процесс переименования занял: ").append(duration / 1_000_000_000).append(" с.\n");
        }

    }
}
