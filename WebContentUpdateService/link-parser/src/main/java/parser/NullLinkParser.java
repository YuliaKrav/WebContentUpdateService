package parser;

import parser.replies.LinkParserReplies;
import parser.replies.NullReply;

public final class NullLinkParser extends LinkProcessor implements LinkParser {
    public NullLinkParser(LinkProcessor nextLinkProcessor) {

        super(nextLinkProcessor);
    }

    public LinkParserReplies process(String url) {
        System.out.println(url + " Nothing");
        return new NullReply(url);
    }
}
