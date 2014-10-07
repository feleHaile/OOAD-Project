package edu.fudan.cs.ooad.project.model.user;

import org.hibernate.Query;
import org.junit.Test;

import edu.fudan.cs.ooad.project.common.HibernateBaseTest;

public class UserTest extends HibernateBaseTest{
	@Test
	public void testCreateTeacher() {
		Teacher result = Teacher.create("Tom","111","teacher1", getPersistenceManager());
		assertObjectPersisted(result);
	}
	
	@Test
	public void testDuplicateTeacher(){
		Teacher result = Teacher.create("Tom","111","teacher1", getPersistenceManager());
		assertObjectPersisted(result);
		result=Teacher.create("Tom","111","teacher1", getPersistenceManager());
		assertEquals(result,null);
	}
	
	@Test
	public void testFindTeacher() {
		Teacher result = Teacher.create("Tom","111","teacher1", getPersistenceManager());
		String hql = "from Teacher t where t.userName = 'Tom' "
				+ "and t.password = '111' "
				+ "and t.facultyID = 'teacher1'";
		Query query = getPersistenceManager().createQuery(hql);
		assertEquals(1, query.list().size());
		assertEquals(result, query.list().iterator().next());
	}
	
	@Test
	public void testCreateAcademicDean() {
		AcademicDean result = AcademicDean.create("Tom","111","ad1", getPersistenceManager());
		assertObjectPersisted(result);
	}
	
	@Test
	public void testDuplicateAcademicDean(){
		AcademicDean result = AcademicDean.create("Tom","111","ad", getPersistenceManager());
		assertObjectPersisted(result);
		result=AcademicDean.create("Tom","111","teacher1", getPersistenceManager());
		assertEquals(result,null);
	}
	
	@Test
	public void testFindAcademicDean() {
		AcademicDean result = AcademicDean.create("Tom","111","ad", getPersistenceManager());
		String hql = "from AcademicDean a where a.userName = 'Tom' "
				+ "and a.password = '111' "
				+ "and a.facultyID = 'ad'";
		Query query = getPersistenceManager().createQuery(hql);
		assertEquals(1, query.list().size());
		assertEquals(result, query.list().iterator().next());
	}
	
	@Test
	public void testCreateStudent() {
		Student result = Student.create("Tom","111","student1", getPersistenceManager());
		assertObjectPersisted(result);
	}
	
	@Test
	public void testDuplicateStudent(){
		Student result = Student.create("Tom","111","student1", getPersistenceManager());
		assertObjectPersisted(result);
		result=Student.create("Tom","111","teacher1", getPersistenceManager());
		assertEquals(result,null);
	}
	
	@Test
	public void testFindStudent() {
		Student result = Student.create("Tom","111","student1", getPersistenceManager());
		String hql = "from Student s where s.userName = 'Tom' "
				+ "and s.password = '111' "
				+ "and s.studentID = 'student1'";
		Query query = getPersistenceManager().createQuery(hql);
		assertEquals(1, query.list().size());
		assertEquals(result, query.list().iterator().next());
	}
	
	@Test
	public void testCreateAndFindUser(){
		Teacher teacher = Teacher.create("Tom","111","teacher1", getPersistenceManager());
		Student student = Student.create("Tom","111","student1", getPersistenceManager());
		AcademicDean academicDean = AcademicDean.create("Tom","111","ad", getPersistenceManager());
		assertObjectPersisted(teacher);
		assertObjectPersisted(student);
		assertObjectPersisted(academicDean);
		
		String hql1 = "from User u";
		int size = getPersistenceManager().createQuery(hql1).list().size();
		assertEquals(3, size);
		
		String hql2 = "from Student s";
		size = getPersistenceManager().createQuery(hql2).list().size();
		assertEquals(1, size);
		
		String hql3 = "from Teacher t";
		size = getPersistenceManager().createQuery(hql3).list().size();
		assertEquals(1, size);
		
		String hql4 = "from AcademicDean a";
		size = getPersistenceManager().createQuery(hql4).list().size();
		assertEquals(1, size);
	}
}
