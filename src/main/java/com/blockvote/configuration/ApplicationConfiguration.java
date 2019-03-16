package com.blockvote.configuration;

import com.blockvote.controllers.AppPreloaderController;
import com.blockvote.controllers.MainPageController;
import com.blockvote.os.OsInteraction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static com.blockvote.os.OsInteraction.UNIX;
import static com.blockvote.os.OsInteractionFactory.createOsInteraction;

@Configuration
@SuppressWarnings({"SpringFacetCodeInspection", "Duplicates"})
public class ApplicationConfiguration {

    @Bean
    public OsInteraction osInteraction() {
        return createOsInteraction(UNIX);
    }

    @Bean
    public MainPageController mainPageController() {
        return new MainPageController();
    }

    @Bean
    public AppPreloaderController appPreloaderController(OsInteraction osInteraction) {
        return new AppPreloaderController(osInteraction);
    }

    @Bean(name = "mainPageScene")
    public Scene mainPageScene(MainPageController mainPageController) {
        final FXMLLoader mainPageLoader = new FXMLLoader(getClass().getResource("/views/main_page.fxml"));
        mainPageLoader.setControllerFactory(param -> mainPageController);
        try {
            return new Scene(mainPageLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean(name = "appPreloaderScene")
    public Scene appPreloaderScene(AppPreloaderController appPreloaderController) {
        final FXMLLoader appPreloader = new FXMLLoader(getClass().getResource("/views/app_preloader.fxml"));
        appPreloader.setControllerFactory(param -> appPreloaderController);
        try {
            return new Scene(appPreloader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
