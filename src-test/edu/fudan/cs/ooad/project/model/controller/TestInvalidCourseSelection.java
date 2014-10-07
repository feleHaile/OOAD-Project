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

public class TestInvalidCourseSelection extends HibernateBaseTest{
	@Autowired
	private ICourseSelectionHandler courseSelectionHandler;

	private Initialization init;
	
	@Before
	public void initiate(){
		init=new Initialization(this.getPersistenceManager());
	}
	
	@Test
	public void testInvalidGetAvailableCourse(){
		Semester semester=init.getSemester();
		CourseBook courseBook=init.getCourseBook();
		AcademicDean academicDean=init.getAcademicDean();
		Collection<ConcreteCourse> courseList=courseSelectionHandler.getAvailableCourse(courseBook, 
				semester.getYear(), semester.getTerm());
		assertEquals(courseList.size(),0);
		academicDean.startCourseSelection(semester);
		courseList=courseSelectionHandler.getAvailableCourse(courseBook, 
				semester.getYear(), semester.getTerm());
		assertEquals(courseList.size(),3);
		//Semester closed
		academicDean.closeCourseSelection(semester);
		courseList=courseSelectionHandler.getAvailableCourse(courseBook, 
				semester.getYear(), semester.getTerm());
		assertEquals(courseList.size(),0);
		//non-existed semester
		courseList=courseSelectionHandler.getAvailableCourse(courseBook, 
				2014, 1);
		assertEquals(courseList.size(),0);
		
	}
	
	@Test
	public void testFollowInvalidCourse(){
		Semester semester=init.getSemester();
		CourseBook courseBook=init.getCourseBook();
		Student student1=init.getStudent1();
		Course course1=init.getCourse1();
		Course course2=init.getCourse2();
		Course course3=init.getCourse3();
		//Semester not started
		boolean result=courseSelectionHandler.followCourse(courseBook, student1, 
				course3.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student1.getFollowedCourses().size(),0);
		init.getAcademicDean().startCourseSelection(semester);
		//Course selectable
		result=courseSelectionHandler.followCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student1.getFollowedCourses().size(),0);
		//Follow a Course
		result=courseSelectionHandler.followCourse(courseBook, student1, 
				course3.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,true);
		assertEquals(student1.getFollowedCourses().size(),1);
		//Course that has already been followed
		result=courseSelectionHandler.followCourse(courseBook, student1, 
				course3.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student1.getFollowedCourses().size(),1);
		//Non-existed course
		result=courseSelectionHandler.followCourse(courseBook, student1, 
				"xxx", semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student1.getFollowedCourses().size(),1);
		//Time conflict
		result=courseSelectionHandler.followCourse(courseBook, student1, 
				course2.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student1.getFollowedCourses().size(),1);
	}
	
	@Test
	public void testUnfollowInvalidCourse(){
		Semester semester=init.getSemester();
		CourseBook courseBook=init.getCourseBook();
		Course course3=init.getCourse3();
		Student student1=init.getStudent1();
		init.getAcademicDean().startCourseSelection(semester);
		courseSelectionHandler.followCourse(courseBook, student1, 
				course3.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(student1.getFollowedCourses().size(),1);
		boolean result=courseSelectionHandler.unfollowCourse(courseBook, student1, 
				course3.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,true);
		assertEquals(student1.getFollowedCourses().size(),0);
		//Unfollow courses that have not been followed
		result=courseSelectionHandler.unfollowCourse(courseBook, student1, 
				course3.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student1.getFollowedCourses().size(),0);
		//Semester closed
		courseSelectionHandler.followCourse(courseBook, student1, 
				course3.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(student1.getFollowedCourses().size(),1);
		init.getAcademicDean().closeCourseSelection(semester);
		
		
		result=courseSelectionHandler.unfollowCourse(courseBook, student1, 
				course3.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student1.getFollowedCourses().size(),1);
	}
	
	@Test
	public void testSelectInvalidCourse(){
		//Semester not started
		Semester semester=init.getSemester();
		Course course1=init.getCourse1();
		Course course2=init.getCourse2();
		Student student1=init.getStudent1();
		Student student2=init.getStudent2();
		CourseBook courseBook=init.getCourseBook();
		boolean result=courseSelectionHandler.selectCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student1.getSelectedCourses().size(),0);
		init.getAcademicDean().startCourseSelection(semester);
		//Select a course
		result=courseSelectionHandler.selectCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,true);
		assertEquals(student1.getSelectedCourses().size(),1);
		//Exceed limit
		result=courseSelectionHandler.selectCourse(courseBook, student2, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student2.getSelectedCourses().size(),0);
		//Course that has already been followed
		result=courseSelectionHandler.selectCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student1.getSelectedCourses().size(),1);
		//Non-existed course
		result=courseSelectionHandler.selectCourse(courseBook, student1, 
				"xxx", semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student1.getSelectedCourses().size(),1);
		//Time conflict
		result=courseSelectionHandler.selectCourse(courseBook, student1, 
				course2.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,false);
		assertEquals(student1.getSelectedCourses().size(),1);
		
	}
	
	@Test
	public void testDropInvalidCourse(){
		Semester semester=init.getSemester();
		CourseBook courseBook=init.getCourseBook();
		Course course1=init.getCourse1();
		Student student1=init.getStudent1();
		ConcreteCourse concreteCourse1=init.getConcreteCourse1();
		init.getAcademicDean().startCourseSelection(semester);
		courseSelectionHandler.selectCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(student1.getSelectedCourses().size(),1);
		DropCourseRecord result;
		courseSelectionHandler.dropCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(student1.getSelectedCourses().size(),0);
		assertEquals(concreteCourse1.getSelectedStudents().size(),0);

		//Drop courses that have not been followed
		result=courseSelectionHandler.dropCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,null);
		assertEquals(student1.getSelectedCourses().size(),0);
		//Semester closed
		boolean tmp=courseSelectionHandler.selectCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(tmp,true);
		init.getAcademicDean().closeCourseSelection(semester);
		
		
		result=courseSelectionHandler.dropCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(result,null);
		assertEquals(student1.getSelectedCourses().size(),1);
	}
}
