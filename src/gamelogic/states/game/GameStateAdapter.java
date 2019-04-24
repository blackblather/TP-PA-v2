package gamelogic.states.game;

import gamelogic.data.GameDataHandler;

public abstract class GameStateAdapter implements IGameState {
    GameDataHandler gameDataHandler;
    GameStateAdapter(GameDataHandler gameDataHandler) {
        this.gameDataHandler = gameDataHandler;
    }

    //Estes métodos estão aqui para caso se use um objecto de uma classe filha para chamar um método que não se deva, o estado nao mudar
    @Override
    public IGameState _JourneyPhase() {
        return this;
    }

    @Override
    public IGameState _ScanningPhase() {
        return this;
    }

    @Override
    public IGameState _SpawnAliensPhase() {
        return this;
    }

    @Override
    public IGameState _RestPhase() {
        return this;
    }

    @Override
    public IGameState _RestPhase(int opt) {
        return this;
    }

    @Override
    public IGameState _RestPhase(int opt, int[] additionalInputs) {
        return this;
    }

    @Override
    public IGameState _CrewPhase() {
        return this;
    }

    @Override
    public IGameState _CrewPhase(int opt) {
        return this;
    }

    @Override
    public IGameState _CrewPhase(int opt, int[] additionalInputs) {
        return this;
    }

    @Override
    public IGameState _AlienPhase() {
        return this;
    }

    @Override
    public IGameState _GameOver() {
        return this;
    }

    @Override
    public IGameState _Win() {
        return this;
    }
}
