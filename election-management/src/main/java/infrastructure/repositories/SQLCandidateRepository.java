package infrastructure.repositories;

import domain.Candidate;
import domain.CandidateQuery;
import domain.CandidateRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SQLCandidateRepository implements CandidateRepository {

    private final EntityManager entityManager;

    public SQLCandidateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(List<Candidate> candidates) {
        candidates.stream()
                .map(infrastructure
                        .repositories
                        .entities
                        .Candidate::fromDomain)
                .forEach(entityManager::merge);
    }

    @Override
    public List<Candidate> find(CandidateQuery query) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(infrastructure.repositories.entities.Candidate.class);
        var root = cq.from(infrastructure.repositories.entities.Candidate.class);

        var where = query.ids().map(id -> cb.in(root.get("id")).value(id)).get();

        cq.select(root).where(where);

        return entityManager.createQuery(cq)
                .getResultStream()
                .map(infrastructure.repositories.entities.Candidate::toDomain)
                .toList();
    }

    @Override
    public List<Candidate> findAll() {
        return List.of();
    }

    @Override
    public Optional<Candidate> findById(String id) {
        return Optional.empty();
    }

}
