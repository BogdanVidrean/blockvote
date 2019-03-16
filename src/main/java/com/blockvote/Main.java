package com.blockvote;

import com.blockvote.configuration.ApplicationConfiguration;
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
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        final Scene preloaderScene = (Scene) applicationContext.getBean("appPreloaderScene");
        primaryStage.setScene(preloaderScene);
        primaryStage.show();
    }
}
