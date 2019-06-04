package com.infotech.people.management.app.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infotech.people.management.app.entities.Person;

@Repository
public interface PeopleManagementDao extends CrudRepository<Person, Integer> 
{
	//@Query(value = "SELECT p FROM Person p WHERE p.lastName=?1") //this is a JPQL query
	@Query(value = "SELECT * FROM person_table WHERE last_name=?1", nativeQuery = true)	//this is SQL native query
	List<Person> getPersonInfoByLastName(String lastName);
	
	//@Query(value = "SELECT p FROM Person p WHERE p.firstName=?1 AND p.email=?2")
	//@Query(value = "SELECT * FROM person_table WHERE first_name=?1 AND email=?2", nativeQuery = true)	//this is SQL native query
	//List<Person> findByFirstNameAndEmail(String firstName, String email);
	
	//example of Named Parameters
	@Query(value = "SELECT p FROM Person p WHERE p.firstName=:firstName AND p.email=:email")
	//@Query(value = "SELECT * FROM person_table WHERE first_name=?1 AND email=?2", nativeQuery = true)	//this is SQL native query
	List<Person> findByFirstNameAndEmail(@Param("firstName") String firstName, @Param("email") String email);

	@Transactional
	@Modifying(clearAutomatically = true) //it automatically flush all changes in EntityManager
	@Query(value = "UPDATE Person set email=:newEmail WHERE id=:personId")
	void updatePersonEmailById(@Param("personId") int id, @Param("newEmail") String newEmail);
}
