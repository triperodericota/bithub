package ar.edu.unlp.info.bd2.repositories.spring.data;

import ar.edu.unlp.info.bd2.model.Branch;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BranchBithubRepository extends CrudRepository<Branch,Long> {

    Optional<Branch> findByName(String aName);
}
