package parser;

import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.replies.GitHubReply;
import parser.replies.LinkParserReplies;

public final class GitHubLinkParser extends LinkProcessor implements LinkParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubLinkParser.class);
    private final int segmentsLength = 3;

    public GitHubLinkParser(LinkProcessor nextLinkProcessor) {
        super(nextLinkProcessor);
    }

    public LinkParserReplies process(String url) {
        try {
            URI uri = new URI(url);
            if ("github.com".equals(uri.getHost())) {
                String[] pathSegments = uri.getPath().split("/");
                if (pathSegments.length >= segmentsLength) {
                    LOGGER.info("github: " + url + " " + pathSegments[1] + " " + pathSegments[2]);
                    return new GitHubReply(url, pathSegments[1], pathSegments[2]);
                }
            }
            return super.process(url);

        } catch (URISyntaxException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }
}

