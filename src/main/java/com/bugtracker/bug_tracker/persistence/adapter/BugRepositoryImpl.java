package com.bugtracker.bug_tracker.persistence.adapter;

import com.bugtracker.bug_tracker.application.port.BugRepository;
import com.bugtracker.bug_tracker.domain.model.Bug;
import com.bugtracker.bug_tracker.persistence.entity.BugEntity;
import com.bugtracker.bug_tracker.persistence.entity.ProjectEntity;
import com.bugtracker.bug_tracker.persistence.entity.UserEntity;
import com.bugtracker.bug_tracker.persistence.mapper.BugMapper;
import com.bugtracker.bug_tracker.persistence.repository.BugJpaRepository;
import com.bugtracker.bug_tracker.persistence.repository.ProjectJpaRepository;
import com.bugtracker.bug_tracker.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BugRepositoryImpl implements BugRepository {

    private final BugJpaRepository bugJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ProjectJpaRepository projectJpaRepository;

    public BugRepositoryImpl(
            BugJpaRepository bugJpaRepository,
            UserJpaRepository userJpaRepository,
            ProjectJpaRepository projectJpaRepository
    ) {
        this.bugJpaRepository = bugJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.projectJpaRepository = projectJpaRepository;
    }

    // ✅ REQUIRED METHOD — YOU WERE MISSING THIS
    @Override
    public Bug load(Long bugId) {
        BugEntity entity = bugJpaRepository.findById(bugId)
                .orElseThrow(() -> new RuntimeException("Bug not found"));

        return BugMapper.toDomain(entity);
    }

    @Override
    public Bug save(Bug bug) {

        UserEntity reporter =
                userJpaRepository.getReferenceById(bug.getReporterId());

        ProjectEntity project =
                projectJpaRepository.getReferenceById(bug.getProjectId());

        BugEntity entity =
                BugMapper.toEntity(bug, reporter, project);

        return BugMapper.toDomain(
                bugJpaRepository.save(entity)
        );
    }
}

