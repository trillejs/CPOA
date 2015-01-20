package gui.view;


import gui.model.TaskTableModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import com.codurance.training.tasks.Task;


/**
 * @author André PENINOU
 * @author Fabrice PELLEAU
 */

public class TaskListFrame extends JFrame {

	///  fenêtre d'édition d'un task (créé à la première utilisation)
	private TaskDialog taskDialog = null;
	
	// TableModel utilisé
	private TaskTableModel taskTM = null;

	// =======================================================================
	// Attributs graphiques (gérés par V.E.)
	// =======================================================================
	private JPanel    jContentPane = null;
	private JPanel    topPanel = null;
	private JPanel centerPanel = null;
	private JButton   butAjout = null;
	private JButton   butSuppr = null;
	private JButton   butModif = null;
	private JButton butGauche = null;
	private JButton butDroite = null;
	private JScrollPane jScrollPaneTable = null;
	private JScrollPane jScrollPaneTable2 = null;

	private JTable taskTable = null;
	private JTable taskTable2 = null;

	/**
	 * This is the default constructor
	 */
	public TaskListFrame() {
		super();
		this.initialize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.taskTM = new TaskTableModel();
		this.gettaskTable().setModel(this.taskTM);
	}

	/**
	 * Variante du constructeur avec initialisation de la liste des tasks à traiter
	 */
	public taskListFrame(List<Task> listetasksInitiale) {
		super();
		this.initialize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.taskTM = new TaskTableModel();
		this.taskTM.loadDatas(listetasksInitiale);
		this.gettaskTable().setModel(this.taskTM);		
	}
	
