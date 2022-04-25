package me.shenderov.casino.entities;

import java.util.Map;

public class GetGameActionWrapper {
    private String gameId;
    private Map<String, Object> parameters;

    public GetGameActionWrapper() {
    }

    public GetGameActionWrapper(String gameId, Map<String, Object> parameters) {
        this.gameId = gameId;
        this.parameters = parameters;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "GetGameActionWrapper{" +
                "gameId='" + gameId + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
