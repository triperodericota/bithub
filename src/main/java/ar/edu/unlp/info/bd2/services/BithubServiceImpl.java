package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.config.HibernateConfiguration;
import ar.edu.unlp.info.bd2.models.*;
import ar.edu.unlp.info.bd2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BithubServiceImpl implements BithubService {

    @Autowired
    HibernateConfiguration sesion;

    public BithubServiceImpl(BithubRepository repository ) {

    }

    @Override
    public User createUser(String email, String name) {
        User nuevo=new User(email, name);
        sesion.sessionFactory().getObject().openSession().save(nuevo);
        return nuevo;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Branch createBranch(String name) {
        return null;
    }

    @Override
    public Commit createCommit(String description, String hash, User author, List<File> files, Branch branch) {
        return null;
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
        return null;
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
