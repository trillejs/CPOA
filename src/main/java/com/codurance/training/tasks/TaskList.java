package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;


import org.joda.time.DateTime;

import com.codurance.training.projects.Project;
/**A la fois contrôleur et vue du programme. 
 * @author Jean-Sebastien TRILLE & Antoine RIVALIER
 *
 */
public final class TaskList implements Runnable {
	

	private static final String QUIT = "quit";

	private final BufferedReader in;

	private final PrintWriter out;

	/**
	 * Liste des projets de l'application
	 */
	private ArrayList<Project> listeProjet;
	
	/**
	 * Liste des tâches de l'application
	 */
	private ArrayList<TaskMultiple> listeTask;

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		new TaskList(in, out).run();

	}

	/** Initialise les différents paramètres
	 * @param reader
	 * @param writer
	 */
	public TaskList(BufferedReader reader, PrintWriter writer)
	{
		this.in = reader;
		this.out = writer;
		this.listeProjet = new ArrayList<Project>();
		this.listeTask = new ArrayList<TaskMultiple>();


		//Pour vous faciliter l'utilisation du programme quelques données :
		this.listeProjet.add(new Project("Projet 1"));
		this.listeProjet.add(new Project("Projet 2"));

		this.listeTask.add(new TaskMultiple(1,"desc1",false));
		this.listeTask.add(new TaskMultiple(2,"desc2",false));
		this.listeTask.add(new TaskMultiple(3,"desc3",false));
		this.listeTask.add(new TaskMultiple(4,"desc4",false));
		this.listeTask.add(new TaskMultiple(5,"desc5",false));
		this.listeTask.add(new TaskMultiple(6,"desc6",false));
		this.listeTask.add(new TaskMultiple(7,"desc7",false));

		addTaskToTask((long)1, (long) 2);
		addTaskToTask((long)3, (long) 4);

		addTaskToProject("Projet 1", (long) 1);
		addTaskToProject("Projet 1", (long) 3);
		addTaskToProject("Projet 2", (long) 5);
		addTaskToProject("Projet 2", (long) 6);
		addTaskToProject("Projet 1", (long) 7);
		addTaskToProject("Projet 2", (long) 7);


	}

	public void run()
	{
		while (true) 
		{
			out.print("> ");
			out.flush();
			String command;
			try 
			{
				command = in.readLine();
			} 
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
			if (command.equals(QUIT))
			{
				break;
			}
			execute(command);
		}
	}

	/** Menu de l'application
	 * @param commandLine
	 */
	private void execute(String commandLine)
	{
		String[] commandRest = commandLine.split(" ", 2);
		String command = commandRest[0];

		switch (command) 
		{
		case "delete":
			delete(Long.parseLong(commandRest[1]));
			break;
		case "add":
			add(commandRest[1]);
			break;
		case "deadline":
			String[] parametre = commandRest[1].split(" ", 2);
			deadline(Long.parseLong(parametre[0]), parametre[1]);
		case "today":
			today();
			break;
		case "check":
			check(commandRest[1]);
			break;
		case "uncheck":
			uncheck(commandRest[1]);
			break;
		case "view":
			if( commandRest[1].equals("by deadline"))
				viewByDeadLine();
			else if(commandRest[1].equals("by date"))
				viewByDate();
			else if(commandRest[1].equals("by project"))
				viewByProject();
			break;
		case "help":
			help();
			break;
		default:
			error(command);
			break;
		}
	}

	/**
	 * Affiche les Tâches par projet
	 */
	private void viewByProject() 
	{	
		for(Project p : this.listeProjet)
		{

			out.printf("%s%n",p.getName());

			for(TaskMultiple tache: p.getListTask())
			{
				out.printf("    [%c] %d: %s%n", (tache.isDone() ? 'x' : ' '), tache.getId(), tache.getDescription());
				out.println("    deadLine : " + tache.getDeadLine());
				for(TaskMultiple t : tache.getSousTaches()){
					out.printf("        [%c] %d: %s%n", (t.isDone() ? 'x' : ' '), t.getId(), t.getDescription());
					out.println("        deadLine : " + t.getDeadLine());
				}
			}

			for(TaskMultiple tache: p.getListTaskDone())
			{
				out.printf("    [%c] %d: %s%n", (tache.isDone() ? 'x' : ' '), tache.getId(), tache.getDescription());
				out.println("    deadLine : " + tache.getDeadLine());
				for(TaskMultiple t : tache.getSousTaches()){
					out.printf("        [%c] %d: %s%n", (t.isDone() ? 'x' : ' '), t.getId(), t.getDescription());
					out.println("        deadLine : " + t.getDeadLine());
				}
			}

		}
	}

	/**
	 * Affiche les tâches dont la deadLine correspond à aujourd'hui
	 */
	private void today()
	{
		for(Project p : this.listeProjet)
		{	
			out.println("Projet n° : " + p.getName());
			for(TaskMultiple tache: p.getListTask())
			{
				if(tache.getDeadLine() != null)
				{
					if( tache.getDeadLine().getDayOfWeek()  ==  DateTime.now().getDayOfWeek() )
					{

						out.print(" tache : " + tache.getId());
						out.println(" | description : " + tache.getDescription());
					}
				}			
			}
		}
	}

	/** Change la deadLine d'une tâche
	 * @param pId - long, ID de la tâche
	 * @param commandLine - String, date de la deadLine
	 */
	private void deadline(long pId, String commandLine)
	{

		String[] date = commandLine.split("/", 3);
		out.print(date[0] + "/" + date[1] + "/" + date[2] + " ");
		DateTime deadLine = new DateTime( Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]), 0, 0);
		boolean exist = false;

		for(TaskMultiple t : listeTask)
		{

			if( t.getId()  ==  pId )
			{
				t.setDeadLine(deadLine);
				exist = true;
			}


		}

		if( exist  ==  false )
			out.println("Aucun projet ayant cet ID");
	}

	/**
	 * Affiche les tâches par leurs dates de création
	 */
	private void viewByDate()
	{
		ArrayList<TaskMultiple> listeDate = new ArrayList<TaskMultiple>();

		for(TaskMultiple task : this.listeTask)
		{
			if( task.getDeadLine() != null )
			{
				int k = listeDate.size();

				do
				{
					//On vérifie si la liste n'est pas vide avant de devoir comparer des DateTime

					if( listeDate.size()  ==  0 )
						listeDate.add(task);
					else
					{
						if( task.getDate().isAfter(listeDate.get(k-1).getDate()) )
						{
							listeDate.add(k, task);
							k = 0;
						}
						else if( k - 1  ==  0 )
							listeDate.add(k - 1, task);
					}

					k--;
				}
				while( k > 0 );
			}

		}

		// un fois que toutes les taches avec deadLine sont dans notre List on affiche

		for(TaskMultiple task : listeDate)
		{
			out.println( "ID : " + task.getId() + " deadline : " + task.getDeadLine().getDayOfWeek() + "/" + task.getDeadLine().getMonthOfYear() + "/" + task.getDeadLine().getYear());
		} 	
	}

	/**
	 * Affiche les tâches par deadLine
	 */
	private void viewByDeadLine()
	{
		ArrayList<TaskMultiple> listeDeadLine = new ArrayList<TaskMultiple>();

		for(TaskMultiple task : this.listeTask)
		{
			if( task.getDeadLine() != null )
			{
				int k = listeDeadLine.size();

				do
				{
					//On vérifie si la liste n'est pas vide avant de devoir comparer des DateTime

					if( listeDeadLine.size()  ==  0 )
						listeDeadLine.add(task);
					else
					{
						if( task.getDeadLine().isAfter(listeDeadLine.get(k-1).getDeadLine()) )
						{
							listeDeadLine.add(k, task);
							k = 0;
						}
						else if( k - 1  ==  0 )
							listeDeadLine.add(k - 1, task);
					}

					k--;
				}
				while( k > 0 );
			}

		}

		// un fois que toutes les taches avec deadLine sont dans notre List on affiche

		for(TaskMultiple task : listeDeadLine)
		{
			out.println( "ID : " + task.getId() + " deadline : " + task.getDeadLine().getDayOfWeek() + "/" + task.getDeadLine().getMonthOfYear() + "/" + task.getDeadLine().getYear());
		} 	
	}

	/** Supprimme une tâche, de la liste des tâches, des projets et des sous-tâches
	 * @param id - long, ID de la tâche à supprimer
	 */
	private void delete(long id)
	{
		for(TaskMultiple task : this.listeTask){
			for(TaskMultiple t : task.getSousTaches()){
				if(t.getId() == id)
					task.getSousTaches().remove(t);
				break;
			}
		}
		
		for(TaskMultiple task : this.listeTask){

			if(task.getId() == id)
			{
				this.listeTask.remove(task);
				for(Project p : this.listeProjet){
					p.getListTask().remove(task);
					p.getListTaskDone().remove(task);
				}
				return;
			}
		}
	}


	/** Appelle les différentes commandes add [...]
	 * @param commandLine - String
	 */
	private void add(String commandLine)
	{
		String[] subcommandRest = commandLine.split(" ", 2);
		String subcommand = subcommandRest[0];

		if (subcommand.equals("project"))
		{
			addProject(subcommandRest[1]);
		} 
		else if (subcommand.equals("task"))   
		{
			String[] projectTask = subcommandRest[1].split(" ", 2);
			addTask(Long.parseLong(projectTask[0]), projectTask[1]);
		}        
		else if (subcommand.equals("taskToProject"))   
		{
			String[] projectTask = subcommandRest[1].split(" ", 2);
			addTaskToProject(projectTask[0], Long.parseLong(projectTask[1]));
		}
		else if (subcommand.equals("taskToTask"))   
		{
			String[] projectTask = subcommandRest[1].split(" ", 2);
			addTaskToTask(Long.parseLong(projectTask[0]), Long.parseLong(projectTask[1]));
		}
	}

	/** Ajoute un projet
	 * @param pName - String, Nom du projet
	 */
	private void addProject(String pName) 
	{
		listeProjet.add(new Project(pName));
	}

	/** Ajoute une tâche
	 * @param id - long, ID de la tâche
	 * @param description - String, Description de la tâche
	 */
	private void addTask(Long id, String description) 
	{
		listeTask.add(new TaskMultiple(id,description,false));
	}

	/** Ajoute une tâche à une autre tâche
	 * @param id - long, tâche qui reçoit la sous-tâche
	 * @param id2 - long, sous-tâche
	 */
	private void addTaskToTask(long id, long id2){

		TaskMultiple t1=null;
		TaskMultiple t2=null;

		for(TaskMultiple t : this.listeTask){
			if(t.getId() == id)
				t1=t;
			else if(t.getId() == id2)
				t2=t;
		}

		if(t1 == null || t2 == null)
			System.out.println("Une de ces deux tâches n'existe pas.");
		else{
			try {
				t1.addTask(t2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/** Ajoute une tâche à un projet
	 * @param projectName - String, Nom du projet
	 * @param id - long, ID de la tâche
	 */
	private void addTaskToProject(String projectName, Long id) 
	{

		boolean exist = false;
		int i;

		for(i = 0; i < listeProjet.size(); i++)
		{
			if(listeProjet.get(i).getName().equals(projectName))
			{
				exist = true;  	
				TaskMultiple pTask=null;
				for(TaskMultiple t : listeTask){
					if(t.getId() == id)
						pTask=t;	   				
				}
				if(pTask == null)
					System.out.println("Could not find the task with id "+id);
				else
					listeProjet.get(i).addTask(pTask);
			}
		}

		if (exist == false)
		{
			out.printf("Could not find a project with the name \"%s\".", projectName);
			out.println();
			return;
		}

	}

	/** Appelle setDone avec le paramètre true
	 * @param idString - String, ID de la TAsk
	 */
	private void check(String idString) 
	{
		setDone(idString, true);
	}

	/** Appelle setDone avec le paramètre false
	 * @param idString - String, ID de la task
	 */
	private void uncheck(String idString)
	{
		setDone(idString, false);
	}

	/** Modifie l'état de la tâche dont l'ID est passée en paramètre
	 * @param idString - String, ID de la task
	 * @param done - boolean
	 */
	private void setDone(String idString, boolean done)
	{
		Long id = Long.parseLong(idString);


		for(TaskMultiple t : listeTask)
		{
			if(t.getId() == id){
				t.setDone(done);

				//Si une tâche qui en contient d'autres est marquée comme finie, les tâches qu'elle contient doivent l'être aussi
				if(done){
					for(TaskMultiple task : t.getSousTaches()){
						task.setDone(done);
					}
				}
				return;
			}		

		}
		out.printf("Could not find a task with an ID of %d.", id);
		out.println();
	}

	/**
	 * Affiche le menu d'aide
	 */
	private void help() 
	{
		out.println("Commands:");
		out.println("  add project <project name>");
		out.println("  add task <Task id> <task description>");
		out.println("  add taskToTask <Task1 id> <task2 id> Task2 is added to Task1");
		out.println("  add taskToProject <project name> <task id>");
		out.println("  deadline <ID> <date>");
		out.println("  view by dead line");
		out.println("  view by date");
		out.println("  view by project");
		out.println("  check <task ID>");
		out.println("  uncheck <task ID>");
		out.println("  today");
		out.println("  delete");
		out.println();
	}	

	/** Est appelée quand la commande de l'utilisateur n'est pas comprise.
	 * @param command - String
	 */
	private void error(String command) 
	{
		out.printf("I don't know what the command \"%s\" is.", command);
		out.println();
	}


}
