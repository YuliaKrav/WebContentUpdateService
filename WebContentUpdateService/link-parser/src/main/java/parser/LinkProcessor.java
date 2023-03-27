package parser;

import parser.replies.LinkParserReplies;

public abstract class LinkProcessor {

    private LinkProcessor nextLinkProcessor;

    public LinkProcessor(LinkProcessor nextLinkProcessor) {
        this.nextLinkProcessor = nextLinkProcessor;
    }

    public void process(String url) {
        if (url != null) {
            nextLinkProcessor.processParsing(url);
        }
    }

    public abstract LinkParserReplies processParsing(String url);
}
