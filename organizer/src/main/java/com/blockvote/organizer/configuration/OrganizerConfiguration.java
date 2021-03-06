package com.blockvote.organizer.configuration;

import com.blockvote.core.bootstrap.BootstrapMediator;
import com.blockvote.core.bootstrap.BootstrapService;
import com.blockvote.core.contracts.dispatcher.ElectionsDispatcher;
import com.blockvote.core.contracts.interfaces.IElectionMaster;
import com.blockvote.core.gethRpcServices.AdminService;
import com.blockvote.core.gethRpcServices.MiningService;
import com.blockvote.core.os.OsInteraction;
import com.blockvote.organizer.controllers.AppPreloaderController;
import com.blockvote.organizer.controllers.CreateAccountController;
import com.blockvote.organizer.controllers.ElectionCreationController;
import com.blockvote.organizer.controllers.HomeController;
import com.blockvote.organizer.controllers.MainPageController;
import com.blockvote.organizer.controllers.RegisterVoterController;
import com.blockvote.organizer.controllers.VoteController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import static com.blockvote.core.os.OsInteraction.UNIX;
import static com.blockvote.core.os.OsInteractionFactory.createOsInteraction;

@Configuration
@SuppressWarnings({"SpringFacetCodeInspection", "Duplicates"})
@ComponentScan(basePackages = {"com.blockvote.core.configuration"})
public class OrganizerConfiguration {

    private final IElectionMaster electionMaster;
    private final Web3j web3j;
    private final ElectionsDispatcher electionsDispatcher;
    private final MiningService miningService;
    private final BootstrapService bootstrapService;
    private final AdminService adminService;
    private final Properties applicationProperties;
    private final ExecutorService executorService;

    public OrganizerConfiguration(
            IElectionMaster electionMaster,
            Web3j web3j,
            ElectionsDispatcher electionsDispatcher,
            MiningService miningService,
            BootstrapService bootstrapService,
            AdminService adminService,
            Properties applicationProperties,
            ExecutorService executorService) {
        this.electionMaster = electionMaster;
        this.web3j = web3j;
        this.electionsDispatcher = electionsDispatcher;
        this.miningService = miningService;
        this.bootstrapService = bootstrapService;
        this.adminService = adminService;
        this.applicationProperties = applicationProperties;
        this.executorService = executorService;
    }

    @PostConstruct
    public void postConstruct() {
        //setters
        mainPageController().setAppPreloaderScene(appPreloaderScene());

        //  login observers
        appPreloaderController().addObserver(electionCreationController());
        appPreloaderController().addObserver(voteController());
        appPreloaderController().addObserver(electionsDispatcher);

        // logout observers
        mainPageController().addObserver(electionCreationController());
        mainPageController().addObserver(voteController());
        mainPageController().addObserver(registerVoterController());
    }

    @Bean
    public OsInteraction osInteraction() {
        return createOsInteraction(UNIX);
    }

    @Bean
    public BootstrapMediator bootstrapMediator() {
        return new BootstrapMediator(osInteraction(), bootstrapService, adminService, executorService, web3j);
    }

    @Bean
    public MainPageController mainPageController() {
        MainPageController mainPageController = new MainPageController();
        mainPageController.setElectionCreationNode(electionCreationView());
        mainPageController.setRegisterVoterNode(registerVoterView());
        mainPageController.setHomeNode(homeView());
        mainPageController.setVotePage(voteView());
        mainPageController.setMiningService(miningService);
        return mainPageController;
    }

    @Bean
    public CreateAccountController createAccountController() {
        return new CreateAccountController();
    }

    @Bean
    public AppPreloaderController appPreloaderController() {
        return new AppPreloaderController(
                electionMaster, osInteraction(), mainPageScene(), createAccountScene(), createAccountController(),
                mainPageController(), bootstrapMediator());
    }

    @Bean(name = "mainPageScene")
    public Scene mainPageScene() {
        final FXMLLoader mainPageLoader = new FXMLLoader(getClass().getResource("/views/main_page.fxml"));
        mainPageLoader.setControllerFactory(param -> mainPageController());
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
        appPreloader.setControllerFactory(param -> appPreloaderController());
        try {
            return new Scene(appPreloader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean(name = "createAccountScene")
    public Scene createAccountScene() {
        final FXMLLoader createAccountModal = new FXMLLoader(getClass().getResource("/views/create_account_modal.fxml"));
        createAccountModal.setControllerFactory(param -> createAccountController());
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
        electionCreationController.setWeb3j(web3j);
        electionCreationController.setApplicationProperties(applicationProperties);
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
    public VoteController voteController() {
        final VoteController voteController = new VoteController();
        voteController.setElectionMaster(electionMaster);
        voteController.setElectionsDispatcher(electionsDispatcher);
        voteController.setWeb3j(web3j);
        voteController.setApplicationProperties(applicationProperties);
        return voteController;
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

    @Bean
    public Node voteView() {
        try {
            final FXMLLoader voteViewLoader = new FXMLLoader(getClass().getResource("/views/vote_page.fxml"));
            voteViewLoader.setControllerFactory(param -> this.voteController());
            return voteViewLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
