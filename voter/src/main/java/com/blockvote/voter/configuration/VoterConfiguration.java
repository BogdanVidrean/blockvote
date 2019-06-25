package com.blockvote.voter.configuration;

import com.blockvote.core.bootstrap.BootstrapMediator;
import com.blockvote.core.bootstrap.BootstrapService;
import com.blockvote.core.contracts.dispatcher.ElectionsDispatcher;
import com.blockvote.core.contracts.interfaces.IElectionMaster;
import com.blockvote.core.gethRpcServices.AdminService;
import com.blockvote.core.gethRpcServices.MiningService;
import com.blockvote.core.os.OsInteraction;
import com.blockvote.voter.controllers.AppPreloaderController;
import com.blockvote.voter.controllers.CheckEligibilityController;
import com.blockvote.voter.controllers.CreateAccountController;
import com.blockvote.voter.controllers.HomeController;
import com.blockvote.voter.controllers.MainPageController;
import com.blockvote.voter.controllers.VoteController;
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
public class VoterConfiguration {

    private final IElectionMaster electionMaster;
    private final Web3j web3j;
    private final ElectionsDispatcher electionsDispatcher;
    private final MiningService miningService;
    private final BootstrapService bootstrapService;
    private final AdminService adminService;
    private final Properties applicationProperties;
    private final ExecutorService executorService;

    public VoterConfiguration(IElectionMaster electionMaster,
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

        //  logout observers
        mainPageController().addObserver(voteController());

        //login observers
        appPreloaderController().addObserver(voteController());
        appPreloaderController().addObserver(electionsDispatcher);
    }

    @Bean
    public OsInteraction osInteraction() {
        return createOsInteraction(UNIX);
    }

    @Bean
    public BootstrapMediator bootstrapMediator() {
        return new BootstrapMediator(osInteraction(), bootstrapService, adminService, executorService);
    }

    @Bean
    public MainPageController mainPageController() {
        MainPageController mainPageController = new MainPageController();
        mainPageController.setHomeNode(homeViewScene());
        mainPageController.setCheckEligibilityNode(checkEligibilityScene());
        mainPageController.setVoteScene(voteScene());
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
                electionMaster,
                osInteraction(),
                mainPageScene(),
                createAccountScene(),
                createAccountController(),
                mainPageController(),
                bootstrapMediator());
    }

    @Bean
    public HomeController homeController() {
        final HomeController homeController = new HomeController();
        return homeController;
    }

    @Bean
    public CheckEligibilityController checkEligibilityController() {
        final CheckEligibilityController checkEligibilityController = new CheckEligibilityController();
        checkEligibilityController.setElectionsMaster(electionMaster);
        return checkEligibilityController;
    }

    @Bean
    public VoteController voteController() {
        VoteController voteController = new VoteController();
        voteController.setElectionMaster(electionMaster);
        voteController.setElectionsDispatcher(electionsDispatcher);
        voteController.setWeb3j(web3j);
        voteController.setApplicationProperties(applicationProperties);
        voteController.setExecutorService(executorService);
        return voteController;
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

    @Bean(name = "homeViewScene")
    public Node homeViewScene() {
        try {
            final FXMLLoader homeViewLoader = new FXMLLoader(getClass().getResource("/views/home_view.fxml"));
            homeViewLoader.setControllerFactory(param -> this.homeController());
            return homeViewLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean(name = "checkEligibilityScene")
    public Node checkEligibilityScene() {
        try {
            final FXMLLoader homeViewLoader = new FXMLLoader(getClass().getResource("/views/check_eligibility_page.fxml"));
            homeViewLoader.setControllerFactory(param -> this.checkEligibilityController());
            return homeViewLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean(name = "voteScene")
    public Node voteScene() {
        try {
            final FXMLLoader homeViewLoader = new FXMLLoader(getClass().getResource("/views/vote_page.fxml"));
            homeViewLoader.setControllerFactory(param -> this.voteController());
            return homeViewLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
