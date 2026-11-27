package com.api.springaidecisionmodel.service;


import com.api.springaidecisionmodel.payload.Moderation;
import org.springframework.ai.decision.DecisionModel;
import org.springframework.ai.decision.DecisionResponse;
import org.springframework.ai.decision.question.ChoiceQuestion;
import org.springframework.ai.decision.question.ScoreQuestion;
import org.springframework.ai.decision.question.YesNoQuestion;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class CommentService {


    private  static final YesNoQuestion SPAM = YesNoQuestion.of("Is the comment spam or self-promotional?");
    private  static final ChoiceQuestion TOPIC = ChoiceQuestion.of("What is the topic of the comment?", "Question", "Feedback", "off-topic");
    private  static final ScoreQuestion TONE = ScoreQuestion.of("How respectful is this comment?", "Rude", "Neutral", "Kind");


    private final DecisionModel decisionModel;

    public CommentService(DecisionModel decisionModel) {
        this.decisionModel = decisionModel;
    }

    public Moderation getCommentDetails(String comment) {
        DecisionResponse response = decisionModel.call(comment, Map.of(
                "spam", SPAM,
                "topic", TOPIC,
                "tone", TONE
        ));

        boolean spam = response.getYesNo("spam").isYes();
        String topic = response.getChoice("topic").getValue();
        String tone = TONE.getLevelLabels().get(response.getScore("tone").getNearestLevel());
        return new Moderation(spam, topic, tone);

    }
}
