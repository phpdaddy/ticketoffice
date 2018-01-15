package com.phpdaddy.ticketoffice;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Ticket {
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    private Integer priority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
