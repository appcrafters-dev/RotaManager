package com.deeppatel.rotamanager.repositories;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.deeppatel.rotamanager.models.Model;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.stream.Collectors;

public class FirebaseRepository {
    protected FirebaseFirestore firestore;

    public FirebaseRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public <T> void getDocumentResult(String collection, String documentId, Class<T> valueType, OnRepositoryTaskCompleteListener<T> onCompleteListener) {
        firestore.collection(collection).document(documentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                RepositoryResult<T> result = new RepositoryResult<>();
                result.setErrorMessage(String.format("Couldn't fetch document(%s) from collection(%s)", documentId, collection));

                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null) {
                        result.setResult(documentSnapshot.toObject(valueType));
                    }
                }
                onCompleteListener.onComplete(result);
            }
        });
    }

    public CollectionReference getCollectionReference(String collection) {
        return firestore.collection(collection);
    }

    public <T extends Model> void getQueryResult(Query query, Class<T> valueType, OnRepositoryTaskCompleteListener<List<T>> onCompleteListener) {
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                RepositoryResult<List<T>> result = new RepositoryResult<>();
                result.setErrorMessage("Couldn't fetch documents");

                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        result.setResult(querySnapshot
                                .getDocuments()
                                .stream()
                                .map(document -> {
                                    T model = document.toObject(valueType);
                                    if(model == null) return null;
                                    model.setId(document.getId());
                                    return  model;
                                })
                                .collect(Collectors.toList()));
                    }
                }
                onCompleteListener.onComplete(result);
            }
        });
    }

}
