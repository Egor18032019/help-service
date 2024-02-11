package org.example.configuration;

import org.example.annotation.Configuration;
import org.example.annotation.Instance;
import org.example.servlet.AnotherNewServlet;
import org.example.servlet.HelpServletImpl;
import org.example.store.GoodRepository;
import org.example.store.GoodRepositoryImpl;

@Configuration
public class SupportConfiguration {
    @Instance
    public HelpServletImpl getHelpServlet(GoodRepository getGoodRepository) {
        return new HelpServletImpl(getGoodRepository);
    }
    @Instance
    public AnotherNewServlet getAnotherNewServelet( ) {
        return new AnotherNewServlet( );
    }

    @Instance
    public GoodRepository getGoodRepository() {
        return new GoodRepositoryImpl();
    }
}
