package sondow.twitter

import spock.lang.Specification
import twitter4j.Status
import twitter4j.Twitter

class TweeterSpec extends Specification {

    def "attempting to tweet a message calls the twitter api's updateStatus"() {
        setup:
        Status mockStatus = Stub()
        Tweeter tweeter = new Tweeter()
        Twitter mockTwitter = Mock()
        tweeter.twitter = mockTwitter
        Status result

        when:
        result = tweeter.tweet("Hey look I'm a tweet")

        then:
        1 * mockTwitter.updateStatus("Hey look I'm a tweet") >> mockStatus
        result == mockStatus
    }
}
