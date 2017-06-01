package com.niit.collaboration.backend.dao;

import java.util.List;

import com.niit.collaboration.backend.model.Job;



public interface JobDAO {
	public List<Job> getAllJobs();
	public void postJob(Job job);
	public boolean update(Job job);
	public boolean delete(Job job);
	public Job getJobByID(int jobid);
	public Job getJobByTitle(String jobTitle);
}

