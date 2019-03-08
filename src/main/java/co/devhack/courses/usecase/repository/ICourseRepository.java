package co.devhack.courses.usecase.repository;

import co.devhack.courses.usecase.domain.Course;

import java.util.List;

public interface ICourseRepository {

    String addCurso(Course course) throws Exception;

    List<Course> getAll() throws Exception;
}
