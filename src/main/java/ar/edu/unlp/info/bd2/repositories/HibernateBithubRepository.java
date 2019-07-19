package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class HibernateBithubRepository {

  @Autowired
  private SessionFactory session;

  public HibernateBithubRepository(){

  }

  private Session getSession(){
    return session.getCurrentSession();
  }

  public void createUser(User aNewUser){ this.getSession().save(aNewUser); }

  public Optional<User> getUserByEmail(String email){
    User u = (User) this.getSession().createQuery("select u " + "from User u " + "where u.email= :email").
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
    Commit c = (Commit) this.getSession().createQuery("select c " + "from Commit c " + "where c.hash= :commitHash").
            setParameter("commitHash", commitHash).uniqueResult();
    Optional<Commit> toReturn = Optional.ofNullable(c);
    return toReturn;
  }

  public void createBranch(Branch aNewBranch){
    this.getSession().save(aNewBranch);
  }

  public Optional<Branch> getBranchByName(String branchName){
    Branch b = (Branch) this.getSession().createQuery("select b " + "from Branch b " + "where b.name= :branchName").
            setParameter("branchName", branchName).uniqueResult();
    Optional<Branch> toReturn = Optional.ofNullable(b);
    return toReturn;
  }

  public void createReview(Review aNewReview) { this.getSession().save(aNewReview); }

  public void createFileReview(FileReview aNewFileReview) { this.getSession().save(aNewFileReview); }

  public Optional<Review> getReviewById(long id){
    Review r = (Review) this.getSession().createQuery("select r " + "from Review r " + "where r.id= :id").
            setParameter("id", id).getSingleResult();
    Optional<Review> toReturn = Optional.of(r);
    return toReturn;
  }

  public void createTag(Tag newTag){ this.getSession().save(newTag);}

  public Optional<Tag> getTagByName(String name){
    Tag t = (Tag) this.getSession().createQuery("select t " + "from Tag t " + "where t.name= :tagName").
            setParameter("tagName",name).uniqueResult();
    Optional<Tag> toReturn = Optional.ofNullable(t);
    return toReturn;
  }

  public User getUserForId(long userId){
    User u = (User) this.getSession().createQuery("select u "+"from User u "+ "where u.id=:userId").
            setParameter("userId",userId).uniqueResult();
    return u;
  }

  public List getCommitsCountPerUser(){
    return this.getSession().createQuery("select c.author, count(c.id)" + "from Commit c" +
            " group by c.author").list();
  }

  public List<User> getUsersThatCommittedInBranch(Branch branch) {

    return this.getSession().createQuery("select c.author "+"from Commit c "+"where c.branch=:branch group by c.author").
            setParameter("branch" , branch).list();
  }

}
