package edu.fudan.cs.ooad.project.model.controller;

import java.util.Collection;

import edu.fudan.cs.ooad.project.model.course.ConcreteCourse;
import edu.fudan.cs.ooad.project.model.course.Course;
import edu.fudan.cs.ooad.project.model.course.CourseBook;
import edu.fudan.cs.ooad.project.model.course.SchoolHour;
import edu.fudan.cs.ooad.project.model.course.Semester;
import edu.fudan.cs.ooad.project.model.user.AcademicDean;
import edu.fudan.cs.ooad.project.model.user.Teacher;

public interface ICourseManagementHandler {
	public boolean modifyStudentNumLimit(CourseBook courseBook,AcademicDean academicDean,
			String courseID,int year,int term,int studentNumLimit);
	
	public Course newCourse(CourseBook courseBook,AcademicDean academicDean,
			String courseID,String courseName);
	
	public Semester newSemester(CourseBook courseBook,AcademicDean academicDean, 
			int year, int term);
	
	public boolean startCourseSelection(CourseBook courseBook,
			AcademicDean academicDean,int year,int term);
	
	public boolean closeCourseSelection(CourseBook courseBook,
			AcademicDean academicDean,int year,int term);
	
	public ConcreteCourse registerConcreteCourse(CourseBook courseBook,
			Teacher teacher,String courseID,int year,int term,String classroom,
			Collection<SchoolHour> schoolHours,int studentNumLimit);
	
	
}
