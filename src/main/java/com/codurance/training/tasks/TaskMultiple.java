package com.codurance.training.tasks;

import java.util.ArrayList;
import java.util.Observable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

public class TaskMultiple extends Task{
	
	private ArrayList<Task> sousTaches;

	public TaskMultiple(long id, String description, boolean done) {
		this(id, description, done,null);		
	}
	
	public TaskMultiple(long id, String description, boolean done, DateTime pDeadLine) {
		super(id, description, done,pDeadLine);
		sousTaches = new ArrayList<Task>();
	}

	public void addTask(Task pTask) throws Exception{
		
		if(this.sousTaches.contains(pTask))
			throw new Exception("Tache d�j� pr�sente");
		
		else if(this.getDeadLine()==null){
			
			if(pTask.getDeadLine()==null)
				
				this.sousTaches.add(pTask);
			
			else{
				
				this.sousTaches.add(pTask);
				this.setDeadLine(pTask.getDeadLine());
				
			}	
		}else{
			
			if(pTask.getDeadLine()==null)				
				this.sousTaches.add(pTask);
			
			else{
				switch(DateTimeComparator.getInstance().compare(this.getDeadLine(), pTask.getDeadLine())){
				case -1:
					this.sousTaches.add(pTask);
					this.setDeadLine(pTask.getDeadLine());
					break;
				case 0:
					this.sousTaches.add(pTask);
					break;
				case 1:
					this.sousTaches.add(pTask);
					
					break;					
					
				}
						
			}
			
			
		}
		
	}
	
	@Override
	public void update(Observable obs, Object obj) {
		if(obs instanceof Task){
			if(DateTimeComparator.getInstance().compare(this.getDeadLine(), ((Task) obs).getDeadLine())==-1){
				this.setDeadLine(((Task) obs).getDeadLine());
				
			}
		}
		
	}
	
}
