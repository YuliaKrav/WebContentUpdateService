package parser;

import parser.replies.LinkParserReplies;

public abstract class LinkProcessor {

    private LinkProcessor nextLinkProcessor;

    public LinkProcessor(LinkProcessor nextLinkProcessor) {
        this.nextLinkProcessor = nextLinkProcessor;
    }

    public LinkParserReplies process(String url) {
        if (nextLinkProcessor != null) {
            return nextLinkProcessor.process(url);
        }
        return null;
    }

}
