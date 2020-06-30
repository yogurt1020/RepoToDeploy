package com.naver.kyunblue.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.kyunblue.domain.BackLog;
import com.naver.kyunblue.domain.Project;
import com.naver.kyunblue.domain.ProjectTask;
import com.naver.kyunblue.exceptions.ProjectNotFoundException;
import com.naver.kyunblue.repositories.BackLogRepository;
import com.naver.kyunblue.repositories.ProjectRepository;
import com.naver.kyunblue.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private BackLogRepository backLogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		try {
			BackLog backLog = backLogRepository.findByProjectIdentifier(projectIdentifier);
			projectTask.setBacklog(backLog);
			
			Integer BackLogSequence = backLog.getPTSequence();
			backLog.setPTSequence(++BackLogSequence);
			
			projectTask.setProjectSequence(projectIdentifier + "-" + BackLogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			if(projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
				projectTask.setPriority(3);
			}
			
			if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			
			return projectTaskRepository.save(projectTask);			
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}

	}

	public Iterable<ProjectTask> findProjectTasksByPI(String projectIdentifier) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
		
		if(project == null) {
			throw new ProjectNotFoundException("Project with ID [" + projectIdentifier + "] is not found");
		}
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
	}
	
//	public ProjectTask findPTByProjectSequence(String projectIdentifier, String sequence) {
//		// 프로젝트 존재여부
//		Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
//		if(project == null) {
//			throw new ProjectNotFoundException("Project with ID [" + projectIdentifier + "] is not found");
//		}
//		// 프로젝트 태스크 존재 여부
//		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence);
//		if(projectTask == null) {
//			throw new ProjectNotFoundException("Project Task [" + sequence + "] is not found");
//		}
//		// 해당 프로젝트 하위에 이 태스크가 존재하는지
//		if(!projectTask.getProjectIdentifier().equals(projectIdentifier)) {
//			throw new ProjectNotFoundException("Project Task [" + sequence + "] is not found in project [" + projectIdentifier + "]");
//		}
//		
//		return projectTask;
//	}
//	
	
    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id){

        BackLog backlog = backLogRepository.findByProjectIdentifier(backlog_id);
        if(backlog==null){
            throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' does not exist");
        }

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' not found");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '"+pt_id+"' does not exist in project: '"+backlog_id);
        }


        return projectTask;
    }
    
//	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String projectIdentifier, String sequence) {
//		ProjectTask projectTask = findPTByProjectSequence(projectIdentifier, sequence);
//		
//		projectTask = updatedTask;
//		
//		return projectTaskRepository.save(updatedTask);
//	}
	
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

        projectTaskRepository.delete(projectTask);
    }
}
