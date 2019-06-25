package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.mongo.Association;
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

    public MongoDBBithubServiceImplementation(MongoDBBithubRepository repo){ //PREGUNTAR!!
        this.repository = repo;
    }

    @Override
    public User createUser(String email, String name) {
        User newUser = new User(email,name);
        repository.saveDocument(newUser, "User");
        return newUser;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return repository.getDocument("email", email, "User");
    }

    @Override
    public Branch createBranch(String name) {
        Branch newBranch = new Branch(name);
        repository.saveDocument(newBranch,"Branch");
        return newBranch;
    }

    @Override
    public Tag createTagForCommit(String commitHash, String name) throws BithubException {
        Optional<Commit> commitOptional = this.getCommitByHash(commitHash);
        if(commitOptional.isPresent()){
            Tag newTag = new Tag(commitHash, name, commitOptional.get());
            repository.saveDocument(newTag, "Tag");
            return newTag;
        }else{
            throw new BithubException("The commit don't exist.");
        }
    }

    @Override
    public Optional<Commit> getCommitByHash(String commitHash) {
        return repository.getDocument("hash",commitHash, "Commit");
    }

    @Override
    public File createFile(String content, String name) {
        File newFile = new File(name,content);
        repository.saveDocument(newFile,"File");
        return newFile;
    }

    @Override
    public Optional<Tag> getTagByName(String tagName) {
        return repository.getDocument("name", tagName, "Tag");
    }

    @Override
    public Review createReview(Branch branch, User user) {
        Review newReview = new Review(branch, user);
        repository.saveDocument(newReview,"Review");
        Association review_user= new Association(newReview.getObjectId(),newReview.getAuthor().getObjectId());
        repository.saveAssociation("review_user",review_user);
        //Association review_branch = new Association(newReview.getObjectId(),newReview.getBranch().getObjectId());
        //repository.saveAssociation("review_branch",review_branch);
        return newReview;
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
        Optional<User> user = repository.getDocument("_id", userId, "User");
        System.out.println(" -- User: " + user.get().getName() + " -- user commits => " + user.get().getCommits().toString());
        return user.get().getCommits();
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
        return repository.getDocument("name",branchName,"Branch");
    }

    @Override
    public Commit createCommit(String description, String hash, User author, List<File> list, Branch branch) {
        System.out.println(branch.getObjectId());
        Commit newCommit = new Commit(description,hash,author,list,branch);
        branch.addCommit(newCommit);
        author.addCommit(newCommit);
        repository.saveDocument(newCommit,"Commit");
        Association commit_branch = new Association(newCommit.getObjectId(), newCommit.getBranch().getObjectId());
        Association commit_user = new Association(newCommit.getObjectId(), newCommit.getAuthor().getObjectId());
        repository.saveAssociation("commit_branch", commit_branch);
        repository.saveAssociation("commit_author", commit_user);
        repository.replaceDocument(branch, "Branch");
        repository.replaceDocument(author, "User");
        return newCommit;

    }
}

/*
    CREAR UNA COLECCION POR CADA TIPO DE ASOCIACIÓN: EJ => COMMIT-FILE, BRANCH-COMMIT

 */