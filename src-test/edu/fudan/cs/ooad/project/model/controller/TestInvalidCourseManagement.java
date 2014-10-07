package edu.fudan.cs.ooad.project.model.controller;

import java.util.ArrayList;
import java.util.Collection;

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
import edu.fudan.cs.ooad.project.model.course.WeekDay;
import edu.fudan.cs.ooad.project.model.user.AcademicDean;
import edu.fudan.cs.ooad.project.model.user.Teacher;

public class TestInvalidCourseManagement extends HibernateBaseTest{
	@Autowired
	private ICourseManagementHandler courseManagementHandler;
	

	private Initialization init;
	
	
	@Before
	public void initiate(){
	
		init=new Initialization(this.getPersistenceManager());
	}
	
	@Test 
	public void testInvalidStudentNumLimit(){
		int studentNumLimit=10;
		int studentNumLimit1=-1;
		int studentNumLimit2=1;
		AcademicDean academicDean=init.getAcademicDean();
		CourseBook courseBook=init.getCourseBook();
		Semester semester=init.getSemester();
		ConcreteCourse concreteCourse1 =init.getConcreteCourse1();
		courseManagementHandler.modifyStudentNumLimit(courseBook, 
				academicDean, concreteCourse1.getCourseID(), semester.getYear(), 
				semester.getTerm(),studentNumLimit );
		assertEquals(concreteCourse1.getStudentNumLimit(),studentNumLimit);
		//studentNumLimit<0
		boolean result1=courseManagementHandler.modifyStudentNumLimit(courseBook, 
				academicDean, concreteCourse1.getCourseID(), semester.getYear(), 
				semester.getTerm(),studentNumLimit1 );
		assertEquals(result1,false);
		assertEquals(concreteCourse1.getStudentNumLimit(),studentNumLimit);
		//studentNumLimit<actual selected number
		concreteCourse1.addSelectedStudent(init.getStudent1());
		concreteCourse1.addSelectedStudent(init.getStudent2());
		boolean result2=courseManagementHandler.modifyStudentNumLimit(courseBook, 
				academicDean, concreteCourse1.getCourseID(), semester.getYear(), 
				semester.getTerm(),studentNumLimit2 );
		assertEquals(result2,false);
		assertEquals(concreteCourse1.getStudentNumLimit(),studentNumLimit);
	}
	
	
	@Test
	public void testDuplicateNewCourse(){
		Course course1=courseManagementHandler.newCourse(init.getCourseBook(), 
				init.getAcademicDean(), "course10", "ics");
		assertObjectPersisted(course1);
		Course course2=courseManagementHandler.newCourse(init.getCourseBook(), 
				init.getAcademicDean(), "course10", "ics");
		assertEquals(course2,null);
	}
	
	
	@Test
	public void testDuplicateNewSemester(){
		Semester semester1=courseManagementHandler.newSemester(init.getCourseBook(), 
				init.getAcademicDean(), 2014, 2);
		assertObjectPersisted(semester1);
		Semester semester2=courseManagementHandler.newSemester(init.getCourseBook(), 
				init.getAcademicDean(), 2014, 2);
		assertEquals(semester2,null);
	}
	

	@Test
	public void testStartInvalidSelection(){
		Semester semester=Semester.create(2014, 2, getPersistenceManager());
		semester.setStatus(CoursesStatus.started);
		//already satrted
		boolean result1=courseManagementHandler.startCourseSelection(init.getCourseBook(), 
				init.getAcademicDean(), 2014, 2);
		assertEquals(result1,false);
		assertEquals(semester.getStatus(),CoursesStatus.started);
		semester.setStatus(CoursesStatus.finished);
		//already finished
		boolean result2=courseManagementHandler.startCourseSelection(init.getCourseBook(), 
				init.getAcademicDean(), 2014, 2);
		assertEquals(result2,false);
		assertEquals(semester.getStatus(),CoursesStatus.finished);
	}
	
