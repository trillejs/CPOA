package gui.view;


import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.SwingConstants;

import gui.model.TaskTableModel;
import com.codurance.training.projects.*;
import com.codurance.training.tasks.*;

/**
 * Boite de dialogue utilis�e cr�er ou modifier une donn�e de type TaskMultiple.
 * 
 * La boite de dialogue est activ�e par la m�thode publique "ouvrirDialog".
 * Il existe 3 modes de fonctionnement distincts (Cf. enumeration "ModeEdition").
 * 
 * @author Fabrice PELLEAU
 */

public class TaskMultipleDialog extends JDialog {
	
	public enum ModeEdition {
		CREATION, MODIFICATION, SUPPRESSION
	};

	private TaskMultiple TaskMultiple = null;
	private boolean etatValidation = false;
	private ModeEdition modeActuel;

	private static final long serialVersionUID = 1L;

	// =======================================================================
	// Attributs graphiques (g�r�s par V.E.)
	// =======================================================================
	
	private JPanel jContentPane = null;
	private JPanel panGauche = null;
	private JPanel panCentre = null;
	private JLabel labID = null;
	private JLabel lanName = null;
	private JLabel labFirstname = null;
	private JLabel labGroup = null;
	private JTextField txtID = null;
	private JTextField txtName = null;
	private JTextField txtFirstname = null;
	private JTextField txtGroup = null;
	private JPanel panBas = null;
	private JButton butValider = null;
	private JButton butAnnuler = null;
	private JLabel labMessage = null;

	// =======================================================================
	// M�thodes publiques
	// =======================================================================
	
	/**
	 * Constructeur permettant l'association de la JDialog avec la fen�tre appelante.
	 * 
	 * @param owner fenetre appellante.
	 */
	public TaskMultipleDialog(Frame owner) {
		super(owner);
		this.initialize();
	}
	
	/**
     * Ouverture de la boite de dialogue.
	 *
	 * @param stud  Objet de type TaskMultiple � �diter (jamais null).
	 * @param mode  Mode d'ouverture (CREATION, MODIFICATION, SUPPRESSION)
	 * @return true si l'action est valid�e / false sinon
	 */
	public boolean ouvrirDialogue(TaskMultiple task, TaskMultipleDialog.ModeEdition mode) {
		this.modeActuel = mode;
		switch (mode) {
		case CREATION:
			task.id=0;
			task.surname = "";
			task.firstname = "";
			task.TPgroup="";
			this.TaskMultiple = task;
			this.getTxtID().setEnabled(false);
			this.getTxtName().setEnabled(true);
			this.getTxtFirstName().setEnabled(true);
			this.getTxtGroup().setEnabled(true);
			this.labMessage.setText("Description du TaskMultiple � cr�er");
			this.getButValider().setText("Ajouter");
			this.getButAnnuler().setText("Annuler");
			break;
		case MODIFICATION:
			this.TaskMultiple = stud;
			this.getTxtID().setEnabled(false);
			this.getTxtName().setEnabled(true);
			this.getTxtFirstName().setEnabled(true);
			this.getTxtGroup().setEnabled(true);
			this.labMessage.setText("TaskMultiple actuellement en base");
			this.getButValider().setText("Modifier");
			this.getButAnnuler().setText("Annuler");
			break;
		case SUPPRESSION:
			this.TaskMultiple = stud;
			this.getTxtID().setEnabled(false);
			this.getTxtName().setEnabled(false);
			this.getTxtFirstName().setEnabled(false);
			this.getTxtGroup().setEnabled(false);
			this.labMessage.setText("Voulez-vous r�ellement supprimer ce TaskMultiple ?");
			this.getButValider().setText("Supprimer");
			this.getButAnnuler().setText("Conserver");
			break;
		}
		this.updateFields();
		this.etatValidation  = false;
		this.setModal(true);
		this.setLocationRelativeTo(this.getParent());		
		
		this.setVisible(true);
		
		// le programme appelant est bloqu� jusqu'au masquage de la JDialog.
		return this.etatValidation;
	}

