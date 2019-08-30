package ar.edu.unlp.info.bd2.elasticApp;

import ar.edu.unlp.info.bd2.config.ElasticConfiguration;
import ar.edu.unlp.info.bd2.services.BithubService;

public class ElasticApp {

    public static void main(String[] args) {
        ElasticConfiguration ec = new ElasticConfiguration();
        BithubService elasticService = ec.elasticService();

        elasticService.createUser("user@bithub.bd2.info.unlp.edu.ar", "User");



    }
}
