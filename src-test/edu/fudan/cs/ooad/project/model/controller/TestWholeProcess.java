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
import edu.fudan.cs.ooad.project.model.course.DropCourseRecord;
import edu.fudan.cs.ooad.project.model.course.SchoolHour;
import edu.fudan.cs.ooad.project.model.course.Semester;
import edu.fudan.cs.ooad.project.model.course.WeekDay;
import edu.fudan.cs.ooad.project.model.user.AcademicDean;
import edu.fudan.cs.ooad.project.model.user.Student;
import edu.fudan.cs.ooad.project.model.user.Teacher;

public class TestWholeProcess extends HibernateBaseTest{
	@Autowired
	private ICourseManagementHandler courseManagementHandler;
	@Autowired
	private ICourseSelectionHandler courseSelectionHandler;
	private CourseBook courseBook;
	private Student student1,student2,student3;
	private AcademicDean academicDean;
	private Teacher teacher1,teacher2,teacher3;
	private SchoolHour schoolHour1,schoolHour2,schoolHour3,schoolHour4;
	private Collection<SchoolHour> schoolHours1,schoolHours2,schoolHours3;
	@Before
	public void initiate(){
		courseBook=new CourseBook(getPersistenceManager());
		student1 = Student.create("Bonnie","111","student1", getPersistenceManager());
		student2 = Student.create("Clyder","117","student2", getPersistenceManager());
		student3 = Student.create("Tommy","119","student3", getPersistenceManager());
		academicDean = AcademicDean.create("Tom","111","ad1", 
				getPersistenceManager());
		teacher1 = Teacher.create("Jerry","112","teacher1", getPersistenceManager());
		teacher2 = Teacher.create("Jack","113","teacher2", getPersistenceManager());
		teacher3 = Teacher.create("Rose","114","teacher3", getPersistenceManager());
		schoolHour1=SchoolHour.create(1, 3, WeekDay.Mon, getPersistenceManager());
		schoolHour2=SchoolHour.create(2, 4, WeekDay.Mon, getPersistenceManager());
		schoolHour3=SchoolHour.create(1, 3, WeekDay.Tue, getPersistenceManager());
		schoolHour4=SchoolHour.create(4, 6, WeekDay.Mon, getPersistenceManager());
		schoolHours1=new ArrayList<SchoolHour>();
		schoolHours2=new ArrayList<SchoolHour>();
		schoolHours3=new ArrayList<SchoolHour>();
		schoolHours1.add(schoolHour1);
		schoolHours2.add(schoolHour2);
		schoolHours3.add(schoolHour3);
		schoolHours3.add(schoolHour4);
	}
	@Test
	public void testWholeProcess(){
		//new semester
		Semester semester=courseManagementHandler.newSemester(courseBook, 
				academicDean, 2013, 2);
		assertObjectPersisted(semester);
		//add course
		Course course1=courseManagementHandler.newCourse(courseBook, 
				academicDean, "course1", "ics");
		Course course2=courseManagementHandler.newCourse(courseBook, 
				academicDean, "course2", "ooad");
		Course course3=courseManagementHandler.newCourse(courseBook, 
				academicDean, "course3", "dbms");
		assertObjectPersisted(course1);
		assertObjectPersisted(course2);
		assertObjectPersisted(course3);
		//register 3 concreteCourse
		int studentNumLimit1=10;
		String classroom1="3108";
		ConcreteCourse concreteCourse1=courseManagementHandler.registerConcreteCourse(courseBook, 
				teacher1, course1.getCourseID(), semester.getYear(), semester.getTerm(), classroom1, 
				schoolHours1, studentNumLimit1);
		int studentNumLimit2=1;
		String classroom2="3408";
		ConcreteCourse concreteCourse2=courseManagementHandler.registerConcreteCourse(courseBook, 
				teacher2, course2.getCourseID(), semester.getYear(), semester.getTerm(), classroom2, 
				schoolHours2, studentNumLimit2);
		int studentNumLimit3=10;
		String classroom3="3308";
		ConcreteCourse concreteCourse3=courseManagementHandler.registerConcreteCourse(courseBook, 
				teacher3, course3.getCourseID(), semester.getYear(), semester.getTerm(), classroom3, 
				schoolHours3, studentNumLimit3);
		assertObjectPersisted(concreteCourse1);
		assertObjectPersisted(concreteCourse2);
		assertObjectPersisted(concreteCourse3);
		//start new selection
		boolean startSemesterResult=courseManagementHandler.startCourseSelection(courseBook, 
				academicDean, 2013, 2);
		assertEquals(startSemesterResult,true);
		//select course
		boolean selectResult10=courseSelectionHandler.selectCourse(courseBook, student1, 
				course1.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(selectResult10,true);
		assertEquals(student1.getSelectedCourses().size(),1);
		boolean selectResult11=courseSelectionHandler.selectCourse(courseBook, student1, 
				course2.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(selectResult11,false);
		boolean selectResult20=courseSelectionHandler.selectCourse(courseBook, student2, 
				course2.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(selectResult20,true);
		assertEquals(student2.getSelectedCourses().size(),1);
		boolean selectResult30=courseSelectionHandler.selectCourse(courseBook, student3, 
				course2.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(selectResult30,false);
		boolean followResult30=courseSelectionHandler.followCourse(courseBook, student3, 
				course2.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(followResult30,true);
		assertEquals(student3.getFollowedCourses().size(),1);
		//drop course
		DropCourseRecord dropCourse20=courseSelectionHandler.dropCourse(courseBook, student2,
				course2.getCourseID(), semester.getYear(), semester.getTerm());
		assertObjectPersisted(dropCourse20);
		assertEquals(student2.getSelectedCourses().size(),0);
		boolean selectResult31=courseSelectionHandler.selectCourse(courseBook, student3, 
				course2.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(selectResult31,true);
		assertEquals(student3.getSelectedCourses().size(),1);
		assertEquals(student3.getFollowedCourses().size(),0);
		//follow course
		boolean followResult20=courseSelectionHandler.followCourse(courseBook, student2, 
				course2.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(followResult20,true);
		assertEquals(student2.getFollowedCourses().size(),1);
		//unfollow course
		boolean unFollowResult20=courseSelectionHandler.unfollowCourse(courseBook, student2, 
				course2.getCourseID(), semester.getYear(), semester.getTerm());
		assertEquals(unFollowResult20,true);
		assertEquals(student2.getFollowedCourses().size(),0);
		boolean closeResult=courseManagementHandler.closeCourseSelection(courseBook, 
				academicDean, semester.getYear(), semester.getTerm());
		assertEquals(closeResult,true);
		assertEquals(semester.getStatus(),CoursesStatus.finished);
	}

}
