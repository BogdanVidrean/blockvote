package com.blockvote.voter.configuration;

import com.blockvote.core.os.OsInteraction;
import com.blockvote.voter.controllers.AppPreloaderController;
import com.blockvote.voter.controllers.CreateAccountController;
import com.blockvote.voter.controllers.MainPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static com.blockvote.core.os.OsInteraction.UNIX;
import static com.blockvote.core.os.OsInteractionFactory.createOsInteraction;

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
    public CreateAccountController createAccountController() {
        return new CreateAccountController();
    }

    @Bean
    public AppPreloaderController appPreloaderController(OsInteraction osInteraction,
                                                         @Qualifier("mainPageScene") Scene mainPageScene,
                                                         @Qualifier("createAccountScene") Scene createAccountScene,
                                                         CreateAccountController createAccountController,
                                                         MainPageController mainPageController) {
        return new AppPreloaderController(osInteraction, mainPageScene, createAccountScene, createAccountController, mainPageController);
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

    @Bean(name = "createAccountScene")
    public Scene createAccountScene(CreateAccountController createAccountController) {
        final FXMLLoader createAccountModal = new FXMLLoader(getClass().getResource("/views/create_account_modal.fxml"));
        createAccountModal.setControllerFactory(param -> createAccountController);
        try {
            return new Scene(createAccountModal.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
