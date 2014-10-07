package edu.fudan.cs.ooad.project.model.course;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import edu.fudan.cs.ooad.project.common.BaseModelObject;
import edu.fudan.cs.ooad.project.common.IPersistenceManager;


@Entity
public class SchoolHour extends BaseModelObject{
	public static SchoolHour create(int startHour,int endHour,WeekDay weekDay,
			IPersistenceManager pm){
		SchoolHour result = new SchoolHour();
		result.setStartHour(startHour);
		result.setEndHour(endHour);
		result.setWeekDay(weekDay);
		pm.save(result);
		return result;
	}
	
	private int startHour;
	
	private int endHour;
	
	private WeekDay weekDay;

	@ManyToOne
	private ConcreteCourse courseInfo;
	
	public WeekDay getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(WeekDay weekDay) {
		this.weekDay = weekDay;
	}
	
	public ConcreteCourse getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(ConcreteCourse courseInfo) {
		this.courseInfo = courseInfo;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	
	public boolean isOverLapped(SchoolHour schoolHour){
		if(this.weekDay==schoolHour.getWeekDay()){
			if(schoolHour.getStartHour()>=this.startHour&&
					schoolHour.getStartHour()<=this.endHour)
				return true;
			if(schoolHour.getEndHour()>=this.startHour&&
					schoolHour.getStartHour()<=this.endHour)
				return true;
		}
		return false;
			
	}

	
	
}
