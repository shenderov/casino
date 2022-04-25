import {httpGetCb, httpPost} from "../connector.js";
import {errorHttp, errorMessage} from "../error.js";
import {getSession} from "../sms.js";

export default class SlotsController {
    constructor() {
        this.gameId = 'slots';
        this.betTypes = ['X1', 'X2', 'X3'];
        this.reel = this.getReel();
        this.reelInitialPosition = -1310;
        this.reelEndPosition = -1396;
        this.numberOfSpins = 0;
        this.autoplayEnabled = false;
        this.getGameInfo();
        this.setCurrentBetType(0);
        this.setInitialReelsPosition();
        this.listenSpinButton();
        this.listenBetMaxButton();
        this.listenBetPlusButton();
        this.listenBetMinusButton();
        this.listenSpinTenButton();
        this.listenSpinFiveButton();
        this.listenAutoPlayButton();
    }

    setCurrentBetType(index) {
        this.currentBetType = this.betTypes[index];
        document.getElementById('slots-bet-type').innerText = this.currentBetType;
    }

    setCurrentNumberOfSpins(numberOfSpins) {
        this.numberOfSpins = numberOfSpins;
        document.getElementById('slots-spins').innerText = this.numberOfSpins;
    }

    setInitialReelsPosition() {
        document.getElementById('slots-reel-drum-one').style.transform = `translateY(${this.reelInitialPosition}px)`;
        document.getElementById('slots-reel-drum-two').style.transform = `translateY(${this.reelInitialPosition}px)`;
        document.getElementById('slots-reel-drum-three').style.transform = `translateY(${this.reelInitialPosition}px)`;
    }

    getGameAction() {
        let request = {
            "gameId": this.gameId,
            "parameters": {
                "betType": this.currentBetType
            }
        };
        httpPost('/game/gameAction', request, errorHttp, (data) => {
            const gameAction = JSON.parse(data.body);
            if (gameAction.transaction.status !== 'PROCESSED') {
                return errorMessage({
                    title: 'Transaction Error: ' + gameAction.transaction.status,
                    message: 'Transaction is not processed'
                })
            }
            this.spinReels(gameAction.gameAction.reels.reelPosition, () => {
                document.getElementById('slots-win').innerText = gameAction.transaction.win;
                getSession();
            });
            document.getElementById('slots-bet').innerText = gameAction.transaction.bet;
            document.getElementById('slots-bet-type').innerText = gameAction.gameAction.betType;
        });
    }

    getGameInfo() {
        httpGetCb('/game/game?game_id=' + this.gameId, errorHttp, function (data) {
            const game = JSON.parse(data.body);
            document.getElementById('slots-name').innerText = game.name;
        });
    }

    getReel() {
        return {
            0: {
                rangeStart: 0,
                rangeEnd: -30
            },
            1: {
                rangeStart: -60,
                rangeEnd: -155
            },
            2: {
                rangeStart: -185,
                rangeEnd: -220
            },
            3: {
                rangeStart: -250,
                rangeEnd: -345
            },
            4: {
                rangeStart: -375,
                rangeEnd: -415
            },
            5: {
                rangeStart: -440,
                rangeEnd: -535
            },
            6: {
                rangeStart: -565,
                rangeEnd: -635
            },
            7: {
                rangeStart: -660,
                rangeEnd: -700
            },
            8: {
                rangeStart: -720,
                rangeEnd: -800
            },
            9: {
                rangeStart: -825,
                rangeEnd: -915
            },
            10: {
                rangeStart: -940,
                rangeEnd: -1000
            },
            11: {
                rangeStart: -1025,
                rangeEnd: -1165
            },
            12: {
                rangeStart: -1190,
                rangeEnd: -1230
            },
            13: {
                rangeStart: -1255,
                rangeEnd: -1365
            },
        }
    }

    autoplay() {
        if (!this.autoplayEnabled) {
            this.autoplayStart();
        } else {
            this.autoplayStop();
        }
    }

    autoplayStart() {
        this.autoplayEnabled = true;
        this.getGameAction();
        this.autoplayInterval = setInterval(() => {
            this.getGameAction();
        }, 5000);
    }

    autoplayStop() {
        this.autoplayEnabled = false;
        clearInterval(this.autoplayInterval);
    }

    spinNumberOfTimes() {
        let pause = 0;
        for (let i = this.numberOfSpins; i > 0; i--) {
            setTimeout(() => {
                this.getGameAction();
                this.setCurrentNumberOfSpins(i - 1);
            }, pause);
            pause += 5000;
        }
    }

    spinReels(position, callback) {
        const position1 = this.getRandomNumberInRange(this.reel[position[0]].rangeStart, this.reel[position[0]].rangeEnd);
        const position2 = this.getRandomNumberInRange(this.reel[position[1]].rangeStart, this.reel[position[1]].rangeEnd);
        const position3 = this.getRandomNumberInRange(this.reel[position[2]].rangeStart, this.reel[position[2]].rangeEnd);
        this.animateReel('slots-reel-drum-one', 3, position1);
        this.animateReel('slots-reel-drum-two', 4, position2);
        this.animateReel('slots-reel-drum-three', 5, position3, callback);
    }

    animateReel(reelDrumId, iterations, finalPosition, callback) {
        const animateSpin = document.getElementById(reelDrumId).animate(
            [
                {transform: `translateY(0)`},
                {transform: `translateY(${this.reelEndPosition}px)`}
            ],
            {
                duration: 800,
                iterations: iterations
            }
        );
        animateSpin.finished.then(() => {
            document.getElementById(reelDrumId).style.transform = `translateY(${finalPosition}px)`
            if (callback) {
                callback();
            }
        });
    }

    getRandomNumberInRange(start, end) {
        return Math.floor(Math.random() * (end - start) + start);
    }

    listenSpinButton() {
        document.getElementById("slots-btn-spin").addEventListener("click", () => {
            this.autoplayStop();
            this.getGameAction();
        });
    }

    listenBetMaxButton() {
        document.getElementById("slots-btn-bet-max").addEventListener("click", () => {
            this.autoplayStop();
            this.setCurrentBetType(this.betTypes.length - 1);
            this.getGameAction();
        });
    }

    listenBetPlusButton() {
        document.getElementById("slots-btn-bet-plus").addEventListener("click", () => {
            if (this.betTypes.indexOf(this.currentBetType) < this.betTypes.length - 1) {
                this.setCurrentBetType(this.betTypes.indexOf(this.currentBetType) + 1);
            }
        });
    }

    listenBetMinusButton() {
        document.getElementById("slots-btn-bet-minus").addEventListener("click", () => {
            if (this.betTypes.indexOf(this.currentBetType) > 0) {
                this.setCurrentBetType(this.betTypes.indexOf(this.currentBetType) - 1);
            }
        });
    }

    listenSpinTenButton() {
        document.getElementById("slots-btn-spin-ten").addEventListener("click", () => {
            this.autoplayStop();
            this.setCurrentNumberOfSpins(10);
            this.spinNumberOfTimes();
        });
    }

    listenSpinFiveButton() {
        document.getElementById("slots-btn-spin-five").addEventListener("click", () => {
            this.autoplayStop();
            this.setCurrentNumberOfSpins(5);
            this.spinNumberOfTimes();
        });
    }

    listenAutoPlayButton() {
        document.getElementById("slots-btn-autoplay").addEventListener("click", () => {
            this.autoplay();
        });
    }
}