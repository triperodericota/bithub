package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.mongo.Association;
import ar.edu.unlp.info.bd2.repositories.MongoDBBithubRepository;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import ar.edu.unlp.info.bd2.model.*;

import java.util.*;

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
        return Optional.ofNullable((User) repository.getDocument("email", email, "User").first());
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
        Optional OptCommit = Optional.ofNullable((repository.getDocument("hash",commitHash, "Commit")).first());
        if(OptCommit.isPresent()) {
            Commit commit = (Commit) OptCommit.get();
            List files = new ArrayList();
            repository.getDocument("commit._id", commit.getObjectId(), "File").into(files);
            commit.setFiles(files);
            return (Optional.ofNullable(commit));
        }else {
            return OptCommit;
        }
    }

    @Override
    public File createFile(String content, String name) {
        File newFile = new File(name,content);
        repository.saveDocument(newFile,"File");
        return newFile;
    }

    @Override
    public Optional<Tag> getTagByName(String tagName) {
        return Optional.ofNullable((Tag) repository.getDocument("name", tagName, "Tag").first());
    }

    @Override
    public Review createReview(Branch branch, User user) {
        Review newReview = new Review(branch, user);
        repository.saveDocument(newReview,"Review");
        Association review_branch = new Association(newReview.getObjectId(),newReview.getBranch().getObjectId());
        repository.saveAssociation("review_branch",review_branch);
        return newReview;
    }

    @Override
    public FileReview addFileReview(Review review, File file, int lineNumber, String comment) throws BithubException {
        if (file.getCommit().getBranch().equals(review.getBranch())) {
            FileReview newFileReview = new FileReview(review, file, lineNumber, comment);
            repository.saveDocument(newFileReview, "FileReview");
            return newFileReview;
        }else{
            throw new BithubException("The review's branch must be equals to file's branch");
        }
    }

    @Override
    public Optional<Review> getReviewById(ObjectId id) {
        Review review= (Review) repository.getDocument("_id",id, "Review").first();
        List filereviews= new ArrayList();
        repository.getDocument("review._id",id,"FileReview").into(filereviews);
        review.setReviews(filereviews);
        return Optional.of(review);
    }

    @Override
    public List<Commit> getAllCommitsForUser(ObjectId userId) {
        /*Optional<User> user = repository.getDocument("_id", userId, "User");*/
        System.out.println(userId);
        FindIterable<Association> commitsForUser = repository.getAssociations("destination", userId, "commit_author");
        List<ObjectId> commits_ids = new ArrayList<ObjectId>();
        List<Commit> commits = new ArrayList<Commit>();;
       /* Block<Association> retrieveCommits;

        retrieveCommits = new Block<Association>() {
            @Override
            public void apply(final Association commit_author){
                commits_ids.add(commit_author.getSource());
            }
        };
*/
        commitsForUser.forEach((Block<Association>) commit_author -> commits_ids.add(commit_author.getSource()));
        System.out.println(commits_ids.toString());
        return commits;
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
        return Optional.ofNullable((Branch) repository.getDocument("name",branchName,"Branch").first());
    }

    @Override
    public Commit createCommit(String description, String hash, User author, List<File> list, Branch branch) {
        Commit newCommit = new Commit(description,hash,author,list,branch);
        branch.addCommit(newCommit);
        repository.saveDocument(newCommit,"Commit");
        Association commit_branch = new Association(newCommit.getObjectId(), newCommit.getBranch().getObjectId());
        Association commit_user = new Association(newCommit.getObjectId(), newCommit.getAuthor().getObjectId());
        repository.saveAssociation("commit_branch", commit_branch);
        repository.saveAssociation("commit_author", commit_user);
        repository.replaceDocument(branch, "Branch");
        for (File file: list){
            file.setCommit(newCommit);
            repository.replaceDocument(file, "File");
        }
        return newCommit;

    }
}

/*
    CREAR UNA COLECCION POR CADA TIPO DE ASOCIACIÓN: EJ => COMMIT-FILE, BRANCH-COMMIT

 */