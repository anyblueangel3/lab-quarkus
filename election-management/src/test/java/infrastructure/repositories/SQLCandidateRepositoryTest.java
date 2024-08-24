package infrastructure.repositories;

import domain.CandidateRepository;
import domain.CandidateRepositoryTest;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;

@QuarkusTest
public class SQLCandidateRepositoryTest extends CandidateRepositoryTest {
    @Inject
    SQLCandidateRepository repository;

    @Inject
    EntityManager entityManager;

    @Override
    public CandidateRepository repository() {
        return repository;
    }

    @BeforeEach
    @TestTransaction
    void tearDown() {
        entityManager.createNativeQuery("TRUNCATE TABLE candidates").executeUpdate();
    }
}
