package ar.edu.unlp.info.bd2.repositories.spring.data;

import ar.edu.unlp.info.bd2.model.Commit;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommitBithubRepository extends CrudRepository<Commit,Long> {

    Optional<Commit> findByHash(String aHash);
}
