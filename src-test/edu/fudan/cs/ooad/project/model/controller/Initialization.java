package edu.fudan.cs.ooad.project.model.controller;

import java.util.ArrayList;
import java.util.Collection;

import edu.fudan.cs.ooad.project.common.IPersistenceManager;
import edu.fudan.cs.ooad.project.model.course.ConcreteCourse;
import edu.fudan.cs.ooad.project.model.course.Course;
import edu.fudan.cs.ooad.project.model.course.CourseBook;
import edu.fudan.cs.ooad.project.model.course.SchoolHour;
import edu.fudan.cs.ooad.project.model.course.Semester;
import edu.fudan.cs.ooad.project.model.course.WeekDay;
import edu.fudan.cs.ooad.project.model.user.AcademicDean;
import edu.fudan.cs.ooad.project.model.user.Student;
import edu.fudan.cs.ooad.project.model.user.Teacher;

public class Initialization {

	
	public Initialization(IPersistenceManager persistenceManager){
		courseBook=new CourseBook(persistenceManager);
		student1 = Student.create("Tom","111","student1", persistenceManager);
		student2 = Student.create("Tommy","111","student2", persistenceManager);
		academicDean = AcademicDean.create("Tom","111","ad1", 
				persistenceManager);
		teacher1 = Teacher.create("Jerry","112","teacher1", persistenceManager);
		teacher2 = Teacher.create("Jack","113","teacher2", persistenceManager);
		teacher3 = Teacher.create("Rose","114","teacher3", persistenceManager);
		semester=academicDean.newSemester(2013, 2, persistenceManager);
		course1=academicDean.newCourse("course1", "ooad", persistenceManager);
		course2=academicDean.newCourse("course2", "music", persistenceManager);
		course3=academicDean.newCourse("course3", "art", persistenceManager);
		schoolHour1=SchoolHour.create(1, 3, WeekDay.Mon, persistenceManager);
		schoolHour2=SchoolHour.create(2, 4, WeekDay.Mon, persistenceManager);
		schoolHour3=SchoolHour.create(1, 3, WeekDay.Tue, persistenceManager);
		schoolHour4=SchoolHour.create(4, 6, WeekDay.Mon, persistenceManager);
		schoolHours1=new ArrayList<SchoolHour>();
		schoolHours2=new ArrayList<SchoolHour>();
		schoolHours3=new ArrayList<SchoolHour>();
		schoolHours1.add(schoolHour1);
		schoolHours2.add(schoolHour2);
		schoolHours3.add(schoolHour3);
		schoolHours3.add(schoolHour4);
		
		concreteCourse1=teacher1.registCourse(course1, semester,"3108",schoolHours1, 1,courseBook, persistenceManager);
		concreteCourse2=teacher2.registCourse(course2, semester,"2108",schoolHours2, 0,courseBook, persistenceManager);
		concreteCourse3=teacher3.registCourse(course3, semester,"2034",schoolHours3, 0,courseBook, persistenceManager);
	}
	
	
	private CourseBook courseBook;
	
	private Student student1,student2;
	
	private Semester semester;
	
	private AcademicDean academicDean;
	
	private Teacher teacher1,teacher2,teacher3;
	
	private Course course1,course2,course3;

	private ConcreteCourse concreteCourse1,concreteCourse2,concreteCourse3;
	
	private SchoolHour schoolHour1,schoolHour2,schoolHour3,schoolHour4;
	
	private Collection<SchoolHour> schoolHours1,schoolHours2,schoolHours3;
	
	public CourseBook getCourseBook() {
		return courseBook;
	}

	public void setCourseBook(CourseBook courseBook) {
		this.courseBook = courseBook;
	}

	public Student getStudent1() {
		return student1;
	}

	public void setStudent1(Student student1) {
		this.student1 = student1;
	}

	public Student getStudent2() {
		return student2;
	}

	public void setStudent2(Student student2) {
		this.student2 = student2;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public AcademicDean getAcademicDean() {
		return academicDean;
	}

	public void setAcademicDean(AcademicDean academicDean) {
		this.academicDean = academicDean;
	}

	public Teacher getTeacher1() {
		return teacher1;
	}

	public void setTeacher1(Teacher teacher1) {
		this.teacher1 = teacher1;
	}

	public Teacher getTeacher2() {
		return teacher2;
	}

	public void setTeacher2(Teacher teacher2) {
		this.teacher2 = teacher2;
	}

	public Teacher getTeacher3() {
		return teacher3;
	}

	public void setTeacher3(Teacher teacher3) {
		this.teacher3 = teacher3;
	}

	public Course getCourse1() {
		return course1;
	}

	public void setCourse1(Course course1) {
		this.course1 = course1;
	}

	public Course getCourse2() {
		return course2;
	}

	public void setCourse2(Course course2) {
		this.course2 = course2;
	}

	public Course getCourse3() {
		return course3;
	}

	public void setCourse3(Course course3) {
		this.course3 = course3;
	}

	public ConcreteCourse getConcreteCourse1() {
		return concreteCourse1;
	}

	public void setConcreteCourse1(ConcreteCourse concreteCourse1) {
		this.concreteCourse1 = concreteCourse1;
	}

	public ConcreteCourse getConcreteCourse2() {
		return concreteCourse2;
	}

	public void setConcreteCourse2(ConcreteCourse concreteCourse2) {
		this.concreteCourse2 = concreteCourse2;
	}

	public ConcreteCourse getConcreteCourse3() {
		return concreteCourse3;
	}

	public void setConcreteCourse3(ConcreteCourse concreteCourse3) {
		this.concreteCourse3 = concreteCourse3;
	}

	public SchoolHour getSchoolHour1() {
		return schoolHour1;
	}

	public void setSchoolHour1(SchoolHour schoolHour1) {
		this.schoolHour1 = schoolHour1;
	}

	public SchoolHour getSchoolHour2() {
		return schoolHour2;
	}

	public void setSchoolHour2(SchoolHour schoolHour2) {
		this.schoolHour2 = schoolHour2;
	}

	public SchoolHour getSchoolHour3() {
		return schoolHour3;
	}

	public void setSchoolHour3(SchoolHour schoolHour3) {
		this.schoolHour3 = schoolHour3;
	}

	public SchoolHour getSchoolHour4() {
		return schoolHour4;
	}

	public void setSchoolHour4(SchoolHour schoolHour4) {
		this.schoolHour4 = schoolHour4;
	}

	public Collection<SchoolHour> getSchoolHours1() {
		return schoolHours1;
	}

	public void setSchoolHours1(Collection<SchoolHour> schoolHours1) {
		this.schoolHours1 = schoolHours1;
	}

	public Collection<SchoolHour> getSchoolHours2() {
		return schoolHours2;
	}

	public void setSchoolHours2(Collection<SchoolHour> schoolHours2) {
		this.schoolHours2 = schoolHours2;
	}

	public Collection<SchoolHour> getSchoolHours3() {
		return schoolHours3;
	}

	public void setSchoolHours3(Collection<SchoolHour> schoolHours3) {
		this.schoolHours3 = schoolHours3;
	}

	
	
}
