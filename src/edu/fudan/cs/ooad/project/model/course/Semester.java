package edu.fudan.cs.ooad.project.model.course;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import edu.fudan.cs.ooad.project.common.BaseModelObject;
import edu.fudan.cs.ooad.project.common.IPersistenceManager;

@Entity
public class Semester extends BaseModelObject{
	
	public static Semester create(int year,int term,
			IPersistenceManager pm) {
		Semester result = new Semester();
		result.setYear(year);
		result.setTerm(term);
		result.setStatus(CoursesStatus.unstarted);
		pm.save(result);
		return result;
	}
	
	private int year;
	
	private int term;
	
	@OneToMany(mappedBy = "semesterInfo", cascade = { CascadeType.ALL })
	private Collection<ConcreteCourse> courses = new ArrayList<ConcreteCourse>();

	private CoursesStatus status;

	public void start() {
	
		this.status=CoursesStatus.started;
	}
	
	public void close() {
		this.status=CoursesStatus.finished;
		
	}
	
	public boolean isStarted(){
		if(this.status==CoursesStatus.started)
			return true;
		return false;
	}
	
	public boolean isFinished(){
		if(this.status==CoursesStatus.finished)
			return true;
		return false;
	}
	
	public void addCourse(ConcreteCourse course){
		if(!courses.contains(course))
			this.courses.add(course);
	}
	
	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}
	
	public CoursesStatus getStatus() {
		return status;
	}

	public void setStatus(CoursesStatus status) {
		this.status = status;
	}

	public Collection<ConcreteCourse> getCourses() {
		return courses;
	}

	public void setCourses(Collection<ConcreteCourse> courses) {
		this.courses = courses;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void removeCourse(ConcreteCourse course){
		if(this.courses.contains(course))
			this.courses.remove(course);
	}

	
}
