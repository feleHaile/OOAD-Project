package edu.fudan.cs.ooad.project.model.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.fudan.cs.ooad.project.common.IPersistenceManager;
import edu.fudan.cs.ooad.project.model.course.ConcreteCourse;
import edu.fudan.cs.ooad.project.model.course.Course;
import edu.fudan.cs.ooad.project.model.course.CourseBook;
import edu.fudan.cs.ooad.project.model.course.SchoolHour;
import edu.fudan.cs.ooad.project.model.course.Semester;
import edu.fudan.cs.ooad.project.model.user.AcademicDean;
import edu.fudan.cs.ooad.project.model.user.Teacher;

@Service
@Transactional
public class CourseManagementHandler implements ICourseManagementHandler{

	@Autowired
	private IPersistenceManager persistenceManager;
	
	@Override
	public boolean modifyStudentNumLimit(CourseBook courseBook,AcademicDean academicDean,
			String courseID,int year, int term, int studentNumLimit) {
		ConcreteCourse concreteCourse=courseBook.getCertainCourse(courseID, year, term);
		try{
			return academicDean.modifyStudentNumLimit(concreteCourse, studentNumLimit);
		}catch(NullPointerException e){
			return false;
		}
	}

	@Override
	public Course newCourse(CourseBook courseBook,AcademicDean academicDean, 
			String courseID,String courseName) {
		if(courseBook.findCourseInfo(courseID)!=null)
			return null;
		Course course=academicDean.newCourse(courseID, courseName, persistenceManager);
		return course;
	}

	public Semester newSemester(CourseBook courseBook,AcademicDean academicDean, 
			int year, int term){
		if(courseBook.findSemester(year, term)!=null)
			return null;
		Semester semester=academicDean.newSemester(year, term, persistenceManager);
		return semester;
	}
	
	@Override
	public boolean startCourseSelection(CourseBook courseBook,
			AcademicDean academicDean, int year, int term) {
		Semester semester=courseBook.findSemester(year, term);
		try{
			if(semester.isStarted()||semester.isFinished())
				return false;
			academicDean.startCourseSelection(semester);
			return true;
		}catch(NullPointerException e){
			return false;
		}
		
	}
	
	@Override
	public boolean closeCourseSelection(CourseBook courseBook,
			AcademicDean academicDean, int year, int term) {
		Semester semester=courseBook.findSemester(year, term);
		try{
			if(semester.isFinished())
				return false;
			academicDean.closeCourseSelection(semester);
			return true;
		}catch(NullPointerException e){
			return false;
		}
	}

	@Override
	public ConcreteCourse registerConcreteCourse(CourseBook courseBook,
			Teacher teacher,String courseID, int year, int term,String classroom,
			Collection<SchoolHour> schoolHours,int studentNumLimit) {
		Semester semesterInfo=courseBook.findSemester(year, term);
		Course courseInfo=courseBook.findCourseInfo(courseID);
		try{
			if(semesterInfo.isFinished())
				return null;
			if(courseInfo==null)
				return null;
			if(courseBook.getCertainCourse(courseID, year, term)!=null)
				return null;
			ConcreteCourse course=teacher.registCourse(courseInfo, semesterInfo,classroom,schoolHours,
					studentNumLimit,courseBook, persistenceManager);
			return course;
		}catch(NullPointerException e){
			return null;
		}
		
	}

}
