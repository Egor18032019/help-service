package org.example.configuration;

import org.example.annotation.Configuration;
import org.example.annotation.Instance;
import org.example.servlet.HelpServlet;
import org.example.store.GoodRepository;
import org.example.store.GoodRepositoryImpl;

@Configuration
public class SupportConfiguration {
    @Instance
    public HelpServlet getHelpServlet() {
        return new HelpServlet( getGoodRepository());
    }

    @Instance
    public GoodRepository getGoodRepository() {
        return new GoodRepositoryImpl();
    }
}
