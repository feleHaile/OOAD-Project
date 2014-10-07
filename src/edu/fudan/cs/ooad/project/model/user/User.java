package edu.fudan.cs.ooad.project.model.user;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.Query;

import edu.fudan.cs.ooad.project.common.BaseModelObject;
import edu.fudan.cs.ooad.project.common.IPersistenceManager;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USERTYPE")
public abstract class User extends BaseModelObject{
	
	protected static boolean isExisted(String userName,
			IPersistenceManager persistenceManager){
		String hql = "from User u where u.userName  = :userName";
		Query query = persistenceManager.createQuery(hql)
				.setParameter("userName", userName);
		if( query.list().size()>0)
			return true;
		return false;
	}
	
	protected User(String userName,String password){
		this.userName=userName;
		this.password=password;
	}
	
	private String userName;
	
	private String password;
	
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
