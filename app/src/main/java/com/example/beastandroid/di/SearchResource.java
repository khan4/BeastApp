package com.example.beastandroid.di;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchResource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public SearchResource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> SearchResource<T> success (@Nullable T data) {
        return new SearchResource<>(Status.SUCCESS, data, null);
    }

    public static <T> SearchResource<T> error(@NonNull String msg, @Nullable T data) {
        return new SearchResource<>(Status.ERROR, data, msg);
    }

    public static <T> SearchResource<T> loading(@Nullable T data) {
        return new SearchResource<>(Status.LOADING, data, null);
    }

    public enum Status { SUCCESS, ERROR, LOADING}
}