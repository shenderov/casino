import AbstractView from "./abstract-view.js";

export default class extends AbstractView {
    constructor(params) {
        super(params);
        this.setTitle("Slots Game");
    }

    async getHtml() {
        return `
            <div class="w3-container w3-section">
                <div class="slots-container">
                <h1 id="slots-name"></h1>
              <div class="slots-reels-window">
                    <div id="slots-reel-one" class="slots-reel">
                        <div id="slots-reel-drum-one" class="slots-reel-drum">
                            <img class="slots-reel-symbol" src="../../img/WILD.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/RED7.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/WHITE7.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/BLUE7.png" alt="">           
                            <img class="slots-reel-symbol" src="../../img/X1_BAR_RED.png" alt="">                 
                            <img class="slots-reel-symbol" src="../../img/X2_BAR_WHITE.png" alt="">      
                            <img class="slots-reel-symbol" src="../../img/BLANK-50.png" alt="">     
                            <img class="slots-reel-symbol" src="../../img/X3_BAR_BLUE.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/BLANK-50.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/WILD.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/RED7.png" alt="">
                        </div>
                        <div class="slots-reel-glass"></div>
                        <hr class="slots-payline">
                    </div>
                    <div id="slots-reel-two" class="slots-reel">
                        <div id="slots-reel-drum-two" class="slots-reel-drum">
                            <img class="slots-reel-symbol" src="../../img/WILD.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/RED7.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/WHITE7.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/BLUE7.png" alt="">           
                            <img class="slots-reel-symbol" src="../../img/X1_BAR_RED.png" alt="">                 
                            <img class="slots-reel-symbol" src="../../img/X2_BAR_WHITE.png" alt="">      
                            <img class="slots-reel-symbol" src="../../img/BLANK-50.png" alt="">     
                            <img class="slots-reel-symbol" src="../../img/X3_BAR_BLUE.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/BLANK-50.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/WILD.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/RED7.png" alt="">
                        </div>
                        <div class="slots-reel-glass"></div>
                        <hr class="slots-payline">
                    </div>
                    <div id="slots-reel-three" class="slots-reel">
                        <div id="slots-reel-drum-three" class="slots-reel-drum">
                            <img class="slots-reel-symbol" src="../../img/WILD.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/RED7.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/WHITE7.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/BLUE7.png" alt="">           
                            <img class="slots-reel-symbol" src="../../img/X1_BAR_RED.png" alt="">                 
                            <img class="slots-reel-symbol" src="../../img/X2_BAR_WHITE.png" alt="">      
                            <img class="slots-reel-symbol" src="../../img/BLANK-50.png" alt="">     
                            <img class="slots-reel-symbol" src="../../img/X3_BAR_BLUE.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/BLANK-50.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/WILD.png" alt="">
                            <img class="slots-reel-symbol" src="../../img/RED7.png" alt="">
                        </div>
                        <div class="slots-reel-glass"></div>
                        <hr class="slots-payline">
                    </div>
                  </div>
                  <div class="slots-control-container w3-container w3-bar">
               <table class="w3-table w3-bordered w3-centered">
                <tr>
                  <th>SPINS</th>
                  <th>BET TYPE</th>
                  <th>COINS PLAYED</th>
                  <th>WIN</th>
                </tr>
                <tr>
                  <td id="slots-spins" class="slots-info-panel">-</td>
                  <td id="slots-bet-type" class="slots-info-panel"></td>
                  <td id="slots-bet" class="slots-info-panel">-</td>
                  <td id="slots-win" class="slots-info-panel">-</td>
                </tr>
                <tr>
                  <td>
                  <div class="slots-autoplay-buttons-group">
                      <div class="slots-control-buttons-group">
                            <button id="slots-btn-spin-ten" class="slots-small-rect-button w3-button w3-border w3-border-white">SPIN 10X</button>
                            <button id="slots-btn-spin-five" class="slots-small-rect-button w3-button w3-border w3-border-white">SPIN 5X</button>
                      </div>
                      <div>
                        <button id="slots-btn-autoplay" class="slots-big-square-button w3-button w3-border w3-border-white">AUTOPLAY</button>
                      </div>
                  </div>
                  </td>
                  <td>                  
                      <div class="slots-control-buttons-group">
                            <button id="slots-btn-bet-plus" class="slots-small-rect-button w3-button w3-border w3-border-white">BET +</button>
                            <button id="slots-btn-bet-minus" class="slots-small-rect-button w3-button w3-border w3-border-white">BET -</button>
                      </div>
                  </td>
                  <td id="slots-btn-bet-max"><button class="slots-big-square-button w3-button w3-border w3-border-white">BET MAX</button></td>
                  <td><button id="slots-btn-spin" class="slots-big-square-button w3-button w3-border w3-border-white">SPIN REELS</button></td>
                </tr>
            </table>
            </div>
              </div>
            </div>            
        `;
    }
}