package com.codurance.training.tasks;
import java.util.Date;

import org.joda.time.DateTime;


public final class Task {
    private final long id;
    private final String description;
    private boolean done;
    private final DateTime date;
    private final DateTime deadLine;

    public Task(long id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.date = new DateTime();
        this.deadLine = null;
    }
    
    public Task(long id, String description, boolean done, DateTime pDeadLine) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.date = new DateTime();
        this.deadLine = pDeadLine;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }
    
    public DateTime getDate(){
    	return date;
    }
    
    public DateTime getDeadLine(){
    	return deadLine;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    
}
