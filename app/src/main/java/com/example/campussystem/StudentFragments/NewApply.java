package com.example.campussystem.StudentFragments;

public class NewApply {
    String jobId;
    String status;

    public NewApply() {
    }

    public NewApply(String jobId, String status) {
        this.jobId = jobId;
        this.status = status;
    }

    public String getJobId() {
        return jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
