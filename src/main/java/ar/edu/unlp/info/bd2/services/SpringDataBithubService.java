package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.spring.data.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

public class SpringDataBithubService implements BithubService<Long> {

    @Autowired private UserBithubRepository userRepository;

    @Autowired private BranchBithubRepository branchRepository;

    @Autowired private FileBithubRepository fileRepository;

    @Autowired private CommitBithubRepository commitRepository;

    @Autowired private TagBithubRepository tagRepository;

    @Autowired private ReviewBithubRepository reviewRepository;

    @Autowired private FileReviewBithubRepository fileReviewRepository;

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
        return userRepository.findByEmail(email);
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
        for (File file : files) {
            file.setCommit(newCommit);
        }
        branch.addCommit(newCommit);
        author.addCommit(newCommit);
        commitRepository.save(newCommit);
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
        File newFile = new File(content,name);
        fileRepository.save(newFile);
        return newFile;
    }

    @Override
    public Optional<Tag> getTagByName(String tagName) {
        return tagRepository.findByName(tagName);
    }

    @Override
    public Review createReview(Branch branch, User user) {
        Review newReview = new Review(branch,user);
        reviewRepository.save(newReview);
        return newReview;
    }

    @Override
    public FileReview addFileReview(Review review, File file, int lineNumber, String comment) throws BithubException {
        if (file.getCommit().getBranch().equals(review.getBranch())) {
            FileReview newFileReview = new FileReview(review,file,lineNumber,comment);
            file.addReview(newFileReview);
            review.addReview(newFileReview);
            fileReviewRepository.save(newFileReview);
            return newFileReview;
        } else {
            throw new BithubException("The review's branch must be equals to file's branch.");
        }
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<Commit> getAllCommitsForUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.get().getCommits();
    }

    @Override
    public Map<Long, Long> getCommitCountByUser() {
        Iterator commitCount_User= commitRepository.getCommitsCountPerUser().iterator();
        Map<Long, Long> map= new HashMap<>();
        while (commitCount_User.hasNext()){
            Object[] tuple = (Object[]) commitCount_User.next();
            User user = (User) tuple[0];
            map.put(user.getId(), (Long) tuple[1]);
        }
        return map;
    }

    @Override
    public List<User> getUsersThatCommittedInBranch(String branchName) throws BithubException {
        Optional<Branch> branchOpt= this.getBranchByName(branchName);
        if(branchOpt.isPresent()){
            Branch b = branchOpt.get();
            return(commitRepository.findUsersByCommits(b));
        }else{
            throw new BithubException("The branch don't exist.");
        }
    }

    @Override
    public Optional<Branch> getBranchByName(String branchName) {
        return branchRepository.findByName(branchName);
    }
}
