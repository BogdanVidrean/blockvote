package com.blockvote.organizer;

import com.blockvote.organizer.configuration.OrganizerConfiguration;
import com.blockvote.organizer.controllers.AppPreloaderController;
import com.blockvote.organizer.controllers.VoteController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.lang.reflect.Field;

public class Main extends Application {
    private VoteController voteController;
    private Web3j web3j;
    private CloseableHttpAsyncClient closeableHttpAsyncClient;
    private CloseableHttpClient closeableHttpClient;
    private HttpService httpService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(OrganizerConfiguration.class);

        voteController = applicationContext.getBean(VoteController.class);
        web3j = applicationContext.getBean(Web3j.class);
        closeableHttpAsyncClient = applicationContext.getBean(CloseableHttpAsyncClient.class);
        closeableHttpClient = applicationContext.getBean(CloseableHttpClient.class);
        httpService = applicationContext.getBean(HttpService.class);

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
//        Unirest.shutDown();
//        try {
//            closeableHttpClient.close();
//            closeableHttpAsyncClient.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
    }
}
