package ar.edu.unlp.info.bd2.repositories.spring.data;

import ar.edu.unlp.info.bd2.model.Commit;
import ar.edu.unlp.info.bd2.model.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FileBithubRepository extends CrudRepository<File, Long> {

    @Query("Update File Set commit=:commit Where id=:file" )
    void modifiedIdCommit(@Param("file") Long idFile, @Param("commit") Commit idCommit);
}
