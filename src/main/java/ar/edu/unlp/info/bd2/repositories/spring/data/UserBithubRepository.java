package ar.edu.unlp.info.bd2.repositories.spring.data;

import ar.edu.unlp.info.bd2.model.*;
import org.springframework.data.repository.CrudRepository;


public interface UserBithubRepository extends CrudRepository<User, Long> {

}
