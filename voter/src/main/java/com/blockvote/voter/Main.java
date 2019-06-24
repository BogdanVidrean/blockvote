package com.blockvote.voter;

import com.blockvote.voter.configuration.VoterConfiguration;
import com.blockvote.voter.controllers.AppPreloaderController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"configuration"})
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(VoterConfiguration.class);
        final Scene preloaderScene = (Scene) applicationContext.getBean("appPreloaderScene");
        final AppPreloaderController appPreloaderController = applicationContext.getBean(AppPreloaderController.class);
        appPreloaderController.setPrimaryStage(primaryStage);
        primaryStage.setScene(preloaderScene);
        primaryStage.show();
    }

    @Override
    public void stop() {

    }
}
