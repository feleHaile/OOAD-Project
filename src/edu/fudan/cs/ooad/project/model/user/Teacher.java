package edu.fudan.cs.ooad.project.model.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.Query;

import edu.fudan.cs.ooad.project.common.IPersistenceManager;
import edu.fudan.cs.ooad.project.model.course.ConcreteCourse;
import edu.fudan.cs.ooad.project.model.course.Course;
import edu.fudan.cs.ooad.project.model.course.CourseBook;
import edu.fudan.cs.ooad.project.model.course.SchoolHour;
import edu.fudan.cs.ooad.project.model.course.Semester;


@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends User{
	
	protected static boolean isExisted(String userName,String facultyID, 
			IPersistenceManager persistenceManager){
		String hql = "from Teacher t where t.facultyID  = :facultyID "
				+"or t.userName  = :userName";
		Query query = persistenceManager.createQuery(hql)
				.setParameter("userName", userName)
				.setParameter("facultyID", facultyID);
		if( query.list().size()>0)
			return true;
		return false;
	}
	
	protected Teacher(String userName, String password) {
		super(userName, password);
		// TODO Auto-generated constructor stub
	}

	public static Teacher create(String userName, String password,
			String facultyID, IPersistenceManager persistenceManager) {
		if(Teacher.isExisted(userName,facultyID, persistenceManager))
			return null;
		Teacher result = new Teacher(userName, password);
		result.setFacultyID(facultyID);
		persistenceManager.save(result);
		return result;
	}
	
	private String facultyID;
	
	@OneToMany(mappedBy = "teacher", cascade = { CascadeType.ALL })
	private Collection<ConcreteCourse> courses = new ArrayList<ConcreteCourse>();

	public String getFacultyID() {
		return facultyID;
	}

	public void setFacultyID(String facultyID) {
		this.facultyID = facultyID;
	}
	
	
	public Collection<ConcreteCourse> getCourses() {
		return courses;
	}

	public void setCourses(Collection<ConcreteCourse> courses) {
		this.courses = courses;
	}
	
	public ConcreteCourse findCourse(int term,String courseID){
		for(ConcreteCourse course : courses) {
	        if(course.getCourseID()==courseID){
	        	return course;
	        }
	    }
		return null;
	}
	

	public ConcreteCourse registCourse(Course courseInfo,Semester semesterInfo,String classroom,
			Collection<SchoolHour> schoolHours,int studentNumLimit,CourseBook courseBook,
			IPersistenceManager persistenceManager) {
		if (!this.timeValidation(schoolHours))
			return null;
		
		if(!this.classroomValidation(classroom, courseBook, schoolHours, semesterInfo))
			return null;
		
		ConcreteCourse course = ConcreteCourse.create(courseInfo,semesterInfo,this,
				persistenceManager);
		course.setSchoolHours(schoolHours);
		course.setStudentNumLimit(studentNumLimit);
		course.setClassroom(classroom);
		courses.add(course);
		return course;
	}
	
	
	
	protected boolean timeValidation(Collection<SchoolHour> schoolHours){
		for(ConcreteCourse course:this.courses)
			if(!course.hourListValidation(schoolHours))
				return false;
		return true;
	}
	
	protected boolean classroomValidation(String classroom,CourseBook courseBook,
			Collection<SchoolHour> schoolHours,Semester semester){
		Collection<ConcreteCourse> courseList=courseBook.findSemesterCourses(semester.getYear(), 
				semester.getTerm());
		for(ConcreteCourse course:courseList)
			if(!course.classRoomValidation(classroom, schoolHours)){
				return false;
			}
		return true;
	}
}
