
package com.cs.nks.easycouriers.model;

public class SavedTask {

	private String JobType;
	private String Job_Duration;
	private String Comments;

	public SavedTask(String jobType, String job_Duration, String comments) {
		this.JobType = jobType;
		this.Job_Duration = job_Duration;
		this.Comments = comments;
	}

	public String getJobType() {
		return JobType;
	}

	public void setJobType(String jobType) {
		JobType = jobType;
	}

	public String getJob_Duration() {
		return Job_Duration;
	}

	public void setJob_Duration(String job_Duration) {
		Job_Duration = job_Duration;
	}

	public String getComments() {
		return Comments;
	}

	public void setComments(String comments) {
		Comments = comments;
	}




	}

	

