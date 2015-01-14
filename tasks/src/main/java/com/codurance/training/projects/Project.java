package com.codurance.training.projects;

import java.util.ArrayList;

import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.TaskMultiple;

public class Project 
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
}
