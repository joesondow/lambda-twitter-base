package sondow.twitter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * The function that AWS Lambda will invoke.
 *
 * @author @JoeSondow
 */
public class LambdaRequestHandler implements RequestHandler<Object, Object> {

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.RequestHandler#handleRequest(java.
     * lang.Object, com.amazonaws.services.lambda.runtime.Context)
     */
    @Override
    public Object handleRequest(Object input, Context context) {
        Configuration config = configure();
        Tweeter tweeter = createTweeter(config);
        String message = "";
        return tweeter.tweet(message);
    }

    /**
     * AWS Lambda only allows underscores in environment variables, not dots, so the default
     * ways twitter4j finds keys aren't possible. Instead, write your own code that gets the
     * configuration either from Lambda-friendly environment variables or from a default
     * twitter4j.properties file at the project root, or on the classpath, or in WEB-INF.
     *
     * @return configuration containing Twitter authentication strings
     */
    /* package */ Configuration configure() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        String consumerKeyEnvVar = getEnvVar("twitter4j_oauth_consumerKey");
        String consumerSecretEnvVar = getEnvVar("twitter4j_oauth_consumerSecret");
        String accessTokenEnvVar = getEnvVar("twitter4j_oauth_accessToken");
        String accessTokenSecretEnvVar = getEnvVar("twitter4j_oauth_accessTokenSecret");
        if (consumerKeyEnvVar != null) {
            cb.setOAuthConsumerKey(consumerKeyEnvVar);
        }
        if (consumerSecretEnvVar != null) {
            cb.setOAuthConsumerSecret(consumerSecretEnvVar);
        }
        if (accessTokenEnvVar != null) {
            cb.setOAuthAccessToken(accessTokenEnvVar);
        }
        if (accessTokenSecretEnvVar != null) {
            cb.setOAuthAccessTokenSecret(accessTokenSecretEnvVar);
        }
        Configuration config = cb.setTrimUserEnabled(true).build();
        return config;
    }

    /**
     * Provides a new Tweeter object.
     * 
     * This method is pulled out to make this class easier to unit test with a Spy in Spock.
     * 
     * @return the Tweeter that will do the tweeting
     */
    /* package */ Tweeter createTweeter(Configuration config) {
        return new Tweeter(config);
    }

    /**
     * Gets an environment variable, or null if there is no such environment variable for the
     * specified key.
     * 
     * This method is pulled out to make this class easier to unit test with a Spy in Spock.
     * 
     * @param key the name of the environment variable
     * @return the value of the environment variable
     */
    /* package */ String getEnvVar(String key) {
        return System.getenv(key);
    }
}
