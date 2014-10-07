package edu.fudan.cs.ooad.project.model.controller;

import org.junit.Test;

import edu.fudan.cs.ooad.project.common.HibernateBaseTest;

public class TestInitialization extends HibernateBaseTest{
	@Test
	public void testInitialization(){
		Initialization init=new Initialization(getPersistenceManager());
		assertObjectPersisted(init.getAcademicDean());
		assertObjectPersisted(init.getConcreteCourse1());
		assertObjectPersisted(init.getConcreteCourse2());
		assertObjectPersisted(init.getConcreteCourse3());
		assertObjectPersisted(init.getCourse1());
		assertObjectPersisted(init.getCourse2());
		assertObjectPersisted(init.getCourse3());
		assertObjectPersisted(init.getSchoolHour1());
		assertObjectPersisted(init.getSchoolHour2());
		assertObjectPersisted(init.getSchoolHour3());
		assertObjectPersisted(init.getSchoolHour4());
		assertObjectPersisted(init.getSemester());
		assertObjectPersisted(init.getStudent1());
		assertObjectPersisted(init.getStudent2());
		assertObjectPersisted(init.getTeacher1());
		assertObjectPersisted(init.getTeacher2());
		assertObjectPersisted(init.getTeacher3());
		
	}
}
