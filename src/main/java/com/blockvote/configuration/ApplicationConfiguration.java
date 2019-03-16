package com.blockvote.configuration;

import com.blockvote.controllers.AppPreloaderController;
import com.blockvote.controllers.MainPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@SuppressWarnings("SpringFacetCodeInspection")
public class ApplicationConfiguration {


    @Bean
    public MainPageController mainPageController() {
        return new MainPageController();
    }

    @Bean
    public AppPreloaderController appPreloaderController() {
        return new AppPreloaderController();
    }

    @Bean(name = "mainPageScene")
    public Scene mainPageScene() {
        final FXMLLoader mainPageLoader = new FXMLLoader(getClass().getResource("/views/main_page.fxml"));
        mainPageLoader.setControllerFactory(param -> this.mainPageController());
        try {
            return new Scene(mainPageLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean(name = "appPreloaderScene")
    public Scene appPreloaderScene() {
        final FXMLLoader appPreloader = new FXMLLoader(getClass().getResource("/views/app_preloader.fxml"));
        appPreloader.setControllerFactory(param -> this.appPreloaderController());
        try {
            return new Scene(appPreloader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
