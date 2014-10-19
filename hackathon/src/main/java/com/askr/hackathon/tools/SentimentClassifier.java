package com.askr.hackathon.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Tom on 19/10/2014.
 */

public class SentimentClassifier {

    protected HashSet negativeWords;
    protected HashSet happyWords;

    protected HashSet<String> buildWordSet( InputStream csvFile ){
        HashSet<String> wordSet = new HashSet<String>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new InputStreamReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                for( String word : line.split(cvsSplitBy) ) {
                    wordSet.add(word);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return wordSet;
    }

    public SentimentClassifier() {
        this.negativeWords = buildWordSet(SentimentClassifier.class.getResourceAsStream("negativewords.csv"));
        this.happyWords = buildWordSet(SentimentClassifier.class.getResourceAsStream("positivewords.csv"));
    }

    public QuestionCategory getQuestionCategory( String messageText ){
        ArrayList<QuestionCategory> categories = new ArrayList<QuestionCategory>();
        QuestionCategory questionCategory;
        if (messageText.toLowerCase().contains( "tv" )) {
            categories.add( QuestionCategory.TV );
        }
        if (messageText.toLowerCase().contains( "broadband" )) {
            categories.add( QuestionCategory.BROADBAND );
        }
        if (messageText.toLowerCase().contains( "phone" )) {
            categories.add( QuestionCategory.PHONE );
        }
        if ( categories.size() > 1 ){
            questionCategory = QuestionCategory.MULTI;
        }
        else if ( categories.size() == 0 ){
            questionCategory = QuestionCategory.OTHER;
        }
        else{
            questionCategory = categories.get( 0 );
        }

        return questionCategory;
    }

    private boolean isHappy( String messageText ) {
        for (String word : messageText.split( " " )){
            if ( this.happyWords.contains( word.toLowerCase() ) ){
                return true;
            }
        }
        return false;
    }

    private boolean isAngry( String messageText ) {
        for (String word : messageText.split( " " )){
            if ( this.negativeWords.contains( word.toLowerCase() ) ){
                return true;
            }
        }
        return false;
    }

    public QuestionSentiment getQuestionSentiment( String messageText ){
        QuestionSentiment questionSentiment;
        ArrayList<QuestionSentiment> questionSentiments = new ArrayList<QuestionSentiment>();
        if ( isAngry( messageText ) ){
            questionSentiments.add( QuestionSentiment.ANGRY );
        }
        if ( isHappy( messageText ) ){
            questionSentiments.add( QuestionSentiment.HAPPY );
        }
        if ( questionSentiments.size() != 1  ){
            questionSentiment = QuestionSentiment.UNCATEGORISED;
        }
        else{
            questionSentiment = questionSentiments.get( 0 );
        }
        return questionSentiment;
    }

}
