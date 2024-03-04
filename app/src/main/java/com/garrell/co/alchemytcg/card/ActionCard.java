package com.garrell.co.alchemytcg.card;

public class ActionCard extends Card implements DescribableCard {
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

    public ActionCard(int id, TargetPlayer player, TargetAction action) {
        super(id);
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
