package com.naver.kyunblue.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.naver.kyunblue.domain.BackLog;

@Repository
public interface BackLogRepository extends CrudRepository<BackLog, Long>{

	BackLog findByProjectIdentifier(String projectIdentifier);
	
}
