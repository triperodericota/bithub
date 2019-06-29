package ar.edu.unlp.info.bd2.repositories.spring.data;

import ar.edu.unlp.info.bd2.model.Branch;
import ar.edu.unlp.info.bd2.model.Commit;
import ar.edu.unlp.info.bd2.model.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommitBithubRepository extends CrudRepository<Commit,Long> {

    Optional<Commit> findByHash(String aHash);

    @Query("select c.author from Commit c where c.branch = :branch group by c.author")
    List<User> findUsersByCommits(@Param("branch") Branch branch);
}
