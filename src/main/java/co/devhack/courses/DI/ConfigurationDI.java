package co.devhack.courses.DI;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class ConfigurationDI {

    @Bean
    @Qualifier("getFirestore")
    public Firestore getFirestore() {

        try {
            FileInputStream serviceAccount =
                    new FileInputStream("WEB-INF/classes/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirestoreOptions firestoreOptions =
                    FirestoreOptions.newBuilder().setTimestampsInSnapshotsEnabled(true).build();

            FirebaseApp.initializeApp(options);

            return firestoreOptions.getService();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
