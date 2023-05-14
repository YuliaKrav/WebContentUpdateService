package parser;

import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.replies.LinkParserReplies;
import parser.replies.StackOverflowReply;

public final class StackOverflowLinkParser extends LinkProcessor implements LinkParser {

    private final int segmentsLength = 3;
    private static final Logger LOGGER = LoggerFactory.getLogger(NullLinkParser.class);

    public StackOverflowLinkParser(LinkProcessor nextLinkProcessor) {
        super(nextLinkProcessor);
    }

    public LinkParserReplies process(String url) {
        try {
            URI uri = new URI(url);
            if ("stackoverflow.com".equals(uri.getHost())) {
                String[] pathSegments = uri.getPath().split("/");
                if (pathSegments.length >= segmentsLength && "questions".equals(pathSegments[1])) {
                    LOGGER.info("stackoverflow: " + url + " " + pathSegments[2]);
                    return new StackOverflowReply(url, pathSegments[2]);
                }
            }
            return super.process(url);
        } catch (URISyntaxException e) {
            LOGGER.warn(e.getMessage());
        }
        return null;
    }
}
