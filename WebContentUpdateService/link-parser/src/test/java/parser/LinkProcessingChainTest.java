package parser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.replies.LinkParserReplies;

import static org.junit.jupiter.api.Assertions.assertNull;

public class LinkProcessingChainTest {


    private LinkProcessingChain linkProcessingChain;

    @BeforeEach
    void setUp() {
        linkProcessingChain = new LinkProcessingChain();
    }

    @Test
    void testValidGitHubLink() {

    }

    @Test
    void testInvalidGitHubLink() {
//        String url = "https://github.com/masha/";
//        LinkParserReplies reply = linkProcessingChain.process(url);
//        assertNull(reply);
    }

    @Test
    void testValidStackOverflowLink() {

    }

    @Test
    void testInvalidStackOverflowLink() {
//        String url = "https://stackoverflow.com/search?q=unsupported%20link";
//        LinkParserReplies reply = linkProcessingChain.process(url);
//        assertNull(reply);

    }

    @Test
    void tesUnsupportedLink() {
//        String url = "https://www.udemy.com/";
//        LinkParserReplies reply = linkProcessingChain.process(url);
//        assertNull(reply);
    }

}
