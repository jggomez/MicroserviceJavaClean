package co.devhack.courses.endpoint;

import co.devhack.courses.usecase.course.AddCourseUseCase;
import co.devhack.courses.usecase.course.GetAllCourseUseCase;
import co.devhack.courses.usecase.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/apis/v1")
public class CourseEndpoint {

    @Autowired
    GetAllCourseUseCase getAllCourseUseCase;

    @Autowired
    AddCourseUseCase addCourseUseCase;

    @PostMapping(value = "/course")
    public String add(@RequestBody Course course) {
        try {
            addCourseUseCase.setCourse(course);
            return addCourseUseCase.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/course")
    public List<Course> getAll() {
        try {
            return getAllCourseUseCase.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
