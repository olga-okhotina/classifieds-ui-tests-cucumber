package context;

import config.DriverFactory;
import org.openqa.selenium.WebDriver;
import utils.User;
import utils.UserSession;

public class ScenarioContext {

    public final WebDriver driver = DriverFactory.createDriver();

    public User currentUser;
    public UserSession userSession;
    public String accessToken;
    public String createdAdTitle;
}
