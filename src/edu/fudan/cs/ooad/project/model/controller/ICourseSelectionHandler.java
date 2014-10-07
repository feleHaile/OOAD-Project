package edu.fudan.cs.ooad.project.model.controller;

import java.util.Collection;

import edu.fudan.cs.ooad.project.model.course.ConcreteCourse;
import edu.fudan.cs.ooad.project.model.course.CourseBook;
import edu.fudan.cs.ooad.project.model.course.DropCourseRecord;
import edu.fudan.cs.ooad.project.model.user.Student;

public interface ICourseSelectionHandler {
	public boolean followCourse(CourseBook courseBook,Student student,String courseID,int year,int term);
	
	public Collection<ConcreteCourse> getAvailableCourse(CourseBook courseBook,int year,int term);
	
	public boolean unfollowCourse(CourseBook courseBook,Student student,String courseID,int year,int term);
	
	public boolean selectCourse(CourseBook courseBook,Student student,String courseID,int year,int term);
	
	public DropCourseRecord dropCourse(CourseBook courseBook,Student student, 
			String courseID,int year,int term);
}
