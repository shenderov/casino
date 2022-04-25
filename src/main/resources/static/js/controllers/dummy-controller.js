import {httpGetCb, httpPost} from "../connector.js";
import {errorHttp, errorMessage} from "../error.js";
import {getSession} from "../sms.js";

export default class DummyController {
    constructor() {
        this.gameId = 'dummy';
        this.listenDefaultAction();
        this.listenCustomAction();
        this.getGameInfo(this.gameId);
    }

    listenDefaultAction() {
        document.getElementById("dummy-btn-default-action").addEventListener("click", () => {
            this.getGameAction()
        });
    }

    listenCustomAction() {
        document.getElementById("dummy-btn-custom-action").addEventListener("click", () => {
            const parameters = {
                "bet": document.getElementById('dummy-input-bet').value,
                "win": document.getElementById('dummy-input-win').value
            };
            this.getGameAction(parameters)
        });
    }

    getGameAction(parameters) {
        const defaultRequest = {
            "gameId": this.gameId
        };
        if (parameters) {
            defaultRequest.parameters = parameters;
        }
        httpPost('/game/gameAction', defaultRequest, errorHttp, function (data) {
            const gameAction = JSON.parse(data.body);
            if (gameAction.transaction.status !== 'PROCESSED') {
                return errorMessage({
                    title: 'Transaction Error: ' + gameAction.transaction.status,
                    message: 'Transaction is not processed'
                })
            }
            document.getElementById('dummy-bet').innerText = gameAction.transaction.bet;
            document.getElementById('dummy-info-bet').innerText = gameAction.transaction.bet;
            document.getElementById('dummy-win').innerText = gameAction.transaction.win;
            document.getElementById('dummy-info-win').innerText = gameAction.transaction.win;
            getSession();
        });
    }

    getGameInfo(gameId) {
        httpGetCb('/game/game?game_id=' + gameId, errorHttp, function (data) {
            const game = JSON.parse(data.body);
            document.getElementById('dummy-name').innerText = game.name;
            document.getElementById('dummy-description').innerText = game.description;
            document.getElementById('dummy-base-bet').innerText = game.baseBetPrice;
        });
    }
}