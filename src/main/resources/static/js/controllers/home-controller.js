import {httpGetCb} from "../connector.js";
import {errorHttp} from "../error.js";

export default class HomeController {
    constructor() {
        this.getGameList();
    }

    getGameList() {
        httpGetCb('/game/games', errorHttp, function (data) {
            const games = JSON.parse(data.body);
            let items = `<tr>
                              <th class="w3-center">ID</th>
                              <th class="w3-center" style="width: 135px;">Name</th>
                              <th class="w3-center">Description</th>
                              <th class="w3-center" style="width: 90px;">Base Bet</th>
                              <th class="w3-center" style="width: 85px;">Enabled</th>
                              <th></th>
                            </tr>`;
            games.forEach((game) => {
                items +=
                    `<tr>
                          <td>${game.id}</td>
                          <td>${game.name}</td>
                          <td>${game.description}</td>
                          <td class="w3-center">${game.baseBetPrice}</td>
                          <td class="w3-center"><input class="w3-check" type="checkbox" disabled ${game.enabled ? 'checked' : ''}></td>
                          <td class="w3-center"><a href="/${game.id}" data-link class="w3-button w3-red w3-small">Play!</a></td>
                        </tr>`
            });
            document.getElementById('game-list').innerHTML = items;
        });
    }
}