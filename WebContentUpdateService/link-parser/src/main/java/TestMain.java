import parser.LinkProcessingChain;

public class TestMain {
    public static void main(String[] args) {
        String[] testUrlArray = {"https://github.com/sanyarnd/tinkoff-java-course-2022/",
                "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
                "https://stackoverflow.com/search?q=unsupported%20link",
                "test.org",
                "https://github.com/masha/"};


        LinkProcessingChain chainTest = new LinkProcessingChain();
        for (String url : testUrlArray) {
            chainTest.process(url);
        }
    }
}
