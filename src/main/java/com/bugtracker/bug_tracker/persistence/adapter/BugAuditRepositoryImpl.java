package com.bugtracker.bug_tracker.persistence.adapter;

import com.bugtracker.bug_tracker.application.port.BugAuditRepository;
import com.bugtracker.bug_tracker.domain.model.BugAuditLog;
import com.bugtracker.bug_tracker.persistence.entity.BugAuditLogEntity;
import com.bugtracker.bug_tracker.persistence.repository.BugAuditJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BugAuditRepositoryImpl implements BugAuditRepository {

    private final BugAuditJpaRepository jpaRepository;

    public BugAuditRepositoryImpl(BugAuditJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(BugAuditLog log) {
        BugAuditLogEntity entity = new BugAuditLogEntity();
        entity.setBugId(log.getBugId());
        entity.setAction(log.getAction());
        entity.setOldValue(log.getOldValue());
        entity.setNewValue(log.getNewValue());
        entity.setActorId(log.getActorId());
        entity.setActorRole(log.getActorRole());
        jpaRepository.save(entity);
    }

//    @Override
//    public List<BugAuditLog> findByBugId(Long bugId) {
//        return jpaRepository.findByBugIdOrderByCreatedAtAsc(bugId)
//                .stream()
//                .map(entity -> new BugAuditLog(
//                        entity.getBugId(),
//                        entity.getAction(),
//                        entity.getOldValue(),
//                        entity.getNewValue(),
//                        entity.getActorId(),
//                        entity.getActorRole()
//                ))
//                .toList();
//    }

    @Override
    public Page<BugAuditLog> findByBugId(
            Long bugId,
            Pageable pageable
    ) {
        return jpaRepository.findByBugId(bugId, pageable)
                .map(entity -> new BugAuditLog(
                        entity.getBugId(),
                        entity.getAction(),
                        entity.getOldValue(),
                        entity.getNewValue(),
                        entity.getActorId(),
                        entity.getActorRole()
                ));
    }


}