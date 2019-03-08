package co.devhack.courses.endpoint;

import co.devhack.courses.usecase.course.AddCourseUseCase;
import co.devhack.courses.usecase.course.GetAllCourseUseCase;
import co.devhack.courses.usecase.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class CourseEndpoint {

    @Autowired
    GetAllCourseUseCase getAllCourseUseCase;

    @Autowired
    AddCourseUseCase addCourseUseCase;

    @RequestMapping(value = "/apis/v1/course", method = RequestMethod.POST)
    public String add(@RequestBody Course course) {
        try {
            addCourseUseCase.setCourse(course);
            return addCourseUseCase.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/apis/v1/course", method = RequestMethod.GET)
    public List<Course> getAll() {
        try {
            return getAllCourseUseCase.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
