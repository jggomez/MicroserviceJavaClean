package co.devhack.courses.repository.courses;

import co.devhack.courses.repository.entities.CourseEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class CourseFirebaseDataSource {

    private final Firestore firestore;

    @Autowired
    public CourseFirebaseDataSource(Firestore firestore) {
        this.firestore = firestore;
    }

    public String insert(CourseEntity courseEntity) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> result =
                firestore.collection("courses")
                        .add(courseEntity);

        return result.get().getId();
    }

    public List<CourseEntity> getAll() throws ExecutionException, InterruptedException {
        List<CourseEntity> lstCourseEntity = new ArrayList<>();

        ApiFuture<QuerySnapshot> query =
                firestore.collection("courses").get();

        QuerySnapshot querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            lstCourseEntity.add(document.toObject(CourseEntity.class));
        }

        return lstCourseEntity;
    }

}
