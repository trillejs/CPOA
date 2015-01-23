package com.codurance.training.tasks;
import java.util.Observable;
import java.util.Observer;

import org.joda.time.DateTime;


public abstract class Task extends Observable implements Observer  {
    private final long id;
    private final String description;
    private boolean done;
    private final DateTime date;
    private DateTime deadLine;

    public Task(long id, String description, boolean done) {
    	this(id, description, done,null);
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
		setChanged();
		notifyObservers(this.done);
    }
    
    public void setDeadLine(DateTime pDeadLine){
    	this.deadLine=pDeadLine;
    }
    

    
}
