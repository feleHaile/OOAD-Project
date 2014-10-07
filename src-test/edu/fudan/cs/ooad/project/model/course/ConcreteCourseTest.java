package edu.fudan.cs.ooad.project.model.course;

import org.hibernate.Query;
import org.junit.Test;
import edu.fudan.cs.ooad.project.common.HibernateBaseTest;

public class ConcreteCourseTest extends HibernateBaseTest{
	@Test
	public void testCreateCourse() {
		Course course=Course.create("123", "234", getPersistenceManager());
		ConcreteCourse concreteCourse = ConcreteCourse.create(course,null,null,
				getPersistenceManager());
		course.getCourses().add(concreteCourse);

		assertObjectPersisted(concreteCourse);
		
	}
	
	public void testFindCourse(){
		Course course=Course.create("123", "234", getPersistenceManager());
		ConcreteCourse concreteCourse = ConcreteCourse.create(course,null,null,
				getPersistenceManager());
		course.getCourses().add(concreteCourse);
		String hql1 = "from ConcreteCourse c where c.courseInfo.courseID= :id";
		Query query= getPersistenceManager().createQuery(hql1).setParameter("id", "123");
		
		assertEquals(1, query.list().size());
	}
	
}
