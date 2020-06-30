package com.naver.kyunblue.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.kyunblue.domain.BackLog;
import com.naver.kyunblue.domain.Project;
import com.naver.kyunblue.exceptions.ProjectIdException;
import com.naver.kyunblue.repositories.BackLogRepository;
import com.naver.kyunblue.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private BackLogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){
        try {
        	project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
        	
        	if(project.getId() == null) {	// save인 경우
            	BackLog backlog = new BackLog();
            	project.setBacklog(backlog);
            	backlog.setProject(project);
            	backlog.setProjectIdentifier(project.getProjectIdentifier());
        	} else {	// update인 경우
        		project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
        	}

        	return projectRepository.save(project);
        }catch (Exception e) {
			throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
		}
    }

    public Project findProjectByIdentifier(String projectId) {
	    Project project = projectRepository.findByProjectIdentifier(projectId);

	    if(project == null) {
	    	throw new ProjectIdException("Project does not exist");
	    }
    	
	    return project;
    }
    
    public Iterable<Project> findAllProjects() {
    	return projectRepository.findAll();
    }
    
    public void deleteProjectByIdentifier(String projectIdentifier) {
    	Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
    	
    	if(project == null) {
    		throw new ProjectIdException("No Such Project [Project Identifier : "+ projectIdentifier + "]");
    	}
    	
    	projectRepository.delete(project);
    }
}