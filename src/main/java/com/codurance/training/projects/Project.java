package com.codurance.training.projects;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.TaskMultiple;

public class Project implements Observer 
{
	private final String name;
	private ArrayList<TaskMultiple> listTask;
	private ArrayList<TaskMultiple> listTaskDone;
	
	public Project(String pName) 
	{
		this.name = pName;
		this.listTask = new ArrayList<TaskMultiple>();
		this.listTaskDone = new ArrayList<TaskMultiple>();
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<TaskMultiple> getListTask()
	{
		return listTask;
	}
	
	public ArrayList<TaskMultiple> getListTaskDone()
	{
		return listTaskDone;
	}

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
