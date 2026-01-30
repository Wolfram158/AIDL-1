package ru.wolfram.aidl_app;

interface ISortService {
    int[] sort(in int[] ints);

    int getPid();
}