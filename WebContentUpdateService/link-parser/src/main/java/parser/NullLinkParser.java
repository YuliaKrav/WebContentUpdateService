package parser;

import parser.replies.LinkParserReplies;

public final class NullLinkParser extends LinkProcessor implements LinkParser {
    public NullLinkParser(LinkProcessor nextLinkProcessor) {

        super(nextLinkProcessor);
    }

    @Override
    public LinkParserReplies processParsing(String url) {
        System.out.println(url + " Nothing");
        return null;
    }
}
