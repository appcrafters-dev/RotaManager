package com.deeppatel.rotamanager.repositories;

import androidx.annotation.NonNull;

import com.deeppatel.rotamanager.models.RepositoryResult;

public interface OnRepositoryTaskCompleteListener<T> {
        void onComplete(@NonNull RepositoryResult<T> result);
}
