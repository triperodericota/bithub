package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.models.*;
import ar.edu.unlp.info.bd2.repositories.BithubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class BithubServiceImpl implements BithubService {

    @Autowired
    BithubRepository repository;


    public BithubServiceImpl(BithubRepository repository ) {
        this.repository=repository;
    }

    @Override
    public User createUser(String email, String name) {
        User newUser=new User(email, name);
        repository.createUser(newUser);
        return newUser;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    @Override
    public Branch createBranch(String name) {
        Branch newBranch= new Branch(name);
        repository.createBranch(newBranch);
        return newBranch;

    }

    @Override
    public Commit createCommit(String description, String hash, User author, List<File> files, Branch branch) {
        Commit newCommit= new Commit(description,hash,author,files,branch);
        repository.createCommit(newCommit);
        return newCommit;
    }

    @Override
    public Tag createTagForCommit(String commitHash, String name) throws BithubException {
        return null;
    }

    @Override
    public Optional<Commit> getCommitByHash(String commitHash) {
        return repository.getCommitByHash(commitHash);
    }

    @Override
    public File createFile(String content, String name) {
        File newFile= new File(content, name);
        repository.createFile(newFile);
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
        return repository.getBranchByName(branchName);
    }

}
