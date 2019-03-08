package co.devhack.courses.repository.courses;

import co.devhack.courses.repository.entities.CourseEntity;
import co.devhack.courses.usecase.domain.Course;
import co.devhack.courses.usecase.repository.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseRepositorio implements ICourseRepository {

    private final CourseFirebaseDataSource courseFirebaseDataSource;

    public CourseRepositorio(@Autowired CourseFirebaseDataSource courseFirebaseDataSource) {
        this.courseFirebaseDataSource = courseFirebaseDataSource;
    }

    @Override
    public String addCurso(Course course) throws Exception {
        return courseFirebaseDataSource.insert(mapToCourseEntity(course));
    }

    @Override
    public List<Course> getAll() throws Exception {
        List<CourseEntity> lstCourseEntity =
                courseFirebaseDataSource.getAll();

        List<Course> lstCourse = new ArrayList<>();

        for (CourseEntity courseEntity : lstCourseEntity) {
            lstCourse.add(mapToCourse(courseEntity));
        }

        return lstCourse;
    }

    private Course mapToCourse(CourseEntity courseEntity) {
        Course course = new Course();
        course.setId(courseEntity.getId());
        course.setName(courseEntity.getName());
        course.setDescription(courseEntity.getDescription());
        return course;
    }

    private CourseEntity mapToCourseEntity(Course course) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(course.getId());
        courseEntity.setName(course.getName());
        courseEntity.setDescription(course.getDescription());
        return courseEntity;
    }
}
