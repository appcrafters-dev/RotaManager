package com.deeppatel.rotamanager.models;

import java.util.List;

public class TimeChangeRequestsByDate {
    private String title;
    private List<TimeChangeRequest> timeChangeRequests;

    public TimeChangeRequestsByDate(String title, List<TimeChangeRequest> timeChangeRequests) {
        this.title = title;
        this.timeChangeRequests = timeChangeRequests;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public List<TimeChangeRequest> getTimeChangeRequests() { return timeChangeRequests; }

    public void setChildItemList(List<TimeChangeRequest> requests) { this.timeChangeRequests = requests; }
}