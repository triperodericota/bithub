package ar.edu.unlp.info.bd2.repositories.spring.data;

import ar.edu.unlp.info.bd2.model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileBithubRepository extends CrudRepository<File, Long> {
}
