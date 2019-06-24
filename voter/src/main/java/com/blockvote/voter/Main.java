package com.blockvote.voter;

import com.blockvote.voter.configuration.VoterConfiguration;
import com.blockvote.voter.controllers.AppPreloaderController;
import com.blockvote.voter.controllers.VoteController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.web3j.protocol.Web3j;

@ComponentScan(basePackages = {"configuration"})
public class Main extends Application {

    private VoteController voteController;
    private Web3j web3j;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(VoterConfiguration.class);

        voteController = applicationContext.getBean(VoteController.class);
        web3j = applicationContext.getBean(Web3j.class);

        final Scene preloaderScene = (Scene) applicationContext.getBean("appPreloaderScene");
        final AppPreloaderController appPreloaderController = applicationContext.getBean(AppPreloaderController.class);
        appPreloaderController.setPrimaryStage(primaryStage);
        primaryStage.setScene(preloaderScene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        if (voteController != null) {
            voteController.destroyOnExit();
        }
        if (web3j != null) {
            web3j.shutdown();
        }
    }
}
