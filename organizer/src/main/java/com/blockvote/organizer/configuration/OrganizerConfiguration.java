package com.blockvote.organizer.configuration;

import com.blockvote.core.bootstrap.BootstrapMediator;
import com.blockvote.core.contracts.interfaces.IElection;
import com.blockvote.core.contracts.interfaces.IElectionMaster;
import com.blockvote.core.os.OsInteraction;
import com.blockvote.organizer.controllers.AppPreloaderController;
import com.blockvote.organizer.controllers.CreateAccountController;
import com.blockvote.organizer.controllers.ElectionCreationController;
import com.blockvote.organizer.controllers.HomeController;
import com.blockvote.organizer.controllers.MainPageController;
import com.blockvote.organizer.controllers.RegisterVoterController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static com.blockvote.core.os.OsInteraction.UNIX;
import static com.blockvote.core.os.OsInteractionFactory.createOsInteraction;

@Configuration
@SuppressWarnings({"SpringFacetCodeInspection", "Duplicates"})
@ComponentScan(basePackages = {"com.blockvote.core.configuration"})
public class OrganizerConfiguration {

    private final IElection election;
    private final IElectionMaster electionMaster;

    public OrganizerConfiguration(IElection election,
                                  IElectionMaster electionMaster) {
        this.election = election;
        this.electionMaster = electionMaster;
    }

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
        mainPageController.setRegisterVoterNode(registerVoterView());
        mainPageController.setHomeNode(homeView());
        return mainPageController;
    }

    @Bean
    public CreateAccountController createAccountController() {
        return new CreateAccountController();
    }

    @Bean
    public AppPreloaderController appPreloaderController(IElection election,
                                                         IElectionMaster electionMaster,
                                                         OsInteraction osInteraction,
                                                         @Qualifier("mainPageScene") Scene mainPageScene,
                                                         @Qualifier("createAccountScene") Scene createAccountScene,
                                                         CreateAccountController createAccountController,
                                                         MainPageController mainPageController,
                                                         BootstrapMediator bootstrapMediator) {
        return new AppPreloaderController(election, electionMaster, osInteraction, mainPageScene, createAccountScene, createAccountController,
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
    public RegisterVoterController registerVoterController() {
        final RegisterVoterController registerVoterController = new RegisterVoterController();
        registerVoterController.setElectionMaster(electionMaster);
        return registerVoterController;
    }

    @Bean
    public HomeController homeController() {
        final HomeController homeController = new HomeController();
        return homeController;
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

    @Bean
    public Node registerVoterView() {
        try {
            final FXMLLoader registerVoterLoader = new FXMLLoader(getClass().getResource("/views/register_voter_view.fxml"));
            registerVoterLoader.setControllerFactory(param -> this.registerVoterController());
            return registerVoterLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public Node homeView() {
        try {
            final FXMLLoader homeViewLoader = new FXMLLoader(getClass().getResource("/views/home_view.fxml"));
            homeViewLoader.setControllerFactory(param -> this.homeController());
            return homeViewLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
