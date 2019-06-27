package ar.edu.unlp.info.bd2.repositories.spring.data;

import ar.edu.unlp.info.bd2.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagBithubRepository extends CrudRepository<Tag,Long> {

    Optional<Tag> findByName(String name);

}
