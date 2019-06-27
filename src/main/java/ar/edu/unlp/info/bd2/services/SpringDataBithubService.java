package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.spring.data.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpringDataBithubService implements BithubService<Long> {

    @Autowired private UserBithubRepository userRepository;

    @Autowired private BranchBithubRepository branchRepository;

    @Autowired private FileBithubRepository fileRepository;

    @Autowired private CommitBithubRepository commitRepository;

    @Autowired private TagBithubRepository tagRepository;

    public SpringDataBithubService(){
    }

    @Override
    public User createUser(String email, String name) {
        User newUser = new User(email,name);
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Branch createBranch(String name) {
        Branch newBranch = new Branch(name);
        branchRepository.save(newBranch);
        return newBranch;
    }

    @Override
    public Commit createCommit(String description, String hash, User author, List<File> files, Branch branch) {
        Commit newCommit = new Commit(description,hash,author,files,branch);
        commitRepository.save(newCommit);
        branch.addCommit(newCommit);
        branchRepository.save(branch);
        return newCommit;
    }

    @Override
    public Tag createTagForCommit(String commitHash, String name) throws BithubException {
        Optional<Commit> commitOptional= this.getCommitByHash(commitHash);
        if(commitOptional.isPresent()){
            Tag newTag= new Tag(commitHash,name,commitOptional.get());
            tagRepository.save(newTag);
            return newTag;
        }else{
            throw new BithubException("The commit don't exist.");
        }
    }

    @Override
    public Optional<Commit> getCommitByHash(String commitHash) {
        return (commitRepository.findByHash(commitHash));

    }

    @Override
    public File createFile(String content, String name) {
        File newFile = new File(name,content);
        fileRepository.save(newFile);
        return newFile;
    }

    @Override
    public Optional<Tag> getTagByName(String tagName) {
        return tagRepository.findByName(tagName);
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
    public Optional<Review> getReviewById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Commit> getAllCommitsForUser(Long userId) {
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
        return branchRepository.findByName(branchName);
    }
}
