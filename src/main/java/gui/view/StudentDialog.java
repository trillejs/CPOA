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

import data.model.Student;

/**
 * Boite de dialogue utilisée créer ou modifier une donnée de type Student.
 * 
 * La boite de dialogue est activée par la méthode publique "ouvrirDialog".
 * Il existe 3 modes de fonctionnement distincts (Cf. enumeration "ModeEdition").
 * 
 * @author Fabrice PELLEAU
 */

public class StudentDialog extends JDialog {
	
	public enum ModeEdition {
		CREATION, MODIFICATION, SUPPRESSION
	};

	private Student student = null;
	private boolean etatValidation = false;
	private ModeEdition modeActuel;

	private static final long serialVersionUID = 1L;

	// =======================================================================
	// Attributs graphiques (gérés par V.E.)
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
	// Méthodes publiques
	// =======================================================================
	
	/**
	 * Constructeur permettant l'association de la JDialog avec la fenêtre appelante.
	 * 
	 * @param owner fenetre appellante.
	 */
	public StudentDialog(Frame owner) {
		super(owner);
		this.initialize();
	}
	
	/**
     * Ouverture de la boite de dialogue.
	 *
	 * @param stud  Objet de type Student à éditer (jamais null).
	 * @param mode  Mode d'ouverture (CREATION, MODIFICATION, SUPPRESSION)
	 * @return true si l'action est validée / false sinon
	 */
	public boolean ouvrirDialogue(Student stud, StudentDialog.ModeEdition mode) {
		this.modeActuel = mode;
		switch (mode) {
		case CREATION:
			stud.id=0;
			stud.surname = "";
			stud.firstname = "";
			stud.TPgroup="";
			this.student = stud;
			this.getTxtID().setEnabled(false);
			this.getTxtName().setEnabled(true);
			this.getTxtFirstName().setEnabled(true);
			this.getTxtGroup().setEnabled(true);
			this.labMessage.setText("Description du Student à créer");
			this.getButValider().setText("Ajouter");
			this.getButAnnuler().setText("Annuler");
			break;
		case MODIFICATION:
			this.student = stud;
			this.getTxtID().setEnabled(false);
			this.getTxtName().setEnabled(true);
			this.getTxtFirstName().setEnabled(true);
			this.getTxtGroup().setEnabled(true);
			this.labMessage.setText("Student actuellement en base");
			this.getButValider().setText("Modifier");
			this.getButAnnuler().setText("Annuler");
			break;
		case SUPPRESSION:
			this.student = stud;
			this.getTxtID().setEnabled(false);
			this.getTxtName().setEnabled(false);
			this.getTxtFirstName().setEnabled(false);
			this.getTxtGroup().setEnabled(false);
			this.labMessage.setText("Voulez-vous réellement supprimer ce Student ?");
			this.getButValider().setText("Supprimer");
			this.getButAnnuler().setText("Conserver");
			break;
		}
		this.updateFields();
		this.etatValidation  = false;
		this.setModal(true);
		this.setLocationRelativeTo(this.getParent());		
		
		this.setVisible(true);
		
		// le programme appelant est bloqué jusqu'au masquage de la JDialog.
		return this.etatValidation;
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
		this.setSize(386, 200);
		this.setTitle("Gestion d'un Student");
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
			this.labMessage.setText("Action à réaliser");
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
			this.labFirstname.setText("Prénom");
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
					StudentDialog.this.student.surname = StudentDialog.this.getTxtName().getText().trim();
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
					StudentDialog.this.student.firstname = StudentDialog.this.getTxtFirstName().getText().trim();
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
					StudentDialog.this.student.TPgroup = StudentDialog.this.getTxtGroup().getText().trim();
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
					StudentDialog.this.actionValider();
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
					StudentDialog.this.actionAnnuler();
				}
			});
		}
		return this.butAnnuler;
	}

	// =======================================================================
	// Méthodes internes liées aux fonctionnalités du composant
	// =======================================================================

	/**
	 * Mise à jour des zones d'édition en fonction de l'objet "Student" actuellement associé.
	 */
	private void updateFields() {
		this.getTxtID().setText(""+this.student.id);
		this.getTxtName().setText(this.student.surname);
		this.getTxtFirstName().setText(this.student.firstname);
		this.getTxtGroup().setText(this.student.TPgroup);
	}
	/**
	 * Controle de validité sur les champs modifiables.
	 *  - l'attribut "studnt" est automatiquement actualisé (évènements focusLost des textField)
	 *  - la validité de l'age a déjà ete testée : entier d'interval [0..120]
	 *  
	 * @return true si tous les champs sont valides.
	 */
	private boolean isStudentValide() {
		if ( this.student.surname.isEmpty() ) {
	    	JOptionPane.showMessageDialog(this, "Le nom ne doit pas être vide");
	    	this.getTxtName().requestFocus();
			return false;
		}
		if ( this.student.firstname.isEmpty() ) {
	    	JOptionPane.showMessageDialog(this, "Le prénom ne doit pas être vide");
	    	this.getTxtFirstName().requestFocus();
			return false;
		}
		if ( this.student.TPgroup.isEmpty() ) {
	    	JOptionPane.showMessageDialog(this, "Le groupe TP ne doit pas être vide");
	    	this.getTxtGroup().requestFocus();
			return false;
		}
		this.student.TPgroup = this.student.TPgroup.toUpperCase();
		if ( this.student.TPgroup.length() != 2 
				||  ! (""+this.student.TPgroup.charAt(0)).matches("1|2|3|4")
				||  ! (""+this.student.TPgroup.charAt(1)).matches("A|B")
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
			if (this.isStudentValide()) {
				this.etatValidation = true;
				this.setVisible(false);
			}
			break;
		case MODIFICATION:
			if (this.isStudentValide()) {
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