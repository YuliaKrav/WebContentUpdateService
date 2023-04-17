package parser;

import parser.replies.LinkParserReplies;
import parser.replies.StackOverflowReply;

import java.net.URI;
import java.net.URISyntaxException;

public final class StackOverflowLinkParser extends LinkProcessor implements LinkParser {
    public StackOverflowLinkParser(LinkProcessor nextLinkProcessor) {
        super(nextLinkProcessor);
    }
    public LinkParserReplies process(String url) {
        try {
            URI uri = new URI(url);
            if ("stackoverflow.com".equals(uri.getHost())) {
                String[] pathSegments = uri.getPath().split("/");
                if (pathSegments.length >= 3 && "questions".equals(pathSegments[1])) {
                    System.out.println("stackoverflow: " + url + " " + pathSegments[2]);
                    return new StackOverflowReply(url, pathSegments[2]);
                }
            }
            return super.process(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
