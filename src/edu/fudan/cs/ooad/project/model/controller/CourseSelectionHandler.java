package edu.fudan.cs.ooad.project.model.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.fudan.cs.ooad.project.common.IPersistenceManager;
import edu.fudan.cs.ooad.project.model.course.ConcreteCourse;
import edu.fudan.cs.ooad.project.model.course.CourseBook;
import edu.fudan.cs.ooad.project.model.course.DropCourseRecord;
import edu.fudan.cs.ooad.project.model.course.Semester;
import edu.fudan.cs.ooad.project.model.user.Student;

@Service
@Transactional
public class CourseSelectionHandler implements ICourseSelectionHandler{

	@Autowired
	private IPersistenceManager persistenceManager;

	@Override
	public Collection<ConcreteCourse> getAvailableCourse(CourseBook courseBook,
			int year,int term) {
		Semester semester=courseBook.findSemester(year, term);
		try{
			if(!semester.isStarted())
				return new ArrayList<ConcreteCourse>();
			return courseBook.findSemesterCourses(year, term);
		}catch(NullPointerException e){
			return new ArrayList<ConcreteCourse>();
		}
	}

	@Override
	public boolean followCourse(CourseBook courseBook, Student student,
			String courseID, int year, int term) {

		ConcreteCourse concreteCourse=courseBook.getCertainCourse(courseID, year, term);
		try{
			if(!student.followCourse(concreteCourse))
				return false;
			return true;
		}catch(NullPointerException e){
			return false;
		}
	}

	@Override
	public boolean unfollowCourse(CourseBook courseBook,Student student, 
			String courseID, int year,int term) {
		ConcreteCourse concreteCourse=courseBook.getCertainCourse(courseID, year, term);
		try{
			return student.unfollowCourse(concreteCourse);
		}catch(NullPointerException e){
			return false;
		}
	}

	@Override
	public boolean selectCourse(CourseBook courseBook,Student student, 
			String courseID, int year,
			int term) {

		ConcreteCourse concreteCourse=courseBook.getCertainCourse(courseID, year, term);
		try{
			if(!student.selectCourse(concreteCourse))
				return false;
			return true;
		}catch(NullPointerException e){
			return false;
		}
	}

	@Override
	public DropCourseRecord dropCourse(CourseBook courseBook,Student student, 
			String courseID, int year,int term) {

		ConcreteCourse concreteCourse=courseBook.getCertainCourse(courseID, year, term);
		try{
			return student.dropCourse(concreteCourse, persistenceManager);
		}catch(NullPointerException e){
			return null;
		}

	}
}
