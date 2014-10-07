package edu.fudan.cs.ooad.project.model.course;

import org.junit.Test;

import edu.fudan.cs.ooad.project.common.HibernateBaseTest;

public class CourseBookTest extends HibernateBaseTest {
	@Test
	public void testCourseBook() {
		CourseBook courseBook=new CourseBook(getPersistenceManager());
		Semester sm=Semester.create(2013, 1, this.getPersistenceManager());
		assertEquals(courseBook.findSemester(2013, 1),sm);
		Course course=Course.create("123", "234", getPersistenceManager());
		assertEquals(courseBook.findCourseInfo(course.getCourseID()),course);
		ConcreteCourse concreteCourse = ConcreteCourse.create(course,sm,null,
				getPersistenceManager());
		course.getCourses().add(concreteCourse);
		assertEquals(courseBook.getCertainCourse(course.getCourseID(), 2013, 1),concreteCourse);
		assertEquals(courseBook.findSemesterCourses(2013, 1).size(),1);

	}
}
