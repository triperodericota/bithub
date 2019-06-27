package ar.edu.unlp.info.bd2.repositories.spring.data;

import ar.edu.unlp.info.bd2.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;


public interface SpringDataBithubRepository extends CrudRepository<User, Long> {

}
