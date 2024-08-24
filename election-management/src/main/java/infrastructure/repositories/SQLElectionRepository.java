package infrastructure.repositories;

import domain.Candidate;
import domain.Election;
import domain.ElectionRepository;
import domain.annotations.Principal;
import infrastructure.repositories.entities.ElectionCandidate;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

@Principal
@ApplicationScoped
public class SQLElectionRepository implements ElectionRepository {

    private final EntityManager entityManager;

    public SQLElectionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void submit(Election election) {
        infrastructure.repositories.entities.Election entity =
                infrastructure.repositories.entities.Election.fromDomain(election);

        entityManager.merge(entity);

        election.votes()
                .entrySet()
                .stream()
                .map(entry -> ElectionCandidate.fromDomain(election, entry.getKey(),
                        entry.getValue()))
                .forEach(entityManager::merge);
    }

    @Override
    public List<Election> findAll() {
        Stream<Object[]> stream = entityManager.createNativeQuery(
                " Pesquisa muito complicada, não preenchi... ");

        Map<String, List<Object[]>> map = stream.collect(groupingBy(o -> (String) o[0]));

        return map.entrySet()
                .stream()
                .map(entry -> {
                    Map.Entry<Candidate, Integer[]> candidates = entry.getValue()
                            .stream()
                            .map(row -> Map.entry(new Candidate((String) row[1],
                                    Optional.ofNullable((String) row[2]),
                                    (String) row[3],
                                    (String) row[4],
                                    (String) row[5],
                                    Optional.ofNullable((String) row[6]),
                                    Optional.ofNullable((String) row[7]),
                                    (Integer) row[8])
                                    .toArray(Map.entry[]::new);
                    return new Election(entry.getKey(), Map.ofEntries(candidates));
                }).toList();

    }

    public void sync(Election election) {
        election.votes()
                .entrySet()
                .stream()
                .map(entry -> {
                    return ElectionCandidate.fromDomain(election, entry.getKey(), entry.getValue());
                }).forEach(entityManager::merge);
    }
}
