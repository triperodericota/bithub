package ar.edu.unlp.info.bd2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import ar.edu.unlp.info.bd2.models.*;
 // import org.hibernate.SessionFactory; --> no se si tenemos que usar esta clase y pedirle getCurrentSession o trabajar con LocalSessionFactoryBean

@Repository
public class BithubRepository {

    @Autowired
    LocalSessionFactoryBean session; // -> is a FactoryBean<SessionFactory>. It means that it allows creating instances of SessionFactory.

    public BithubRepository(){

    }

    public void createUser(User aNewUser){
        session.getObject().openSession().save(aNewUser);
    }

    public void createBranch(Branch aNewBranch){
        session.getObject().openSession().save(aNewBranch);
    }

    public void createCommit(Commit aNewCommit){
        session.getObject().openSession().save(aNewCommit);
    }

    public void createFile(File aNewFile){
        session.getObject().openSession().save(aNewFile);
    }




}
