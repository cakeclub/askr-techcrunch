package com.askr.hackathon.tools;

import junit.framework.TestCase;

public class TextReplyerTest extends TestCase {

    public void testSendMessage() throws Exception {

        TextReplyer.sendMessage("This is a reply for you", "447841342133");

    }
}