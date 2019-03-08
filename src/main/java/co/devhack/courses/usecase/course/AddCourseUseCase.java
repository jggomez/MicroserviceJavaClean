package co.devhack.courses.usecase.course;

import co.devhack.courses.usecase.IUseCase;
import co.devhack.courses.usecase.domain.Course;
import co.devhack.courses.usecase.repository.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddCourseUseCase implements IUseCase<String> {

    private final ICourseRepository courseRepository;
    private Course course;

    @Autowired
    public AddCourseUseCase(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String execute() throws Exception {
        return courseRepository.addCurso(this.course);
    }
}
