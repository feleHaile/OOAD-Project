package edu.fudan.cs.ooad.project.model.course;

import org.junit.Test;

import edu.fudan.cs.ooad.project.common.HibernateBaseTest;

public class CourseTest  extends HibernateBaseTest{
	@Test
	public void testCourse() {
		Course course=Course.create("123", "234", getPersistenceManager());
		assertObjectPersisted(course);
		
	}
}
