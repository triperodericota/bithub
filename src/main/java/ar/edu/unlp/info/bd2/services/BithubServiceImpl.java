package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.models.*;
import ar.edu.unlp.info.bd2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BithubServiceImpl implements BithubService {

    @Autowired
    LocalSessionFactoryBean sesion;

    BithubRepository repository; //COMO INYECTO LA VARIABLE ??

    @Autowired
    public BithubServiceImpl(BithubRepository repository ) {
        this.repository=repository;
    }

    @Override
    public User createUser(String email, String name) {
        User newUser=new User(email, name);
        sesion.getObject().openSession().save(newUser);
        return newUser;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Branch createBranch(String name) {
        Branch newBranch= new Branch(name);
        sesion.getObject().openSession().save(newBranch); //ESTO FUNCIONA, ¿PERO SE PUEDE HACER MAS ELEGANTE? PORQUE ME DICE QUE PUEDE SER NULL?
        return newBranch;

    }

    @Override
    public Commit createCommit(String description, String hash, User author, List<File> files, Branch branch) {
        Commit newCommit= new Commit(description,hash,author,files,branch);
        sesion.getObject().openSession().save(newCommit);
        return newCommit;
    }

    @Override
    public Tag createTagForCommit(String commitHash, String name) throws BithubException {
        return null;
    }

    @Override
    public Optional<Commit> getCommitByHash(String commitHash) {
        return Optional.empty();
    }

    @Override
    public File createFile(String content, String name) {
        File newFile= new File(content, name);
        sesion.getObject().openSession().save(newFile);
        return newFile;
    }

    @Override
    public Optional<Tag> getTagByName(String tagName) {
        return Optional.empty();
    }

    @Override
    public Review createReview(Branch branch, User user) {
        return null;
    }

    @Override
    public FileReview addFileReview(Review review, File file, int lineNumber, String comment) throws BithubException {
        return null;
    }

    @Override
    public Optional<Review> getReviewById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Commit> getAllCommitsForUser(long userId) {
        return null;
    }

    @Override
    public Map<Long, Long> getCommitCountByUser() {
        return null;
    }

    @Override
    public List<User> getUsersThatCommittedInBranch(String branchName) throws BithubException {
        return null;
    }

    @Override
    public Optional<Branch> getBranchByName(String branchName) {
        return Optional.empty();
    }

}
