package com.deeppatel.rotamanager.repositories;

import androidx.annotation.NonNull;

import com.deeppatel.rotamanager.models.RepositoryResult;
import com.deeppatel.rotamanager.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseRepository {
    protected FirebaseFirestore firestore;

    public FirebaseRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public <T> void getDocument(String collection, String documentId, Class<T> valueType, OnRepositoryTaskCompleteListener<T> onCompleteListener) {
        firestore.collection(collection).document(documentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                RepositoryResult<T> result = new RepositoryResult<>();
                result.setErrorMessage(String.format("Couldn't fetch document(%s) from collection(%s)", documentId, collection));

                if(task.isSuccessful()) {
                    DocumentSnapshot userSnapshot = task.getResult();
                    if(userSnapshot != null) {
                        result.setResult(task.getResult().toObject(valueType));
                    }
                }
                onCompleteListener.onComplete(result);
            }
        });
    }

}
