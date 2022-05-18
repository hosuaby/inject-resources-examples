package io.hosuaby.inject.resources.examples.junit5.domain;

import java.util.Date;
import java.util.Objects;

public class YamlLog {
    private Date date;
    private LogSeverity severity;
    private String msg;

    public YamlLog() {
    }

    public YamlLog(Date date, LogSeverity severity, String msg) {
        this.date = date;
        this.severity = severity;
        this.msg = msg;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        YamlLog another = (YamlLog) other;
        return Objects.equals(date, another.date)
                && severity == another.severity
                && Objects.equals(msg, another.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, severity, msg);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LogSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(LogSeverity severity) {
        this.severity = severity;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
