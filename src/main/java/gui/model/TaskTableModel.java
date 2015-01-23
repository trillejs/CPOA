package gui.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.TaskMultiple;
import com.sun.xml.internal.ws.util.StringUtils;

import com.codurance.training.projects.*;
import com.codurance.training.tasks.*;

/**
 * @author Andr� PENINOU
 * @author Fabrice PELLEAU
 */

public class TaskTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<Task> tasklist = null;

	/**
	 * Constructeur.
	 */ 
	public TaskTableModel() {
		this.tasklist = new ArrayList<>();
	}
	
	// =======================================================================
	// Surcharges des m�thodes abstraites de la classe AbstractTableModel
	// =======================================================================
	
	public int getColumnCount() {
		return 4;
	}

	public int getRowCount() {
		int nbRow = 0;
		if (this.tasklist!=null) {
			nbRow = this.tasklist.size();
		}
		return nbRow;
	}

	public Object getValueAt(int lig, int col) {
		if (this.tasklist==null) { return "!!!"; }
		
		switch (col) {
			case 0:
				return ""+this.tasklist.get(lig).getId();
			case 1:
				return this.tasklist.get(lig).getDescription();
			case 2:
				return this.tasklist.get(lig).getDate();
			case 3:
				return ""+this.tasklist.get(lig).getDeadLine();
			default:
				return "???";
		}

	}
	
	// =======================================================================
	// M�thodes sp�cifiques � la classe taskTableModel
	// =======================================================================
	/**
	 * Lecture (ou relecture forc�e) des donn�es dans la base
	 * 
	 * @param listeDonnees	  ArrayList contenant les tasks � pr�senter dans la table
	 */
	public void loadDatas( List<Task> listeDonnees ) {
		this.tasklist = listeDonnees;
		this.fireTableDataChanged();
	}
	
	
	/**
	 * Retourner la copie d'un �l�ment de type task repr�sentant l'enregistrement de la ligne "lig".
	 * 
	 * @param lig num�ro de la ligne (dans la table).
	 * 
	 * @return l'�l�ment concern� ou NULL en cas d'erreur
	 */
	public Task gettaskAt(int lig) {
		Task task = null;
		if ( this.tasklist != null ) {
			if (this.tasklist.size()>lig) {
				task = this.tasklist.get(lig);
			}
		}
		return task;
	}

	@Override
	public String getColumnName(int arg0) {
		switch(arg0){
		case 0:
			return "ID";
		case 1:			
			return "Nom";

		case 2:			
			return "Pr�nom";

		case 3:			
			return "Groupe";

		}
		return "";
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex==0||columnIndex==1)
			return false ;
		else
			return true ;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
		List<String> listeGrp = Arrays.asList("1A","1B","2A","2B","3A","3B","4A","4B");
		
		
		if(columnIndex==1||columnIndex==2){
			if(!aValue.equals("")){
				if(columnIndex==1)
					this.tasklist.get(rowIndex).surname=(String) aValue;
				else
					this.tasklist.get(rowIndex).firstname=(String) aValue;
			}
		}else{
			String grp = (String) aValue;
			grp.toUpperCase();
			if(listeGrp.contains(grp))
				this.tasklist.get(rowIndex).TPgroup=grp;
		}
	}
	
	public void updatetaskAt(Task taskUpdated, int lig) {
		
		setValueAt(taskUpdated.getDescription(),lig,2);		
		setValueAt(taskUpdated.getDeadLine(),lig,3);
		this.fireTableDataChanged();
	}
				
	public void addtask( Task task) {
		
		@SuppressWarnings("unused")
		Boolean valide=true;

		this.tasklist.add(task);
		this.fireTableDataChanged();
	}
	
	public void removetask( int lig) {
		this.tasklist.remove(lig);
		this.fireTableDataChanged();
	}
	
}
