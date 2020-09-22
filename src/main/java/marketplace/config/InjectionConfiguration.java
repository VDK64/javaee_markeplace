package marketplace.config;

import marketplace.persistence.BidDao;
import marketplace.persistence.ItemDao;
import marketplace.persistence.UserDao;
import marketplace.persistence.impl.BidDaoImpl;
import marketplace.persistence.impl.ItemDaoImpl;
import marketplace.persistence.impl.UserDaoImpl;
import marketplace.persistence.mappers.ItemMapper;
import marketplace.persistence.mappers.UserMapper;
import marketplace.persistence.mappers.impl.ItemMapperImpl;
import marketplace.persistence.mappers.impl.UserMapperImpl;
import marketplace.services.BidService;
import marketplace.services.ItemService;
import marketplace.services.impl.BidServiceImpl;
import marketplace.services.impl.ItemServiceImpl;
import marketplace.validation.BidValidator;
import marketplace.validation.CommonValidator;
import marketplace.validation.ItemValidator;
import marketplace.validation.UserValidator;
import marketplace.validation.impl.BidValidatorImpl;
import marketplace.validation.impl.ItemValidatorImpl;
import marketplace.validation.impl.UserValidatorImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Singleton;

/**
 * This is a configuration class which enable dependency injection in a Rest Resource classes.
 */
public class InjectionConfiguration extends ResourceConfig {

    /**
     * Default constructor to register all classes of interfaces and bind them with implementations.
     */
    public InjectionConfiguration() {
        register(ItemService.class);
        register(BidService.class);
        register(UserDao.class);
        register(ItemDao.class);
        register(BidDao.class);
        register(CommonValidator.class);
        register(ItemValidator.class);
        register(BidValidator.class);
        register(ItemMapper.class);
        register(UserMapper.class);
        register(UserValidator.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(ItemServiceImpl.class).to(ItemService.class).in(Singleton.class);
                bind(BidServiceImpl.class).to(BidService.class).in(Singleton.class);
                bind(UserDaoImpl.class).to(UserDao.class).in(Singleton.class);
                bind(ItemDaoImpl.class).to(ItemDao.class).in(Singleton.class);
                bind(BidDaoImpl.class).to(BidDao.class).in(Singleton.class);
                bindAsContract(CommonValidator.class).in(Singleton.class);
                bind(ItemValidatorImpl.class).to(ItemValidator.class).in(Singleton.class);
                bind(BidValidatorImpl.class).to(BidValidator.class).in(Singleton.class);
                bind(UserValidatorImpl.class).to(UserValidator.class).in(Singleton.class);
                bind(ItemMapperImpl.class).to(ItemMapper.class).in(Singleton.class);
                bind(UserMapperImpl.class).to(UserMapper.class).in(Singleton.class);
            }
        });
    }

}