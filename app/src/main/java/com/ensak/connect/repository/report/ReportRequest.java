package com.ensak.connect.repository.report;

public class ReportRequest {
    private String postType;
    private Integer postId;
    private String flag;

    public ReportRequest(String postType, Integer postId, String flag) {
        this.postType = postType;
        this.postId = postId;
        this.flag = flag;
    }
}