	// =======================================================================
	// Initialisation et accesseur des composants graphiques (géré par V.E.)
	// =======================================================================

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1000, 300);
		this.setContentPane(this.getJContentPane());
		this.setTitle("Gestion des tasks");
	}

	/**
	 * This method initializes jScrollPaneTable	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneTable() {
		
		this.jScrollPaneTable = new JScrollPane();
		this.jScrollPaneTable.setViewportView(this.gettaskTable());
		this.jScrollPaneTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.jScrollPaneTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		return this.jScrollPaneTable;
	}
	
	private JScrollPane getJScrollPaneTable2() {
		
		this.jScrollPaneTable2 = new JScrollPane();
		this.jScrollPaneTable2.setViewportView(this.gettaskTable2());
		this.jScrollPaneTable2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.jScrollPaneTable2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
		return this.jScrollPaneTable2;
	}

	/**
	 * This method initializes taskTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable gettaskTable() {
		
		if(this.taskTable==null){
			this.taskTable = new JTable();
			this.taskTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);		
			
		}
		return this.taskTable;
	}

	private JTable gettaskTable2() {
		
		if(this.taskTable2==null){
			this.taskTable2 = new JTable();
			this.taskTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);		
		}
		return this.taskTable2;
	}
	
	/**
	 * Initialisation de la boite de dialog taskDialog.
	 * 
	 * @return taskDialog
	 */
	private taskDialog gettaskDialog() {
		if (this.taskDialog==null) {
			this.taskDialog = new taskDialog(this);
		}
		return this.taskDialog;
	}
	
	
	/**
	 * This method initializes topPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTopPanel() {
		if (this.topPanel == null) {
			this.topPanel = new JPanel();
			this.topPanel.setLayout(new BorderLayout());
			this.topPanel.add(this.getButAjout(), BorderLayout.WEST);
			this.topPanel.add(this.getButSuppr(), BorderLayout.EAST);
			this.topPanel.add(this.getButModif(), BorderLayout.CENTER);
		}
		return this.topPanel;
	}

	/**
	 * This method initializes butAjout	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButAjout() {
		if (this.butAjout == null) {
			this.butAjout = new JButton();
			this.butAjout.setText("Ajouter");
			this.butAjout.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					taskListFrame.this.ajouttask();
				}
			});
		}
		return this.butAjout;
	}

	/**
	 * This method initializes butSuppr	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButSuppr() {
		if (this.butSuppr == null) {
			this.butSuppr = new JButton();
			this.butSuppr.setText("Supprimer");
			this.butSuppr.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					taskListFrame.this.supprtask();
				}
			});
		}
		return this.butSuppr;
	}

	/**
	 * This method initializes butModif	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButModif() {
		if (this.butModif == null) {
			this.butModif = new JButton();
			this.butModif.setText("Modifier");
			this.butModif.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					taskListFrame.this.modiftask();
				}
			});
		}
		return this.butModif;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		
			this.jContentPane = new JPanel();
			this.jContentPane.setLayout(new BorderLayout());
			this.jContentPane.add(this.getTopPanel(), BorderLayout.NORTH);
			this.jContentPane.add(this.getButtonPanel(), BorderLayout.CENTER);
			this.jContentPane.add(this.getJScrollPaneTable(), BorderLayout.WEST);
			this.jContentPane.add(this.getJScrollPaneTable2(), BorderLayout.EAST);
			
		
		return this.jContentPane;
	}

	private Component getButtonPanel() {
		if (this.centerPanel == null) {
			this.centerPanel = new JPanel();
			this.butGauche = new JButton();
			this.butDroite = new JButton();
			this.butGauche.setText("<=");
			this.butDroite.setText("=>");
			this.butGauche.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					taskListFrame.this.deplacerGauche();
				}
			});
			this.butDroite.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					taskListFrame.this.deplacerDroite();
				}
			});
			
			this.centerPanel.add(butGauche, BorderLayout.NORTH);
			this.centerPanel.add(butDroite, BorderLayout.SOUTH);
		}
		return this.centerPanel;
	}

	// =======================================================================
	// Méthodes internes liées aux fonctionnalités du composant
	// =======================================================================
	
	
	/**
	 * Ajout d'un nouveau task dans la base de données
	 */
	private void ajouttask() {

		Task stud = new Task();
		boolean  popupResult;

		// Création ou réutilisation de la boite de dialog "taskDialog"
		// ouverture en mode "ajout"
		// récupération du code de retour (true/false) et des données saisies (stud) 
		popupResult = this.gettaskDialog().ouvrirDialogue( stud, taskDialog.ModeEdition.CREATION ); 
		
		if ( popupResult) {
			
			taskTM.addtask(stud);
			JOptionPane.showMessageDialog(this,"Etudiant ajouté");
			
			
		} else {
			// action non validée
			JOptionPane.showMessageDialog(this,"ok on n'ajoute rien");
		}

	}
	
	/**
	 * Modification d'un task de la base de données
	 */
	private void modiftask() {

    	int selectedRow = this.gettaskTable().getSelectedRow();
    	    	
    	if ( selectedRow >= 0 ) {
    		task stud = this.taskTM.gettaskAt( selectedRow );
    		if ( stud != null ) {

    			boolean  popupResult;

    			// Création ou réutilisation de la boite de dialog "taskDialog"
    			// ouverture en mode "modification"
    			// récupération du code de retour (true/false) et des données saisies (stud) 
    			popupResult = this.gettaskDialog().ouvrirDialogue( stud, taskDialog.ModeEdition.MODIFICATION ); 
    			
    			if ( popupResult) {
    				
    				taskTM.updatetaskAt(stud, selectedRow);
    				JOptionPane.showMessageDialog(this,"Etudiant modifié");
    				
    				
    			} else {
    				// action non validée
    				JOptionPane.showMessageDialog(this,"ok on ne modifie rien");
    			}
    		}
    	} else {
    		JOptionPane.showMessageDialog(this,"Veuillez sélectionner la ligne à modifier");
    	}
	}

	/**
	 * Suppression d'un task de la base de données
	 */
	private void supprtask() {

    	int selectedRow = this.gettaskTable().getSelectedRow();
    	
    	if ( selectedRow >= 0 ) {
    		task stud = this.taskTM.gettaskAt( selectedRow );
    		if ( stud != null ) {

    			boolean  popupResult;

    			// Création ou réutilisation de la boite de dialog "taskDialog"
    			// ouverture en mode "suppression"
    			// récupération du code de retour (true/false) et des données saisies (stud) 
    			popupResult = this.gettaskDialog().ouvrirDialogue( stud, taskDialog.ModeEdition.SUPPRESSION ); 
    			
    			if ( popupResult) {
    				
    				taskTM.removetask(selectedRow);
    				JOptionPane.showMessageDialog(this,"Etudiant supprimé");
    				
    				
    			} else {
    				// action non validée
    				JOptionPane.showMessageDialog(this,"ok on ne supprime rien");
    			}
    		}
    	} else {
    		JOptionPane.showMessageDialog(this,"Veuillez sélectionner la ligne à supprimer");
    	}
		
	}
	
	private void deplacerGauche(){
		
		int selectedRow = this.gettaskTable2().getSelectedRow();
		
		if ( selectedRow >= 0 ) {
    		task stud = this.taskTM2.gettaskAt( selectedRow );
    		if ( stud != null ) {
    			
    			taskTM2.removetask(selectedRow);
    			taskTM.addtask(stud);
    		}
    	}
	}
	
	private void deplacerDroite(){
		int selectedRow = this.gettaskTable().getSelectedRow();
		
		if ( selectedRow >= 0 ) {
    		task stud = this.taskTM.gettaskAt( selectedRow );
    		if ( stud != null ) {
    			
    			taskTM.removetask(selectedRow);
    			taskTM2.addtask(stud);
    		}
    	}
	}
	

}