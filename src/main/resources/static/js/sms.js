import {httpGetCb} from "./connector.js";
import {errorHttp, errorMessage} from "./error.js"

export const getSession = () => {
    httpGetCb('/sms/getSession',
        function (data) {
            const status = data.status;
            const message = JSON.parse(data.body).message;
            if (status === 400 && message === 'Session not found') {
                getNewSession();
            } else {
                errorMessage({
                    title: 'Session Error',
                    message: 'Error getting a session'
                })
            }
        },
        function (data) {
            const session = JSON.parse(data.body);
            document.getElementById('nav-name').innerText = session.name;
            document.getElementById('nav-balance').innerText = session.balance;
            document.getElementById('nav-currency-code').innerText = session.currency.currencyCode;
            document.getElementById('nav-currency-code').title = session.currency.currencyName;
        });
}
getSession();

const getNewSession = () => {
    document.cookie = "sId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    getSession();
}

const openChangeNameInput = () => {
    document.getElementById("nav-name-edit-icon").addEventListener("click", function () {
        document.getElementById('nav-edit-name').style.display = 'block';
        document.getElementById('nav-session-info').style.display = 'none';
        document.getElementById('nav-edit-name-input').value = document.getElementById('nav-name').innerText;
    });
}
openChangeNameInput();

const closeChangeNameInput = () => {
    document.getElementById("nav-edit-name-close").addEventListener("click", function () {
        document.getElementById('nav-edit-name').style.display = 'none';
        document.getElementById('nav-session-info').style.display = 'block';
    });
}
closeChangeNameInput();

const changeName = () => {
    document.getElementById("nav-edit-name-submit").addEventListener("click", function () {
        httpGetCb('/sms/setName?name=' + document.getElementById('nav-edit-name-input').value, errorHttp, function () {
            document.getElementById('nav-edit-name').style.display = 'none';
            document.getElementById('nav-session-info').style.display = 'block';
            getSession();
        });
    });
}
changeName();

const openResetBalance = () => {
    document.getElementById("nav-balance-reset").addEventListener("click", function () {
        document.getElementById('nav-balance-reset-confirm').style.display = 'block';
        document.getElementById('nav-session-info').style.display = 'none';
    });
}
openResetBalance();

const closeResetBalance = () => {
    document.getElementById("nav-balance-reset-close").addEventListener("click", function () {
        document.getElementById('nav-balance-reset-confirm').style.display = 'none';
        document.getElementById('nav-session-info').style.display = 'block';
    });
}
closeResetBalance();

const resetBalance = () => {
    document.getElementById("nav-balance-reset-submit").addEventListener("click", function () {
        httpGetCb('/sms/resetBalance', errorHttp, function () {
            document.getElementById('nav-balance-reset-confirm').style.display = 'none';
            document.getElementById('nav-session-info').style.display = 'block';
            getSession();
        });
    });
}
resetBalance();