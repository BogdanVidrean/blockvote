package com.blockvote.organizer;

import com.blockvote.organizer.configuration.OrganizerConfiguration;
import com.blockvote.organizer.controllers.AppPreloaderController;
import com.blockvote.organizer.controllers.VoteController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;

public class Main extends Application {
    private VoteController voteController;
    private Web3j web3j;
    private HttpService httpService;
    private ExecutorService executorService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(OrganizerConfiguration.class);

        voteController = applicationContext.getBean(VoteController.class);
        web3j = applicationContext.getBean(Web3j.class);
        httpService = applicationContext.getBean(HttpService.class);
        executorService = applicationContext.getBean(ExecutorService.class);

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
        if (httpService != null) {
            try {
                httpService.close();
                Field f = httpService.getClass().getDeclaredField("httpClient");
                f.setAccessible(true);
                OkHttpClient okHttpClient = (OkHttpClient) f.get(httpService);
                okHttpClient.dispatcher().executorService().shutdown();
                okHttpClient.connectionPool().evictAll();
            } catch (IOException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
