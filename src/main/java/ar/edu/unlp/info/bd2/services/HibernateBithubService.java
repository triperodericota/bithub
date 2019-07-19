package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.HibernateBithubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
public class HibernateBithubService implements BithubService<Long>{

    @Autowired
    HibernateBithubRepository repository;


    public HibernateBithubService(HibernateBithubRepository repository ) {
        this.repository=repository;
    }

    @Override
    @Transactional
    public User createUser(String email, String name) {
        User newUser=new User(email, name);
        repository.createUser(newUser);
        return newUser;
    }

    @Override
    @Transactional
    public Optional<User> getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    @Override
    @Transactional
    public Branch createBranch(String name) {
        Branch newBranch= new Branch(name);
        repository.createBranch(newBranch);
        return newBranch;
    }

    @Override
    @Transactional
    public Commit createCommit(String description, String hash, User author, List<File> files, Branch branch) {
        Commit newCommit = new Commit(description, hash, author, files, branch);
        repository.createCommit(newCommit);
        for (File file : files) {
            file.setCommit(newCommit);
        }
        branch.addCommit(newCommit);
        author.addCommit(newCommit);
        return newCommit;
    }

    @Override
    @Transactional
    public Tag createTagForCommit(String commitHash, String name) throws BithubException {
        Optional<Commit> commitOptional= this.getCommitByHash(commitHash);
        if(commitOptional.isPresent()) {
            Commit commit = commitOptional.get();
            Tag newTag = new Tag(commitHash, name, commit);
            repository.createTag(newTag);
            commit.setTag(newTag);
            return newTag;
        }else{
            throw new BithubException("The commit don't exist.");
        }
    }

    @Override
    @Transactional
    public Optional<Commit> getCommitByHash(String commitHash) {
        return repository.getCommitByHash(commitHash);
    }

    @Override
    @Transactional
    public File createFile(String content, String name) {
        File newFile= new File(content, name);
        repository.createFile(newFile);
        return newFile;
    }

    @Override
    @Transactional
    public Optional<Tag> getTagByName(String tagName) {
        return repository.getTagByName(tagName);
    }

    @Override
    @Transactional
    public Review createReview(Branch branch, User user) {
        Review newReview = new Review(branch, user);
        repository.createReview(newReview);
        return newReview;
    }

    @Override
    @Transactional
    public FileReview addFileReview(Review review, File file, int lineNumber, String comment) throws BithubException {
        if(file.getCommit().getBranch().equals(review.getBranch())) {
            FileReview newFileReview = new FileReview(review, file, lineNumber, comment);
            repository.createFileReview(newFileReview);
            review.addReview(newFileReview);
            file.addReview(newFileReview);
            return newFileReview;

        }else {
            throw new BithubException("The review's branch must be equals to file's branch");
        }
    }

    @Override
    @Transactional
    public Optional<Review> getReviewById(Long id) {
        return repository.getReviewById(id);
    }

    @Override
    @Transactional
    public List<Commit> getAllCommitsForUser(Long userId) {
        User u= repository.getUserForId(userId);
        return u.getCommits();
    }

    @Override
    @Transactional
    public Map<Long, Long> getCommitCountByUser() {
        Iterator commitCount_User= repository.getCommitsCountPerUser().iterator();
        Map<Long, Long> map= new HashMap<>();
        while (commitCount_User.hasNext()){
            Object[] tuple = (Object[]) commitCount_User.next();
            User user = (User) tuple[0];
            map.put(user.getId(), (Long) tuple[1]);
        }
        return map;
    }

    @Override
    @Transactional
    public List<User> getUsersThatCommittedInBranch(String branchName) throws BithubException {
        Optional<Branch> branchOpt= this.getBranchByName(branchName);
        if(branchOpt.isPresent()){
            Branch b = branchOpt.get();
            return(repository.getUsersThatCommittedInBranch(b));
        }else{
            throw new BithubException("The branch don't exist.");
        }
    }

    @Override
    @Transactional
    public Optional<Branch> getBranchByName(String branchName) {
        return repository.getBranchByName(branchName);
    }


}