	// =======================================================================
	// Initialisation et accesseur des composants graphiques (g�r� par V.E.)
	// =======================================================================
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(386, 200);
		this.setTitle("Gestion d'un TaskMultiple");
		this.setContentPane(this.getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (this.jContentPane == null) {
			this.labMessage = new JLabel();
			this.labMessage.setText("Action � r�aliser");
			this.labMessage.setHorizontalTextPosition(SwingConstants.CENTER);
			this.labMessage.setHorizontalAlignment(SwingConstants.CENTER);
			this.labMessage.setPreferredSize(new Dimension(93, 32));
			this.jContentPane = new JPanel();
			this.jContentPane.setLayout(new BorderLayout());
			this.jContentPane.add(this.getPanGauche(), BorderLayout.WEST);
			this.jContentPane.add(this.getPanCentre(), BorderLayout.CENTER);
			this.jContentPane.add(this.labMessage, BorderLayout.NORTH);
			this.jContentPane.add(this.getPanBas(), BorderLayout.SOUTH);
		}
		return this.jContentPane;
	}

	/**
	 * This method initializes panGauche	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanGauche() {
		if (this.panGauche == null) {
			this.labGroup = new JLabel();
			this.labGroup.setText("Groupe TP");
			this.labFirstname = new JLabel();
			this.labFirstname.setText("Pr�nom");
			this.lanName = new JLabel();
			this.lanName.setText("Nom");
			this.labID = new JLabel();
			this.labID.setText("ID");
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(4);
			this.panGauche = new JPanel();
			this.panGauche.setLayout(gridLayout);
			this.panGauche.add(this.labID, null);
			this.panGauche.add(this.lanName, null);
			this.panGauche.add(this.labFirstname, null);
			this.panGauche.add(this.labGroup, null);
		}
		return this.panGauche;
	}

	/**
	 * This method initializes panCentre	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanCentre() {
		if (this.panCentre == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(4);
			this.panCentre = new JPanel();
			this.panCentre.setLayout(gridLayout1);
			this.panCentre.add(this.getTxtID(), null);
			this.panCentre.add(this.getTxtName(), null);
			this.panCentre.add(this.getTxtFirstName(), null);
			this.panCentre.add(this.getTxtGroup(), null);
		}
		return this.panCentre;
	}

	/**
	 * This method initializes txtID	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtID() {
		if (this.txtID == null) {
			this.txtID = new JTextField();
		}
		return this.txtID;
	}

	/**
	 * This method initializes txtNom	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtName() {
		if (this.txtName == null) {
			this.txtName = new JTextField();
			this.txtName.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusLost(java.awt.event.FocusEvent e) {
					TaskMultipleDialog.this.TaskMultiple.surname = TaskMultipleDialog.this.getTxtName().getText().trim();
				}
			});
		}
		return this.txtName;
	}

	/**
	 * This method initializes txtPrenom	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtFirstName() {
		if (this.txtFirstname == null) {
			this.txtFirstname = new JTextField();
			this.txtFirstname.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusLost(java.awt.event.FocusEvent e) {
					TaskMultipleDialog.this.TaskMultiple.firstname = TaskMultipleDialog.this.getTxtFirstName().getText().trim();
				}
			});
		}
		return this.txtFirstname;
	}

	/**
	 * This method initializes txtAge	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtGroup() {
		if (this.txtGroup == null) {
			this.txtGroup = new JTextField();
			this.txtGroup.addFocusListener(new java.awt.event.FocusAdapter() {
				@Override
				public void focusLost(java.awt.event.FocusEvent e) {
					TaskMultipleDialog.this.TaskMultiple.TPgroup = TaskMultipleDialog.this.getTxtGroup().getText().trim();
				}
			});
		}
		return this.txtGroup;
	}

	/**
	 * This method initializes panBas	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanBas() {
		if (this.panBas == null) {
			GridLayout gridLayout2 = new GridLayout();
			gridLayout2.setRows(1);
			gridLayout2.setColumns(2);
			this.panBas = new JPanel();
			this.panBas.setLayout(gridLayout2);
			this.panBas.add(this.getButValider(), null);
			this.panBas.add(this.getButAnnuler(), null);
		}
		return this.panBas;
	}

	/**
	 * This method initializes butValider	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButValider() {
		if (this.butValider == null) {
			this.butValider = new JButton();
			this.butValider.setText("txt valider");
			this.butValider.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TaskMultipleDialog.this.actionValider();
				}
			});
		}
		return this.butValider;
	}

	/**
	 * This method initializes butAnnuler	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButAnnuler() {
		if (this.butAnnuler == null) {
			this.butAnnuler = new JButton();
			this.butAnnuler.setText("txt annuler");
			this.butAnnuler.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TaskMultipleDialog.this.actionAnnuler();
				}
			});
		}
		return this.butAnnuler;
	}

	// =======================================================================
	// M�thodes internes li�es aux fonctionnalit�s du composant
	// =======================================================================

	/**
	 * Mise � jour des zones d'�dition en fonction de l'objet "TaskMultiple" actuellement associ�.
	 */
	private void updateFields() {
		this.getTxtID().setText(""+this.TaskMultiple.id);
		this.getTxtName().setText(this.TaskMultiple.surname);
		this.getTxtFirstName().setText(this.TaskMultiple.firstname);
		this.getTxtGroup().setText(this.TaskMultiple.TPgroup);
	}
	/**
	 * Controle de validit� sur les champs modifiables.
	 *  - l'attribut "studnt" est automatiquement actualis� (�v�nements focusLost des textField)
	 *  - la validit� de l'age a d�j� ete test�e : entier d'interval [0..120]
	 *  
	 * @return true si tous les champs sont valides.
	 */
	private boolean isTaskMultipleValide() {
		if ( this.TaskMultiple.surname.isEmpty() ) {
	    	JOptionPane.showMessageDialog(this, "Le nom ne doit pas �tre vide");
	    	this.getTxtName().requestFocus();
			return false;
		}
		if ( this.TaskMultiple.firstname.isEmpty() ) {
	    	JOptionPane.showMessageDialog(this, "Le pr�nom ne doit pas �tre vide");
	    	this.getTxtFirstName().requestFocus();
			return false;
		}
		if ( this.TaskMultiple.TPgroup.isEmpty() ) {
	    	JOptionPane.showMessageDialog(this, "Le groupe TP ne doit pas �tre vide");
	    	this.getTxtGroup().requestFocus();
			return false;
		}
		this.TaskMultiple.TPgroup = this.TaskMultiple.TPgroup.toUpperCase();
		if ( this.TaskMultiple.TPgroup.length() != 2 
				||  ! (""+this.TaskMultiple.TPgroup.charAt(0)).matches("1|2|3|4")
				||  ! (""+this.TaskMultiple.TPgroup.charAt(1)).matches("A|B")
			) {
	    	JOptionPane.showMessageDialog(this, "Groupe TP invalide (1|2|3|4  A|B)");
	    	this.getTxtGroup().requestFocus();
			return false;
		}

		return true;
	}
	
	/**
	 * mise en oeuvre d'une Validation (en fonction du contexte)
	 *
	 */
	private void actionValider() {
		switch (this.modeActuel) {
		case CREATION:
			if (this.isTaskMultipleValide()) {
				this.etatValidation = true;
				this.setVisible(false);
			}
			break;
		case MODIFICATION:
			if (this.isTaskMultipleValide()) {
				this.etatValidation = true;
				this.setVisible(false);
			}
			break;
		case SUPPRESSION:
			this.etatValidation = true;
			this.setVisible(false);
			break;
		}
	}
	
	/**
	 * mise en oeuvre d'une Annulation (en fonction du contexte)
	 */
	private void actionAnnuler() {
		this.etatValidation = false;
		this.setVisible(false);
	}
}