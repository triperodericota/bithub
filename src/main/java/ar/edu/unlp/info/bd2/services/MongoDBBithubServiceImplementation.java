package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.mongo.Association;
import ar.edu.unlp.info.bd2.repositories.MongoDBBithubRepository;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
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
        Optional OptCommit = repository.getDocument("hash",commitHash, "Commit");
        if(OptCommit.isPresent()) {
            Commit commit = (Commit) OptCommit.get();
            List files = new ArrayList();
            repository.getDocuments("commit._id", commit.getObjectId(), "File").into(files);
            commit.setFiles(files);
            return (Optional.ofNullable(commit));
        }else {
            return OptCommit;
        }

    }

    @Override
    public File createFile(String content, String name) {
        File newFile = new File(content,name);
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
        Review review= (Review) repository.getDocument("_id",id, "Review").get();
        List filereviews= new ArrayList();
        repository.getDocuments("review._id",id,"FileReview").into(filereviews);
        review.setReviews(filereviews);
        return Optional.of(review);
    }

    @Override
    public List<Commit> getAllCommitsForUser(ObjectId userId) {
        MongoCursor commits = repository.findCommitsForUser(userId).iterator();
        List<Commit> toReturn = new ArrayList<Commit>();
        while(commits.hasNext()){
            Association doc =  (Association) commits.next();
            Commit c = (Commit) repository.getDocument("_id", doc.getSource(),"Commit").get();
            toReturn.add(c);
        }
        return toReturn;
    }

    @Override
    public Map getCommitCountByUser() {
        Map<ObjectId,Long> toReturn = new HashMap<>();
        Iterator<Document> commitsPerUser = repository.computedTotalCommitsPerUser();
        while(commitsPerUser.hasNext()){
            Document doc = commitsPerUser.next();
            ObjectId userId = (ObjectId)doc.get("_id");
            Long totalCommits = ((Integer) doc.get("count")).longValue();
            toReturn.put(userId,totalCommits);
        }
        return toReturn;
    }

    @Override
    public List<User> getUsersThatCommittedInBranch(String branchName) throws BithubException {
        Optional<Branch> branch = this.getBranchByName(branchName);
        if(branch.isPresent()){
            return repository.usersThatCommitedInBranch(branchName);
        }else {
            throw new BithubException("The branch don't exist.");
        }
    }

    @Override
    public Optional<Branch> getBranchByName(String branchName) {
        return repository.getDocument("name",branchName,"Branch");
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
