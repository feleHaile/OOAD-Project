package edu.fudan.cs.ooad.project.model.course;

import org.junit.Test;

import edu.fudan.cs.ooad.project.common.HibernateBaseTest;

public class SemesterTest extends HibernateBaseTest{
	@Test
	public void testSemester() {
		Semester sm=Semester.create(2013, 1, this.getPersistenceManager());
		assertObjectPersisted(sm);
		
	}
}
