package ua.com.alevel.logs.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.com.alevel.logs.LogLevel;
import ua.com.alevel.logs.LogService;

@Component("loggerService")
public class LogServiceImpl implements LogService {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger(LogLevel.INFO.getLevel());
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger(LogLevel.WARN.getLevel());
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger(LogLevel.ERROR.getLevel());

    @Override
    public void log(LogLevel level, String message) {
        switch (level) {
            case INFO -> LOGGER_INFO.info(message);
            case WARN -> LOGGER_WARN.warn(message);
            case ERROR -> LOGGER_ERROR.error(message);
        }
    }
}