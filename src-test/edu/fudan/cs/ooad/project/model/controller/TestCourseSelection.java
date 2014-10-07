package edu.fudan.cs.ooad.project.model.controller;


import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.fudan.cs.ooad.project.common.HibernateBaseTest;
import edu.fudan.cs.ooad.project.model.course.ConcreteCourse;
import edu.fudan.cs.ooad.project.model.course.Course;
import edu.fudan.cs.ooad.project.model.course.CourseBook;
import edu.fudan.cs.ooad.project.model.course.DropCourseRecord;
import edu.fudan.cs.ooad.project.model.course.Semester;
import edu.fudan.cs.ooad.project.model.user.AcademicDean;
import edu.fudan.cs.ooad.project.model.user.Student;


public class TestCourseSelection extends HibernateBaseTest{
	@Autowired
	private ICourseSelectionHandler courseSelectionHandler;

	private Initialization init;
	
	@Before
	public void initiate(){
		init=new Initialization(this.getPersistenceManager());
	}
	
	@Test
	public void testGetAvailableCourse(){
		Semester semester=init.getSemester();
		init.getAcademicDean().startCourseSelection(semester);
		Collection<ConcreteCourse> courseList=courseSelectionHandler.getAvailableCourse(init.getCourseBook(), 
				semester.getYear(), semester.getTerm());
		assertEquals(courseList.size(),3);
		
		
	}
	
	@Test
	public void testFollowCourse(){
		Semester semester=init.getSemester();
		AcademicDean academicDean=init.getAcademicDean();
		CourseBook courseBook=init.getCourseBook();
		academicDean.startCourseSelection(semester);
		boolean result=courseSelectionHandler.selectCourse(courseBook, init.getStudent2(), 
				init.getCourse1().getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,true);
		assertEquals(init.getStudent2().getSelectedCourses().size(),1);
		result=courseSelectionHandler.followCourse(courseBook, init.getStudent1(), 
				init.getCourse1().getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,true);
		assertEquals(init.getStudent1().getFollowedCourses().size(),1);
		result=courseSelectionHandler.followCourse(courseBook, init.getStudent1(), 
				init.getCourse3().getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,true);
		assertEquals(init.getStudent1().getFollowedCourses().size(),2);
	}
	
	@Test
	public void testUnfollowCourse(){
		Semester semester=init.getSemester();
		CourseBook courseBook=init.getCourseBook();
		Student student1=init.getStudent1();
		Course course3=init.getCourse3();
		init.getAcademicDean().startCourseSelection(semester);
		courseSelectionHandler.followCourse(courseBook, student1, 
				course3.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(student1.getFollowedCourses().size(),1);
		boolean result=courseSelectionHandler.unfollowCourse(courseBook, student1, 
				course3.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,true);
		assertEquals(student1.getFollowedCourses().size(),0);
	}
		
	@Test
	public void testSelectCourse(){
		Semester semester=init.getSemester();
		CourseBook courseBook=init.getCourseBook();
		Course course1=init.getCourse1();
		Student student1=init.getStudent1();
		init.getAcademicDean().startCourseSelection(semester);
		boolean result=courseSelectionHandler.selectCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,true);
		assertEquals(student1.getSelectedCourses().size(),1);
		
	}
		
	@Test
	public void testDropCourse(){
		Semester semester=init.getSemester();
		CourseBook courseBook=init.getCourseBook();
		Course course1=init.getCourse1();
		Student student1=init.getStudent1();
		ConcreteCourse concreteCourse1=init.getConcreteCourse1();
		init.getAcademicDean().startCourseSelection(semester);
		courseSelectionHandler.selectCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(student1.getSelectedCourses().size(),1);
		DropCourseRecord result=courseSelectionHandler.dropCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertObjectPersisted(result);
		assertEquals(result.getCourseInfo(),concreteCourse1);
		assertEquals(result.getStudentInfo(),student1);
		assertEquals(student1.getSelectedCourses().size(),0);
	}
		
	@Test
	public void testSelectionAfterFollow(){
		Semester semester=init.getSemester();
		CourseBook courseBook=init.getCourseBook();
		Course course1=init.getCourse1();
		Student student1=init.getStudent1();
		init.getAcademicDean().startCourseSelection(semester);
		boolean result=courseSelectionHandler.selectCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,true);
		assertEquals(student1.getSelectedCourses().size(),1);
		result=courseSelectionHandler.followCourse(courseBook, init.getStudent2(), 
				init.getCourse1().getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,true);
		assertEquals(init.getStudent2().getFollowedCourses().size(),1);
		courseSelectionHandler.dropCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		result=courseSelectionHandler.selectCourse(courseBook, init.getStudent2(), 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,true);
		assertEquals(init.getStudent2().getSelectedCourses().size(),1);
		assertEquals(init.getStudent2().getFollowedCourses().size(),0);
		
	}
}
