package org.example.configuration;

import org.example.annotation.Configuration;
import org.example.annotation.Instance;
import org.example.servlet.AnotherNewController;
import org.example.servlet.HelpControllerImpl;
import org.example.store.GoodRepository;
import org.example.store.GoodRepositoryImpl;

@Configuration
public class SupportConfiguration {
    @Instance
    public HelpControllerImpl getHelpServlet(GoodRepository getGoodRepository) {
        return new HelpControllerImpl(getGoodRepository);
    }
    @Instance
    public AnotherNewController getAnotherNewServlet(GoodRepository getGoodRepository ) {
        return new AnotherNewController(getGoodRepository);
    }

    @Instance
    public GoodRepository getGoodRepository() {
        return new GoodRepositoryImpl();
    }
}
