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
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(VoterConfiguration.class);
        final Scene preloaderScene = (Scene) applicationContext.getBean("appPreloaderScene");
        final AppPreloaderController appPreloaderController = applicationContext.getBean(AppPreloaderController.class);
        appPreloaderController.setPrimaryStage(primaryStage);
        primaryStage.setScene(preloaderScene);
        //TODO: This is a limitation, and the logout might me a little bit problematic regarding the height of the stage
//        primaryStage.setMinHeight(945);
//        primaryStage.setMinWidth(1200);
//        primaryStage.fullScreenProperty().addListener((v, o, n) -> {
//            primaryStage.setResizable(false);
//            primaryStage.setResizable(true);
//        });
        primaryStage.show();
    }
}
