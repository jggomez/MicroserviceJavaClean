package co.devhack.courses.usecase.course;

import co.devhack.courses.usecase.IUseCase;
import co.devhack.courses.usecase.domain.Course;
import co.devhack.courses.usecase.repository.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllCourseUseCase implements IUseCase<List<Course>> {

    private final ICourseRepository courseRepository;

    public GetAllCourseUseCase(@Autowired ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> execute() throws Exception {
        return courseRepository.getAll();
    }
}
