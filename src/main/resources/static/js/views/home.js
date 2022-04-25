import AbstractView from "./abstract-view.js";

export default class extends AbstractView {
    constructor(params) {
        super(params);
        this.setTitle("Home");
    }

    async getHtml() {
        return `
    <div class="w3-container w3-section">
        <div class="home-container">
            <div class="w3-container">
                <h1>Game List</h1>
            </div>
            <div class="w3-container">
                <table id="game-list" class="w3-table w3-bordered"></table>
            </div>
        </div>
    </div>
        `;
    }
}

