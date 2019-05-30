package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.repositories.MongoDBBithubRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import ar.edu.unlp.info.bd2.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MongoDBBithubServiceImplementation implements BithubService<ObjectId>{

    @Autowired
    private MongoDBBithubRepository repository;

    public MongoDBBithubServiceImplementation(){

    }

    public MongoDBBithubServiceImplementation(MongoDBBithubRepository repo){ //PREGUNTAR!!
        this.repository = repo;
    }

    @Override
    public User createUser(String email, String name) {
        return null;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Branch createBranch(String name) {
        Branch newBranch = new Branch(name);
        repository.saveBranch(newBranch);
        return newBranch;
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
    public File createFile(String name, String content) {
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
    public Optional<Review> getReviewById(ObjectId id) {
        return Optional.empty();
    }

    @Override
    public List<Commit> getAllCommitsForUser(ObjectId userId) {
        return null;
    }

    @Override
    public Map getCommitCountByUser() {
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

    @Override
    public Commit createCommit(String description, String hash, User author, List list, Branch branch) {
        return null;
    }
}
