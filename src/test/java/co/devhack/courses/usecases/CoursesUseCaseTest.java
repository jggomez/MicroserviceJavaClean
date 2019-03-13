package co.devhack.courses.usecases;

import co.devhack.courses.repository.courses.CourseFirebaseDataSource;
import co.devhack.courses.repository.courses.CourseRepositorio;
import co.devhack.courses.usecase.course.AddCourseUseCase;
import co.devhack.courses.usecase.course.GetAllCourseUseCase;
import co.devhack.courses.usecase.domain.Course;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
public class CoursesUseCaseTest {

    private static Firestore firestore;

    @BeforeClass
    public static void before() {
        FirestoreOptions firestoreOptions = null;

        try {
            FileInputStream serviceAccount = new FileInputStream("./serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            firestoreOptions =
                    FirestoreOptions.newBuilder().setTimestampsInSnapshotsEnabled(true).build();

            FirebaseApp.initializeApp(options);

            firestore = firestoreOptions.getService();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TestConfiguration
    static class CourseTestConfig {

        @Bean
        public GetAllCourseUseCase getAllCourseUseCase() {

            return new GetAllCourseUseCase(
                    new CourseRepositorio(
                            new CourseFirebaseDataSource(
                                    firestore
                            )
                    ));

        }

        @Bean
        public AddCourseUseCase addCourseUseCase() {

            return new AddCourseUseCase(
                    new CourseRepositorio(
                            new CourseFirebaseDataSource(
                                    firestore
                            )
                    ));
        }

    }

    @Autowired
    private AddCourseUseCase addCourseUseCase;

    @Autowired
    private GetAllCourseUseCase getAllCourseUseCase;

    @Test
    public void addCourseTest() {

        try {
            Course course = new Course();
            course.setName("Test");
            course.setDescription("DescriptionTest");

            addCourseUseCase.setCourse(course);
            String key = addCourseUseCase.execute();

            Assertions.assertThat(key).isNotEmpty();

        } catch (Exception e) {

        }
    }

    @Test
    public void getAllCourseTest() {
        try {
            List<Course> lstCourse = getAllCourseUseCase.execute();

            int sizeTmp = lstCourse.size();

            Course course = new Course();
            course.setName("TestGet");
            course.setDescription("DescriptionTestGet");
            addCourseUseCase.setCourse(course);
            addCourseUseCase.execute();

            lstCourse = getAllCourseUseCase.execute();

            Assertions.assertThat(lstCourse.size()).isGreaterThanOrEqualTo(sizeTmp);

        } catch (Exception e) {

        }
    }
}
