package edu.fudan.cs.ooad.project.model.course;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import edu.fudan.cs.ooad.project.common.BaseModelObject;
import edu.fudan.cs.ooad.project.common.IPersistenceManager;
@Entity
public class Course extends BaseModelObject{
	public static Course create(String courseID,String courseName,
			IPersistenceManager pm) {
		Course result = new Course();
		result.setCourseID(courseID);
		result.setCourseName(courseName);
		pm.save(result);
		return result;
	}
	
	private String courseID;
	
	private String courseName;
	
	@OneToMany(mappedBy = "courseInfo", cascade = { CascadeType.ALL })
	private Collection<ConcreteCourse> courses = new ArrayList<ConcreteCourse>();

	public void addCourse(ConcreteCourse course){
		if(!courses.contains(course))
			this.courses.add(course);
	}
	
	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Collection<ConcreteCourse> getCourses() {
		return courses;
	}

	public void setCourses(Collection<ConcreteCourse> courses) {
		this.courses = courses;
	}
	
}
