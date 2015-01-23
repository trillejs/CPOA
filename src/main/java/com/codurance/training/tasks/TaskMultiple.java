package com.codurance.training.tasks;

import java.util.ArrayList;
import java.util.Observable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

/** La classe TaskMultiple étend Task, elle ajoute une liste de sous-tâches, en fait des TaskMultiple, à cette Task.
 * Cette classe a des observateurs pour chaque TaskMultiple qu'elle a dans son ArrayList de sous-tâches.
 * @author Jean-Sebastien TRILLE & Antoine RIVALIER
 *
 */
public class TaskMultiple extends Task{

	/**
	 * ArrayList de TaskMultiple
	 */
	private ArrayList<TaskMultiple> sousTaches;

	/** Constructeur sans DeadLine
	 * @param id
	 * @param description
	 * @param done
	 */
	public TaskMultiple(long id, String description, boolean done) {
		this(id, description, done,null);		
	}

	/** Constructeur avec DeadLine, renvoie vers le constructeur de Task
	 * @param id
	 * @param description
	 * @param done
	 * @param pDeadLine
	 */
	public TaskMultiple(long id, String description, boolean done, DateTime pDeadLine) {
		super(id, description, done,pDeadLine);
		sousTaches = new ArrayList<TaskMultiple>();
	}

	/** Méthode pour ajouter une TaskMultiple à notre TaskMultiple, vérifie la deadLine pour changer en conséquence si besoin.
	 * Ajoute les observateurs à la sous-tâche
	 * @param pTask
	 * @throws Exception
	 */
	public void addTask(TaskMultiple pTask) throws Exception{

		if(this.sousTaches.contains(pTask))
			throw new Exception("Tache déjà présente");

		else if(this.getDeadLine() == null){

			pTask.addObserver(this);
			if(pTask.getDeadLine() == null)

				this.sousTaches.add(pTask);

			else{

				this.sousTaches.add(pTask);
				this.setDeadLine(pTask.getDeadLine());

			}	
		}else{

			pTask.addObserver(this);
			if(pTask.getDeadLine() == null)				
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

	/**Accesseur de la liste de sous-tâches
	 * @return sousTaches - ArrayList<TaskMultiple>
	 */
	public ArrayList<TaskMultiple> getSousTaches() {
		return this.sousTaches;
	}

	@Override
	public void update(Observable obs, Object obj) {
		if(obs instanceof Task){
			if(this.getDeadLine() == null){
				this.setDeadLineNoObserver(((Task) obs).getDeadLine());
			}
			else if(DateTimeComparator.getInstance().compare(this.getDeadLine(), ((Task) obs).getDeadLine()) == -1){
				this.setDeadLineNoObserver(((Task) obs).getDeadLine());
			}
		}

	}

}
