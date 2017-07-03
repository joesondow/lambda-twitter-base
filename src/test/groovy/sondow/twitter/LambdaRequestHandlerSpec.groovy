package sondow.twitter

import com.amazonaws.services.lambda.runtime.Context

import spock.lang.Specification
import twitter4j.conf.Configuration

class LambdaRequestHandlerSpec extends Specification {

    def "handleRequest should create a configured tweeter and tweet an empty string"() {
        setup:
        Context context = Stub()
        Configuration config = Stub()
        Tweeter tweeter = Mock()
        LambdaRequestHandler handler = Spy()

        when:
        handler.handleRequest(new Object(), context)

        then:
        1 * handler.configure() >> config
        1 * handler.createTweeter(config) >> tweeter
        1 * tweeter.tweet("")
    }


    def "configure should populate a configuration with environment variables"() {
        setup:
        LambdaRequestHandler handler = Spy()

        when:
        Configuration config = handler.configure()

        then:
        4 * handler.getEnvVar(_) >> { String key -> "123" + key[16..-1] }
        with (config) {
            OAuthConsumerKey == '123consumerKey'
            OAuthConsumerSecret == '123consumerSecret'
            OAuthAccessToken == '123accessToken'
            OAuthAccessTokenSecret == '123accessTokenSecret'
        }
    }

    def "handleRequest should throw IllegalStateException if environment variables are missing"() {
        setup:
        Context context = Stub()
        LambdaRequestHandler handler = Spy()

        when:
        handler.handleRequest(new Object(), context)

        then:
        4 * handler.getEnvVar(_) >> null
        thrown(IllegalStateException)
    }
}
