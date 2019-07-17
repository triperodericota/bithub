package ar.edu.unlp.info.bd2.repositories.spring.data;

import ar.edu.unlp.info.bd2.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;


public interface UserBithubRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
