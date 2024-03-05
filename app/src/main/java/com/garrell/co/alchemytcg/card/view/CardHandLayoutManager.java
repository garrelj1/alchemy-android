package com.garrell.co.alchemytcg.card.view;

import android.content.Context;
import android.widget.Space;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.garrell.co.alchemytcg.card.DescribableCard;
import com.garrell.co.baseapp.R;
import com.garrell.co.baseapp.screens.common.views.ViewToolkit;
import com.garrell.co.baseapp.screens.common.views.res.ResourcesFacade;

public class CardHandLayoutManager {

    private interface ViewNode {
        int getViewId();
        void setNext(ViewNode node);
        ViewNode getNext();
        void setPrevious(ViewNode node);
        ViewNode getPrevious();
    }

    private static abstract class BaseViewNode {
        private ViewNode previous;
        private ViewNode next;

        public void setNext(ViewNode next) {
            this.next = next;
        }

        public ViewNode getNext() {
            return next;
        }

        public void setPrevious(ViewNode previous) {
            this.previous = previous;
        }

        public ViewNode getPrevious() {
            return previous;
        }
    }

    private static class ParentViewNode extends BaseViewNode implements ViewNode {
        @Override
        public int getViewId() {
            return ConstraintLayout.LayoutParams.PARENT_ID;
        }
    }

    private static class CardViewNode extends BaseViewNode implements ViewNode {
        private final DragableCardView cardView;
        private final ConstraintLayout.LayoutParams layoutParams;

        private CardViewNode(DragableCardView cardView) {
            this.cardView = cardView;
            this.layoutParams = (ConstraintLayout.LayoutParams) cardView.getLayoutParams();
            this.setPrevious(new ParentViewNode());
            this.setNext(new ParentViewNode());
        }

        @Override
        public int getViewId() {
            return cardView.getId();
        }

        @Override
        public void setNext(ViewNode next) {
            super.setNext(next);
            layoutParams.endToStart = next.getViewId();
        }

        @Override
        public void setPrevious(ViewNode previous) {
            super.setPrevious(previous);
            layoutParams.startToEnd = previous.getViewId();
        }

    }

    private final ResourcesFacade resourcesFacade;
    private final ViewToolkit viewToolkit;
    private final Context context;
    private final ConstraintLayout constraintLayout;
    private final Space cardBaseline;

    private ViewNode head;
    private ViewNode tail;

    public CardHandLayoutManager(Context context,
                                 ConstraintLayout constraintLayout,
                                 Space cardBaseline) {
        this.context = context;
        this.resourcesFacade = new ResourcesFacade(context);
        this.viewToolkit = new ViewToolkit(context);
        this.constraintLayout = constraintLayout;
        this.cardBaseline = cardBaseline;
    }

    public DragableCardView add(DescribableCard card) {
        CardViewNode newNode = new CardViewNode(createDefaultCardView(card));

        if (head == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
        }

        tail = newNode;

        // add to constraintLayout
        constraintLayout.addView(newNode.cardView);
        constraintLayout.requestLayout();

        return newNode.cardView;
    }

    public void remove(PlayableCardView playableCard) {
        boolean removed = removeCardInternal(playableCard);
        if (removed) {
            constraintLayout.removeView(playableCard.getRootView());
            constraintLayout.requestLayout();
        }
    }

    private boolean removeCardInternal(PlayableCardView view) {
        ViewNode current = head;

        while (current != null) {
            if (current.getViewId() == view.getViewId()) {
                if (current == head) {
                    head = head.getNext();
                    if (head != null) {
                        head.setPrevious(new ParentViewNode());
                    }
                }
                else if (current == tail) {
                    tail = tail.getPrevious();
                    tail.setNext(new ParentViewNode());
                }
                else {
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                }
                // view removed
                return true;
            }
            current = current.getNext();
        }

        // view was not found
        return false;
    }

    private DragableCardView createDefaultCardView(DescribableCard card) {
        DragableCardView cardView = new DragableCardView(context);
        cardView.setText(card.describe());

        // set width and height
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
            viewToolkit.dpToPx(resourcesFacade.getResource(R.dimen.card_width)),
            viewToolkit.dpToPx(resourcesFacade.getResource(R.dimen.card_height))
        );

        // initial layout position
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToTop = cardBaseline.getId();

        cardView.setLayoutParams(layoutParams);

        return cardView;
    }

}
