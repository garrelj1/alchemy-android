package com.garrell.co.alchemytcg.card.view;

import android.content.Context;
import android.widget.Space;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.garrell.co.alchemytcg.card.DescribableCard;

public class CardHandLayoutManager {

    private static class CardViewNode {
        private DragableCardView cardView;
        private ConstraintLayout.LayoutParams layoutParams;
        private CardViewNode previous;
        private CardViewNode next;

        private CardViewNode(Context context, String cardDescription) {
            this.cardView = new DragableCardView(context);
            cardView.setText(cardDescription);

            this.layoutParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );

            cardView.setLayoutParams(layoutParams);
        }

        int getViewId() {
            return cardView.getId();
        }

        void setNext(CardViewNode next) {
            this.next = next;
            layoutParams.endToStart = next.getViewId();
        }

        void setPrevious(CardViewNode previous) {
            this.previous = previous;
            layoutParams.startToEnd = previous.getViewId();
        }

        public void setBaseline(Space cardBaseline) {
            layoutParams.bottomToTop = cardBaseline.getTop();
        }
    }

    private final Context context;
    private final ConstraintLayout constraintLayout;
    private final Space cardBaseline;

    private CardViewNode head;
    private CardViewNode tail;

    public CardHandLayoutManager(Context context,
                                 ConstraintLayout constraintLayout,
                                 Space cardBaseline) {
        this.context = context;
        this.constraintLayout = constraintLayout;
        this.cardBaseline = cardBaseline;
    }

    public DragableCardView add(DescribableCard card) {
        CardViewNode newNode = new CardViewNode(context, card.describe());
        newNode.setBaseline(cardBaseline);

        if (head == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
        }

        tail = newNode;

        // add to constraintLayout
        constraintLayout.addView(newNode.cardView);

        return newNode.cardView;
    }

    public void remove(DragableCardView view) {
    }

}
