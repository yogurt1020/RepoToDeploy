package com.naver.kyunblue.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class ProjectTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable = false, unique = true)
	private String projectSequence;
	@NotBlank(message = "프로젝트 요약을 입력해주세요.")
	private String summary;
	private String acceptanceCriteria;
	private String status;
	private Integer priority;
	private Date dueDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="backlog_id", updatable=false, nullable=false)
	@JsonIgnore
	private BackLog backlog;
	
	@Column(updatable = false)
	private String projectIdentifier;
	
	private Date created_At;
	private Date updated_At;
	
	@PrePersist
	private void onCreate() {
		this.created_At = new Date();
	}
	
	@PreUpdate
	private void onUpdate() {
		this.updated_At = new Date();
	}
}
