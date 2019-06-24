package com.blockvote.organizer;

import com.blockvote.organizer.configuration.OrganizerConfiguration;
import com.blockvote.organizer.controllers.AppPreloaderController;
import com.blockvote.organizer.controllers.ElectionCreationController;
import com.blockvote.organizer.controllers.VoteController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.web3j.protocol.Web3j;

public class Main extends Application {
    private VoteController voteController;
    private ElectionCreationController electionCreationController;
    private Web3j web3j;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(OrganizerConfiguration.class);

        voteController = applicationContext.getBean(VoteController.class);
        electionCreationController = applicationContext.getBean(ElectionCreationController.class);
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
