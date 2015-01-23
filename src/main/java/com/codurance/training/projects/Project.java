/*
 * 
 */
package com.codurance.training.projects;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.TaskMultiple;


/**
 *  La classe Project regroupe des tâches et implémente l'interface Observer, elle est notifiée des changements de tâches.
 *
 * @author Jean-Sebastien TRILLE & Antoine RIVALIER
 */
public class Project implements Observer 
{
	
	/** Le nom du projet. */
	private final String name;
	
	/** La liste de tâches non finies. */
	private ArrayList<TaskMultiple> listTask;
	
	/** La liste de tâches finies. */
	private ArrayList<TaskMultiple> listTaskDone;
	
	
	/**
	 * Constructeur du projet.
	 *
	 * @param pName - String
	 */
	public Project(String pName) 
	{
		this.name = pName;
		this.listTask = new ArrayList<TaskMultiple>();
		this.listTaskDone = new ArrayList<TaskMultiple>();
	}

	
	/**
	 * Retourne le nom du projet.
	 *
	 * @return  name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Retourne la liste des tâches non finies.
	 *
	 * @return  listTask
	 */
	public ArrayList<TaskMultiple> getListTask()
	{
		return listTask;
	}
	
	/**
	 * Retourne la liste des tâches finies.
	 *
	 * @return listTaskDone
	 */
	public ArrayList<TaskMultiple> getListTaskDone()
	{
		return listTaskDone;
	}
	
	/**
	 * Ajoute une tâche.
	 *
	 * @param pTask - taskMultiple
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
