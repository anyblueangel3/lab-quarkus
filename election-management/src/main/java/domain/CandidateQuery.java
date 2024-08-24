package domain;

import org.inferred.freebuilder.FreeBuilder;

import javax.annotation.processing.Generated;
import java.util.Optional;
import java.util.Set;

@FreeBuilder
public interface CandidateQuery {

    Optional<Set<String>> ids();

    Optional<String> name();

    class Builder extends CandidateQuery_Builder {
        public CandidateQuery build() {
        }

        public void ids(Set<String> id) {
        }

        public Builder name(String poi) {
            return null;
        }
    }

}
