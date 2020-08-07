package com.javahelps.jpa;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Parameter;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Main2 {
	// Create an EntityManagerFactory when you start the application.
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
			.createEntityManagerFactory("JavaHelps");

	public static void main(String[] args) {

		// Create two Students
		create(1, "Alice", 22); // Alice will get an id 1
		ENTITY_MANAGER_FACTORY.close();
	}

	/**
	 * Create a new Student.
	 * 
	 * @param name
	 * @param age
	 */
	public static void create(int id, String name, int age) {
		// Create an EntityManager
		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			
			Query query  = manager.createNativeQuery("select * from employee where name =:name , age=:age");
			
			Set<Parameter<?>> set = query.getParameters();
			
			System.out.println(set);
			
		} catch (Exception ex) {
			// If there are any exceptions, roll back the changes
			if (transaction != null) {
				transaction.rollback();
			}
			// Print the Exception
			ex.printStackTrace();
		} finally {
			// Close the EntityManager
			manager.close();
		}
	}

	/**
	 * Read all the Students.
	 * 
	 * @return a List of Students
	 */
	public static List<Student> readAll() {

		List<Student> students = null;

		// Create an EntityManager
		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			// Get a transaction
			transaction = manager.getTransaction();
			// Begin the transaction
			transaction.begin();

			// Get a List of Students
			students = manager.createQuery("SELECT s FROM Student s", Student.class).getResultList();

			// Commit the transaction
			transaction.commit();
		} catch (Exception ex) {
			// If there are any exceptions, roll back the changes
			if (transaction != null) {
				transaction.rollback();
			}
			// Print the Exception
			ex.printStackTrace();
		} finally {
			// Close the EntityManager
			manager.close();
		}
		return students;
	}

	/**
	 * Delete the existing Student.
	 * 
	 * @param id
	 */
	public static void delete(int id) {
		// Create an EntityManager
		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			// Get a transaction
			transaction = manager.getTransaction();
			// Begin the transaction
			transaction.begin();

			// Get the Student object
			Student stu = manager.find(Student.class, id);

			// Delete the student
			manager.remove(stu);

			// Commit the transaction
			transaction.commit();
		} catch (Exception ex) {
			// If there are any exceptions, roll back the changes
			if (transaction != null) {
				transaction.rollback();
			}
			// Print the Exception
			ex.printStackTrace();
		} finally {
			// Close the EntityManager
			manager.close();
		}
	}

	/**
	 * Update the existing Student.
	 * 
	 * @param id
	 * @param name
	 * @param age
	 */
	public static void upate(int id, String name, int age) {
		// Create an EntityManager
		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;

		try {
			// Get a transaction
			transaction = manager.getTransaction();
			// Begin the transaction
			transaction.begin();

			// Get the Student object
			Student stu = manager.find(Student.class, id);

			// Change the values
			stu.setName(name);
			stu.setAge(age);

			// Update the student
			manager.persist(stu);

			// Commit the transaction
			transaction.commit();
		} catch (Exception ex) {
			// If there are any exceptions, roll back the changes
			if (transaction != null) {
				transaction.rollback();
			}
			// Print the Exception
			ex.printStackTrace();
		} finally {
			// Close the EntityManager
			manager.close();
		}
	}
}
