package marketplace;

import marketplace.config.connection_pool.impl.BasicConnectionPool;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.security.JDBCLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Main class of an application. Includes configurations of embedded server (Jetty) and starts it.
 */
public class MarketPlaceApplication {
    private static final String[] JETTY_CONFIGURATION_CLASSES = {"org.eclipse.jetty.webapp.WebXmlConfiguration"};
    private static final String JETTY_WEB_XML_CONFIGURATION = "org.eclipse.jetty.webapp.JettyWebXmlConfiguration";
    private static final String ANNOTATION_CONFIGURATION = "org.eclipse.jetty.annotations.AnnotationConfiguration";
    private static final String WAR_FILE = "target/marketplace-1.0-SNAPSHOT.war";
    private static final String RESOURCE_BASE = "src/main/webapp";
    private static final String TEMP_DIR = "java.io.tmpdir";
    private static final String MARKET_PLACE = "marketplace";
    private static final String SCRATCH_DIRECTORY_ERROR = "Unable to create scratch directory: ";
    private static final String JAVAX_SERVLET_TEMP_DIR = "javax.servlet.context.tempdir";
    private static final String JAR_SCANNER = "org.apache.tomcat.JarScanner";
    private static final String JDBC_PROPERTIES_FILE = "jdbcRealm.properties";
    private static final String SECURITY_REALM_NAME = "Main Realm";
    private final int port;
    private Server server;

    /**
     * Entry point of an application. Set port, create application instance,
     * start server and wait for interrupt if occurred.
     *
     * @param args arguments from command line.
     * @throws Exception if occurred.
     */
    public static void main(String[] args) throws Exception {
        BasicConnectionPool.load();
        int port = 9090;
        MarketPlaceApplication application = new MarketPlaceApplication(port);
        application.start();
        application.waitForInterrupt();
    }

    /**
     * Start server method. Creates connector instance with certain port,
     * set connector to the server. Also, create web context and sets it to the server too.
     *
     * @throws Exception if occurred.
     */
    public void start() throws Exception {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);
        configureClassList();
        WebAppContext webapp = new WebAppContext();
        configureWebContext(webapp);
        server.setHandler(webapp);
        final String configs
                = Objects.requireNonNull(this.getClass().getClassLoader()
                .getResource(JDBC_PROPERTIES_FILE)).toExternalForm();
        server.addBean(new JDBCLoginService(SECURITY_REALM_NAME, configs));
        server.start();
    }

    /**
     * Configures class list of <code>ClassList</code> class in <code>Configuration</code> interface.
     */
    private void configureClassList() {
        Configuration.ClassList classlist = Configuration.ClassList
                .setServerDefault(server);
        classlist.addBefore(JETTY_WEB_XML_CONFIGURATION, ANNOTATION_CONFIGURATION);
    }

    /**
     * Method which configures a web context by setting: context path,
     * configuration classes, resource base, classloader, attribute,
     * <code>JspStarter</code> bean.
     *
     * @param webapp web context to configure.
     * @throws IOException - throw this exception if occurred.
     */
    private void configureWebContext(WebAppContext webapp) throws IOException {
        webapp.setContextPath("/");
        Path warFile = Paths.get(WAR_FILE);
        if (!Files.exists(warFile)) {
            throw new FileNotFoundException(warFile.toString());
        }
        webapp.setConfigurationClasses(JETTY_CONFIGURATION_CLASSES);
        webapp.setResourceBase(RESOURCE_BASE);
        webapp.setWarResource(new PathResource(warFile));
        webapp.setExtractWAR(true);
        File tempDir = new File(System.getProperty(TEMP_DIR));
        File scratchDir = new File(tempDir.toString(), MARKET_PLACE);
        if (!scratchDir.exists()) {
            if (!scratchDir.mkdirs()) {
                throw new IOException(SCRATCH_DIRECTORY_ERROR + scratchDir);
            }
        }
        webapp.setAttribute(JAVAX_SERVLET_TEMP_DIR, scratchDir);
        ClassLoader jspClassLoader = new URLClassLoader(new URL[0], this.getClass().getClassLoader());
        webapp.setClassLoader(jspClassLoader);
        webapp.addBean(new JspStarter(webapp));
    }

    /**
     * Constructor of an application class to set certain port.
     *
     * @param port of application.
     */
    public MarketPlaceApplication(int port) {
        this.port = port;
    }

    /**
     * Make the server wait for a interrupt.
     *
     * @throws InterruptedException if occurred.
     */
    public void waitForInterrupt() throws InterruptedException {
        server.join();
    }

    /**
     * Static configuration class to set context class loader,
     * set <code>JettyJasperInitializer</code>, <code>StandardJarScanner</code>
     * and start <code>JspStarter</code>.
     */
    public static class JspStarter extends AbstractLifeCycle
            implements ServletContextHandler.ServletContainerInitializerCaller {
        private final JettyJasperInitializer sci;
        private final ServletContextHandler context;

        public JspStarter(ServletContextHandler context) {
            this.sci = new JettyJasperInitializer();
            this.context = context;
            this.context.setAttribute(JAR_SCANNER, new StandardJarScanner());
        }

        @Override
        protected void doStart() throws Exception {
            ClassLoader old = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(context.getClassLoader());
            try {
                sci.onStartup(null, context.getServletContext());
                super.doStart();
            } finally {
                Thread.currentThread().setContextClassLoader(old);
            }
        }
    }

}


