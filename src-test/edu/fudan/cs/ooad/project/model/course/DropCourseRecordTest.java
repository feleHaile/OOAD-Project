package edu.fudan.cs.ooad.project.model.course;

import org.junit.Test;

import edu.fudan.cs.ooad.project.common.HibernateBaseTest;
import edu.fudan.cs.ooad.project.model.user.Student;

public class DropCourseRecordTest extends HibernateBaseTest{
	@Test
	public void testCreateDropCourseRecord() {
		Student student = Student.create("Bonnie","111","student1", getPersistenceManager());
		Course course=Course.create("123", "234", getPersistenceManager());
		ConcreteCourse concreteCourse = ConcreteCourse.create(course,null,null,
				getPersistenceManager());
		course.getCourses().add(concreteCourse);
		DropCourseRecord dcr=DropCourseRecord.create(concreteCourse, student, getPersistenceManager());
		assertObjectPersisted(dcr);
		
	}
}
