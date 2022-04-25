# Casino

This project is a hobby/study project written in Java, Spring Boot for backend and Javascript for test front end. 
The project provides back end APIs for casino platform and a two demo games with UI.

Features Overview:
* REST API for creation and management of sessions
* REST API for games
* Simple session authorization
* Payback percentage can be set as an application parameter to define bet/win ratio
* Transactions can be processed to make sure total wins are not excess total casino balance
* A test game is available and it covers all APIs

## APIs

| API                  | Parameters                           | Description                                             |
|----------------------|--------------------------------------|---------------------------------------------------------|
| GET sms/getSession   | cookie: sId                          | Create a new session(if sId empty) or renew an existing |
| GET sms/setName      | cookie: sId, query: name             | Change session name                                     |
| GET sms/resetBalance | cookie: sId                          | Reset session balance to the default value              |
| POST game/gameAction | cookie: sId, body: gameActionRequest | Returns game-specific action                            |
| GET game/games       |                                      | Get list of available games                             |
| GET game/game        | query: game_id                       | Get game details                                        |

Check a Postman collection for full list of APIs and usage examples:
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/20041876-4f698a8f-966c-4646-9248-bbdb80e2d0fb?action=collection%2Ffork&collection-url=entityId%3D20041876-4f698a8f-966c-4646-9248-bbdb80e2d0fb%26entityType%3Dcollection%26workspaceId%3Dc8c3f32a-3baa-4233-a4ce-f351287e455a)

## Test Web UI
Available at `http://localhost:8080/` by default.
Source code can be found in `resources/static` directory

### Casino as the platform
Casino provides platform that handles user sessions, casino balance, transactions, and games management.
A new game can be easily added by implementing `me.shenderov.casino.interfaces.IGame` interface:
```java
   public class DemoGameController implements IGame {
    public static final double BASE_BET = 1;

    @Override
    public GameAction getGameAction(Transaction transaction, Map<String, Object> parameters) {
        //Get random win
        double win = roulette();
        //This can be any object with any data required by the game's UI
        String action = win > 0 ? "WIN!!!" : "TRY AGAIN!";
        //Platform will verify that user has enough credits to play the game and charge that amount from user's balance
        transaction.setBet(BASE_BET);
        //Platform will verify that Casino's balance is enough to pay that win
        transaction.setWin(win);
        return new GameAction(action, transaction);
    }

    @Override
    public double getBaseBet() {
        return BASE_BET;
    }

    private double roulette() {
        int roulettePosition = getRandomNumber();
        double win;
        switch (roulettePosition) {
            case 3:
                win = 2.0;
                break;
            case 7:
                win = 5.0;
                break;
            default:
                win = 0.0;
                break;
        }
        return win;
    }

    private int getRandomNumber(){
        return (int) ((Math.random() * (10 - 1)) + 1);
    }
  }
```
In order to make the game available in APIs, it should be registered as a Spring Bean in me.shenderov.casino.config.ApplicationConfig:
```java
    @Bean(name = "demo")
    public IGame getDemoGame() {
        return new DemoGameController();
    }
```
The bean name will be used as the game ID.
The game is registered now, but by default it is disabled. In order to enable it, a `GameInfo` object should be saved in `GameRepository`:
```java
GameInfo demoInfo = new GameInfo("demo", "Demo Game Name", "Demo Game Description", 1, true);
gameRepository.save(demoInfo);
```
Our game is registered and enabled now. We can verify it by calling `/game/game?game_id=demo`:
```json
{
    "id": "demo",
    "name": "Demo Game Name",
    "description": "Demo Game Description",
    "baseBetPrice": 1.0,
    "enabled": true
}
```
So, we can now create a new session(`/sms/getSession`) and call `/game/gameAction` providing `{ "gameId": "demo" }` as the request body:
```json
{
    "gameAction": "TRY AGAIN!",
    "transaction": {
        "currency": {
            "currencyCode": "CRD",
            "currencyName": "Credit",
            "virtual": true,
            "free": true
        },
        "bet": 1.0,
        "win": 0.0,
        "status": "PROCESSED"
    }
}
```

### Games
#### Dummy game
Created for debug/testing purposes. The game has default bet and win values, or can receive both bet and win as parameters. Returns a map with 3 hardcoded values.

#### Slots
Slots game generates reels, reels position and key for win validation on each game action request.

### Limitations
* There are no admin APIs to access/manage casino balance, transactions, sessions, or games

### Prerequisites

To run the project you need the following:
* Git client
* Java 8+
* Java IDE. IntelliJ IDEA is strongly recommended as the project wasn't tested with other IDEs. You can obtain a copy of IntelliJ IDEA Community Edition(free and open source) here: https://www.jetbrains.com/idea/download

## Authors

* **Konstantin Shenderov** - *Initial work*

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details