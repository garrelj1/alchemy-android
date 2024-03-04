package com.garrell.co.alchemytcg.card;

public class ActionCard implements Describable {
    private final TargetPlayer player;
    private final TargetAction action;



    public enum TargetPlayer {
        OPPONENT,
        SELF
    }

    public enum TargetAction {
        HEALTH,
        ATTACK
    }

    public ActionCard(TargetPlayer player, TargetAction action) {
        this.player = player;
        this.action = action;
    }

    @Override
    public String describe() {
        return player.toString() + " " + action.toString();
    }

    public TargetPlayer getPlayer() {
        return player;
    }

    public TargetAction getAction() {
        return action;
    }

}