	@Test
	public void testCloseInvalidSelection(){
		//already finished
		Semester semester=Semester.create(2014, 2, getPersistenceManager());
		semester.setStatus(CoursesStatus.finished);
		boolean result=courseManagementHandler.closeCourseSelection(init.getCourseBook(), 
				init.getAcademicDean(), 2014, 2);
		assertEquals(result,false);
		assertEquals(semester.getStatus(),CoursesStatus.finished);
	}
	
	@Test 
	public void testRegisterDuplicateConcreteCourse(){
		Semester semester=Semester.create(2014, 2, getPersistenceManager());
		Course course1=Course.create("123", "234", getPersistenceManager());
		CourseBook courseBook=init.getCourseBook();
		ConcreteCourse concreteCourse=courseManagementHandler.registerConcreteCourse(courseBook, 
				init.getTeacher1(), course1.getCourseID(), semester.getYear(), semester.getTerm(), "5565", new ArrayList<SchoolHour>(), 0);
		assertObjectPersisted(concreteCourse);
		ConcreteCourse concreteCourse2=courseManagementHandler.registerConcreteCourse(courseBook, 
				init.getTeacher1(), course1.getCourseID(), semester.getYear(), semester.getTerm(), "6867", new ArrayList<SchoolHour>(), 0);
		assertEquals(concreteCourse2,null);
	}
	
	
	@Test
	public void testRegisterInvalidConcreteCourse(){
		Semester semester;
		AcademicDean academicDean=init.getAcademicDean();
		Teacher teacher1 = Teacher.create("Bonnie","1120","teacher10", getPersistenceManager());
		Teacher teacher2 = Teacher.create("Clyde","1130","teacher20", getPersistenceManager());
		Course course1,course2,course3;
		CourseBook courseBook=init.getCourseBook();
		SchoolHour schoolHour1,schoolHour2,schoolHour3,schoolHour4;
		Collection<SchoolHour> schoolHours1,schoolHours2,schoolHours3;
		semester=academicDean.newSemester(2014, 2, getPersistenceManager());
		course1=academicDean.newCourse("course10", "ics", getPersistenceManager());
		course2=academicDean.newCourse("course20", "dm", getPersistenceManager());
		course3=academicDean.newCourse("course30", "dbms", getPersistenceManager());
		schoolHour1=SchoolHour.create(1, 3, WeekDay.Mon, getPersistenceManager());
		schoolHour2=SchoolHour.create(2, 4, WeekDay.Mon, getPersistenceManager());
		schoolHour3=SchoolHour.create(1, 3, WeekDay.Tue, getPersistenceManager());
		schoolHour4=SchoolHour.create(2, 4, WeekDay.Mon, getPersistenceManager());
		schoolHours1=new ArrayList<SchoolHour>();
		schoolHours2=new ArrayList<SchoolHour>();
		schoolHours3=new ArrayList<SchoolHour>();
		schoolHours1.add(schoolHour1);
		schoolHours2.add(schoolHour2);
		schoolHours3.add(schoolHour3);
		schoolHours3.add(schoolHour4);
		//invalid time
		courseManagementHandler.registerConcreteCourse(courseBook, teacher1, course1.getCourseID(),
				semester.getYear(), semester.getTerm(), "1222", schoolHours1, 1);
		courseManagementHandler.registerConcreteCourse(courseBook, teacher1, course2.getCourseID(),
				semester.getYear(), semester.getTerm(), "12134", schoolHours2, 2);
		courseManagementHandler.registerConcreteCourse(courseBook, teacher1, course3.getCourseID(),
				semester.getYear(), semester.getTerm(), "4534", schoolHours3, 2);
		assertEquals(teacher1.getCourses().size(),1);
		//invalid classroom
		courseManagementHandler.registerConcreteCourse(courseBook, teacher2, course2.getCourseID(),
				semester.getYear(), semester.getTerm(), "1222", schoolHours1, 2);
		assertEquals(teacher2.getCourses().size(),0);
	}
}
