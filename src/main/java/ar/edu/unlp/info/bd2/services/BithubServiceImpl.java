package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.models.*;
import ar.edu.unlp.info.bd2.repositories.BithubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
    public Tag createTagForCommit(String commitHash, String name) throws BithubException {
        Optional<Commit> commitOptional= this.getCommitByHash(commitHash);
        if(commitOptional.isPresent()) {
            Commit commit = commitOptional.get();
            Tag newTag = new Tag(commitHash, name, commit);
            repository.createTag(newTag);
            commit.setTag(newTag);
            return newTag;
        }else{
            throw new BithubException("El commit no existe.");
        }
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
        return repository.getTagByName(tagName);
    }

    @Override
    public Review createReview(Branch branch, User user) {
        Review newReview = new Review(branch, user);
        repository.createReview(newReview);
        return newReview;
    }

    @Override
    public FileReview addFileReview(Review review, File file, int lineNumber, String comment) throws BithubException {
        if(file.getCommit().getBranch().equals(review.getBranch())) {
            FileReview newFileReview = new FileReview(review, file, lineNumber, comment);
            return null;
        }else {
            throw new BithubException("The review's branch must be equals to file's branch");
        }
    }

    @Override
    public Optional<Review> getReviewById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Commit> getAllCommitsForUser(long userId) {
        User u= repository.getUserForId(userId);
        return u.getCommits();
    }

    @Override
    public Map<Long, Long> getCommitCountByUser() {
        List<Long> ids= repository.getIdsForUsers(); //Obtengo todos los ids de los usuarios.
        Map<Long, Long> map= new HashMap<Long,Long>();
        for (Long id: ids){
            User u= repository.getUserForId(id); //Por cada id, traigo el usuario, junto con su lista de commits. PREGUNTAR SI HAY MANERA MAS EFICIENTE.
            Long count= new Long (u.getCommits().size());
            map.put(id,count);
        }
        return map;
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
