package parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.replies.GitHubReply;
import parser.replies.LinkParserReplies;
import parser.replies.StackOverflowReply;

import static org.junit.jupiter.api.Assertions.*;

public class LinkProcessingChainTest {

    private LinkProcessingChain linkProcessingChain;

    @BeforeEach
    void setUp() {
        linkProcessingChain = new LinkProcessingChain();
    }

    @Test
    void testValidGitHubLink() {
        String url = "https://github.com/sanyarnd/tinkoff-java-course-2022/";
        LinkParserReplies reply = linkProcessingChain.process(url);
        assertTrue(reply instanceof GitHubReply);
        assertEquals(url, ((GitHubReply) reply).url());
        assertEquals("sanyarnd", ((GitHubReply) reply).user());
        assertEquals("tinkoff-java-course-2022", ((GitHubReply) reply).repository());
    }

    @Test
    void testInvalidGitHubLink() {
        String url = "https://github.com/masha/";
        LinkParserReplies reply = linkProcessingChain.process(url);
        assertNull(reply);
    }

    @Test
    void testValidStackOverflowLink() {
        String url = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        LinkParserReplies reply = linkProcessingChain.process(url);
        assertTrue(reply instanceof StackOverflowReply);
        assertEquals(url, ((StackOverflowReply) reply).url());
        assertEquals("1642028", ((StackOverflowReply) reply).idRequest());
    }

    @Test
    void testInvalidStackOverflowLink() {
        String url = "https://stackoverflow.com/search?q=unsupported%20link";
        LinkParserReplies reply = linkProcessingChain.process(url);
        assertNull(reply);
    }

    @Test
    void tesUnsupportedLink() {
        String url = "https://www.udemy.com/";
        LinkParserReplies reply = linkProcessingChain.process(url);
        assertNull(reply);
    }
}
