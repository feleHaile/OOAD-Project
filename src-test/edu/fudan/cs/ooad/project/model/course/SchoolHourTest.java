package edu.fudan.cs.ooad.project.model.course;

import org.junit.Test;

import edu.fudan.cs.ooad.project.common.HibernateBaseTest;

public class SchoolHourTest extends HibernateBaseTest{
	@Test
	public void testCreateSchoolHour() {
		SchoolHour sh=SchoolHour.create(1, 3, WeekDay.Mon, getPersistenceManager());;
		assertObjectPersisted(sh);
		
	}
}
