package com.codurance.training.tasks;
import java.util.Observable;
import java.util.Observer;

import org.joda.time.DateTime;

/** Classe abstraite Task, implémente Observer et observable pour que des tâches puissent en regarder d'autres, 
 * utile pour des tâches multiples
 * @author Jean-Sebastien TRILLE & Antoine RIVALIER
 *
 */

public abstract class Task extends Observable implements Observer  {
	
    /**
     * ID de la tâche
     */
    private final long id;
    
    /**
     * Description de la tâche
     */
    private final String description;
    
    /**
     * Etat de la tâche, true si finie, false si non-finie
     */
    private boolean done;
    
    /**
     * Date de création de la tâche
     */
    private final DateTime date;
    
    /**
     * Date limite de la tâche
     */
    private DateTime deadLine;

    /** Constructeur d'une tâche sans deadLine
     * @param id
     * @param description
     * @param done
     */
    public Task(long id, String description, boolean done) {
    	this(id, description, done,null);
    }
    
    /** Constructeur d'une tâche avec deadLine
     * @param id
     * @param description
     * @param done
     * @param pDeadLine
     */
    public Task(long id, String description, boolean done, DateTime pDeadLine) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.date = new DateTime();
        this.deadLine = pDeadLine;
    }

    /**Accesseur de l'ID de la tâche
     * @return id - Long
     */
    public long getId() {
        return id;
    }

    /**Accesseur de la description de la tâche
     * @return description - String
     */
    public String getDescription() {
        return description;
    }

    /** Accesseur de l'état de la tâche
     * @return done - Boolean
     */
    public boolean isDone() {
        return done;
    }
    
    /**Accesseur de la date de la tâche
     * @return date - DateTime
     */
    public DateTime getDate(){
    	return date;
    }
    
    /** Accesseur de la deadLine de la tâche
     * @return deadLine - DateTime
     */
    public DateTime getDeadLine(){
    	return deadLine;
    }

    /** Setter de l'état de la tâche
     * Notifie les observateurs (Project) d'un changement d'état
     * @param done - Boolean
     */
    public void setDone(boolean done) {
        this.done = done;
		setChanged();
		notifyObservers(this.done);
    }
    
    /** Setter de la deadLine
     * Notifie les observateurs (TaskMultiple) d'un changement de deadline
     * @param pDeadLine
     */
    public void setDeadLine(DateTime pDeadLine){
    	this.deadLine=pDeadLine;
    	setChanged();
		notifyObservers(this.deadLine);
    }
    
    /**Setter de la deadLine sans notification, évite les doublons.
     * @param pDeadLine
     */
    public void setDeadLineNoObserver(DateTime pDeadLine){
    	this.deadLine=pDeadLine;
    }
    

    
}
