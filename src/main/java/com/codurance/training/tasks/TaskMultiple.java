package com.codurance.training.tasks;

import java.util.ArrayList;
import java.util.Observable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

/**
 * @author Jean-Sebastien TRILLE & Antoine RIVALIER
 *
 */
public class TaskMultiple extends Task{
	
	private ArrayList<TaskMultiple> sousTaches;

	public TaskMultiple(long id, String description, boolean done) {
		this(id, description, done,null);		
	}
	
	public TaskMultiple(long id, String description, boolean done, DateTime pDeadLine) {
		super(id, description, done,pDeadLine);
		sousTaches = new ArrayList<TaskMultiple>();
	}

	public void addTask(TaskMultiple pTask) throws Exception{
		
		if(this.sousTaches.contains(pTask))
			throw new Exception("Tache déjà présente");
		
		else if(this.getDeadLine()==null){
			
			pTask.addObserver(this);
			if(pTask.getDeadLine()==null)
				
				this.sousTaches.add(pTask);
			
			else{
				
				this.sousTaches.add(pTask);
				this.setDeadLine(pTask.getDeadLine());
				
			}	
		}else{
			
			pTask.addObserver(this);
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
	
	public ArrayList<TaskMultiple> getSousTaches() {
		return this.sousTaches;
	}
	
	@Override
	public void update(Observable obs, Object obj) {
		if(obs instanceof Task){
			if(this.getDeadLine()==null){
				this.setDeadLineNoObserver(((Task) obs).getDeadLine());
			}
			else if(DateTimeComparator.getInstance().compare(this.getDeadLine(), ((Task) obs).getDeadLine())==-1){
				this.setDeadLineNoObserver(((Task) obs).getDeadLine());
			}
		}
		
	}
	
}
