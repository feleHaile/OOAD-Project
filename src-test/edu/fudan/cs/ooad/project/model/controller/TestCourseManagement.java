package edu.fudan.cs.ooad.project.model.controller;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.fudan.cs.ooad.project.common.HibernateBaseTest;
import edu.fudan.cs.ooad.project.model.course.ConcreteCourse;
import edu.fudan.cs.ooad.project.model.course.Course;
import edu.fudan.cs.ooad.project.model.course.CourseBook;
import edu.fudan.cs.ooad.project.model.course.CoursesStatus;
import edu.fudan.cs.ooad.project.model.course.SchoolHour;
import edu.fudan.cs.ooad.project.model.course.Semester;
import edu.fudan.cs.ooad.project.model.user.AcademicDean;


public class TestCourseManagement extends HibernateBaseTest{
	@Autowired
	private ICourseManagementHandler courseManagementHandler;
	

	private Initialization init;
	
	
	@Before
	public void initiate(){
	
		init=new Initialization(this.getPersistenceManager());
	}
	
	@Test
	public void testModifyStudentNumLimit(){
		int studentNumLimit=10;
		Semester semester=init.getSemester();
		AcademicDean academicDean=init.getAcademicDean();
		boolean result=courseManagementHandler.modifyStudentNumLimit(init.getCourseBook(), 
				academicDean, init.getCourse1().getCourseID(), semester.getYear(), 
				semester.getTerm(),studentNumLimit );
		assertEquals(result,true);
		assertEquals(init.getConcreteCourse1().getStudentNumLimit(),studentNumLimit);
	}
	
	
	
	@Test
	public void testNewCourse(){
		Course course=courseManagementHandler.newCourse(init.getCourseBook(), 
				init.getAcademicDean(), "course10", "ics");
		assertObjectPersisted(course);
	}
	
	
	@Test
	public void testNewSemester(){
		Semester semester=courseManagementHandler.newSemester(init.getCourseBook(), 
				init.getAcademicDean(), 2014, 2);
		assertObjectPersisted(semester);
	}
	
	
	
	@Test
	public void testStartSelection(){
		Semester semester=Semester.create(2014, 2, getPersistenceManager());
		boolean result=courseManagementHandler.startCourseSelection(init.getCourseBook(), 
				init.getAcademicDean(), 2014, 2);
		assertEquals(result,true);
		assertEquals(semester.getStatus(),CoursesStatus.started);
	}
	
	
	@Test
	public void testCloseSelection(){
		Semester semester=Semester.create(2014, 2, getPersistenceManager());
		semester.setStatus(CoursesStatus.started);
		boolean result=courseManagementHandler.closeCourseSelection(init.getCourseBook(), 
				init.getAcademicDean(), 2014, 2);
		assertEquals(result,true);
		assertEquals(semester.getStatus(),CoursesStatus.finished);
	}
	
	
	
	@Test
	public void testRegisterConcreteCourse(){
		Semester semester=Semester.create(2014, 2, getPersistenceManager());
		Course course=Course.create("123", "234", getPersistenceManager());
		CourseBook courseBook=init.getCourseBook();
		ConcreteCourse concreteCourse=courseManagementHandler.registerConcreteCourse(courseBook, 
				init.getTeacher1(), course.getCourseID(), semester.getYear(), semester.getTerm(), "65756", new ArrayList<SchoolHour>(), 0);
		assertObjectPersisted(concreteCourse);
	}
	
	
	
}
