package edu.fudan.cs.ooad.project.model.course;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import edu.fudan.cs.ooad.project.common.BaseModelObject;
import edu.fudan.cs.ooad.project.common.IPersistenceManager;
import edu.fudan.cs.ooad.project.model.user.Student;

@Entity
public class DropCourseRecord extends BaseModelObject{
	
	public static DropCourseRecord create(ConcreteCourse courseInfo,
			Student studentInfo,IPersistenceManager pm) {
		DropCourseRecord result = new DropCourseRecord();
		result.setCourseInfo(courseInfo);
		result.setStudentInfo(studentInfo);
		result.setDropDate(new Date());
		pm.save(result);
		return result;
	}
	
	@ManyToOne
	private ConcreteCourse courseInfo;
	
	@ManyToOne
	private Student studentInfo;
	
	private Date dropDate;
	
	public ConcreteCourse getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(ConcreteCourse courseInfo) {
		this.courseInfo = courseInfo;
	}
	
	
	public Student getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(Student studentInfo) {
		this.studentInfo = studentInfo;
	}

	public Date getDropDate() {
		return dropDate;
	}

	public void setDropDate(Date dropDate) {
		this.dropDate = dropDate;
	}

	
}
