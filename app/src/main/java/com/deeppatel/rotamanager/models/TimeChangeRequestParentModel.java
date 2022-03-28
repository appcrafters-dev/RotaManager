package com.deeppatel.rotamanager.models;

import java.util.List;

public class TimeChangeRequestParentModel {

    private String ParentItemTitle;
    private List<TimeChangeRequestChildModel> timeChangeRequestChildModelList;

    public TimeChangeRequestParentModel(String ParentItemTitle, List<TimeChangeRequestChildModel> timeChangeRequestChildModelList) {
        this.ParentItemTitle = ParentItemTitle;
        this.timeChangeRequestChildModelList = timeChangeRequestChildModelList;
    }

    public String getParentItemTitle() { return ParentItemTitle; }

    public void setParentItemTitle(String parentItemTitle) { ParentItemTitle = parentItemTitle; }

    public List<TimeChangeRequestChildModel> getChildItemList() { return timeChangeRequestChildModelList; }

    public void setChildItemList(List<TimeChangeRequestChildModel> timeChangeRequestChildModelList) { this.timeChangeRequestChildModelList = timeChangeRequestChildModelList; }
}