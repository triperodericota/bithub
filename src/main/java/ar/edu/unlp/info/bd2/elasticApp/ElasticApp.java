package ar.edu.unlp.info.bd2.elasticApp;

import ar.edu.unlp.info.bd2.config.ElasticConfiguration;
import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.services.BithubException;
import ar.edu.unlp.info.bd2.services.BithubService;

import java.util.Arrays;

public class ElasticApp {

    public static void main(String[] args) throws BithubException {
        ElasticConfiguration ec = new ElasticConfiguration();
        BithubService elasticService = ec.elasticService();

        /* Create indexes and documents */

        User user = elasticService.createUser("user@bithub.bd2.info.unlp.edu.ar", "User");
        Branch master = elasticService.createBranch("Master");
        File file1 = elasticService.createFile("System.out.println(\"Hello world\");", "Main.java");
        File file2 = elasticService.createFile("print(\"Hello world\")", "Main.py");

        Commit commit =
                elasticService.createCommit(
                        "Initial commit", "ab4f19z", user, Arrays.asList(file1, file2), master);

        Tag tag = elasticService.createTagForCommit("ab4f19z", "tag1");

        /* Searchs */


    }
}
