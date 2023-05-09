package parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.replies.LinkParserReplies;
import parser.replies.NullReply;

public final class NullLinkParser extends LinkProcessor implements LinkParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(NullLinkParser.class);

    public NullLinkParser(LinkProcessor nextLinkProcessor) {

        super(nextLinkProcessor);
    }

    public LinkParserReplies process(String url) {
        LOGGER.info(url + " Nothing");
        return new NullReply(url);
    }
}
