package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import data.TodoDAO;
import entities.Todo;

@RestController
public class TodoController {
	
	@Autowired
	private TodoDAO dao;
	
	/* Time to build the controller which will handle requests sent from the view, grab appropriate data,
	 * and pass it along to our DAOImpl to execute database logic for us.
	 * Need to handle the 2 reads, and the CUD functions
	 * The controller is where our method path naming, and actual method naming are part of RESTful practices
	 * Plurals in routes, and appropriate names for each type of HTTPRequest that the method handles ("index" for the "GET" and showing all of them)
	 */
	
	@RequestMapping(path="{userId}/todos", method=RequestMethod.GET)
	public List<Todo> index(@PathVariable int userId){
		return dao.showAll(userId);
	}
	
	@RequestMapping(path="{userId}/todos/{todoId}", method=RequestMethod.GET)
	public Todo show(@PathVariable int userId, @PathVariable int todoId) {
		return dao.showById(userId, todoId);
	}
	
	@RequestMapping(path="{userId}/todos/", method=RequestMethod.POST)
	public Todo create(@PathVariable int userId, @RequestBody String todoJSON) {
		return dao.createTodo(todoJSON, userId);
	}
	
	@RequestMapping(path="{userId}/todos/{todoId}", method=RequestMethod.PUT)
	public Todo update(@PathVariable int userId, @PathVariable int todoId, @RequestBody String todoJSON) {
		return dao.updateTodo(todoJSON, userId, todoId);
	}
	
	@RequestMapping(path="{userId}/todos/{todoId}", method=RequestMethod.DELETE)
	public boolean destroy(@PathVariable int userId, @PathVariable int todoId) {
		return dao.destroyTodo(userId, todoId);
	}

}
