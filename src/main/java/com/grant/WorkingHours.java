package com.grant;

public class WorkingHours { // Класс будет отвечать за расчёт время работы
    private final Model model;
    private long startTime;
    private long endTime;

    public WorkingHours(Model model) {
        this.model = model;
    }

    public void startTime() {
        startTime = System.nanoTime();
    }

    public void stopTime() {
        endTime = System.nanoTime();
    }

    public void durationTime() {
        long duration = (endTime - startTime);
        if (duration < 1_000_000_000) {
            model.getConsoleStringBuilder().append("Процесс переименования занял: ").append(duration / 1_000_000).append(" мс.\n");
        } else {
            model.getConsoleStringBuilder().append("Процесс переименования занял: ").append(duration / 1_000_000_000).append(" с.\n");
        }

    }
}
