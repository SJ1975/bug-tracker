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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

//    @Override
//    public void save(Bug bug) {
//
//        UserEntity reporter =
//                userJpaRepository.getReferenceById(bug.getReporterId());
//
//        ProjectEntity project =
//                projectJpaRepository.getReferenceById(bug.getProjectId());
//
//        BugEntity entity =
//                BugMapper.toEntity(bug, reporter, project);
//
//        bugJpaRepository.save(entity);
//    }

    @Override
    @Transactional
    public Bug create(Bug bug) {

        UserEntity reporter =
                userJpaRepository.getReferenceById(bug.getReporterId());

        ProjectEntity project =
                projectJpaRepository.getReferenceById(bug.getProjectId());

        UserEntity developer =
                bug.getDeveloperId() == null ? null :
                        userJpaRepository.getReferenceById(bug.getDeveloperId());

        UserEntity tester =
                bug.getTesterId() == null ? null :
                        userJpaRepository.getReferenceById(bug.getTesterId());

        BugEntity entity =
                BugMapper.toEntity(bug, reporter, project, developer, tester);

        BugEntity saved = bugJpaRepository.save(entity);

        return BugMapper.toDomain(saved);
    }


    @Override
    @Transactional
    public void save(Bug bug) {

        BugEntity entity = bugJpaRepository.findById(bug.getId())
                .orElseThrow(() -> new IllegalStateException("Bug not found"));

        entity.setStatus(bug.getStatus());
        entity.setPriority(bug.getPriority());

        entity.setDeveloper(
                bug.getDeveloperId() == null
                        ? null
                        : userJpaRepository.getReferenceById(bug.getDeveloperId())
        );

        entity.setTester(
                bug.getTesterId() == null
                        ? null
                        : userJpaRepository.getReferenceById(bug.getTesterId())
        );
    }

    @Override
    public List<Bug> findAll() {
        return bugJpaRepository.findAll()
                .stream()
                .map(BugMapper::toDomain)
                .toList();
    }

    @Override
    public List<Bug> findByReporterId(Long reporterId) {
        return bugJpaRepository.findByReporterId(reporterId)
                .stream()
                .map(BugMapper::toDomain)
                .toList();
    }

    @Override
    public List<Bug> findByDeveloperId(Long developerId) {
        return bugJpaRepository.findByDeveloperId(developerId)
                .stream()
                .map(BugMapper::toDomain)
                .toList();
    }

    @Override
    public List<Bug> findByTesterId(Long testerId) {
        return bugJpaRepository.findByTesterId(testerId)
                .stream()
                .map(BugMapper::toDomain)
                .toList();
    }

}

