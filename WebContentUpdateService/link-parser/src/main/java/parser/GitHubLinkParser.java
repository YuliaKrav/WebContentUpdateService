package parser;

import parser.replies.GitHubReply;
import parser.replies.LinkParserReplies;
import java.net.URI;
import java.net.URISyntaxException;

public final class GitHubLinkParser extends LinkProcessor implements LinkParser {
    public GitHubLinkParser(LinkProcessor nextLinkProcessor) {
        super(nextLinkProcessor);
    }

    @Override
    public LinkParserReplies processParsing(String url) {
        try {
            URI uri = new URI(url);
            if ("github.com".equals(uri.getHost())) {
                String[] pathSegments = uri.getPath().split("/");
                if (pathSegments.length >= 3) {
                    System.out.println("github: " + url + " " + pathSegments[1] + " " + pathSegments[2]);
                    return new GitHubReply(url, pathSegments[1], pathSegments[2]);
                }
            }
            super.process(url);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}

