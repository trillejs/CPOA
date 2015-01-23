package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.codurance.training.projects.Project;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final BufferedReader in;
    private final PrintWriter out;

    private long lastId = 0;
    private ArrayList<Project> listeProjet;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer)
    {
        this.in = reader;
        this.out = writer;
        this.listeProjet = new ArrayList<Project>();
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

    private void execute(String commandLine)
    {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        
        switch (command) 
        {
            case "show":
                show();
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
            	if( commandRest[1].equals("by deadline"));
            		viewByDeadLine();
            	break;
            case "help":
                help();
                break;
            default:
                error(command);
                break;
        }
    }

    private void show() 
    {	
    	for(int i = 0; i < listeProjet.size(); i++)
    	{
    		for(int j = 0; j < listeProjet.get(i).getListTask().size(); j++)
    		{
    			TaskMultiple tache = listeProjet.get(i).getListTask().get(j);
    			out.printf("    [%c] %d: %s%n", (tache.isDone() ? 'x' : ' '), tache.getId(), tache.getDescription());
    			out.println(" deadLine : " + tache.getDeadLine());
    		}
    		
    		for(int j = 0; j < listeProjet.get(i).getListTaskDone().size(); j++)
    		{
    			TaskMultiple tache = listeProjet.get(i).getListTaskDone().get(j);
    			out.printf("    [%c] %d: %s%n", (tache.isDone() ? 'x' : ' '), tache.getId(), tache.getDescription());
    			out.println(" deadLine : " + tache.getDeadLine());
    		}
    		
    	}
    }
    
    public void today()
    {
    	for(int i = 0; i < listeProjet.size(); i++)
    	{	
    		for(int j = 0; j < listeProjet.get(i).getListTask().size(); j++)
    		{
    			if(listeProjet.get(i).getListTask().get(j).getDeadLine() != null)
    			{
        			if( listeProjet.get(i).getListTask().get(j).getDeadLine().getDayOfWeek() == DateTime.now().getDayOfWeek() )
        			{
        				if(j == 0)
        					out.println("Projet n° : " + listeProjet.get(i).getName());
        				
        				out.print(" tache : " + listeProjet.get(i).getListTask().get(j).getId());
        				out.println(" | description : " + listeProjet.get(i).getListTask().get(j).getDescription());
        			}
    			}			
    		}
    	}
    }
    
    public void deadline(long pId, String commandLine)
    {
    	
    	 String[] date = commandLine.split("/", 3);
    	 out.print(date[0] + "/" + date[1] + "/" + date[2] + " ");
    	 DateTime deadLine = new DateTime( Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]), 0, 0);
    	 boolean exist = false;
    	
    	for(int i = 0; i < listeProjet.size(); i++)
    	{
    		int j = listeProjet.get(i).getListTask().size();
    		
    		while( j > 0 )
    		{
    			if( listeProjet.get(i).getListTask().get(j-1).getId() == pId )
    			{
    				listeProjet.get(i).getListTask().get(j-1).setDeadLine(deadLine);
    				out.println(listeProjet.get(i).getListTask().get(j-1).getDeadLine());
    				j = 0;
    				exist = true;
    			}
    			
    			j--;
    			
    		}
    	}
    	
    	if( exist == false )
    		out.println("Aucun projet ayant cet ID");
    }
    
    public void viewByDeadLine()
    {
    	ArrayList<DateTime> listeDeadLine = new ArrayList<DateTime>();
    	
    	for(int i = 0; i < listeProjet.size(); i++)
    	{
    		for(int j = 0; j < listeProjet.get(i).getListTask().size(); j++)
    		{
    			if( listeProjet.get(i).getListTask().get(j).getDeadLine() != null )
    			{
    				int k = listeDeadLine.size();
    				
    				do
    				{
    					//On vérifie si la liste n'est pas vide avant de devoir comparer des DateTime
    					
    					if( listeDeadLine.size() == 0 )
    						listeDeadLine.add(listeProjet.get(i).getListTask().get(j).getDeadLine());
    					else
    					{
    						if( listeProjet.get(i).getListTask().get(j).getDeadLine().isAfter(listeDeadLine.get(k-1)) )
    						{
    							listeDeadLine.add(k, listeProjet.get(i).getListTask().get(j).getDeadLine());
    							out.print(" | " +  k +" ");
    							out.print(listeProjet.get(i).getListTask().get(j).getDeadLine());
    							k = 0;
    						}
    						else if( k - 1 == 0 )
    							listeDeadLine.add(k - 1, listeProjet.get(i).getListTask().get(j).getDeadLine());
    					}
    						
    					k--;
    				}
    				while( k > 0 );
    			}
    		}
    	}
    	
		// un fois que toutes les taches avec deadLine sont dans notre List on affiche
		
		for( int i = 0; i < listeDeadLine.size(); i++)
		{
			out.println(listeDeadLine.get(i).toString());
			out.println(listeDeadLine.get(i).getDayOfWeek() + "/" + listeDeadLine.get(i).getMonthOfYear() + "/" + listeDeadLine.get(i).getYearOfCentury());
		} 	
    }

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
            addTask(projectTask[0], projectTask[1]);
        }
    }

    private void addProject(String pName) 
    {
    	listeProjet.add(new Project(pName));
    }

    private void addTask(String projectName, String description) 
    {
    	
    	boolean exist = false;
    	int i;
    	
    	for(i = 0; i < listeProjet.size(); i++)
    	{
    		if(listeProjet.get(i).getName().equals(projectName))
    		{
    			exist = true;  			
    			listeProjet.get(i).getListTask().add(new TaskMultiple(this.nextId(), description, false));
    		}
    	}
    	
        if (exist == false)
        {
            out.printf("Could not find a project with the name \"%s\".", projectName);
            out.println();
            return;
        }
        
    }

    private void check(String idString) 
    {
        setDone(idString, true);
    }

    private void uncheck(String idString)
    {
        setDone(idString, false);
    }

    private void setDone(String idString, boolean done)
    {
        int id = Integer.parseInt(idString);
        
	        for(int i = 0; i < listeProjet.size(); i++)
	    	{
	    		for(int j = 0; j < listeProjet.get(i).getListTask().size(); j++)
	    		{
	    			TaskMultiple tache = listeProjet.get(i).getListTask().get(j);
	    			if( tache.getId() == id )
	    			{
	    				tache.setDone(done);
	    				return;
	    			}	
	    		}
	    		
	    		for(int j = 0; j < listeProjet.get(i).getListTaskDone().size(); j++)
	    		{
	    			TaskMultiple tache = listeProjet.get(i).getListTaskDone().get(j);
	    			
	    			if( tache.getId() == id )
	    			{
	    				tache.setDone(done);
	    				return;
	    			}
	    		}
	    		
	    	}
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }

    private void help() 
    {
        out.println("Commands:");
        out.println("  show");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task description>");
        out.println("  deadline <ID> <date>");
        out.println("  view by dead line");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println("  today");
        out.println("  delete");
        out.println();
    }	

    private void error(String command) 
    {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }

    private long nextId()
    {
        return ++lastId;
    }
}
