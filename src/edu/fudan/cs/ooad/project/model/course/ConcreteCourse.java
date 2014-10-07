package edu.fudan.cs.ooad.project.model.course;



import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


import edu.fudan.cs.ooad.project.common.BaseModelObject;
import edu.fudan.cs.ooad.project.common.IPersistenceManager;
import edu.fudan.cs.ooad.project.model.user.Student;
import edu.fudan.cs.ooad.project.model.user.Teacher;


@Entity
public class ConcreteCourse extends BaseModelObject{
	public static ConcreteCourse create(Course courseInfo,Semester semesterInfo,
			Teacher teacher,IPersistenceManager pm) {
		ConcreteCourse result = new ConcreteCourse();
		result.setCourseInfo(courseInfo);
		result.setSemesterInfo(semesterInfo);
		result.setTeacher(teacher);
		pm.save(result);
		return result;
	}
	
	
	@ManyToOne
	private Course courseInfo;

	@ManyToOne
	private Semester semesterInfo;

	@ManyToOne
	private Teacher teacher;

	private String classroom;
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name="selectedstudents")
	private Collection<Student> selectedStudents = new ArrayList<Student>();
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name="followedstudents")
	private Collection<Student> followedStudents = new ArrayList<Student>();
	
	@OneToMany(mappedBy = "courseInfo", cascade = { CascadeType.ALL })
	private Collection<DropCourseRecord> dropCourseRecords = new ArrayList<DropCourseRecord>();
	
	@OneToMany(mappedBy = "courseInfo", cascade = { CascadeType.ALL })
	private Collection<SchoolHour> schoolHours = new ArrayList<SchoolHour>();
	
	private int studentNumLimit;
	
	public Course getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(Course courseInfo) {
		this.courseInfo = courseInfo;
		if(courseInfo!=null){
			courseInfo.addCourse(this);
		}
	}
	
	public String getCourseID(){
		return this.courseInfo.getCourseID();
	}
	
	public Semester getSemesterInfo() {
		return semesterInfo;
	}

	public void setSemesterInfo(Semester semesterInfo) {
		this.semesterInfo = semesterInfo;
		if(semesterInfo!=null)
			semesterInfo.addCourse(this);
	}

	public int getTerm(){
		return this.semesterInfo.getTerm();
	}
	
	public int getYear(){
		return this.semesterInfo.getYear();
	}
	
	public Collection<Student> getSelectedStudents() {
		return selectedStudents;
	}

	public void setSelectedStudents(Collection<Student> selectedStudents) {
		this.selectedStudents = selectedStudents;
	}

	public Collection<Student> getFollowedStudents() {
		return followedStudents;
	}

	public void setFollowedStudents(Collection<Student> followedStudents) {
		this.followedStudents = followedStudents;
	}

	public int getStudentNumLimit() {
		return studentNumLimit;
	}

	public void setStudentNumLimit(int studentNumLimit) {
		this.studentNumLimit = studentNumLimit;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	
	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	
	public Collection<DropCourseRecord> getDropCourseRecords() {
		return dropCourseRecords;
	}

	public void setDropCourseRecords(Collection<DropCourseRecord> dropCourseRecords) {
		this.dropCourseRecords = dropCourseRecords;
	}
	
	public void addDropCourseRecord(DropCourseRecord record){
		this.dropCourseRecords.add(record);
	}
	
	public void addSelectedStudent(Student student){
		this.selectedStudents.add(student);
	}
	
	public void removeSelectedStudent(Student student){
		this.selectedStudents.remove(student);
	}
	
	public void addFollowedStudent(Student student){
		this.followedStudents.add(student);
	}
	
	public void removeFollowedStudent(Student student){
		this.followedStudents.remove(student);
	}
	
	public int getSelectedNum(){
		return this.selectedStudents.size();
	}
	
	public Collection<SchoolHour> getSchoolHours() {
		return schoolHours;
	}

	public void setSchoolHours(Collection<SchoolHour> schoolHours) {
		this.schoolHours = schoolHours;
		if(schoolHours!=null){
			for(SchoolHour schoolHour:schoolHours){
				if(schoolHour!=null)
					schoolHour.setCourseInfo(this);
			}
		}
	}
	
	public boolean isSelectable(){
		if(this.studentNumLimit>this.getSelectedNum())
			return true;
		return false;
	}

	public boolean hasSelectedStudent(String studentID) {
		for(Student student:this.selectedStudents){
			if(student.getStudentID().equals(studentID))
				return true;
		}
		return false;
	}

	public boolean hasFollowedStudent(String studentID) {
		for(Student student:this.followedStudents){
			if(student.getStudentID().equals(studentID))
				return true;
		}
		return false;
	}
	
	public boolean coursesTimeValidation(Collection<ConcreteCourse> courseList){
		for(ConcreteCourse course:courseList){
			for(SchoolHour schoolHour:this.schoolHours){
				for(SchoolHour comparedHour:course.getSchoolHours())
					if(schoolHour.isOverLapped(comparedHour))
						return false;
			}
		}
		return true;
	}
	
	
	public boolean hourListValidation(Collection<SchoolHour> schoolHourList){
		for(SchoolHour schoolHour:this.schoolHours){
			for(SchoolHour comparedHour:schoolHourList)
				if(schoolHour.isOverLapped(comparedHour))
					return false;
		}
		return true;
	}
	
	public boolean classRoomValidation(String classroom,Collection<SchoolHour> schoolHourList){
		if(this.classroom.equals(classroom)){
			for(SchoolHour schoolHour:this.schoolHours){
				for(SchoolHour comparedHour:schoolHourList){
					if(schoolHour.isOverLapped(comparedHour))
						return false;
				}
			}
		}
		return true;
	}

	public boolean isStarted(){
		return this.semesterInfo.isStarted();
		
	}
	
	

	
	
}