package parser;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.replies.LinkParserReplies;

public class TestMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(NullLinkParser.class);

    private TestMain() {

    }

    public static void main(String[] args) {
        String[] testUrlArray = {"https://github.com/sanyarnd/tinkoff-java-course-2022/",
            "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
            "https://stackoverflow.com/search?q=unsupported%20link",
            "test.org",
            "https://github.com/masha/",
            "https://github.com/fedya/math/"};

        List<LinkParserReplies> replyList = new ArrayList<>();

        LinkProcessingChain chainTest = new LinkProcessingChain();
        for (String url : testUrlArray) {
            replyList.add(chainTest.process(url));
        }

        LOGGER.info(replyList.toString());
    }
}
