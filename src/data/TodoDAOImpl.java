package data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Todo;
import entities.User;

@Repository
@Transactional
public class TodoDAOImpl implements TodoDAO{

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<Todo> showAll(int userId) {
		String query = "SELECT t FROM Todo t WHERE user.id= :id"; //JPQL
		return em.createQuery(query, Todo.class)
				.setParameter("id", userId)
				.getResultList();
	}

	@Override
	public Todo showById(int userId, int todoId) {
		Todo t = em.find(Todo.class, userId);
		
		if(t.getUser().getId() == userId) {
			return t;
		}
		else{
			return null;
		}
	}

	@Override
	public Todo createTodo(String todoJSON, int userId) {
		ObjectMapper mapper = new ObjectMapper();
		User u = em.find(User.class, userId);
		
		try {
			Todo t = mapper.readValue(todoJSON, Todo.class);
			t.setCreatedAt(new java.sql.Date(new java.util.Date().getTime()));
			t.setCompleted("false");
			t.setUser(u);
			em.persist(t);
			em.flush();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Todo updateTodo(String todoJSON, int userId, int todoId) {
		ObjectMapper mapper = new ObjectMapper();
		Todo t = em.find(Todo.class, todoId);
//		User u = em.find(User.class, userId) //don't need this step because the todo should already exist in the db. don't need to do this to set the user regardless.
//		can just do our check the other way after this.
	
		try {
			
			Todo newTodo = mapper.readValue(todoJSON, Todo.class);
			if(t.getUser().getId() == userId) {
				t.setCompleted(newTodo.isCompleted()); //return to this later after we know all of the things we will want to update in a given update
				/*
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 */
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		

		return null;
	}

	@Override
	public boolean destroyTodo(int userId, int todoId) {
		Todo t = em.find(Todo.class, todoId);
		//if the appropriate user is trying to delete this Todo, then do it
		//we can grab the userId as a path variable by the way from the controller
		if(t.getUser().getId() == userId) {
			em.remove(t);
			return true;
		}
		
		return false;
	}

}
