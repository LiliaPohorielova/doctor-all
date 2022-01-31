package ua.com.alevel.logs;

public enum LogLevel {

    INFO("info"),
    WARN("warn"),
    ERROR("error");

    LogLevel(String level) {
        this.level = level;
    }

    private final String level;

    public String getLevel() {
        return level;
    }
}
