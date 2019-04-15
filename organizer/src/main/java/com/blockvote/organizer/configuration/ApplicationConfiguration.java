package com.blockvote.organizer.configuration;

import com.blockvote.core.bootstrap.BootstrapMediator;
import com.blockvote.core.os.OsInteraction;
import com.blockvote.organizer.controllers.AppPreloaderController;
import com.blockvote.organizer.controllers.CreateAccountController;
import com.blockvote.organizer.controllers.ElectionCreationController;
import com.blockvote.organizer.controllers.MainPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    public BootstrapMediator bootstrapMediator(OsInteraction osInteraction) {
        return new BootstrapMediator(osInteraction);
    }

    @Bean
    public MainPageController mainPageController() {
        MainPageController mainPageController = new MainPageController();
        mainPageController.setElectionCreationNode(electionCreationView());
        return mainPageController;
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
                                                         MainPageController mainPageController,
                                                         BootstrapMediator bootstrapMediator) {
        return new AppPreloaderController(osInteraction, mainPageScene, createAccountScene, createAccountController,
                mainPageController, bootstrapMediator);
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

    @Bean
    public ElectionCreationController electionCreationController() {
        final ElectionCreationController electionCreationController = new ElectionCreationController();
        return electionCreationController;
    }

    @Bean
    public Node electionCreationView() {
        try {
            final FXMLLoader electionCreationLoade = new FXMLLoader(getClass().getResource("/views/election_creation_view.fxml"));
            electionCreationLoade.setControllerFactory(param -> this.electionCreationController());
            return electionCreationLoade.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
