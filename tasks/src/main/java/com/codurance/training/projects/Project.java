package com.codurance.training.projects;

import java.util.ArrayList;

import com.codurance.training.tasks.Task;

public class Project 
{
	private final String name;
	private ArrayList<Task> listTask;
	private ArrayList<Task> listTaskDone;
	
	public Project(String pName) 
	{
		this.name = pName;
		this.listTask = new ArrayList<Task>();
		this.listTaskDone = new ArrayList<Task>();
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Task> getListTask()
	{
		return listTask;
	}
	
	public ArrayList<Task> getListTaskDone()
	{
		return listTaskDone;
	}
}
