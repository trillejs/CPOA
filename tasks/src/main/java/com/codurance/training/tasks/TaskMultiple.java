package com.codurance.training.tasks;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class TaskMultiple extends Task{
	
	private ArrayList<Task> sousTaches;

	public TaskMultiple(long id, String description, boolean done) {
		this(id, description, done,null);		
	}
	
	public TaskMultiple(long id, String description, boolean done, DateTime pDeadLine) {
		super(id, description, done,pDeadLine);
		sousTaches = new ArrayList<Task>();
	}

	public void addTask(Task pTask){
		
	}
	
}
