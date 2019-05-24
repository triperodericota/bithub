package ar.edu.unlp.info.bd2.repositories;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import ar.edu.unlp.info.bd2.models.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// import org.hibernate.SessionFactory; --> no se si tenemos que usar esta clase y pedirle getCurrentSession o trabajar con LocalSessionFactoryBean

@Transactional
@Repository
public class BithubRepository {

    @Autowired
    LocalSessionFactoryBean session; // -> is a FactoryBean<SessionFactory>. It means that it allows creating instances of SessionFactory.

    public BithubRepository(){

    }

    private Session getSession(){
        return session.getObject().getCurrentSession();
    }

    public void createUser(User aNewUser){ this.getSession().save(aNewUser); }

    public Optional<User> getUserByEmail(String email){
        User u = (User) this.getSession().createQuery("select u " + "from User u " + "where u.email like :email").
                setParameter("email", email).getSingleResult();
        Optional<User> toReturn = Optional.of(u);
        return toReturn;
    }

    public void createCommit(Commit aNewCommit){
        this.getSession().save(aNewCommit);
    }

    public void createFile(File aNewFile){
        this.getSession().save(aNewFile);
    }

    public Optional<Commit> getCommitByHash(String commitHash){
        Commit c = (Commit) this.getSession().createQuery("select c " + "from Commit c " + "where c.hash like :commitHash").
                setParameter("commitHash", commitHash).uniqueResult();
        Optional<Commit> toReturn = Optional.ofNullable(c);
        return toReturn;
    }

    public void createBranch(Branch aNewBranch){
        this.getSession().save(aNewBranch);
    }

    public Optional<Branch> getBranchByName(String branchName){
        Branch b = (Branch) this.getSession().createQuery("select b " + "from Branch b " + "where b.name like :branchName").
                setParameter("branchName", branchName).getSingleResult();
        Optional<Branch> toReturn = Optional.of(b);
        return toReturn;
    }

    public void createReview(Review aNewReview) { this.getSession().save(aNewReview); }

    public void createTag(Tag newTag){ this.getSession().save(newTag);}

    public Optional<Tag> getTagByName(String name){
        Tag t = (Tag) this.getSession().createQuery("select t " + "from Tag t " + "where t.name like :tagName").
                setParameter("tagName",name).uniqueResult();
        Optional<Tag> toReturn = Optional.ofNullable(t);
        return toReturn;
    }

    public User getUserForId(long userId){
        User u = (User) this.getSession().createQuery("select u "+"from User u "+ "where u.id=:userId").
                setParameter("userId",userId).uniqueResult();
        return u;
    }

    public List<Long> getIdsForUsers(){
        List<Long> ids= this.getSession().createQuery("select u.id "+"from User u ").list();
        return ids;
    }

    public List<User> getUsersThatCommittedInBranch(Long branch){ //Todavia no esta terminado!
        List <User> users= this.getSession().createQuery("select c.author "+"from Commit c "+"where c.branch=:branchId group by c.author").
                setParameter("brachId" , branch).list();
        return users;
    }
}
