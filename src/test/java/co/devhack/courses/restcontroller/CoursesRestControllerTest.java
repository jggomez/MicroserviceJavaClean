package co.devhack.courses.restcontroller;


import co.devhack.courses.endpoint.CourseEndpoint;
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
import com.google.gson.Gson;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;
import java.io.IOException;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseEndpoint.class)
public class CoursesRestControllerTest {

    @Autowired
    private MockMvc mvc;

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
    public void addCoursesControllerTest() throws Exception {

        Course course = new Course();
        course.setName("TestGetrest");
        course.setDescription("DescriptionTestrestGet");

        Gson gson = new Gson();
        String jsonObject = gson.toJson(course);

        mvc.perform(MockMvcRequestBuilders.post("/apis/v1/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getCoursesControllerTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/apis/v1/course")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


}
