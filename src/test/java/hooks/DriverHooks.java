package hooks;

import context.ScenarioContext;
import io.cucumber.java.After;

public class DriverHooks {

    private final ScenarioContext context;

    public DriverHooks(ScenarioContext context) {
        this.context = context;
    }

    @After
    public void tearDown() {
        context.driver.quit();
    }
}
