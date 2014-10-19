package com.askr.hackathon.tools;

import java.util.ArrayList;

/**
 * Created by Tom on 19/10/2014.
 */

public class SentimentClassifier {


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
        return false;
    }
    private boolean isImpatient( String messageText ) {
        return false;
    }
    private boolean isAngry( String messageText ) {
        return false;
    }

    public QuestionSentiment getQuestionSentiment( String messageText ){
        QuestionSentiment questionSentiment;
        ArrayList<QuestionSentiment> questionSentiments = new ArrayList<QuestionSentiment>();
        if ( isAngry( messageText ) ){
            questionSentiments.add( QuestionSentiment.ANGRY );
        }
        if ( isImpatient( messageText ) ){
            questionSentiments.add( QuestionSentiment.IMPATIENT );
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
