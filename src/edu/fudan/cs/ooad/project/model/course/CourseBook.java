package edu.fudan.cs.ooad.project.model.course;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Query;

import edu.fudan.cs.ooad.project.common.IPersistenceManager;


public class CourseBook {
	
	private IPersistenceManager persistenceManager;
	
	public CourseBook(IPersistenceManager persistenceManager) {
		this.persistenceManager=persistenceManager;
	}

	public Course findCourseInfo(String courseID){
		String hql="from Course c where c.courseID= :courseID";
		Query query = persistenceManager.createQuery(hql)
				.setParameter("courseID", courseID);
		if(query.list().size()<1)
			return null;
		return (Course) query.list().iterator().next();
	}
	
	public ConcreteCourse getCertainCourse(String courseID,int year,int term){
		String hql="from ConcreteCourse c where c.courseInfo.courseID= :courseID "
				+"and c.semesterInfo.year = :year "
				+"and c.semesterInfo.term = :term";
		Query query = persistenceManager.createQuery(hql)
				.setParameter("courseID", courseID)
				.setParameter("year", year)
				.setParameter("term", term);
		if(query.list().size()<1)
			return null;
		return (ConcreteCourse) query.list().iterator().next();
	}

	public Collection<ConcreteCourse> findSemesterCourses(int year,int term){
		String hql="from ConcreteCourse c where c.semesterInfo.year = :year "
				+"and c.semesterInfo.term = :term";
		Query query = persistenceManager.createQuery(hql)
				.setParameter("year", year)
				.setParameter("term", term);
		Collection<ConcreteCourse> courseList=new ArrayList<ConcreteCourse>();
		for(Object c:query.list()){
			courseList.add((ConcreteCourse) c);
		}
		return courseList;
	}
	

	
	public Semester findSemester(int year,int term){
		String hql="from Semester s where s.year= :year "
				+"and s.term = :term";
		Query query = persistenceManager.createQuery(hql)
				.setParameter("year", year)
				.setParameter("term", term);
		if(query.list().size()<1)
			return null;
		return (Semester) query.list().iterator().next();
	}
	
}
