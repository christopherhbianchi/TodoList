package data;

import java.util.List;

import entities.Todo;

public interface TodoDAO {
	//CRUD Functionality. 2 Reads. Create, Update, Delete.
	//can name these what they want. Restful naming conventions apply to the controller only
	
	//Reads
	public List<Todo> showAll(int userId);
	public Todo showById(int userId, int todoId);
	
	//CUD
	public Todo createTodo(String todoJSON, int userId);
	public Todo updateTodo(String todoJSON, int userId, int todoId);
	public boolean destroyTodo(int userId, int todoId);

}
