package com.codurance.training.projects;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.TaskMultiple;


/** La classe Project regroupe des tâches et implémente l'interface Observer, elle est notifiée des changements de tâches
 * @author Jean-Sebastien TRILLE & Antoine RIVALIER
 *
 */
public class Project implements Observer 
{
	
	/**
	 * 
	 */
	private final String name;
	/**
	 * 
	 */
	private ArrayList<TaskMultiple> listTask;
	/**
	 * 
	 */
	private ArrayList<TaskMultiple> listTaskDone;
	
	
	/**
	 * @param pName
	 */
	public Project(String pName) 
	{
		this.name = pName;
		this.listTask = new ArrayList<TaskMultiple>();
		this.listTaskDone = new ArrayList<TaskMultiple>();
	}

	
	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return
	 */
	public ArrayList<TaskMultiple> getListTask()
	{
		return listTask;
	}
	
	/**
	 * @return
	 */
	public ArrayList<TaskMultiple> getListTaskDone()
	{
		return listTaskDone;
	}
	
	/**
	 * @param pTask
	 */
	public void addTask(TaskMultiple pTask)
	{
		pTask.addObserver(this);
		if(pTask.isDone()){
			listTaskDone.add(pTask);
		}else{
			listTask.add(pTask);
		}
		
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obs, Object obj) {
		if(obs instanceof Task){
			if(((TaskMultiple) obs).isDone()){
				listTask.remove(obs);
				listTaskDone.add((TaskMultiple) obs);
			}else{
				listTaskDone.remove(obs);
				listTask.add((TaskMultiple) obs);
			}
		}
		
	}
}
