import AbstractView from "./abstract-view.js";

export default class Dummy extends AbstractView {
    constructor(params) {
        super(params);
        this.setTitle("Dummy Game");
    }

    async getHtml() {
        return `
        <div class="w3-container w3-section">
            <div class="dummy-container">
                <div class="w3-container">
                    <h1 id="dummy-name"></h1>
                    <h3 id="dummy-description"></h3>
                </div>
                
                <div class="w3-container w3-row-padding">
                  <div class="w3-panel w3-card w3-red w3-third w3-center">
                    <h1>Bet:</h1>
                    <h1><strong id="dummy-bet">-</strong></h1>
                  </div>
                  <div class="w3-panel w3-third"></div>
                  <div class="w3-panel w3-card w3-green w3-third w3-center">
                    <h1>Win:</h1>
                    <h1><strong id="dummy-win">-</strong></h1>
                  </div>
                </div>
                
                <div class="dummy-control-container w3-container w3-bar">
                <table class="w3-table w3-bordered w3-centered">
                <tr>
                  <th>BASE BET</th>
                  <th>COINS PLAYED</th>
                  <th>WIN</th>
                </tr>
                <tr>
                  <td id="dummy-base-bet" class="dummy-info-panel">-</td>
                  <td id="dummy-info-bet" class="dummy-info-panel">-</td>
                  <td id="dummy-info-win" class="dummy-info-panel">-</td>
                </tr>
                <tr>
                  <td>
                      <div class="dummy-element-group">
                          <div class="dummy-input-group">
                            <input id="dummy-input-bet" placeholder="Set Bet" class="dummy-input w3-input w3-border-0" type="text">
                            <input id="dummy-input-win" placeholder="Set Win" class="dummy-input w3-input w3-border-0" type="text">
                          </div>
                          <div>
                            <button id="dummy-btn-custom-action" class="dummy-big-button w3-button w3-border w3-border-white">RUN CUSTOM ACTION</button>
                          </div>
                      </div>
                  </td>
                  <td></td>
                  <td><button id="dummy-btn-default-action" class="dummy-big-button w3-button w3-border w3-border-white">RUN DEFAULT ACTION</button></td>
                </tr>
            </table>
            </div>
            </div>
        </div>
        `;
    }
}