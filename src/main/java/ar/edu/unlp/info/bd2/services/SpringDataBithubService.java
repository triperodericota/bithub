package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.spring.data.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        commitRepository.save(newCommit);
        for (File file : files) {
            file.setCommit(newCommit);
        }
        fileRepository.saveAll(files);
        branch.addCommit(newCommit);
        branchRepository.save(branch);
        author.addCommit(newCommit);
        userRepository.save(author);
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
            fileReviewRepository.save(newFileReview);
            file.addReview(newFileReview);
            review.addReview(newFileReview);
            fileRepository.save(file);
            reviewRepository.save(review);
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
        List<Long> ids= userRepository.findIdForUsers(); //Obtengo todos los ids de los usuarios.
        Map<Long, Long> map= new HashMap<>();
        for (Long id: ids){
            Optional<User> u= userRepository.findById(id); //Por cada id, traigo el usuario, junto con su lista de commits. PREGUNTAR SI HAY MANERA MAS EFICIENTE.
            Long count= new Long (u.get().getCommits().size());
            map.put(id,count);
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
