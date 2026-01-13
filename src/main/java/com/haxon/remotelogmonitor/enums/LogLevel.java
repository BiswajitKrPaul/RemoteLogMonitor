package com.haxon.remotelogmonitor.enums;

import lombok.Getter;

@Getter
public enum LogLevel {
    TRACE(1), DEBUG(2), INFO(3), WARN(4), ERROR(5), FATAL(6);

    private final int value;

    LogLevel(int value) {
        this.value = value;
    }
}
