package edu.fudan.cs.ooad.project.model.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.Query;

import edu.fudan.cs.ooad.project.common.IPersistenceManager;
import edu.fudan.cs.ooad.project.model.course.ConcreteCourse;
import edu.fudan.cs.ooad.project.model.course.DropCourseRecord;



@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User{

	protected static boolean isExisted(String userName,String studentID, 
			IPersistenceManager persistenceManager){
		String hql = "from Student s where s.studentID  = :studentID "
				+"or s.userName  = :userName";
		Query query = persistenceManager.createQuery(hql)
				.setParameter("userName", userName)
				.setParameter("studentID", studentID);
		if( query.list().size()>0)
			return true;
		return false;
	}
	
	protected Student(String userName, String password) {
		super(userName, password);
		// TODO Auto-generated constructor stub
	}
	
	public static Student create(String userName, String password,
			String studentID, IPersistenceManager persistenceManager) {
		if(Student.isExisted(userName, studentID, persistenceManager))
			return null;
		Student result = new Student(userName, password);
		result.setStudentID(studentID);
		persistenceManager.save(result);
		return result;
	}

	private String studentID;
	
	@ManyToMany(mappedBy = "selectedStudents", cascade = { CascadeType.ALL })
	private Collection<ConcreteCourse> selectedCourses = new ArrayList<ConcreteCourse>();
	
	@ManyToMany(mappedBy = "followedStudents", cascade = { CascadeType.ALL })
	private Collection<ConcreteCourse> followedCourses = new ArrayList<ConcreteCourse>();

	@OneToMany(mappedBy = "studentInfo", cascade = { CascadeType.ALL })
	private Collection<DropCourseRecord> dropCourseRecords = new ArrayList<DropCourseRecord>();
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public Collection<ConcreteCourse> getSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(Collection<ConcreteCourse> selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	public Collection<ConcreteCourse> getFollowedCourses() {
		return followedCourses;
	}

	public void setFollowedCourses(Collection<ConcreteCourse> followedCourses) {
		this.followedCourses = followedCourses;
	}

	public Collection<DropCourseRecord> getDropCourseRecords() {
		return dropCourseRecords;
	}

	public void setDropCourseRecords(Collection<DropCourseRecord> dropCourseRecords) {
		this.dropCourseRecords = dropCourseRecords;
	}

	public boolean selectCourse(ConcreteCourse concreteCourse){
		
		if(!this.selectionValidation(concreteCourse))
			return false;
		this.selectedCourses.add(concreteCourse);
		concreteCourse.addSelectedStudent(this);
		if(this.followedCourses.contains(concreteCourse))
			this.unfollowCourse(concreteCourse);
		return true;
	}
	
	public DropCourseRecord dropCourse(ConcreteCourse concreteCourse,
			IPersistenceManager persistenceManager){
		if(!concreteCourse.isStarted())
			return null;
		if(!this.hasSelectedCourse(concreteCourse.getCourseID(),
				concreteCourse.getYear(),concreteCourse.getTerm()))
			return null;
		this.selectedCourses.remove(concreteCourse);
		concreteCourse.removeSelectedStudent(this);
		DropCourseRecord dropCourseRecord=DropCourseRecord.create(concreteCourse, this, 
				persistenceManager);
		return dropCourseRecord;
	}
	
	public boolean followCourse(ConcreteCourse concreteCourse){
		if(!this.followValidation(concreteCourse))
			return false;
		if(concreteCourse.isSelectable())
			return false;
		this.followedCourses.add(concreteCourse);
		concreteCourse.addFollowedStudent(this);
		return true;
	}
	
	public boolean unfollowCourse(ConcreteCourse concreteCourse){
		if(!concreteCourse.isStarted())
			return false;
		if(!this.hasFollowedCourse(concreteCourse.getCourseID(),
				concreteCourse.getYear(),concreteCourse.getTerm()))
			return false;
		this.followedCourses.remove(concreteCourse);
		concreteCourse.removeFollowedStudent(this);
		return true;
	}

	public boolean hasFollowedCourse(String courseID, int year, int term) {
		for(ConcreteCourse course:this.followedCourses){
			if(course.getCourseID().equals(courseID)&&course.getTerm()==term&&
					course.getYear()==year)
				if(course.hasFollowedStudent(studentID))
					return true;
		}
		return false;
	}

	public boolean hasSelectedCourse(String courseID, int year, int term) {
		for(ConcreteCourse course:this.selectedCourses){
			if(course.getCourseID().equals(courseID)&&course.getTerm()==term&&
					course.getYear()==year)
				if(course.hasSelectedStudent(studentID))
					return true;
		}
		return false;
	}
	
	protected boolean selectionValidation(ConcreteCourse course){
		if(!course.isStarted())
			return false;
		return course.isSelectable() && course.coursesTimeValidation(selectedCourses);
	}
	
	protected boolean followValidation(ConcreteCourse course){
		if(!course.isStarted())
			return false;
		return course.coursesTimeValidation(followedCourses);
	}
}
