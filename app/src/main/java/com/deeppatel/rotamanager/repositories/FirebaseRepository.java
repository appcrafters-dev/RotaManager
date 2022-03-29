package com.deeppatel.rotamanager.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.deeppatel.rotamanager.models.Model;
import com.deeppatel.rotamanager.models.RepositoryResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;
import java.util.stream.Collectors;

public class FirebaseRepository {
    protected FirebaseFirestore firestore;

    public FirebaseRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public <T extends Model> void getDocumentResult(String collection, String documentId, Class<T> valueType, OnRepositoryTaskCompleteListener<T> onCompleteListener) {
        firestore.collection(collection).document(documentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                RepositoryResult<T> result = new RepositoryResult<>();
                result.setErrorMessage(String.format("Couldn't fetch document(%s) from collection(%s)", documentId, collection));

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        T model = document.toObject(valueType);
                        if (model == null) result.setResult(null);
                        else model.setId(document.getId());
                        result.setResult(model);
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
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                RepositoryResult<List<T>> result = new RepositoryResult<>();
                result.setErrorMessage("Couldn't fetch documents");

                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        try{
                            result.setResult(querySnapshot
                                    .getDocuments()
                                    .stream()
                                    .map(document -> {
                                        T model = document.toObject(valueType);
                                        if (model == null) return null;
                                        model.setId(document.getId());
                                        return model;
                                    })
                                    .collect(Collectors.toList()));
                        }catch (Exception e){
                            Log.e("getQueryResult", e.getMessage());
                            e.printStackTrace();
                            result.setErrorMessage(e.getMessage());
                        }
                    }
                }
                onCompleteListener.onComplete(result);
            }
        });
    }

    public <T extends Model> void addNewDocument(String collection, T data, OnRepositoryTaskCompleteListener<T> onCompleteListener) {
        firestore.collection(collection).add(data.toHashMap()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                RepositoryResult<T> result = new RepositoryResult<>(null, String.format("Could not create new document for collection(%s)", collection));
                if (task.isSuccessful()) {
                    DocumentReference documentReference = task.getResult();
                    if (documentReference != null) {
                        data.setId(documentReference.getId());
                        result.setResult(data);
                    }
                }
                onCompleteListener.onComplete(result);
            }
        });
    }

    public <T extends Model> void addNewDocuments(String collection, List<T> listData, OnRepositoryTaskCompleteListener<Void> onCompleteListener) {
        WriteBatch writeBatch = firestore.batch();
        CollectionReference collectionRef = firestore.collection("time_entries");
        for (T data : listData) {
            DocumentReference documentRef = collectionRef.document();
            documentRef.set(data.toHashMap());
        }

        writeBatch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                RepositoryResult<Void> result = new RepositoryResult<>();
                if (!task.isSuccessful()) {
                    result.setErrorMessage(String.format("Could not create new documents for collection(%s)", collection));
                }
                onCompleteListener.onComplete(result);
            }
        });
    }
}
