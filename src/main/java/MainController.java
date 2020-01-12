import specificSiteControllers.AngelList_Controller;
import specificSiteControllers.RabotaUA_Controller;
import specificSiteControllers.WorkUA_Controller;

public class MainController {
    public static void main(String[] args) throws InterruptedException {
        RabotaUA_Controller.searchOnRabota("Developer","brosinskiy@gmail.com","Bogdan","Rosinskiy","C1");
        WorkUA_Controller.searchOnWork("Developer","brosinskiy@gmail.com","Bogdan Rosinskiy");
    }
}

