package ar.edu.unlp.info.bd2.repositories.spring.data;

import ar.edu.unlp.info.bd2.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReviewBithubRepository extends CrudRepository<Review, Long> {

}
