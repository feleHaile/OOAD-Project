package edu.fudan.cs.ooad.project.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.Query;

import edu.fudan.cs.ooad.project.common.IPersistenceManager;
import edu.fudan.cs.ooad.project.model.course.ConcreteCourse;
import edu.fudan.cs.ooad.project.model.course.Course;
import edu.fudan.cs.ooad.project.model.course.Semester;



@Entity
@DiscriminatorValue("ACADEMICDEAN")
public class AcademicDean extends User{
	
	protected static boolean isExisted(String userName,String facultyID, 
			IPersistenceManager persistenceManager){
		String hql = "from AcademicDean a where a.facultyID  = :facultyID "
				+"or a.userName  = :userName";
		Query query = persistenceManager.createQuery(hql)
				.setParameter("facultyID", facultyID)
				.setParameter("userName", userName);
		if( query.list().size()>0)
			return true;
		return false;
	}
	
	protected AcademicDean(String userName, String password) {
		super(userName, password);
	}

	public static AcademicDean create(String userName, String password,
			String facultyID, IPersistenceManager persistenceManager) {
		if(AcademicDean.isExisted(userName, facultyID, persistenceManager))
			return null;
		AcademicDean result = new AcademicDean(userName, password);
		result.setFacultyID(facultyID);
		persistenceManager.save(result);
		return result;
	}
	
	private String facultyID;

	public String getFacultyID() {
		return facultyID;
	}

	public void setFacultyID(String facultyID) {
		this.facultyID = facultyID;
	}
	
	public boolean modifyStudentNumLimit(ConcreteCourse concreteCourse,int studentNumLimit){
		if(concreteCourse.getSelectedNum()<=studentNumLimit){
			concreteCourse.setStudentNumLimit(studentNumLimit);
			return true;
		}
		return false;
	}
	
	public Course newCourse(String courseID,String courseName
			,IPersistenceManager persistenceManager){
		Course course=Course.create(courseID, courseName, persistenceManager);
		return course;
	}
	
	public Semester newSemester(int year, int term
			,IPersistenceManager persistenceManager){
		Semester semester=Semester.create(year, term, persistenceManager);
		return semester;
	}
		
	public void startCourseSelection(Semester semester){
		semester.start();
	}
	
	public void closeCourseSelection(Semester semester){
		semester.close();
	}
}
