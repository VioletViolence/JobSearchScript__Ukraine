import specificSiteControllers.AngelList_Controller;
import specificSiteControllers.RabotaUA_Controller;

public class MainController {
    public static void main(String[] args) throws InterruptedException {
        RabotaUA_Controller.searchOnRabota("Java","brosinskiy@gmail.com","Bogdan","Rosinskiy","C1");
        //AngelList_Controller.searchOnAngelList("brosinskiy@gmail.com","121212aA");
    }
}
